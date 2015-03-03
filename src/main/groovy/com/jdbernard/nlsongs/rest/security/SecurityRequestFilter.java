package com.jdbernard.nlsongs.rest.security;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;

import com.jdbernard.nlsongs.servlet.NLSongsContext;

@Provider
@PreMatching
public class SecurityRequestFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext reqCtx) {
        reqCtx.setSecurityContext(new NLSongsSecurityContext(reqCtx)); }
}
