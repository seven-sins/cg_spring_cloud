package com.cg.shiro.config.token;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author Rex.Tan
 * 2019年3月26日 上午11:07:57
 */
public class JWTToken implements AuthenticationToken {
	
	private static final long serialVersionUID = 132702428094130784L;
	
    private String token;

    public JWTToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
