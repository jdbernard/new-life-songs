package com.jdbernard.nlsongs.rest;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

@Provider @AllowCors @Priority(Priorities.HEADER_DECORATOR)
public class CorsResponseFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext reqCtx,
                       ContainerResponseContext respCtx) {

        MultivaluedMap<String, Object> headers = respCtx.getHeaders();

        headers.add("Access-Control-Allow-Origin",
            reqCtx.getHeaderString("Origin"));

        headers.add("Access-Control-Allow-Methods",
            "GET, POST, PUT, DELETE, OPTIONS");

        headers.add("Access-Control-Allow-Headers",
            reqCtx.getHeaderString("Access-Control-Request-Headers"));

    }
}
