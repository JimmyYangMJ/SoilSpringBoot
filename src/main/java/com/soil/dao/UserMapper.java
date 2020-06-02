package com.soil.dao;

import com.soil.pojo.User;
import org.apache.ibatis.annotations.Param;

//@Repository("userMapper")
//@Mapper
///**使用Spring自动扫描MyBatis的接口并装配
// （Spring将指定包中所有被@Mapper注解标注的接口自动装配为MyBatis的映射接口*/
public interface UserMapper {

    // 选择登陆
    User selectLogin(@Param("username") String username, @Param("password") String password);

    // 检查用户名是否存在
    int checkUsername(String username);

}