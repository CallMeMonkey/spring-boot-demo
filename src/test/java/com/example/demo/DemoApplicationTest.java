package com.example.demo;

import com.example.demo.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTest {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void mySqlTest() {
        String sql = "SELECT id, name, password FROM tb_user";
        List<User> userList = (List<User>) jdbcTemplate.query(sql, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                User user = new User();
                user.setId(resultSet.getString("id"));
                user.setName(resultSet.getString("name"));
                user.setPassword(resultSet.getString("password"));
                return user;
            }
        });
        System.out.println("Query success");
        for (User user : userList) {
            System.out.println("id: " + user.getId() + " name: " + user.getName() + " password: " + user.getPassword());
        }
    }

    @Test
    public void testRedis() {
        redisTemplate.opsForValue().set("name", "Monkey");
        String name = (String)redisTemplate.opsForValue().get("name");
        System.out.println(name);

        redisTemplate.delete("name");
        redisTemplate.opsForValue().set("name", "Michael");
        name = stringRedisTemplate.opsForValue().get("name");
        System.out.println(name);
    }
}