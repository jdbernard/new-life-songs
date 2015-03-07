package com.jdbernard.nlsongs.model

public class Token implements Serializable {

    public static final long EXPIRY_WINDOW = 1000 * 60 * 60 * 24;

    String token
    User user
    Date expires

    public Token(Map namedArgs) {
        if (!namedArgs.user) throw new IllegalArgumentException(
            "Cannot create Token object: missing user property.")

        if (namedArgs.expire) this.expires = namedArgs.expires
        else this.expires = new Date((new Date()).time + EXPIRY_WINDOW)

        if (namedArgs.token) this.token = namedArgs.token
        else this.token = UUID.randomUUID().toString() }

    public Token(User user) { this([user: user]) }

    public void refresh() { this.expires = new Date((new Date()).time + EXPIRY_WINDOW) }

    @Override
    public boolean equals(Object thatObj) {
        if (thatObj == null) return false
        if (!(thatObj instanceof Token)) return false

        Token that = (Token) thatObj

        return (this.token == that?.token) }
}
