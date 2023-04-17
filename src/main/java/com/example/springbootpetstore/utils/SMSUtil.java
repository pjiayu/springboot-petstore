package com.example.springbootpetstore.utils;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author 皮皮皮
 * @date 2023/4/5 14:04
 */
public class SMSUtil {
    public static String code1=null;
    public static long time1;
    public static String sendSMS(HttpServletRequest request, String tel) {
        String reStr = ""; //定义返回值
        // 短信应用SDK AppID  1400开头
        int appid = 1400809084 ;
        // 短信应用SDK AppKey
        String appkey = "3e3fab1e5139e20a9ff3e262359b11a5";
        // 短信模板ID，需要在短信应用中申请
        int templateId = 1754329 ;
        // 签名，使用的是签名内容，而不是签名ID
        String smsSign = "皮皮皮公众号";
        //随机生成四位验证码的工具类
        String code = KeyUtil.keyUtils();
        code1=code;
        try {
            //参数，一定要对应短信模板中的参数顺序和个数，
            String[] params = {code};
            //创建ssender对象
            SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
            //发送
            SmsSingleSenderResult result = ssender.sendWithParam("86", tel,templateId, params, smsSign, "", "");
            if(result.result!=0){
                reStr = "error";
            }
            // 签名参数未提供或者为空时，会使用默认签名发送短信
            HttpSession session = request.getSession();
            //JSONObject存入数据
            JSONObject json = new JSONObject();
            json.put("Code", code);//存入验证码
            time1=System.currentTimeMillis();
            json.put("createTime", System.currentTimeMillis());//存入发送短信验证码的时间
            // 将验证码和短信发送时间码存入SESSION
            request.getSession().setAttribute("Code",code);
            request.getSession().setAttribute("MsCode", json);
            System.out.println(json);
            reStr = "success";
        } catch (HTTPException e) {
            // HTTP响应码错误
            e.printStackTrace();
        } catch (JSONException e) {
            // json解析错误
            e.printStackTrace();
        } catch (IOException e) {
            // 网络IO错误
            e.printStackTrace();
        }catch (Exception e) {
            // 网络IO错误
            e.printStackTrace();
        }
        return reStr;
    }

}
