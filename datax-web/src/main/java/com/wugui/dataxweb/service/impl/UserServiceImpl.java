package com.wugui.dataxweb.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wugui.dataxweb.dao.UserMapper;
import com.wugui.dataxweb.entity.UserEntity;
import com.wugui.dataxweb.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.wugui.dataxweb.util.WorkBookUtil.getValue;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {

    private UserMapper userMapper;

    @Override
    public UserEntity getByUsername(String username) {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public UserEntity get(Long id) {
        return null;
    }

    @Override
    public JSONObject importUser(byte[] bytes, UserEntity enterpriseUser) {
        try {
            InputStream inputStream = new ByteArrayInputStream(bytes);
            Workbook workbook = WorkbookFactory.create(inputStream); // Excel2003/2007/2010都是可以处理
            int sheetCount = workbook.getNumberOfSheets(); // Sheet的数量
            JSONObject json = new JSONObject(); // 返回json对象
            JSONObject jsonError = null; // 返回Excel错误信息
            JSONArray array = new JSONArray();
            boolean isCondition; // 每一行是否满足条件
            Sheet sheet;
            Row row;
            Row rowTitle;
            String tempStr = "";
            boolean empty; // 判断该行是否为空
            List<UserEntity> userEntityList = new ArrayList<>();
            boolean isTempContent = false; //判断导入的是不是模板
            for (int i = 0; i < sheetCount; i++) {
                //获取sheet
                sheet = workbook.getSheetAt(i);
                jsonError = new JSONObject();
                //判断是否是Excel表头，正确返回true
                row = sheet.getRow(1);
                rowTitle = sheet.getRow(0);
                int rowCount = sheet.getPhysicalNumberOfRows();//获取总行数
                List<String> sexList = Arrays.asList("男", "女");
                //从第三行开始读取，模板中第一行默认为标题
                for (int j = 2; j < rowCount ; j++) {
                    //获得行对象
                    row = sheet.getRow(j);
                    empty = isOrNotEmpty(row);
                    if (empty){
                        continue;
                    }
                    //校验行数据，是否满足字段要求，满足返回true
                    jsonError = new JSONObject();
                    isCondition = checkRow(row, jsonError, sexList);
                    if (isCondition){
                        UserEntity userEntity = new UserEntity();
                        userEntity.setName(getValue(row.getCell(1)));
                        userEntity.setPassword("123456");
                        userEntity.setPhone(getValue(row.getCell(3)));
                        userEntity.setSex(getValue(row.getCell(2)));
                        userEntity.setUsername(getValue(row.getCell(1)));
                        userEntityList.add(userEntity);
                    }else{
                        //如果不满足，封装返回错误信息
                        array.add(jsonError);
                    }
                }
            }

            if (array.size() == 0 && !CollectionUtils.isEmpty(userEntityList)) {
                userEntityList.forEach(t-> userMapper.insert(t));
                json.put("code", 0);
                json.put("msg", "用户导入成功！");
            } else {
                json.put("code", 400);
                json.put("arrayError", array);
            }
            return json;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public PageInfo<UserEntity> getAll(String username, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        if (StringUtils.isNotBlank(username)) {
            QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username", username);
            return new PageInfo<>(userMapper.selectList(queryWrapper));
        }
        return new PageInfo<>(userMapper.selectList(null));
    }

    @Override
    public Boolean updatePassword(UserEntity userEntity, String newPassword) {
        UpdateWrapper<UserEntity> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", userEntity.getId());
        return userMapper.update(userEntity, wrapper) > 0;
    }

    @Override
    public Boolean updateUser(UserEntity userEntity) {
        UpdateWrapper<UserEntity> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", userEntity.getId());
        return userMapper.update(userEntity, wrapper) > 0;
    }

    @Override
    public Boolean deleteUpdate(Long id) {
        return userMapper.deleteById(id) > 0;
    }

    @Override
    public UserEntity add(UserEntity userEntity) {
        userMapper.insert(userEntity);
        return userEntity;
    }

    @Override
    public UserEntity getByUsernameAndPassword(String username, String password) {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        queryWrapper.eq("password", password);
        return userMapper.selectOne(queryWrapper);
    }

    private boolean checkRow(Row row, JSONObject jsonError, List<String> sexList) {
        StringBuffer sBuffer = new StringBuffer();
        boolean result = false;
        String temp = "第"+(row.getRowNum()+1)+"行：";
        sBuffer.append(temp);
        // 用户名
        Cell name = row.getCell(1);
        Cell sex = row.getCell(2);
        Cell phone = row.getCell(3);
        //判断凭证编号不能为空
        if (null == name || "".equals(getValue(name))){
            sBuffer.append("用户名为空，不能导入！");
        } else {
            int countByUsername = userMapper.countByUsername(getValue(name));
            if (countByUsername > 0) {
                sBuffer.append("用户名："+getValue(name)+"不能导入！");
            }
        }

        if (null == sex || "".equals(getValue(sex))) {
            sBuffer.append("性别为空，不能导入！");
        } else if (!sexList.contains(sex)) {
            sBuffer.append("性别不正确，不能导入！");
        }

        if (null == phone || "".equals(getValue(phone))) {
            sBuffer.append("手机号为空，不能导入！");
        } else {
            Pattern compile = Pattern.compile("^1\\d{10}$");
            Matcher matcher = compile.matcher(getValue(phone));
            if (!matcher.matches()) {
                sBuffer.append("手机号格式不正确，不能导入！");
            }
        }

        // 返回结果
        if (sBuffer.length() > temp.length()) {
            jsonError.put("msg", sBuffer);
        } else {
            result = true;
        }
        return result;
    }

    /**
     * 判断每一行是否是空数据
     * @param row 行数据
     * @return 返回是否是空数据
     */
    private boolean isOrNotEmpty(Row row) {
        boolean result = true;
        int cellCount = row.getPhysicalNumberOfCells(); // 获取总列数
        for (int i = 0; i < cellCount; i++) {
            if (null != getValue(row.getCell(i)) && !"".equals(getValue(row.getCell(i)))) {
                result = false;
                break;
            }
        }
        return result;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

}
