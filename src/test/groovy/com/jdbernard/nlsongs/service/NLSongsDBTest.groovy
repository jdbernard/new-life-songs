package com.jdbernard.nlsongs.service

import com.jdbernard.nlsongs.db.NLSongsDB
import com.jdbernard.nlsongs.model.*
import com.jdbernard.nlsongs.servlet.NLSongsContext
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource

import groovy.sql.Sql

import java.text.SimpleDateFormat

import org.junit.After
import org.junit.AfterClass
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test

import static org.junit.Assert.*
import static com.jdbernard.nlsongs.model.ServiceType.*

import org.slf4j.Logger
import org.slf4j.LoggerFactory

public class NLSongsDBTest {

    static NLSongsDB songsDB;
    static Sql sql
    static Logger log = LoggerFactory.getLogger(NLSongsDBTest)

    def dateFormat
    def services
    def songs
    def performances

    /// ### Setup

    public NLSongsDBTest() {

        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd")
        this.services = [
            [1, '2015-02-01', SUN_AM],
            [2, '2015-02-01', SUN_PM],
            [3, '2015-02-04', WED],
            [4, '2015-02-08', SUN_AM],
            [5, '2015-02-08', SUN_PM],
            [6, '2015-02-11', WED],
            [7, '2015-02-15', SUN_AM],
            [8, '2015-02-15', SUN_PM]].collect {

            new Service(id: it[0],
                        date: dateFormat.parse(it[1]),
                        serviceType: it[2]) }

        this.songs = [
            [1, 'Breathe On Us', ['Kari Jobe']],
            [2, 'How Great Is Our God', ['Chris Tomlin']],
            [3, 'Glorious', ['Martha Munizzi']],
            [4, 'Rez Power', ['Israel Houghton']]].collect {

            new Song(id: it[0], name: it[1], artists: it[2]) }

        this.performances =  [
            [1, 1, 'Jared Wood', null, 'Jonathan Bernard', 'Christian Thompson', 'Andrew Fraiser; Tony Bagliore', 'Rachel Wood'],
            [1, 2, 'Jared Wood', null, 'Jonathan Bernard', 'Christian Thompson', 'Andrew Fraiser; Tony Bagliore', 'Rachel Wood'],
            [1, 3, 'Jared Wood', null, 'Jonathan Bernard', 'Christian Thompson', 'Andrew Fraiser; Tony Bagliore', 'Rachel Wood'],
            [2, 2, 'Trevor Delano', 'Connie Bernard', 'Jonathan Bernard', 'Christian Thompson', 'Andrew Fraiser', 'Rachel Wood'],
            [2, 3, 'Trevor Delano', 'Connie Bernard', 'Jonathan Bernard', 'Christian Thompson', 'Andrew Fraiser', 'Rachel Wood'],
            [2, 4, 'Trevor Delano', 'Connie Bernard', 'Jonathan Bernard', 'Christian Thompson', 'Andrew Fraiser', 'Rachel Wood'],
            [3, 1, 'Rachel Wood', 'Krista Hatcher', 'Jonathan Bernard', 'Jared Wood', 'Tony Bagliore', 'Rachel Wood'],
            [3, 2, 'Rachel Wood', 'Krista Hatcher', 'Jonathan Bernard', 'Jared Wood', 'Tony Bagliore', 'Rachel Wood'],
            [4, 3, 'Trevor Delano', null, 'Jonathan Bernard', 'Christian Thompson', 'Andrew Fraiser', 'Rachel Wood'],
            [5, 4, 'Jared Wood', null, 'Jonathan Bernard', 'Christian Thompson', 'Tony Bagliore', 'Rachel Wood'],
            [6, 1, 'Jared Wood', null, 'Jonathan Bernard', 'Christian Thompson', 'Andrew Fraiser; Tony Bagliore', 'Rachel Wood'],
            [7, 2, 'Trevor Delano', null, 'Jonathan Bernard', 'Christian Thompson', 'Andrew Fraiser; Tony Bagliore', 'Rachel Wood'],
            [8, 3, 'Jared Wood', null, 'Jonathan Bernard', 'Christian Thompson', 'Andrew Fraiser; Tony Bagliore', 'Rachel Wood'] ].collect {

            new Performance(serviceId: it[0], songId: it[1], pianist: it[2],
                organist: it[3], bassist: it[4], drummer: it[5],
                guitarist: it[6], leader: it[7]) }
    }

    @BeforeClass
    public static void setupDB() {

        // Create Hikari datasource
        HikariConfig hcfg = new HikariConfig(
            "src/test/webapp/WEB-INF/classes/datasource.test.properties")

        HikariDataSource dataSource = new HikariDataSource(hcfg)

        // Create NLSongsDB
        this.songsDB = new NLSongsDB(dataSource)
        this.sql = new Sql(dataSource)

        // Set NLSongsContext
        NLSongsContext.songsDB = songsDB }

    @AfterClass
    public static void tearDownDB() {
        if (NLSongsContext.songsDB)
            NLSongsContext.songsDB.shutdown() }

    @Before
    public void initData() {
        // Get the DB Schema and test data.
        File createSchemaSql = new File("src/main/sql/create-tables.sql")
        File testDataSql = new File("resources/test/testdb.init.sql")

        // Create the DB Schema
        sql.execute(createSchemaSql.text)

        // Populate the DB with test data.
        sql.execute(testDataSql.text) }

    /// ### Services

    @Test public void shouldCreateService() {
        def service = new Service(
            date: new Date(), serviceType: ServiceType.SUN_AM)

        def newService = songsDB.create(service)

        assertTrue(service == songsDB.findService(newService.id)) }

    @Test public void shouldFindServiceById() {
        assertTrue(songsDB.findService(1) == services[0]) }

    @Test public void shouldListAllServices() {
        assertCollectionsEqual(services, songsDB.findAllServices()) }

    @Test public void shouldFindServicesForSongId() {
        def foundPerfs = performances.findAll { it.songId == 1}
        def foundServices = services.findAll { svc ->
            foundPerfs.any { p -> p.serviceId == svc.id } }

        assertCollectionsEqual(
            songsDB.findServicesForSongId(1),
            foundServices) }

    @Test public void shouldFindServicesAfter() {
        Date d = dateFormat.parse('2015-02-08')
        def foundServices = songsDB.findServicesAfter(d)
        assertCollectionsEqual(foundServices, services.findAll { it.date > d })
        assertEquals(foundServices.size(), 3) }

    @Test public void shouldFindServicesBefore() {
        Date d = dateFormat.parse('2015-02-08')
        def foundServices = songsDB.findServicesBefore(d)
        assertCollectionsEqual(foundServices, services.findAll { it.date < d })
        assertEquals(foundServices.size(), 3) }

    @Test public void shouldFindServicesBetween() {
        Date b = dateFormat.parse('2015-02-05')
        Date e = dateFormat.parse('2015-02-09')
        def foundServices = songsDB.findServicesBetween(b, e)
        assertCollectionsEqual(foundServices, services.findAll {
            it.date > b && it.date < e })
        assertEquals(foundServices.size(), 2) }

    @Test public void shouldUpdateService() {
        // Find the service
        def service = songsDB.findService(1)

        // Update it
        service.date = dateFormat.parse('2015-01-01')
        songsDB.update(service)

        // Check it
        assertTrue(service == songsDB.findService(1)) }

    @Test public void shouldDeleteService() {
        songsDB.delete(services[0])

        assertCollectionsEqual(
            services - services[0], songsDB.findAllServices())

        assertCollectionsEqual(
            performances.findAll { it.serviceId != 1 },
            songsDB.findAllPerformances()) }
    /// ### Songs

    @Test public void shoudCreateSong() {
        def song = new Song(name: "Test Song", artists: ["Bob Sam"])
        def newSong = songsDB.create(song)

        assertTrue(song == songsDB.findSong(newSong.id)) }

    @Test public void shoudUpdateSong() {
        def song = songsDB.findSong(1)

        song.name += " - Test"
        songsDB.update(song)

        assertTrue(song == songsDB.findSong(1)) }

    @Test public void shouldFindSongById() {
        assertTrue(songsDB.findSong(1) == songs[0]) }

    @Test public void shouldListAllSongs() {
        assertCollectionsEqual(songs, songsDB.findAllSongs()) }

    @Test public void shouldFindSongsForService() {
        def foundPerfs = performances.findAll { it.serviceId == 1}
        def foundSongs = songs.findAll { song ->
            foundPerfs.any { p -> p.songId == song.id } }

        assertCollectionsEqual(
            foundSongs,
            songsDB.findSongsForServiceId(1)) }

    @Test public void shouldFindSongsByName() {
        assertCollectionsEqual(
            songsDB.findSongsByName('Glorious'),
            songs.findAll { it.name == 'Glorious' }) }

    @Test public void shouldFindSongsLikeName() {
        assertCollectionsEqual(
            songsDB.findSongsLikeName('G'),
            songs.findAll { it.name =~ 'G' }) }

    @Test public void shouldFindSongsByArtist() {
        assertCollectionsEqual(
            songs.findAll { s ->
                s.artists.any { a -> a =~ 'Chris' } },
            songsDB.findSongsByArtist('Chris')) }

    @Test public void shouldFindSongsByNameAndArtist() {
        assertCollectionsEqual(
            songs.findAll { s ->
                s.artists.any { a -> a =~ 'Chris'} &&
                s.name == 'How Great Is Our God' },
            songsDB.findSongsByNameAndArtist('How Great Is Our God', 'Chris')) }

    @Test public void shouldDeleteSong() {
        songsDB.delete(songs[0])

        assertCollectionsEqual(
            songs - songs[0], songsDB.findAllSongs())

        assertCollectionsEqual(
            performances.findAll { it.songId != 1 },
            songsDB.findAllPerformances()) }

    private void assertCollectionsEqual(Collection c1, Collection c2) {
        log.info("C1: $c1")
        log.info("C2: $c2")
        assertEquals(c1.size(), c2.size())

        assertTrue(c1.every { c2.contains(it) }) }
}
