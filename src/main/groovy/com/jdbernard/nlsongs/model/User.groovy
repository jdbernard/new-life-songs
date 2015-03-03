package com.jdbernard.nlsongs.model

import com.lambdaworks.crypto.SCryptUtil

public class User {

    int id
    String username
    String pwd
    Role role

    public void setPwd(String pwd) {
        this.pwd = SCryptUtil.scrypt(pwd, 16384, 16, 1) }

    public boolean checkPwd(String givenPwd) {
        return SCryptUtil.check(this.pwd, givenPwd) }
}
