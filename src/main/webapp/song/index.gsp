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
        <link rel="shortcut icon" href="../images/favicon.ico">

        <title><%= song.name %> - New Life Songs Database</title>
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
            <h2><%= song.name %></h2><%
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
                        <th class="dt-left">Date</th>
                        <th class="dt-left">Service Type</th>
                        <th class="dt-left">Worship Leader</th>
                        <th class="dt-left">Piano</th>
                        <th class="dt-left">Organ</th>
                        <th class="dt-left">Bass</th>
                        <th class="dt-left">Drums</th>
                        <th class="dt-left">Guitar</th>
                </tr></thead>
                <tbody>
                <% songsDB.findPerformancesForSongId(song.id).
                    collect { [perf: it, svc: songsDB.findService(it.serviceId)] }.
                    sort { it.svc.date }.each { row -> %>
                    <tr><td><a href='../service/<%= row.svc.id %>'><%= 
                                row.svc.@date.toString("yyyy-MM-dd") %></a></td>
                        <td><% switch (row.svc.serviceType) {
                            case SUN_PM: out.print("Sunday PM"); break
                            case SUN_AM: out.print("Sunday AM"); break
                            case WED: out.print("Wednesday"); break }
                            %></td>
                        <td><%= row.perf.leader ?: "" %></td>
                        <td><%= row.perf.pianist ?: "" %></td>
                        <td><%= row.perf.organist ?: "" %></td>
                        <td><%= row.perf.bassist ?: "" %></td>
                        <td><%= row.perf.drummer ?: "" %></td>
                        <td><%= row.perf.guitarist ?: "" %></td></tr><% } %>
                </tbody>
                <tfoot><tr>
                        <th class="dt-left">Date</th>
                        <th class="dt-left">Service Type</th>
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
