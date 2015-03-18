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
        <link rel="shortcut icon" href="../images/favicon.ico">

        <title><%= service.@date.toString("yyyy-MM-dd")
           %> (<%= service.serviceType%>) - New Life Songs Database</title>
        <script type="application/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
        <!--<script type="application/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/underscore.js/1.8.2/underscore-min.js"></script>-->
        <!--<script type="application/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/backbone.js/1.1.2/backbone-min.js"></script>-->
        <script type="application/javascript" src="https://cdn.datatables.net/1.10.5/js/jquery.dataTables.js"></script>
        <!--<script type="application/javascript" src="https://cdn.datatables.net/1.10.5/js/jquery.dataTables.min.js"></script>-->
        <!--<script type="application/javascript" src="../js/new-life-songs-@version@.js"></script>-->
        <link href='http://fonts.googleapis.com/css?family=Roboto+Condensed|Roboto|Lato|Cuprum|Dosis|Cantarell' rel='stylesheet' type='text/css'>
        <link href='http://cdn.datatables.net/1.10.5/css/jquery.dataTables.css' rel='stylesheet' type='text/css'>
        <link href='../css/new-life-songs-@version@.css' rel='stylesheet' type='text/css'>
    </head>
    <body>
        <header>
            <h1>New Life Songs</h1>
            <h2><%= service.@date.toString("yyyy-MM-dd") %> (<%=
                    service.serviceType %>)</h2>

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
                        <th class="dt-left">Song</th>
                        <th class="dt-left">Artists</th>
                        <th class="dt-left">Worship Leader</th>
                        <th class="dt-left">Piano</th>
                        <th class="dt-left">Organ</th>
                        <th class="dt-left">Bass</th>
                        <th class="dt-left">Drums</th>
                        <th class="dt-left">Guitar</th>
                </tr></thead>
                <tbody>
                <% songsDB.findPerformancesForServiceId(service.id).
                    collect { [perf: it, song: songsDB.findSong(it.songId)] }.
                    sort { it.song.name }.each { row -> %>
                    <tr><td><a href='../song/<%= row.song.id %>'><%=
                            row.song.name %></a></td>
                        <td><%= row.song.artists.join(", ") %></td>
                        <td><%= row.perf.leader ?: "" %></td>
                        <td><%= row.perf.pianist ?: "" %></td>
                        <td><%= row.perf.organist ?: "" %></td>
                        <td><%= row.perf.bassist ?: "" %></td>
                        <td><%= row.perf.drummer ?: "" %></td>
                        <td><%= row.perf.guitarist ?: "" %></td></tr><% } %>
                </tbody>
                <tfoot><tr>
                        <th class="dt-left">Song</th>
                        <th class="dt-left">Artists</th>
                        <th class="dt-left">Worship Leader</th>
                        <th class="dt-left">Piano</th>
                        <th class="dt-left">Organ</th>
                        <th class="dt-left">Bass</th>
                        <th class="dt-left">Drums</th>
                        <th class="dt-left">Guitar</th>
                </tr></tfoot>
            </table>
        </section>

        <script type="application/javascript">
            window.onload = function() { \$("#performances-table").dataTable(); };
        </script>
    </body>

</html>
