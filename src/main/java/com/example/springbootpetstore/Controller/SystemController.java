package com.example.springbootpetstore.Controller;

import com.example.springbootpetstore.pojo.Admin;
import com.example.springbootpetstore.service.AdminService;
import com.example.springbootpetstore.utils.AjaxResult;
import com.example.springbootpetstore.utils.CpachaUtil;
import com.example.springbootpetstore.utils.SMSUtil;
import com.tencentcloudapi.scf.v20180416.models.Code;
import org.apache.tomcat.util.bcel.Const;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/**
 * @author 皮皮皮
 * @date 2023/3/28 10:15
 */
@Controller
@RequestMapping("/system")
@SessionAttributes(value = {"admin","checkCode"})
//@SessionAttribute用来取session中的值。当向model中add参数时，会先到@SessionAttributes的value中寻找是否有对应的参数名
//Controller层在向其模型Model或ModelAndView中添加数据时，
// 若该数据匹配@SessionAttributes注解指定的名称或者类型时，就会放入session中。没有匹配的则只是存在model或modelAndView中
public class SystemController {
    @Autowired
    private AdminService adminService;

    @GetMapping("/login")
    public String toLogin(){
        return "login";
    }

    //点击发送短信验证码
    @PostMapping("/sendMs")
    @ResponseBody
    public String sendMs (HttpServletRequest request, String phone){
        System.out.println(phone);
        if(phone!=null&&!phone.equals("")){
            String s = SMSUtil.sendSMS(request,phone);
            return s;
        }else{
            return "error";
        }
    }
    @PostMapping("/loginWithNumber")
    @ResponseBody
    public AjaxResult register(HttpServletRequest request,
                               Admin admin,Model model,@RequestParam("Code")String Code) {
        AjaxResult ajaxResult=new AjaxResult();
        System.out.println(admin.getPhone()+"---"+Code);
        JSONObject json = (JSONObject)request.getSession().getAttribute("MsCode");
        if(!json.getString("Code").equals(Code)){
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("验证码错误");
        }
        //我这里模拟了一分钟
        if((System.currentTimeMillis() - json.getLong("createTime")) > 1000 * 60 * 1){
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("验证码过期");
        }
        //数据库查找用户
        Admin ad=adminService.findByPhone(admin);
        model.addAttribute("admin",ad);
        System.out.println(ad);
        ajaxResult.setSuccess(true);
        ajaxResult.setMessage("登录成功");
       return  ajaxResult;
    }

    //登录
    @PostMapping("/login")
    @ResponseBody
    public AjaxResult login(Admin admin, String checkCode, Model model,@SessionAttribute("code") String code){

        AjaxResult ajaxResult=new AjaxResult();
        if(StringUtils.isEmpty(admin.getAdmin_name())){
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("请输入用户名");
            System.out.println(ajaxResult.getMessage());
            return ajaxResult;
        }
        if(StringUtils.isEmpty(admin.getPassword())){
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("请输入密码");
            System.out.println(ajaxResult.getMessage());
            return ajaxResult;
        }
        if(StringUtils.isEmpty(checkCode)){
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("请输入验证码");
            System.out.println(ajaxResult.getMessage());
            return ajaxResult;
        }
        if(StringUtils.isEmpty("code")){
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("会话时间过长，请刷新");
            System.out.println(ajaxResult.getMessage());
            return ajaxResult;
        }else{
            if(!checkCode.equalsIgnoreCase(code)){
                ajaxResult.setSuccess(false);
                ajaxResult.setMessage("验证码错误");
                System.out.println(ajaxResult.getMessage());
                return ajaxResult;
            }
        }

        //查询数据库
        Admin ad=adminService.findByAdmin(admin);
        System.out.println(ad+"-"+checkCode);
        if(ad==null){
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("用户名或密码错误");
            return ajaxResult;
        }
        ajaxResult.setSuccess(true);
        ajaxResult.setMessage("登录成功");
        model.addAttribute("admin",ad);
        System.out.println(ajaxResult.getMessage()+model.getAttribute("admin"));
        System.out.println("****************************************");
        return ajaxResult;

    }

    //生成验证码
    @GetMapping("/checkCode")
    public void generateCpacha(HttpServletRequest request, HttpServletResponse response,
                               @RequestParam(value="vl",defaultValue="4",required=false) Integer vl,
                               @RequestParam(value="w",defaultValue="110",required=false) Integer w,
                               @RequestParam(value="h",defaultValue="39",required=false) Integer h){
        CpachaUtil cpachaUtil = new CpachaUtil(vl, w, h);
        String generatorVCode = cpachaUtil.generatorVCode();
        request.getSession().setAttribute("code", generatorVCode);
        System.out.println("code="+generatorVCode);
        BufferedImage generatorRotateVCodeImage = cpachaUtil.generatorRotateVCodeImage(generatorVCode, true);
        try {
            ImageIO.write(generatorRotateVCodeImage, "gif", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //退出登录
    @RequestMapping("/loginOut")
    public String loginOut(HttpSession session){
        session.invalidate();
        return "login";
    }

    //修改密码
    @GetMapping("/editPsw")
    public String editPsw(){
        return "userController/editPassword";
    }

    @PostMapping("/editPsw")
    @ResponseBody
    public AjaxResult editPsw(@RequestParam(value = "oldPsw" ,required = true) String oldPsw,
                              @RequestParam(value = "newPsw",required = true) String newPsw,
                              @RequestParam(value = "confirmPsw",required = true) String confirmPsw,
                              @SessionAttribute(value = "admin", required = true) Admin admin){
        AjaxResult ajaxResult = new AjaxResult();
        System.out.println("********"+admin);
        System.out.println("oldPsw:"+oldPsw);
        System.out.println("newPsw:"+newPsw);
        if(!admin.getPassword().equals(oldPsw)){
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("原密码错误");
        }else if(!Objects.equals(newPsw, confirmPsw)){
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("两次密码不同");
        }else{
            try{
                admin.setPassword(newPsw);
                int count=adminService.updateAdmin(admin);
                if(count>0){
                    ajaxResult.setSuccess(true);
                    ajaxResult.setMessage("修改成功,请重新登录");

                }else{
                    ajaxResult.setSuccess(false);
                    ajaxResult.setMessage("修改失败");
                }
            }catch (Exception e){
                ajaxResult.setSuccess(false);
                ajaxResult.setMessage("数据出错");
            }

        }
        System.out.println(ajaxResult.isSuccess()+ajaxResult.getMessage());
        return ajaxResult;
    }
}
