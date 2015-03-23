<!DOCTYPE html>
<%
import com.jdbernard.nlsongs.servlet.NLSongsContext
import static com.jdbernard.nlsongs.model.ServiceType.*

songsDB = NLSongsContext.songsDB

pathInfo = ((request.pathInfo?:"").split('/') as List).findAll()

if (pathInfo.size() != 1 || !pathInfo[0].isInteger()) {
    response.sendError(response.SC_NOT_FOUND); return }

song = songsDB.findSong(pathInfo[0] as int)

if (!song) { response.sendError(response.SC_NOT_FOUND); return }

%>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, user-scalable=no">
        <meta name="referrer" content="origin">
        <link rel="shortcut icon" href="../images/favicon.ico">

        <title><%= song.name %> - New Life Songs Database</title>
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
            <h2 class=song-name><%= song.name %></h2><%
            if (song.artists.findAll().size() > 0) {
            %><h3>by <%= song.artists.join(", ") %></h3> <% } %>

            <nav><ul>
                <li><a href="../admin/">Admin</a></li>
                <li><a href="../songs/">Songs</a></li>
                <li><a href="../services/">Services</a></li>
            </ul></nav>
        </header>
        <section class=song>
            <h2>Performances</h2>
            <table id=performances-table class="row-border dataTable hover compact" cellspacing=0>
                <thead><tr>
                        <th class=actions />
                        <th class="dt-left performance-date">Date</th>
                        <th class="dt-left service-type">Service Type</th>
                        <th class="dt-left not-small">Worship Leader</th>
                        <th class="dt-left not-small">Piano</th>
                        <th class="dt-left not-small">Organ</th>
                        <th class="dt-left not-small">Bass</th>
                        <th class="dt-left not-small">Drums</th>
                        <th class="dt-left not-small">Guitar</th>
                </tr></thead>
                <tbody>
                <% songsDB.findPerformancesForSongId(song.id).
                    collect { [perf: it, svc: songsDB.findService(it.serviceId)] }.
                    sort { it.svc.date }.each { row -> %>
                    <tr><td class=actions><a href='<%= NLSongsContext.makeUrl(row.svc, song) %>'><i class="fa fa-download"></i></a></td>
                        <td class=performance-date><a href='../service/<%= row.svc.id %>'><%= 
                                row.svc.@date.toString("yyyy-MM-dd") %></a></td>
                        <td class=service-type><%= row.svc.serviceType.displayName %></td>
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
