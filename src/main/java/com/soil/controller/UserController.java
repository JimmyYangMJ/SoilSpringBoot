package com.soil.controller;

import com.soil.common.Const;
import com.soil.common.ServerResponse;
import com.soil.pojo.User;
import com.soil.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller("UserController")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService iUserService;

    /**
     * 用户登录
     * @param username
     * @param password
     * @param session
     * @return
     */
    @RequestMapping(value = "login.do",method = RequestMethod.POST)
    @ResponseBody // 使得序列化为json
    public ServerResponse<User> login(String username, String password, HttpSession session){
        ServerResponse<User> response = iUserService.login(username,password);
        if(response.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,response.getData());
        }
        return response;
    }
    /**
     * 用于转发和重定向测试
     */
    @RequestMapping("/student")
    public String test1() {
        //不同控制器转发
        //return "forward:/user/list.do";
        //不同控制器重定向
        return "redirect:/user/registerForm02.do";
    }

    /**
     * @RequestParam(value = "uid",required = true,defaultValue = "30")
     * 表示， 需要请求参数， 如果不设置 defaultValue ，那么就会报错
     * @param uid
     * @return
     */
    @RequestMapping("/test2")
    public String test2(@RequestParam(value = "uid",required = true,defaultValue = "30") Integer uid) {
        System.out.println(uid);
        return "redirect:/user/registerForm02.do";
    }

    @RequestMapping("/toReg")
    public String toReg(){
        return "stu/register";
    }


    /**
     * 把json数据转为model（Student）对象
     * @param stu
     * @return
     */
    @RequestMapping("/save")
    public String save(@RequestBody User stu) {
        System.out.println(stu);
        return "redirect: /stu/list.do";
    }
    /**
     * 添加学生信息
     * 一般数据提交,  接收参数要是 name=ymj&sex=male
     * @return
     */
    @RequestMapping("/savetest")
    public String saveTest(User stu) {
        System.out.println(stu);
        return "redirect: /stu/list.do";
    }

    /**
     * 多视图
     */
    @RequestMapping("/get")
    public User get(){
        User user = new User();
        user.setId(1);
        user.setUsername("admin");
        return user;
    }

}