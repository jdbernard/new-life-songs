package com.jdbernard.nlsongs.model

public class Song implements Serializable {

    int id
    String name
    List<String> artists

    @Override public boolean equals(Object thatObj) {
        if (thatObj == null) return false
        if (!(thatObj instanceof Song)) return false

        Song that = (Song) thatObj
        return (this.id == that.id &&
                this.name == that.name &&
                this.artists == that.artists) }

    @Override public String toString() { return "$id: $name ($artists)" }
}
