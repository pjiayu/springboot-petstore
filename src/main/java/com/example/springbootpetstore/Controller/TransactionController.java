package com.example.springbootpetstore.Controller;

import com.example.springbootpetstore.pojo.Transaction;
import com.example.springbootpetstore.pojo.User;
import com.example.springbootpetstore.service.TransactionService;
import com.example.springbootpetstore.service.TransactionServiceImpl;
import com.example.springbootpetstore.service.UserService;
import com.example.springbootpetstore.service.UserServiceImpl;
import com.example.springbootpetstore.utils.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;

/**
 * @author 皮皮皮
 * @date 2023/3/30 22:56
 */
@Controller
@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    TransactionService transactionService=new TransactionServiceImpl();
    @Autowired
    UserService userService=new UserServiceImpl();

    public List<User> allUsers(){
        return userService.QueryAllUsers();
    }

    @GetMapping("/allTransaction")
    public String toAllProducts(Model model){
        List<Transaction> transactionList= transactionService.getAllTransaction();
        List<User>userList=allUsers();
        System.out.println("***所有订单***"+transactionList);
        System.out.println("************所有用户*********"+userList);
        model.addAttribute("transactionList",transactionList);
        model.addAttribute("userList",userList);
        return "transactionController/allTransaction";
    }


    //根据传来的index查看用户订单详情
    @GetMapping("/searchTransactionByUserId")
    public String productInfo(//@RequestParam(value = "index") int index,
                              User user,
                              Model model){
        List<User>userList=allUsers();
        //User user=userList.get(index);
        List<Transaction> transactionList=transactionService.getTransactionsByUserID(user);
        //System.out.println("-------------"+user);
        model.addAttribute("userList",userList);
        model.addAttribute("transactionList",transactionList);
        return "transactionController/allTransaction";
    }
    @PostMapping("/deliverGoods")
    @ResponseBody
    public AjaxResult deliverGoods( @RequestParam(value = "indexList") List<Integer> indexList){
        System.out.println(indexList);
        AjaxResult ajaxResult=new AjaxResult();
        List<Transaction> transactionList= transactionService.getAllTransaction();
        //list循环
        //1.for循环  for(int i=0;i<indexList.size();i++){}

        //2、增强for循环   for(int index:indexList){}

        //3、迭代器循环
//        Iterator<Integer> iterator=indexList.iterator();
//        while (iterator.hasNext()){
//            int index=iterator.next();
//            //....
//        }
        //4、forEach循环
        indexList.stream().forEach(index->{
          Transaction transaction=transactionList.get(index);
          transaction.setState("已发货");
          transactionService.updateTransaction(transaction);
          ajaxResult.setSuccess(true);
          ajaxResult.setMessage("发货成功");
        });
        return ajaxResult;

    }

    @PostMapping("/deleteChosenTransaction")
    @ResponseBody
    public AjaxResult deleteChosenTransaction( @RequestParam(value = "indexList") List<Integer> indexList){
        System.out.println("indexList:"+indexList);
        AjaxResult ajaxResult=new AjaxResult();
        List<Transaction> transactionList= transactionService.getAllTransaction();

        indexList.stream().forEach(index->{
            Transaction transaction=transactionList.get(index);

            transactionService.deleteTransaction(transaction);
            ajaxResult.setSuccess(true);
            ajaxResult.setMessage("删除成功");
        });
        return ajaxResult;

    }

    @PostMapping("/deleteOneTransaction")
    @ResponseBody
    public AjaxResult deleteOneTransaction(@RequestParam(value = "index")String indexStr){
        int index=Integer.parseInt(indexStr);

        System.out.println("删除index"+index);
        AjaxResult ajaxResult=new AjaxResult();
        List<Transaction>transactionList=transactionService.getAllTransaction();
        Transaction transaction=transactionList.get(index);
        transactionService.deleteTransaction(transaction);

        ajaxResult.setSuccess(true);
        ajaxResult.setMessage("删除成功");
        return ajaxResult;
    }
}
