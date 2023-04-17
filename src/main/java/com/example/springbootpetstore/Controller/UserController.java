package com.example.springbootpetstore.Controller;

import com.example.springbootpetstore.pojo.Pet;
import com.example.springbootpetstore.pojo.Transaction;
import com.example.springbootpetstore.pojo.User;
import com.example.springbootpetstore.service.UserService;
import com.example.springbootpetstore.service.UserServiceImpl;
import com.example.springbootpetstore.utils.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.StringJoiner;

/**
 * @author 皮皮皮
 * @date 2023/3/30 22:57
 */
@Controller
@RequestMapping("/user")
@SessionAttributes("userList")
public class UserController {
    @Autowired
    UserService userService=new UserServiceImpl();

    //查询所有账户
    @GetMapping("/allUsers")
    public String getAllUsers(Model model){
        List<User> userList=userService.QueryAllUsers();
        model.addAttribute("userList",userList);
        return "/userController/allUser";
    }

    //添加用户
    @GetMapping("/addUser")
    public String addUser(){
        return "/userController/addUser";
    }
    //根据传来的userID查看用户详情
    @GetMapping("/userInfo")
    public String productInfo(//@RequestParam(value = "index") int index,
                              //@SessionAttribute("userList") List<User> userList,
                              User user,
                              Model model){
       // User user=userList.get(index);
        List<Transaction> transactionList=userService.QueryAllTransactionOfUser(user);
        //System.out.println("-------------"+user);
        model.addAttribute("user",user);
        model.addAttribute("transactionList",transactionList);
        return "userController/userInfo";
    }

    //修改用户密码
    @PostMapping("/editPsw")
    @ResponseBody
    public AjaxResult editPsw(String newPsw,@RequestParam("index") String indexStr,
                              @SessionAttribute("userList") List<User> userList
                              //@SessionAttribute("petList") List<Pet> petList,
                              ){
        AjaxResult ajaxResult=new AjaxResult();

        //System.out.println("------petList-----"+petList);
        User user=userList.get(Integer.parseInt(indexStr));
        user.setPassword(newPsw);
        try{
            userService.ResetPassword(user);
            ajaxResult.setSuccess(true);
            ajaxResult.setMessage("密码修改成功");
        }catch (Exception e){
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("密码修改失败");

        }

        return ajaxResult;
    }
    //编辑用户信息
    @GetMapping("/editUser")
    public String editUser(int userID,Model model){
        User user=userService.findUserByID(userID);
        model.addAttribute("user",user);

        return "userController/editUser";
    }

    @PostMapping("/editUser")
    @ResponseBody
    public AjaxResult editUser(User user){
        System.out.println(user);
        AjaxResult ajaxResult=new AjaxResult();
        try{
            userService.UpdateUser(user);
            ajaxResult.setSuccess(true);
            ajaxResult.setMessage("用户信息修改成功");
        }catch (Exception e){
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("用户信息修改失败");
        }
        return ajaxResult;
    }
}
