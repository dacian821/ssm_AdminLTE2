package com.itheima.ssm.service.impl;

import com.itheima.ssm.dao.IUserDao;
import com.itheima.ssm.domain.Role;
import com.itheima.ssm.domain.UserInfo;
import com.itheima.ssm.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("userService")
public class IUserServceImpl implements IUserService {

    @Autowired
    private IUserDao iUserDao;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 查询所有用户
     *
     * @return
     */
    @Override
    public List<UserInfo> findAllUser() throws Exception{
        List<UserInfo> list = iUserDao.findAllUser();
        return list;
    }

    @Override
    public void saveUser(UserInfo userInfo) throws Exception{
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        iUserDao.saveUser(userInfo);
    }

    @Override
    public UserInfo findById(String id) {
        return iUserDao.findById(id);
    }

    @Override
    public List<Role> findUserByIdAndAllRole(String id) {
        return iUserDao.findUserByIdAndAllRole(id);
    }


    /**
     * 用户添加角色
     * @param roleId
     */
    @Override
    public void addRoleToUser(String userId,String roleId) {
        iUserDao.addRoleToUser(userId,roleId);
    }


    /**
     * 用户登录
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo = null;
        try {
            userInfo = iUserDao.findByUsername(username);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //User user = new User(userInfo.getUsername(),"{noop}"+userInfo.getPassword(),getAuthority(userInfo.getRoles()));
        User user = new User(userInfo.getUsername(), userInfo.getPassword(), userInfo.getStatus() == 0 ? false : true, true, true, true, getAuthority(userInfo.getRoles()));
        return user;
    }

    /**
     * 获取角色
     *
     * @param roles
     * @return
     */
    public List<SimpleGrantedAuthority> getAuthority(List<Role> roles) {
        List<SimpleGrantedAuthority> list = new ArrayList<>();
        for (Role role : roles) {
            list.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
        }

        return list;
    }


}
