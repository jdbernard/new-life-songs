<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <link rel="shortcut icon" href="../images/favicon.ico">

        <title>Songs - New Life Songs Database</title>
        <script type="application/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
        <script type="application/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/underscore.js/1.8.2/underscore-min.js"></script>
        <script type="application/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/backbone.js/1.1.2/backbone-min.js"></script>
        <script type="application/javascript" src="https://cdn.datatables.net/1.10.5/js/jquery.dataTables.js"></script>
        <!--<script type="application/javascript" src="https://cdn.datatables.net/1.10.5/js/jquery.dataTables.min.js"></script>-->
        <script type="application/javascript" src="../js/new-life-songs.js"></script>
        <link href='http://fonts.googleapis.com/css?family=Roboto+Condensed|Roboto|Lato|Cuprum|Dosis|Cantarell' rel='stylesheet' type='text/css'>
        <link href='http://cdn.datatables.net/1.10.5/css/jquery.dataTables.css' rel='stylesheet' type='text/css'>
        <link href='../css/new-life-songs.css' rel='stylesheet' type='text/css'>
    </head>
    <body>
        <header>
            <h1>New Life Songs</h1>
            <h2>Songs</h2>

            <nav><ul>
                <li><a href="../admin/">Admin</a></li>
                <li><a href="../songs/">Songs</a></li>
                <li><a href="../services/">Services</a></li>
            </ul></nav>
        </header>
        <section class=songs>
            <table id=songs-table class="row-border hover compact" cellspacing=0>
                <thead><tr>
                        <th class="dt-left">Name</th>
                        <th class="dt-left">Artists</th>
                </tr></thead>
                <tfoot><tr>
                        <th class="dt-left">Name</th>
                        <th class="dt-left">Artists</th>
                </tr></tfoot>
            </table>
        </section>

        <script type="application/javascript">
            window.onload = function() {
            
                $("#songs-table").dataTable( {
                    "ajax": {
                        "url": "http://localhost:8080/new-life-songs-2.0/api/v1/songs/",
                        "dataSrc": "" },
                    "columns": [
                        { "data": "name" },
                        { "data": "artists" } ] }); };
        </script>
    </body>
</html>
