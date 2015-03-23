package com.jdbernard.nlsongs.rest;

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
import javax.ws.rs.core.MediaType;

import com.jdbernard.nlsongs.servlet.NLSongsContext;
import com.jdbernard.nlsongs.model.Song;

@Path("v1/songs") @AllowCors
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class SongsResource {

    @GET
    public List<Song> getSongs() {
        return NLSongsContext.songsDB.findAllSongs(); }

    @POST @RolesAllowed("admin")
    public Song postSong(Song song) {
        return NLSongsContext.songsDB.create(song); }

    @GET @Path("/{songId}")
    public Song getSong(@PathParam("songId") int songId) {
        return NLSongsContext.songsDB.findSong(songId); }

    @PUT @Path("/{songId}") @RolesAllowed("admin")
    public Song putSong(@PathParam("songId") int songId, Song song) {
        song.setId(songId);
        NLSongsContext.songsDB.update(song);
        return song; }

    @DELETE @Path("/{songId}") @RolesAllowed("admin")
    public Song deleteSong(@PathParam("songId") int songId) {
        Song song = NLSongsContext.songsDB.findSong(songId);

        if (song != null) { NLSongsContext.songsDB.delete(song); }

        return song; }

    @GET @Path("/forService/{serviceId}")
    public List<Song> getSongsForService(@PathParam("serviceId") int serviceId) {
        return NLSongsContext.songsDB.findSongsForServiceId(serviceId); }

    @GET @Path("/byArtist/{artist}")
    public List<Song> getSongsForArtist(@PathParam("artist") String artist) {
        return NLSongsContext.songsDB.findSongsByArtist(artist); }

}
