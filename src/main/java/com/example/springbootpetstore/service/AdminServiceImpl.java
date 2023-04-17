package com.example.springbootpetstore.service;

import com.example.springbootpetstore.dao.AdminMapper;
import com.example.springbootpetstore.pojo.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 皮皮皮
 * @date 2023/3/28 22:04
 */
@Service
public class AdminServiceImpl implements AdminService{
    @Autowired
    AdminMapper adminMapper;
    @Override
    public Admin findByAdmin(Admin admin) {
        return adminMapper.findByAdmin(admin);
    }

    @Override
    public int updateAdmin(Admin admin) {
        return adminMapper.updateAdmin(admin);
    }

    @Override
    public Admin findByPhone(Admin admin) {
        return adminMapper.findByPhone(admin);
    }
}
