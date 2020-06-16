package com.example.demo;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

public class MySpringBootApplicationTests {
    @Resource
    private UserRepository userRepository;

    @Test
    public void testRepository() {
        //查询所有数据
        List<User> userList = userRepository.findAll();
        System.out.println("findAll(): " + userList.size());
    }
}
