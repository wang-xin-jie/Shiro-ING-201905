package com.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.net.Authenticator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CustomRealm extends AuthorizingRealm {


    Map<String,String> userMap = new HashMap<String,String>(16);

    {
//        userMap.put("Mark","123456");
//        userMap.put("Mark","e10adc3949ba59abbe56e057f20f883e");
        userMap.put("Mark","283538989cef48f3d7d8a1c1bdf2008f"); //加盐
        super.setName("customRealm");
    }
    /*
    *  做授权
    * */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String userName = String.valueOf(principalCollection.getPrimaryPrincipal());
        Set<String> roles = getRolesByUserName(userName);
        Set<String> permissions = getPermissionsByUserName(userName);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setRoles(roles);
        simpleAuthorizationInfo.setStringPermissions(permissions);
        return simpleAuthorizationInfo;
    }

    private Set<String> getPermissionsByUserName(String userName) {
        Set<String> sets = new HashSet<String>();
        sets.add("delete");
        sets.add("add");
        return sets;
    }

    /*
    *  模拟数据库获取角色和权限数据
    *
    * */
    private Set<String> getRolesByUserName(String userName) {
        Set<String> sets = new HashSet<String>();
        sets.add("admin");
        sets.add("user");
        return sets;
    }

    /*
    *
    *   做认证
    * */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 从主体传过来的认证中获取用户名
        String userName = String.valueOf(authenticationToken.getPrincipal());  //得到主体

        // 通过用户名从数据库中获取到密码
        String password = getPasswordByUserName(userName);
        if(password == null){
            return null;
        }
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo("Mark",password,"customRealm");
        simpleAuthenticationInfo.setCredentialsSalt(ByteSource.Util.bytes("Mark"));
        return simpleAuthenticationInfo;
    }




    /*
    *  模拟数据库查询凭证
    *  @param userName
    *  @return
    *
    * */
    private String getPasswordByUserName(String userName) {
        return userMap.get(userName);
    }

    public static void main(String[] args) {
        Md5Hash md5Hash = new Md5Hash("123456","Mark");
        System.out.println(md5Hash.toString());

    }
}
