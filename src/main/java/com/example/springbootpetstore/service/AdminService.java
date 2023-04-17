package com.example.springbootpetstore.service;

import com.example.springbootpetstore.pojo.Admin;

/**
 * @author 皮皮皮
 * @date 2023/3/28 21:11
 */
public interface AdminService {
    Admin findByAdmin(Admin admin);
    int updateAdmin(Admin admin);
    Admin findByPhone(Admin admin);
}
