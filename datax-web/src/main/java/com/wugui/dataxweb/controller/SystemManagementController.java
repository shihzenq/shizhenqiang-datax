package com.wugui.dataxweb.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.wugui.dataxweb.dto.group.GroupDTO;
import com.wugui.dataxweb.dto.user.ModifyPasswordDTO;
import com.wugui.dataxweb.dto.user.UserSearchDTO;
import com.wugui.dataxweb.dto.user.UserUpdateDTO;
import com.wugui.dataxweb.entity.JobGroupEntity;
import com.wugui.dataxweb.entity.Role;
import com.wugui.dataxweb.entity.UserEntity;
import com.wugui.dataxweb.export.UserErrorExcel;
import com.wugui.dataxweb.service.JobGroupService;
import com.wugui.dataxweb.service.RoleService;
import com.wugui.dataxweb.service.UserService;
import com.wugui.dataxweb.validator.group.AddChecks;
import com.wugui.dataxweb.validator.group.UpdateChecks;
import com.wugui.dataxweb.vo.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequestMapping("/system-manage")
@Api("系统管理")
public class SystemManagementController extends BaseController {


    private JobGroupService JobGroupService;

    private RoleService roleService;

    private UserService userService;

    @ApiOperation(value = "作业组管理新增" , notes = "作业组管理新增")
    @PostMapping("/add-group")
    public ResponseData<?> addGroup(@RequestBody @Validated({AddChecks.class}) GroupDTO dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return responseFormError(bindingResult);
        }
        JobGroupEntity groupEntity = new JobGroupEntity();
        BeanUtils.copyProperties(dto, groupEntity);
        JobGroupEntity entity = JobGroupService.add(groupEntity);
        return response(entity);
    }


//    @PostMapping("/add-datasource")
//    @ApiOperation(value = "数据源新建连接")
//    public ResponseData<?> addDatasource(@RequestBody @Validated({UpdateChecks.class}) DataSourceDTO dto, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            return responseFormError(bindingResult);
//        }
//        JobDataSourceEntity dataSourceEntity = new JobDataSourceEntity();
//        BeanUtils.copyProperties(dto, dataSourceEntity);
//        JobDataSourceEntity jobDataSourceEntity = jobDataSourceService.add(dataSourceEntity);
//        return response(jobDataSourceEntity);
//    }



    @PostMapping("/role-list")
    @ApiOperation(value = "角色列表")
    public ResponseData<?> roleList() {
        Long userId = getCurrentUser().getId();
        List<Role> roleList = roleService.getAllByUserId(userId);
        return response(roleList);
    }

    @PostMapping("/permission-list")
    @ApiOperation(value = "权限列表")
    public ResponseData<?> permissionList() {
        Long userId = getCurrentUser().getId();
        List<Role> roleList = roleService.getAllByUserId(userId);
        return response(roleList);
    }

    @ApiOperation(value = "用户导入" , notes = "用户导入接口")
    @RequestMapping(value = "/import-user", method = RequestMethod.POST)
    public ResponseData<?> importVoucher(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //业务逻辑校验
        JSONObject json = new JSONObject();
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("file");
        if(null == file) {
            json.put("code", 1);
            json.put("msg", "请重新选择上传文件！");
            return response(json);
        }
        String fileName = file.getOriginalFilename(); // 得到上传的文件名
        if (null != fileName && !"".equals(fileName) && !fileName.endsWith(".xls") && !fileName.endsWith(".xlsx")) {
            json.put("code", 1);
            json.put("msg", "您导入的文件格式不正确！");
            return response(json);
        }
        UserEntity enterpriseUser = getCurrentUser();
        InputStream inputStream = file.getInputStream();
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes);
        JSONObject object = userService.importUser(bytes, enterpriseUser);
        if (object.get("code").equals(400)) {
            String name = "用户：" + enterpriseUser.getName() + "导入用户错误信息";
            errorInfoExcel(request, (JSONArray) object.get("arrayError"), response, name);
        }
        return  response(object);
    }

    @GetMapping("/user-search")
    @ApiOperation(value = "用户搜索")
    public ResponseData<?> userSearch(@RequestParam("username") String username) {
        UserEntity userEntity = userService.getByUsername(username);
        return response(userEntity);
    }

    @PostMapping("/user-list")
    @ApiOperation(value = "用户列表")
    public ResponseData<PageInfo<UserEntity>> userList(@RequestBody UserSearchDTO dto) {
        PageInfo<UserEntity> all = userService.getAll(dto.getUsername(), dto.getPageNum(), dto.getPageSize());
        return response(all);
    }


    @PostMapping("/update-user-password")
    @ApiOperation(value = "修改用户密码")
    public ResponseData<?> updateUserPassword(@Validated(UpdateChecks.class) @RequestBody ModifyPasswordDTO dto, BindingResult result) {
        if(result.hasErrors()) {
            return responseFormError(result);
        }
        UserEntity currentUser = getCurrentUser();
        if(!userService.updatePassword(currentUser, dto.getNewPassword())) {
            return responseError("密码修改失败！");
        }
        return response();
    }

    @PostMapping("/update-user")
    @ApiOperation(value = "用户修改")
    public ResponseData<?> updateUser(@Validated(UpdateChecks.class) @RequestBody UserUpdateDTO dto, BindingResult result) {
        if(result.hasErrors()) {
            return responseFormError(result);
        }
        UserEntity currentUser = getCurrentUser();
        UserEntity userEntity = userService.getById(dto.getId());
        if (null != userEntity) {
            return responseError("用户不存在! ", 400);
        }
        userEntity.setUsername(dto.getUsername());
        userEntity.setSex(dto.getSex());
        userEntity.setPhone(dto.getPhone());
        userEntity.setUpdateUserId(currentUser.getId());
        return response(userService.updateUser(userEntity));
    }

    @PostMapping("/delete-user")
    @ApiOperation(value = "用户删除")
    public ResponseData<?> deleteUser(@RequestParam("id") Long id) {
        return response(userService.deleteUpdate(id));
    }

    private void errorInfoExcel(HttpServletRequest request, JSONArray array, HttpServletResponse response, String fileName) throws  IOException{
        sendExcelHeader(response, fileName);
        UserErrorExcel excel = new UserErrorExcel();
        excel.export(request, array, response.getOutputStream());
    }

    private void sendExcelHeader(HttpServletResponse response, String fileName) throws UnsupportedEncodingException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment;fileName=" + new String(fileName.getBytes(), "ISO8859-1"));
    }


    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setJobGroupService(com.wugui.dataxweb.service.JobGroupService jobGroupService) {
        JobGroupService = jobGroupService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
