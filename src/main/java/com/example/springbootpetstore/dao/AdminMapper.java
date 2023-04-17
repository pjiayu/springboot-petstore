package com.example.springbootpetstore.dao;

import com.example.springbootpetstore.pojo.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author 皮皮皮
 * @date 2023/3/28 20:34
 */
@Mapper
@Repository
public interface AdminMapper {
    Admin findByAdmin(Admin admin);
    int updateAdmin(Admin admin);

    Admin findByPhone(Admin admin);
}
