package com.jdbernard.nlsongs.model

public class Token implements Serializable {

    String token
    User user
    Date expires

    @Override
    public boolean equals(Object thatObj) {
        if (thatObj == null) return false
        if (!(thatObj instanceof Token)) return false

        Token that = (Token) thatObj

        return (this.token == that?.token) }
}
