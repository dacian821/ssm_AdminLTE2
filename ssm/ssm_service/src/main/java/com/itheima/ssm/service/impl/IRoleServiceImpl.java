package com.itheima.ssm.service.impl;

import com.itheima.ssm.dao.IRoleDao;
import com.itheima.ssm.domain.Permission;
import com.itheima.ssm.domain.Role;
import com.itheima.ssm.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IRoleServiceImpl implements IRoleService {

    @Autowired
    private IRoleDao iRoleDao;

    @Override
    public List<Role> findAll() {
        return iRoleDao.findAll();
    }

    @Override
    public void save(Role role) {
        iRoleDao.save(role);
    }

    @Override
    public List<Permission> findPermissionByRoleId(String roleId) {
        return iRoleDao.findPermissionByRoleId(roleId);
    }

    @Override
    public void addPermissionToRole(String permissionId, String roleId) {
        iRoleDao.addPermissionToRole(permissionId, roleId);
    }
}
