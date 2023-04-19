package com.example.springbootpetstore.interceptors;

import com.example.springbootpetstore.pojo.Admin;
import org.springframework.web.servlet.HandlerInterceptor;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 皮皮皮
 * @date 2023/4/18 18:28
 */
//登录拦截器
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object object) throws IOException {

        Admin admin=(Admin)request.getSession().getAttribute("admin");
       // System.out.println("******************************"+admin);
        if(admin!=null){
            return true;
        }
        response.sendRedirect(request.getContextPath() + "/system/login");
        return false;
    }
}
