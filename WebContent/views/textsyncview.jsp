<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <title>Amalia.js</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="icon" href="images/favicon.ico">
        <link href="css/default.css" rel="stylesheet">
        <script src="scripts/bower_components/jquery/dist/jquery.js"></script>
        <script src="scripts/bower_components/jquery-ui/jquery-ui.min.js"></script>        
        <script src="scripts/bower_components/raphael/raphael.js"></script>
        <!-- style-player -->        
        <link href="scripts/css/amalia.js.min.css" rel="stylesheet">
        <!-- /style-player -->        
        <!-- script-player -->        
        <script src="scripts/js/amalia.js.min.js"></script>
        <script src="scripts/js/amalia.js-plugin-text-sync.min.js"></script>        
        <!-- /script-player -->
    </head>
    <body>
        <div class="container">
            <div class="header">
                <h1>Text synchronization plugin</h1>  
            </div>
            <div class="content">           
                <div class="demo">
                    <div style=" clear: both;">

                        <div style="width: 50%;float: left;">
                            <div style="height: 350px;">
                                <div id="defaultPlayer"></div>
                            </div>
 <!--                           <div>
                                <pre class="config">
{
    'className' : 'fr.ina.amalia.player.plugins.TextSyncPlugin',
    'container' : '#myplayer-tsync-tsync',
    'parameters' : {
        metadataId : 'text-amalia01',
        title : 'My title',
        description : 'A description I may have to put here',
        level : 1,
        displayLevel : 1,
        scrollAuto : true
    }
}                 
                                </pre>
                            </div> -->
                        </div>
                        <div style="width: 50%; float: left;">
                            <div id="text_sync_plugin" style="height: 500px;"></div>
                        </div>
                    </div>

                    <script>
                        $( function () {
                            $( "#defaultPlayer" ).mediaPlayer( {
                                autoplay : false,
                                src : "http://tv-download.dw.de/dwtv_video/flv/eme/eme20140119_whisky_sd_avc.mp4",
                                controlBar :
                                    {
                                        sticky : true
                                    },
                                plugins : {
                                    dataServices : [
                                        'scripts/amalia01-text.json'
                                    ],
                                    list : [
                                        {
                                            'className' : 'fr.ina.amalia.player.plugins.TextSyncPlugin',
                                            'container' : '#text_sync_plugin',
                                            'parameters' : {
                                                metadataId : 'text-amalia01',
                                                title : 'Dbpedia key-value',
                                                description : 'property value pair of entities from dbpedia',
                                                level : 1,
                                                displayLevel : 1,
                                                scrollAuto : true
                                            }
                                        }
                                    ]
                                }
                            } );
                        } );
                    </script>

                </div>
            </div>
            
        </div>
    </body>
</html>
