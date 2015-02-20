package com.jdbernard.nlsongs.rest

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("v1/ping")
public class PingResource {

    @GET
    @Produces("text/plain")
    public String ping() { return "pong" } }
