<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html dir="ltr">
    <head>
        <script src="http://ajax.googleapis.com/ajax/libs/dojo/1.6/dojo/dojo.xd.js"
            djConfig="parseOnLoad: true">
        </script>
        <script type="text/javascript">
            dojo.require("dijit.form.Button");
            dojo.require("dijit.Menu");
        </script>
        <script type = "text/javascript" src = "/static/js/dropdown-dojo.js"></script>
        <link rel="stylesheet" type="text/css" href="http://ajax.googleapis.com/ajax/libs/dojo/1.6/dijit/themes/claro/claro.css" />
        <link rel="stylesheet" type="text/css" href="/static/css/blueprint/screen.css" />
        <link rel="stylesheet" type="text/css" href="/static/css/blueprint/style.css" />
    </head>
    
    <body class=" claro ">
        <input id = "username_hidden" type = "hidden" value = "Purav" />

        <div class = "container">
            <div id = "navigation-bar" class = "span-24 last header">
                <div id = "twitter-logo" class = "span-6">
                    <img src = "/static/images/logo.png" alt = "Mini Twitter" />
                </div>

                <div id="dropdownButtonContainer"></div>
            </div>
        </div>

    </body>

</html>