package com.example.springbootpetstore;

import com.example.springbootpetstore.pojo.Admin;
import com.example.springbootpetstore.service.AdminService;
import com.example.springbootpetstore.service.AdminServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author 皮皮皮
 * @date 2023/3/28 22:08
 */
@SpringBootTest
public class AdminServiceTest {
    @Autowired
    AdminService adminService;
    @Test
    public void test(){

        Admin admin=new Admin();
       // admin.setAdminID(1);
        admin.setAdmin_name("pjy");
        admin.setPassword("123");
       // System.out.println(adminService.updateAdmin(admin));
        //adminService.updateAdmin(admin);
        System.out.println(admin);
        System.out.println(adminService.findByAdmin(admin));
    }
    @Test
    public void phoneTest(){
        Admin admin=new Admin();
        admin.setPhone("15079587753");
        System.out.println(adminService.findByPhone(admin));
    }
}
