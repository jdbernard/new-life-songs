<!DOCTYPE html>
<%
import com.jdbernard.nlsongs.servlet.NLSongsContext
import static com.jdbernard.nlsongs.model.ServiceType.*

songsDB = NLSongsContext.songsDB

%>
<html>
    <head>
        <meta charset="UTF-8">
        <link rel="shortcut icon" href="../images/favicon.ico">

        <title>Services - New Life Songs Database</title>
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
            <h2>Services</h2>

            <nav><ul>
                <li><a href="../admin/">Admin</a></li>
                <li><a href="../songs/">Songs</a></li>
                <li><a href="../services/">Services</a></li>
            </ul></nav>
        </header>
        <section class=services>
            <table id=services-table class="row-border dataTable hover compact" cellspacing=0>
                <thead><tr>
                        <th class="dt-left">Date</th>
                        <th class="dt-left">Service Type</th>
                </tr></thead>
                <tbody>
                <% songsDB.findAllServices().sort { it.date }.reverse().each { service -> %>
                    <tr><td class=date><a href="../service/<%= service.id %>"><%=
                            service.@date.toString("yyyy-MM-dd") %></a></td>
                        <td class=service-type><%= service.serviceType.displayName %></td></tr><% } %>
                </tbody>
                <tfoot><tr>
                        <th class="dt-left">Date</th>
                        <th class="dt-left">Service Type</th>
                </tr></tfoot>
            </table>
        </section>

        <script type="application/javascript">
            window.onload = function() { \$("#services-table").dataTable(); };
        </script>
    </body>
</html>
