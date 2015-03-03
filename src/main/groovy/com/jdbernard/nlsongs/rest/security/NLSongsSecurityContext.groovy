package com.jdbernard.nlsongs.rest.security

import java.security.Principal
import javax.ws.rs.container.ContainerRequestContext
import javax.ws.rs.core.SecurityContext

import com.jdbernard.nlsongs.model.Role
import com.jdbernard.nlsongs.model.Token
import com.jdbernard.nlsongs.servlet.NLSongsContext

public class NLSongsSecurityContext implements SecurityContext {

    public final TokenPrincipal principal

    public NLSongsSecurityContext(ContainerRequestContext ctx) {

        // Extract the authentication token (if present)
        String tokenVal = ctx.getHeaderString("Authorization-Token")

        // Look up the token in the database.
        Token token = NLSongsContext.songsDB.findToken(tokenVal)

        // Create our principal based on this token.
        this.principal = new TokenPrincipal(token)
    }

    @Override
    public String getAuthenticationScheme() { return "Authentication-Token" }

    @Override
    public Principal getUserPrincipal() { return principal }

    @Override
    public boolean isSecure() { /* TODO */ return false }

    @Override
    public boolean isUserInRole(String role) {
        println "Required Role: $role"
        println "User's Role: ${principal?.token?.user?.role}"
        println "Required Role == User's Role? ${principal?.token?.user?.role == ((Role)role)} "
        return principal?.token?.user?.role == ((Role) role) }
}

