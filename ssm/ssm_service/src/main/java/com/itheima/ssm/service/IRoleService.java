package com.itheima.ssm.service;

import com.itheima.ssm.domain.Permission;
import com.itheima.ssm.domain.Role;
import org.springframework.stereotype.Service;

import java.util.List;


public interface IRoleService {

    List<Role> findAll();

    void save(Role role);

    List<Permission> findPermissionByRoleId(String roleId);

    void addPermissionToRole(String permissionId, String roleId);
}
