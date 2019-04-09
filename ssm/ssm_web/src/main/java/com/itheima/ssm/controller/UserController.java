package com.itheima.ssm.controller;

import com.itheima.ssm.dao.IUserDao;
import com.itheima.ssm.domain.Role;
import com.itheima.ssm.domain.UserInfo;
import com.itheima.ssm.service.IUserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.naming.Name;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService iUserService;

    @RequestMapping("/findAll.do")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView findAll() throws Exception{

        ModelAndView mv = new ModelAndView();
        List<UserInfo> list = iUserService.findAllUser();
        mv.addObject("userList", list);
        mv.setViewName("user-list");
        return mv;

    }

    /**
     * 添加用户
     * @return
     * @throws Exception
     */
    @RequestMapping("/save.do")
    public String saveUser(UserInfo userInfo) throws Exception{

        ModelAndView mv = new ModelAndView();
        iUserService.saveUser(userInfo);

        return "redirect:findAll.do";

    }

    /**
     * 查询用户详情
     * @return
     * @throws Exception
     */
    @RequestMapping("/findById.do")
    public ModelAndView findById(@RequestParam(name = "id",required = true)String id) throws Exception{

        ModelAndView mv = new ModelAndView();
        UserInfo userInfo= iUserService.findById(id);
        mv.addObject("user", userInfo);
        mv.setViewName("user-show");
        return mv;

    }

    @RequestMapping("/findUserByIdAndAllRole.do")
    public ModelAndView findUserByIdAndAllRole(@RequestParam(name = "id",required = true)String id) {
        ModelAndView mv = new ModelAndView();
        List<Role> roleList = iUserService.findUserByIdAndAllRole(id);
        mv.addObject("userId", id);
        mv.addObject("roleList", roleList);
        mv.setViewName("user-role-add");

        return mv;
    }

    @RequestMapping("/addRoleToUser.do")
    public String addRoleToUser(@RequestParam(name = "ids") String [] ids,@RequestParam(name = "userId")String userId) {
        for (String RoleId : ids) {
            iUserService.addRoleToUser(userId,RoleId);
        }


        return "redirect:findAll.do";

    }
}
