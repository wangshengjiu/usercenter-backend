package com.mark.usercenterbackend;

import com.mark.usercenterbackend.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest(classes = UsercenterBackendApplication.class)
class UsercenterBackendApplicationTests {
    @Resource
    private UserMapper userMapper;

    @Test
    void contextLoads() {
        System.out.println(userMapper.selectList(null));
    }

}
