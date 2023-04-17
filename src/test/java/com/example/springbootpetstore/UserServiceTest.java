package com.example.springbootpetstore;

import com.example.springbootpetstore.pojo.User;
import com.example.springbootpetstore.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


/**
 * @auther 齿轮
 * @create 2023-03-20-19:26
 */

@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    public void testQueryAllUsers(){

        userService.QueryAllUsers().forEach(System.out::println);
    }

    @Test
    public void testUpdateUser(){
        User user = new User(5,"pjy","北京朝阳大道","wssb","pijiayu");
        userService.UpdateUser(user);
        userService.QueryAllUsers().forEach(System.out::println);
    }

    @Test
    public void testResetPassword(){
        User user = new User();
        user.setUserID(24);
        user.setPassword("pjyyyds");

        userService.ResetPassword(user);
        userService.QueryAllUsers().forEach(System.out::println);
    }
    @Test
    public void testGetTransaction(){
        User user = new User();
        user.setUserID(23);
        System.out.println(userService.QueryAllTransactionOfUser(user));
    }

    @Test
    public void findUserByID(){
        System.out.println(userService.findUserByID(25));
    }
}
