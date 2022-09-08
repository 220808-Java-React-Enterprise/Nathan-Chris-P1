package com.revature.services;

import org.junit.Test;

public class TokenServiceTest {
    @Test
    public void test_generateToken_Succeed(){
        String username = "christhewizard";
        TokenService.generateToken(username);
    }
}
