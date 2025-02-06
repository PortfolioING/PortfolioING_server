package com.example.PING.security.token;

import java.util.Collection;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {
// Todo 의존성 추가해야 함
    private String jsonWebToken;
    private Object principal;
    private Object credentials;

    public JwtAuthenticationToken(String jsonWebToken) {
        super(null);
        this.jsonWebToken = jsonWebToken;
        this.setAuthenticated(false);
    }

    public JwtAuthenticationToken(Object principal, Object credentials,
                                  Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        super.setAuthenticated(true);
    }

    public Object getCredentials() {
        return credentials;
    }

    public Object getPrincipal() {
        return this.principal;
    }

    public String getJsonWebToken() {
        return this.jsonWebToken;
    }
}
