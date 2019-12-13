package com.wugui.dataxweb.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class CaptchaService {

    private StringRedisTemplate stringRedisTemplate;

    private Random random = new Random();
    private String randString = "0123456789";// 随机产生的字符串

    private int width = 80;// 图片宽
    private int height = 26;// 图片高
    private int stringNum = 6; // 字符数

    private BufferedImage image;
    private String code;

    @Value("${captcha-test-code}")
    private String testCode;

    private int getFontSize(){
        int size = (int)(width / stringNum * 1.2);
        if(size > height) {
            size = (int)(height*0.7);
        }
        return size;
    }

    /*
     * 获得字体
     */
    private Font getFont() {
        return new Font("Fixedsys", Font.BOLD, getFontSize());
    }

    /*
     * 获得颜色
     */
    private Color getRandColor(int fc, int bc) {
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc - 16);
        int g = fc + random.nextInt(bc - fc - 14);
        int b = fc + random.nextInt(bc - fc - 18);
        return new Color(r, g, b);
    }

    /*
     * 绘制字符串
     */
    private String drawString(Graphics g, String randomString, int i) {
        g.setFont(getFont());
        g.setColor(new Color(random.nextInt(101), random.nextInt(111), random
                .nextInt(121)));
        String rand = String.valueOf(getRandomString(random.nextInt(randString
                .length())));
        randomString += rand;
        g.translate(random.nextInt(3), random.nextInt(3));
        int offset = (width - (getFontSize()-getFontSize()/8)*stringNum) / 2;
        int x = getFontSize()/5;
        if(i > 1) {
            x = (getFontSize()-getFontSize()/5) * (i-1);
        }
        x += offset;
        int y = getFontSize() - getFontSize()/6;
        y += (height - y) / 2;
        g.drawString(rand, x, y);
        return randomString;
    }

    /*
     * 绘制干扰线
     */
    private void drawLine(Graphics g) {
        g.setColor(getRandColor(random.nextInt(110), 133));
        int x = random.nextInt(width);
        int y = random.nextInt(height);
        int x2 = random.nextInt(width);
        int y2 = random.nextInt(height);
        g.drawLine(x, y, x2, y2);
    }

    /*
     * 获取随机的字符
     */
    private String getRandomString(int num) {
        return String.valueOf(randString.charAt(num));
    }

    public void generate(String key) {
        generate(key, 60);
    }

    public void generate(String key, long timeout) {
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        generate();
        operations.set(getKey(key), code, timeout, TimeUnit.SECONDS);
    }

    private void generate(){
        // BufferedImage类是具有缓冲区的Image类,Image类是用于描述图像信息的类
        image = new BufferedImage(width, height,BufferedImage.TYPE_INT_BGR);
        Graphics g = image.getGraphics();// 产生Image对象的Graphics对象,改对象可以在图像上进行各种绘制操作
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Times New Roman", Font.PLAIN, getFontSize()));
        g.setColor(getRandColor(random.nextInt(110), 133));
        // 绘制干扰线
        // 干扰线数量
        int lineSize = (int)(width*height * 0.012);
        for (int i = 0; i <= lineSize; i++) {
            drawLine(g);
        }
        // 绘制随机字符
        String randomString = "";
        // 随机产生字符数量
        for (int i = 1; i <= stringNum; i++) {
            randomString = drawString(g, randomString, i);
        }
        g.dispose();
        code = randomString;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setStringNum(int stringNum) {
        this.stringNum = stringNum;
    }

    public String getCode() {
        return code;
    }

    public BufferedImage getImage() {
        return image;
    }

    public boolean verify(String code, String key){
        if(null != testCode && !testCode.equals("") && testCode.equals(code)) return true;
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        String existsCode = operations.get(getKey(key));
        return null != existsCode && existsCode.equals(code);
    }

    private String getKey(String key) {
        return "captcha-"+key;
    }

    @Autowired
    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }
}
