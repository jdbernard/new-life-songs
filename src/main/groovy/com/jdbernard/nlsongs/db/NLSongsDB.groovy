package com.jdbernard.nlsongs.db

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.text.SimpleDateFormat
import com.zaxxer.hikari.HikariDataSource

import groovy.sql.Sql
import groovy.transform.CompileStatic

import com.jdbernard.nlsongs.model.*

//@CompileStatic
public class NLSongsDB {

    private HikariDataSource dataSource
    private Sql sql

    public NLSongsDB(HikariDataSource dataSource) {
        this.dataSource = dataSource
        this.sql = new Sql(dataSource) }


    public void shutdown() { dataSource.shutdown() }

    /// ### Common
    public def save(def model) {
        if (model.id > 0) return update(model)
        else {
            if (create(model) > 0) return model
            else return null } }


    /// ### Services
    public Service findService(int id) {
        def row = sql.firstRow("SELECT * FROM services WHERE id = ?", [id])
        recordToModel(row, Service) }

    public List<Service> findAllServices() {
        return sql.rows("SELECT * FROM services").
            collect { recordToModel(it, Service) } }

    public List<Service> findServicesForSongId(int songId) {
        return sql.rows("SELECT svc.* " +
                        "FROM services svc JOIN " +
                             "performances prf ON " +
                                "svc.id = prf.service_id " +
                        "WHERE prf.song_id = ?", [songId]).
            collect { recordToModel(it, Service) } }

    public List<Service> findServicesAfter(Date d) {
        def sdf = new SimpleDateFormat("YYYY-MM-dd")
        return sql.rows('SELECT * FROM services WHERE "date" > ?',
            [sdf.format(d)]).  collect { recordToModel(it, Service) } }

    public List<Service> findServicesBefore(Date d) {
        def sdf = new SimpleDateFormat("YYYY-MM-dd")
        return sql.rows('SELECT * FROM services WHERE "date" < ?',
            [sdf.format(d)]).collect { recordToModel(it, Service) } }

    public List<Service> findServicesBetween(Date b, Date e) {
        def sdf = new SimpleDateFormat("YYYY-MM-dd")
        return sql.rows('SELECT * FROM services WHERE "date" BETWEEN ? AND ?',
            [sdf.format(b),sdf.format(e)]).
            collect { recordToModel(it, Service) } }

    public Service create(Service service) {
        def sdf = new SimpleDateFormat("YYYY-MM-dd")
        int newId = sql.executeInsert(
            'INSERT INTO services ("date", service_type) VALUES (?, ?)',
            [sdf.format(service.date), service.serviceType])[0][0]

        service.id = newId
        return service }

    public int update(Service service) {
        def sdf = new SimpleDateFormat("YYYY-MM-dd")
        return sql.executeUpdate(
            'UPDATE services SET "date" = ?, service_type = ? WHERE id = ?',
            [sdf.format(service.date), service.serviceType, service.id] ) }

    public int delete(Service service) {
        sql.execute("DELETE FROM services WHERE id = ?", [service.id])
        return sql.updateCount }

    /// ### Songs
    public Song findSong(int id) {
        def row = sql.firstRow("SELECT * FROM songs WHERE id = ?", [id])
        return recordToModel(row, Song) }

    public List<Song> findAllSongs() {
        return sql.rows("SELECT * FROM songs").
            collect { recordToModel(it, Song) } }

    public List<Song> findSongsForServiceId(int serviceId) {
        return sql.rows("SELECT sng.* " +
                        "FROM songs sng JOIN " +
                             "performances prf ON " +
                                "sng.id = prf.song_id " +
                        "WHERE prf.service_id = ?", [serviceId]).
            collect { recordToModel(it, Song) } }

    public List<Song> findSongsByName(String name) {
        return sql.rows("SELECT * FROM songs WHERE name = ?", [name]).
            collect { recordToModel(it, Song) } }

    public List<Song> findSongsLikeName(String name) {
        return sql.rows("SELECT * FROM songs WHERE name LIKE ?", ["%$name%"]).
            collect { recordToModel(it, Song) } }

    public List<Song> findSongsByArtist(String artist) {
        return sql.rows("SELECT * FROM songs WHERE artists LIKE ?", ["%$artist%"]).
            collect { recordToModel(it, Song) } }

    public List<Song> findSongsByNameAndArtist(String name, String artist) {
        return sql.rows("SELECT * FROM songs WHERE name = ? AND artists LIKE ?",
            [name,"%$artist%"]).collect { recordToModel(it, Song) } }

    public Song create(Song song) {
        int newId = sql.executeInsert(
            "INSERT INTO songs (name, artists) VALUES (?, ?)",
            [song.name, wrapArtists(song.artists)])[0][0]

        song.id = newId
        return song }

    public int update(Song song) {
        return sql.executeUpdate(
            "UPDATE songs SET name = ?, artists = ? WHERE id = ?",
            [song.name, wrapArtists(song.artists), song.id] ) }

    public int delete(Song song) {
        sql.execute("DELETE FROM songs WHERE id = ?", [song.id])
        return sql.updateCount }

    /// ### Performances
    public Performance findPerformance(int serviceId, int songId) {
        def perf = sql.firstRow(
            "SELECT * FROM performances WHERE service_id = ? AND song_id = ?",
            [serviceId, songId])
        return recordToModel(perf, Performance) }

    public List<Performance> findPerformancesForServiceId(int serviceId) {
        return sql.rows("SELECT * FROM performances WHERE service_id = ?",
            [serviceId]).collect { recordToModel(it, Performance) } }

    public List<Performance> findPerformancesForSongId(int songId) {
        return sql.rows("SELECT * FROM performances WHERE song_id = ?",
            [songId]).collect { recordToModel(it, Performance) } }

    public Performance create(Performance perf) {
        // TODO: handle constraint violation (same service and song ids)
        sql.executeInsert(
            "INSERT INTO performances (service_id, song_id, pianist, " +
            "organist, bassist, drummer, guitarist, leader) VALUES " +
            "(?, ?, ?, ?, ?, ?, ?, ?)", [perf.serviceId, perf.songId,
            perf.pianist, perf.organist, perf.bassist, perf.drummer,
            perf.guitarist, perf.leader])
        return perf }

    public int update(Performance perf) {
        // TODO: handle constraint violation (same service and song ids)
        return sql.executeUpdate(
            "UPDATE performances SET pianist = ?, organist = ?, " +
            "bassist = ?, drummer = ?, guitarist = ?, leader = ? " +
            "WHERE service_id = ? AND song_id = ?",
            [perf.pianist, perf.organist, perf.bassist, perf.drummer,
            perf.guitarist, perf.leader, perf.serviceId, perf.songId]) }

    public int delete(Performance perf) {
        sql.execute(
            "DELETE FROM performances WHERE service_id = ? AND song_id = ?",
            [perf.service_id, perf.song_id] )
        return sql.updateCount }

    /// ### Users
    public List<User> findAllUsers() {
        return sql.rows("SELECT * FROM users").
            collect { buildUser(it); } }
        
    public User findUser(String username) {
        def row = sql.firstRow("SELECT * FROM users WHERE username = ?",
            [username])
        return buildUser(row) }

    public User save(User user) {
        if (findUser(user.username)) {
            update(user); return user }
        else return create(user) }

    public User create(User user) {
        int newId = sql.executeInsert(
            "INSERT INTO users (username, pwd, role) VALUES (?, ?, ?)",
            [user.username, user.pwd, user.role])[0][0]

        user.id = newId
        return user }

    public int update(User user) {
        return sql.executeUpdate(
            "UPDATE user SET username = ?, pwd = ?, role = ? WHERE id = ?",
            [user.username, user.pwd, user.role, user.id]) }

    public int delete(User user) {
        sql.execute("DELETE FROM users WHERE username = ?")
        return sql.updateCount }

    private static User buildUser(def row) {
        if (!row) return null

        User user = new User(username: row["username"], role: row["role"])
        user.@pwd = row["pwd"]

        return user; }

    /// ### Tokens
    public Token findToken(String token) {
        def row = sql.firstRow("""\
            SELECT t.*, u.*
            FROM
                tokens t JOIN
                users u ON
                    t.user_id = u.id
            WHERE t.token = ?""", [token])
        return buildToken(row) }

    public Token renewToken(Token token) {
        def foundToken = findToken(token.token);

        // If the token has expired we will not renew it.
        if (new Date() > token.expires) return null;

        // Otherwise, renew and return the new values.
        assert sql.executeUpdate("UPDATE tokens SET " +
            "expires = current_timestamp + interval '1 day' WHEREtoken = ?", 
            [token.token]) == 1

        def updatedToken = findToken(token.token);
        token.expires = updatedToken.expires;
        return token; }
        
    public Token save(Token token) {
        if (findToken(token.token)) {
            update(token); return token }
        else return create(token) }

    public Token create(Token token) {
        sql.executeInsert("INSERT INTO tokens VALUES (?, ?, ?)",
            [token.token, token.user.id, token.expires])
        return Token }

    public int update(Token token) {
        return sql.executeUpdate(
            "UPDATE tokens SET expires = ? WHERE token = ?",
            [token.expires, token.token]) }

    public int delete(Token token) {
        sql.execute("DELETE FROM tokens WHERE token = ?", [token.token])
        return sql.updateCount }

    /// ### Utility functions
    static def recordToModel(def record, Class clazz) {
        if (record == null) return null

        def model = clazz.newInstance()

        record.each { recordKey, v ->
            def pts = recordKey.split('_')
            def modelKey = pts.length == 1 ? pts[0] :
                pts[0] + pts[1..-1].collect { it.capitalize() }.join()

            // Hacky, there should be a better way
            if (recordKey == "artists") v = unwrapArtists(v);

            model[modelKey] = v }
        return model }

    static def modelToRecord(def model) {
        if (model == null) return null

        def record = [:]

        model.properties.each { modelKey, v ->
            if (modelKey == "class") return
            def recordKey = modelKey.
                replaceAll(/(\p{javaUpperCase})/, /_$1/).toLowerCase()

            // Hack
            if (modelKey == "artists") v = wrapArtists(v)

            record[recordKey] = v }
        return record }

    private static Token buildToken(def row) {
        if (!row) return null

        User user = buildUser(row)
        assert user != null

        return new Token(
            token: row["token"], user: user, expires: row["expires"]) }

    public static List<String> unwrapArtists(String artists) {
        return artists.split(';') as List<String> }

    public static String wrapArtists(List<String> artists) {
        return artists.join(':') }
    /*
    static Object recordToModel(GroovyRowResult row, Class clazz) {
        Object model = clazz.newInstance()

        row.each { recordKey, v ->
            String[] pts = ((String) recordKey).split('_')
            String modelKey = pts[0] +
                pts[1..-1].collect { it.capitalize() }.join()
            model[modelKey] = v }
    }
    */
}
