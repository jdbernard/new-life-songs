package com.jdbernard.nlsongs.rest.security;

import java.security.Principal;

import com.jdbernard.nlsongs.model.Token;

public class TokenPrincipal implements Principal {
    public final Token token;

    public TokenPrincipal(Token token) { this.token = token; }

    @Override
    public boolean equals(Object thatObj) {
        if (thatObj == null) return false;
        if (!(thatObj instanceof TokenPrincipal)) return false;

        TokenPrincipal that = (TokenPrincipal) thatObj;

        if (this.token == null) { return that.token == null; }
        else { return this.token.equals(that.token); } }

    @Override
    public String getName() {
        if (this.token == null) return null;
        else return this.token.getUser().getUsername(); }

    @Override
    public int hashCode() {
        if (this.token == null) return 0;
        return this.token.getUser().getUsername().hashCode(); }

    @Override
    public String toString() { return getName(); }
}

