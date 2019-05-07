package com.imooc.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;

public class AuthenticationTest {

    SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();

    @Before
    public void addUser(){
        simpleAccountRealm.addAccount("Mark","123456","admin","user");
    }

    @Test
    public void testAuthentication(){

        // 1 构建SecurityManager 环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(simpleAccountRealm);

        // 2 主题提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        // 3 登陆认证
        UsernamePasswordToken token = new UsernamePasswordToken("Mark","123456");
        subject.login(token);
        System.out.println(subject.isAuthenticated());

    /*    subject.logout();

        System.out.println(subject.isAuthenticated());*/

        subject.checkRole("admin");  // 授权单个
        subject.checkRoles("admin","user");  //授权多个


    }

}
