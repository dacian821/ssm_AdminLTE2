package com.itheima.ssm.dao;

import com.itheima.ssm.domain.Role;
import com.itheima.ssm.domain.UserInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;


public interface IUserDao {

    @Select("select * from users")
    List<UserInfo> findAllUser() throws Exception;


    @Select("select * from users where username= #{username}")
    @Results({
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "username",column = "username"),
            @Result(property = "email",column = "email"),
            @Result(property = "password",column = "password"),
            @Result(property = "phoneNum",column = "phoneNum"),
            @Result(property = "status",column = "status"),
            @Result(property = "statusStr",column = "statusStr"),
            @Result(property = "roles",column = "id",javaType = List.class,many = @Many(select = "com.itheima.ssm.dao.IRoleDao.findById")),
    })
    UserInfo findByUsername(String username) throws Exception;

    @Insert("insert into users(email,username,password,phoneNum,status) values(#{email},#{username},#{password},#{phoneNum},#{status})")
    void saveUser(UserInfo userInfo);

    @Select("select * from users where id = #{id}")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "username",column = "username"),
            @Result(property = "email",column = "email"),
            @Result(property = "password",column = "password"),
            @Result(property = "phoneNum",column = "phoneNum"),
            @Result(property = "status",column = "status"),
            @Result(property = "statusStr",column = "statusStr"),
            @Result(property = "roles",column = "id",javaType = List.class,many = @Many(select = "com.itheima.ssm.dao.IRoleDao.findById"))
    })
    UserInfo findById(String id);



    @Select("select * from role where id not in(select roleid from users_role where userid=#{id})")
    List<Role> findUserByIdAndAllRole(String id);

    @Insert("insert into users_role(userId,roleId) values(#{userId},#{roleId})")
    void addRoleToUser(@Param("userId") String userId,@Param("roleId") String roleId);
}
