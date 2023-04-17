package com.example.springbootpetstore.service;

import com.example.springbootpetstore.dao.UserMapper;
import com.example.springbootpetstore.pojo.Transaction;
import com.example.springbootpetstore.pojo.User;
import com.example.springbootpetstore.utils.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @auther 齿轮
 * @create 2023-03-20-18:42
 */
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserMapper userMapper;

    //功能：获得全部user对象
    //注：获得的user的password属性为密文
    @Override
    public List<User> QueryAllUsers() {
        return userMapper.QueryAllUsers();
    }

    //功能：用户信息修改——根据id，提供user类，更改userID对应的name、address、account信息，不修改密码
    //注：只使用userID、name、address、account属性
    @Override
    public void UpdateUser(User user) {
        userMapper.UpdateUserById(user);
    }

    @Override
    public void UpdateUserByAccount(User user) {
        userMapper.UpdateUserByAccount(user);
    }

    //功能：重置密码，输入用户对象
    //注：仅使用userID、password属性
    //注：user内密码为明文，在service层中变成密文
    @Override
    public void ResetPassword(User user) {
        user.setPassword(Hash.SHA256Encrypt(user.getPassword()));
        userMapper.ResetPassword(user);
    }

    @Override
    public List<Transaction> QueryAllTransactionOfUser(User user) {
        return userMapper.QueryAllTransactionOfUser(user);
    }

    @Override
    public User findUserByID(int userID) {
        return userMapper.findUserByID(userID);
    }


}
