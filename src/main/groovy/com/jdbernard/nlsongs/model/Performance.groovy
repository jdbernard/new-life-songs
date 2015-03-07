package com.jdbernard.nlsongs.model

public class Performance implements Serializable {

    int serviceId
    int songId
    String pianist
    String organist
    String bassist
    String drummer
    String guitarist
    String leader

    @Override public boolean equals(Object thatObj) {
        if (thatObj == null) return false
        if (!(thatObj instanceof Performance)) return false

        Performance that = (Performance) thatObj

        return (this.serviceId == that.serviceId &&
                this.songId == that.songId &&
                this.pianist == that.pianist &&
                this.organist == that.organist &&
                this.bassist == that.bassist &&
                this.drummer == that.drummer &&
                this.guitarist == that.guitarist &&
                this.leader == that.leader) }

    @Override String toString() {
        return "($serviceId, $songId): $leader - $pianist" }
}
