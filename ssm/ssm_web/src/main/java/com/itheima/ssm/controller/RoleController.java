package com.itheima.ssm.controller;

import com.itheima.ssm.domain.Permission;
import com.itheima.ssm.domain.Role;
import com.itheima.ssm.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private IRoleService iRoleService;

    @RequestMapping("/findAll.do")
    public ModelAndView findAll() {

        ModelAndView mv = new ModelAndView();
        List<Role> roleList = iRoleService.findAll();
        mv.addObject("roleList", roleList);
        mv.setViewName("role-list");
        return mv;
    }


    @RequestMapping("/save.do")
    public String save(Role role) {

        iRoleService.save(role);
        return "redirect:findAll.do";
    }

    /**
     * 角色添加权限，查询可添加的权限
     * @param roleId
     * @return
     */
    @RequestMapping("/findPermissionByRoleId.do")
    public ModelAndView findPermissionByRoleId(@RequestParam(name = "id") String roleId) {

        ModelAndView mv = new ModelAndView();
        List<Permission> permissionList = iRoleService.findPermissionByRoleId(roleId);
        mv.addObject("permissionList", permissionList);
        mv.addObject("roleId", roleId);
        mv.setViewName("role-permission-add");
        return mv;
    }


    @RequestMapping("/addPermissionToRole.do")
    public String addPermissionToRole(@RequestParam(name = "ids") String [] permissionIds,@RequestParam(name = "roleId") String roleId) {

        for (String permissionId : permissionIds) {
            iRoleService.addPermissionToRole(permissionId,roleId);
        }
        return "redirect:findAll.do";
    }

}

