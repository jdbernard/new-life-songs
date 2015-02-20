import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import com.jdbernard.nlsongs.db.NLSongsDB
import com.jdbernard.nlsongs.model.*
import java.text.SimpleDateFormat

sdf = new SimpleDateFormat('yyyy-MM-dd')
hcfg = new HikariConfig("/home/jdbernard/projects/new-life-songs/src/main/webapp/WEB-INF/classes/newlifesongs.properties")

makeService = { svcRow ->
  Service svc = new Service()
  svc.date = sdf.parse(svcRow.date)
  svc.serviceType = svcRow.serviceType
  return svc }

pushService = { svcRow ->
  Service svc = makeService(svcRow)
  svc = songsDB.create(svc)
  svcRow.newId = svc.id
  return svc.id }

makeSong = { songRow ->
  Song song = new Song()
  song.name = songRow.name
  song.artists = songRow.artists
  return song }

pushSong = { songRow ->
  Song song = makeSong(songRow)
  song = songsDB.create(song)
  songRow.newId = song.id
  return song.id }

makePerformance = { perfRow ->
    Performance perf = new Performance()
    perfRow.each { k, v -> perf[k] = v }

    // Replace with new DB ids
    perf.serviceId = services.find { it.id == perf.serviceId }.newId
    perf.songId = songs.find { it.id == perf.songId }.newId
    return perf }

pushPerformance = { perfRow ->
    Performance perf = makePerformance(perfRow)
    return songsDB.create(perf) }

makeSongsDB = {
    hds = new HikariDataSource(hcfg)
    songsDB = new NLSongsDB(hds)
    return songsDB }
