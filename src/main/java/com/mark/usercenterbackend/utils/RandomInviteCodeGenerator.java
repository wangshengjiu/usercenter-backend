package com.mark.usercenterbackend.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class RandomInviteCodeGenerator {

    public static void main(String[] args) {
        String inviteCode = generateShortTimestampInviteCode();
        System.out.println("随机生成的邀请码：" + inviteCode);
    }

    // 生成六位邀请码，包含时间戳的后六位和两位随机字符
    public static String generateShortTimestampInviteCode() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmss");
        String timestamp = dateFormat.format(new Date());

        // 获取时间戳的后六位
        String timestampPart = timestamp.substring(6);

        // 生成两位随机字符
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder randomPart = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 2; i++) {
            char randomChar = characters.charAt(random.nextInt(characters.length()));
            randomPart.append(randomChar);
        }

        // 结合时间戳后六位和随机部分生成邀请码

        return timestampPart + randomPart.toString();
    }
}