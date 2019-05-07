package com.imooc.test;

import com.shiro.CustomRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class CustomRealmTest {

    @Test
    public void testAuthentication(){

        CustomRealm customRealm = new CustomRealm();
        // 1 构建SecurityManager 环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(customRealm);

        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("md5");
        matcher.setHashIterations(1);
        customRealm.setCredentialsMatcher(matcher);

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
