package com.example.springbootpetstore.service;

import com.example.springbootpetstore.pojo.Transaction;
import com.example.springbootpetstore.pojo.User;

import java.util.List;

/**
 * @auther 齿轮
 * @create 2023-03-20-18:41
 */
public interface UserService {
    List<User> QueryAllUsers();

    void UpdateUser(User user);
    void UpdateUserByAccount(User user);

    void ResetPassword(User user);
    //根据id查看所有订单
    List<Transaction> QueryAllTransactionOfUser(User user);
    User findUserByID(int userID);

}
