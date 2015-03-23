<!DOCTYPE html>
<%
import com.jdbernard.nlsongs.servlet.NLSongsContext
import static com.jdbernard.nlsongs.model.ServiceType.*

songsDB = NLSongsContext.songsDB

pathInfo = ((request.pathInfo?:"").split('/') as List).findAll()

if (pathInfo.size() != 1 || !pathInfo[0].isInteger()) {
    response.sendError(response.SC_NOT_FOUND); return }

service = songsDB.findService(pathInfo[0] as int)

if (!service) { response.sendError(response.SC_NOT_FOUND); return }

%>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, user-scalable=no">
        <meta name="referrer" content="origin">
        <link rel="shortcut icon" href="../images/favicon.ico">

        <title><%= service.@date.toString("yyyy-MM-dd")
           %> (<%= service.serviceType.displayName %>) - New Life Songs Database</title>
        <script type="application/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
        <!--<script type="application/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/underscore.js/1.8.2/underscore-min.js"></script>-->
        <!--<script type="application/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/backbone.js/1.1.2/backbone-min.js"></script>-->
        <script type="application/javascript" src="https://cdn.datatables.net/1.10.5/js/jquery.dataTables.js"></script>
        <!--<script type="application/javascript" src="https://cdn.datatables.net/1.10.5/js/jquery.dataTables.min.js"></script>-->
        <!--<script type="application/javascript" src="../js/new-life-songs-@version@.js"></script>-->
        <link href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.3.0/css/font-awesome.css' rel='stylesheet' type='text/css'>
        <link href='http://fonts.googleapis.com/css?family=Roboto+Condensed|Cantarell' rel='stylesheet' type='text/css'>
        <link href='http://cdn.datatables.net/1.10.5/css/jquery.dataTables.css' rel='stylesheet' type='text/css'>
        <link href='../css/new-life-songs-@version@.css' rel='stylesheet' type='text/css'>
    </head>
    <body>
        <header>
            <h1><a href="../">New Life Songs</a></h1>
            <h2 class=service-date><%= service.@date.toString("yyyy-MM-dd") %> (<%=
                    service.serviceType.displayName %>)</h2>

            <nav><ul>
                <li><a href="../admin/">Admin</a></li>
                <li><a href="../songs/">Songs</a></li>
                <li><a href="../services/">Services</a></li>
            </ul></nav>
        </header>
        <section class=service>
            <h2>Performances</h2>
            <table id=performances-table class="row-border dataTable hover compact" cellspacing=0>
                <thead><tr>
                        <th class=actions />
                        <th class="dt-left song-name">Song</th>
                        <th class="dt-left artists">Artists</th>
                        <th class="dt-left not-small">Worship Leader</th>
                        <th class="dt-left not-small">Piano</th>
                        <th class="dt-left not-small">Organ</th>
                        <th class="dt-left not-small">Bass</th>
                        <th class="dt-left not-small">Drums</th>
                        <th class="dt-left not-small">Guitar</th>
                </tr></thead>
                <tbody>
                <% songsDB.findPerformancesForServiceId(service.id).
                    collect { [perf: it, song: songsDB.findSong(it.songId)] }.
                    sort { it.song.name }.each { row -> %>
                    <tr><td class=actions><a href="<%= NLSongsContext.makeUrl(service, row.song) %>"><i class="fa fa-download"></i></a></td>
                        <td class=song-name><a href='../song/<%= row.song.id %>'><%=
                            row.song.name %></a></td>
                        <td class=artists><%= row.song.artists.join(", ") %></td>
                        <td class=not-small><%= row.perf.leader ?: "" %></td>
                        <td class=not-small><%= row.perf.pianist ?: "" %></td>
                        <td class=not-small><%= row.perf.organist ?: "" %></td>
                        <td class=not-small><%= row.perf.bassist ?: "" %></td>
                        <td class=not-small><%= row.perf.drummer ?: "" %></td>
                        <td class=not-small><%= row.perf.guitarist ?: "" %></td></tr><% } %>
                </tbody>
            </table>
        </section>

        <script type="application/javascript">
            window.onload = function() { \$("#performances-table").
                dataTable({ "paging": false }); };
        </script>
    </body>

</html>
