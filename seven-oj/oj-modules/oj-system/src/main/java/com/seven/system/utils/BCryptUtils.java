package com.seven.system.utils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
/**
 * 加密算法⼯具类
 */
public class BCryptUtils {
    /**
     * ⽣成加密后密⽂
     *
     * @param password 密码
     * @return 加密字符串
     */
    public static String encryptPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }
    /**
     * 判断密码是否相同
     *
     * @param rawPassword 真实密码
     * @param encodedPassword 加密后密⽂
     * @return 结果
     */

    //根据数据库中查出的加密后的密码, 提取出当时加密用的盐值
    public static boolean matchesPassword(String rawPassword, String
            encodedPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public static void main(String[] args) {
        System.out.println(encryptPassword("123456"));

        //System.out.println(matchesPassword("123456", "$2a$10$o4QUOfdLjcsbGDQ6ttR0jOWyKGRcxO43bsVqJqMjcy3Tc/y78gJDy"));
    }
}