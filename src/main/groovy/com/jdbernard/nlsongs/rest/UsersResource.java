package com.jdbernard.nlsongs.rest;

import java.util.Date;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.annotation.security.PermitAll;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import com.jdbernard.nlsongs.servlet.NLSongsContext;
import com.jdbernard.nlsongs.model.User;
import com.jdbernard.nlsongs.model.UserCredentials;
import com.jdbernard.nlsongs.model.Token;

import static javax.ws.rs.core.Response.Status.*;

@Path("v1/users") @AllowCors @PermitAll
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class UsersResource {

    @Context SecurityContext secCtx;

    @GET @RolesAllowed("admin")
    public List<User> getUsers() {
        return NLSongsContext.songsDB.findAllUsers(); }

    @POST @RolesAllowed("admin")
    public User postUser(User user) {
        return NLSongsContext.songsDB.create(user); }

    @GET @Path("/{username}")
    public Response getUser(@PathParam("username") String username) {

        // If they are looking up their own information, OK.
        if (username == secCtx.getUserPrincipal().getName() ||
            // Or if they are an admin, OK.
            secCtx.isUserInRole("admin")) {

            return Response.ok(
                NLSongsContext.songsDB.findUser(username)).build(); }

        else return Response.status(FORBIDDEN).build(); }


    @PUT @Path("/{username}")
    public Response putUser(@PathParam("username") String username, User user) {

        // If they are looking up their own information, OK.
        if (username == secCtx.getUserPrincipal().getName() ||
            // Or if they are an admin, OK.
            secCtx.isUserInRole("admin")) {

            NLSongsContext.songsDB.update(user);

            return Response.ok(user).build(); }

        else return Response.status(FORBIDDEN).build(); }

    @DELETE @Path("/{username}")
    public Response deleteUser(@PathParam("username") String username) {

        // If they are looking up their own information, OK.
        if (username == secCtx.getUserPrincipal().getName() ||
            // Or if they are an admin, OK.
            secCtx.isUserInRole("admin")) {

            User user = NLSongsContext.songsDB.findUser(username);

            if (user != null) NLSongsContext.songsDB.delete(user);

            return Response.ok(user).build(); }

        else return Response.status(FORBIDDEN).build(); }

    @POST @Path("/login")
    public Response postLogin(UserCredentials cred) {
        User user = NLSongsContext.songsDB.findUser(cred.getUsername());
        if (!user.checkPwd(cred.getPassword())) {
            return Response.status(UNAUTHORIZED).build(); }
        else {
            // Look for a token already belonging to this user.
            Token token = NLSongsContext.songsDB.findTokenForUser(user);

            // If there is no token, create a new one.
            if (token == null) token = new Token(user);

            // If the token has expired, delete it and create a new one.
            else if (token.getExpires().compareTo(new Date()) < 0) {
                NLSongsContext.songsDB.delete(token);
                token = new Token(user); }

            // If the token exists and is still good refresh it and keep using
            // it.
            else token.refresh();

            // Save our updated token and return it.
            NLSongsContext.songsDB.save(token);

            return Response.ok(token).build(); } }
}
