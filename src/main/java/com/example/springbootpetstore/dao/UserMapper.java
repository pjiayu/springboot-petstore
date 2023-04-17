package com.example.springbootpetstore.dao;

import com.example.springbootpetstore.pojo.Transaction;
import com.example.springbootpetstore.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @auther 齿轮
 * @create 2023-03-20-18:08
 *
 * 用户管理：已注册用户的查看、信息修改、密码重置。密码加密。
 */
@Mapper
@Repository
public interface UserMapper {
    //获得全部用户，对应用户查看功能
    List<User> QueryAllUsers();

    //根据ID更新用户信息，对应信息修改功能（不修改密码）
    void UpdateUserById(User user);

    void UpdateUserByAccount(User user);

    //根据ID重置密码，对应密码重置功能
    void ResetPassword(User user);
    //根据id查看所有订单
    List<Transaction> QueryAllTransactionOfUser(User user);

    User findUserByID(int userID);
}
