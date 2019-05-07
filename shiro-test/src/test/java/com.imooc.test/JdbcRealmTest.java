package com.imooc.test;


import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;


public class JdbcRealmTest {

    DruidDataSource dataSource = new DruidDataSource();

    {
        dataSource.setUrl("jdbc:mysql://localhost:3306/mysql");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
    }
    @Test
    public void testAuthentication(){

        JdbcRealm jdbcRealm = new JdbcRealm();
        jdbcRealm.setDataSource(dataSource);
        jdbcRealm.setPermissionsLookupEnabled(true);   //权限开关

        String sql = "select password from test_users where username = ?";
        jdbcRealm.setAuthenticationQuery(sql);

        String roleSql = "select role_name from test_user_role where user_name = ?";
        jdbcRealm.setUserRolesQuery(roleSql);
        // 1 构建SecurityManager 环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(jdbcRealm);
        // 2 主题提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        // 3 登陆认证
//        UsernamePasswordToken token = new UsernamePasswordToken("Mark","123456");
        UsernamePasswordToken token = new UsernamePasswordToken("wang","123456");
        subject.login(token);
        System.out.println(subject.isAuthenticated());

        subject.checkRole("admin");

//        subject.checkPermission("delete");



    }

}
