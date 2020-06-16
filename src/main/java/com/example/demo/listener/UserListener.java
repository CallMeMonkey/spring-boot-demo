package com.example.demo.listener;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.omg.PortableServer.LIFESPAN_POLICY_ID;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.security.AllPermission;
import java.util.List;

@WebListener
public class UserListener implements ServletContextListener {
    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private UserService userService;

    private static final String ALL_USER = "ALL_USER_LIST";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //查询数据库中所有用户
        List<User> userList = userService.findAll();
        //清楚缓存中的用户数据
        redisTemplate.delete(ALL_USER);
        //将数据存放到Redis中
        redisTemplate.opsForList().leftPushAll(ALL_USER, userList);

        List<User> queryUserList = redisTemplate.opsForList().range(ALL_USER, 0, -1);

        System.out.println("缓存中目前的用户数有 " + queryUserList.size());
        System.out.println("ServletContext上下文初始化");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("ServletContext上下文初销毁");
    }
}
