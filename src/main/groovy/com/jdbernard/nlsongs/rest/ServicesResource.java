package com.jdbernard.nlsongs.rest;

import java.util.Date;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.jdbernard.nlsongs.servlet.NLSongsContext;
import com.jdbernard.nlsongs.model.Service;

@Path("v1/services") @AllowCors
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class ServicesResource {

    @GET
    public List<Service> getServices() {
        return NLSongsContext.songsDB.findAllServices(); }

    @POST
    public Service postService(Service service) {
        return NLSongsContext.songsDB.create(service); }

    @GET @Path("/{serviceId}")
    public Service getService(@PathParam("serviceId") int serviceId) {
        return NLSongsContext.songsDB.findService(serviceId); }

    @PUT @Path("/{serviceId}")
    public Service putService(@PathParam("serviceId") int serviceId,
    Service service) {
        service.setId(serviceId);
        NLSongsContext.songsDB.update(service);
        return service; }

    @GET @Path("/withSong/{songId}")
    public List<Service> getServicesForSong(@PathParam("songId") int songId) {
        return NLSongsContext.songsDB.findServicesForSongId(songId); }

    @GET @Path("/byDate/after/{date}")
    public List<Service> getServicesAfter(@PathParam("date") Date date) {
        return NLSongsContext.songsDB.findServicesAfter(date); }

    @GET @Path("/byDate/before/{date}")
    public List<Service> getServicesBefore(@PathParam("date") Date date) {
        return NLSongsContext.songsDB.findServicesBefore(date); }

    @GET @Path("/byDate/between/{b}/{e}")
    public List<Service> getServicesBetween
    (@PathParam("b") Date b, @PathParam("e") Date e) {
        return NLSongsContext.songsDB.findServicesBetween(b, e); }
}
