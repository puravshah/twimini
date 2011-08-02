<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Profile</title>

        <style type="text/css">
            @import "/static/css/blueprint/screen.css";
            @import "/static/css/blueprint/print.css";
            @import "/static/css/blueprint/style.css";
            @import "/static/dojoroot/dijit/themes/tundra/tundra.css";
            @import "/static/dojoroot/dojo/resources/dojo.css"
        </style>

        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.1/jquery.min.js"></script>
        <script type="text/javascript" src="/static/dojoroot/dojo/dojo.js" djConfig="parseOnLoad: true"></script>
        <script type="text/javascript" src="/static/js/ejs_production.js"></script>
        <script type="text/javascript" src="/static/js/external_js_file.js"></script>
        <script src="http://ajax.googleapis.com/ajax/libs/dojo/1.6.0/dojo/dojo.xd.js" type="text/javascript"></script>

        <script>
            dojo.require("dojo.parser");
            dojo.require("dijit.layout.ContentPane");
            dojo.require("dijit.layout.TabContainer");
        </script>

        <style>
            .formContainer {
               width:600px;
               height:600px;
            }
            label {
               width:150px;
               float:left;
            }
         </style>
    </head>

    <body class="tundra">
        <div class="container">
            <div id="navigation-bar" class="span-24 last header">
                <div id="twitter-logo" class="span-6">
                    <img src="/static/images/logo.png" alt="Mini Twitter"/>
                </div>

                <div id="user-nav-head" class span="18 last">
                    <div class="span-2">
                        <a href="/tweet">Home</a>
                    </div>

                    <div class="span-2">
                        <a href="/user?uid=<%= session.getAttribute("uid") %>">Profile</a>
                    </div>

                    <div class="span-9 search-box">
                        <input type="text" name="q" id="search-box"/>
                        <input type="button" value="Search" id="search-button" onclick="search()"/>
                    </div>

                    <div id="dropdown-text" class="span-2" onclick="toggleDropdown()">
                        <a href="#"><%= session.getAttribute("name") %>
                        </a>
                        <img src="/static/images/icon_dropdown_1.png"/>
                    </div>
                </div>

                <div id="dropdown" class="span-2">
                    <div class="span-2 last">
                        <a href="/user/edit?uid=${uid}">Edit Profile</a>
                    </div>
                    <div class="span-2 last add-margin-above-20">
                        <a href="/logout">Logout</a>
                    </div>
                </div>
                <script type="text/javascript">
                    $('#dropdown').hide();
                </script>
            </div>

            <div id="left-right-container" class="add-margin-above-20 span-24">
                <div id="left-container" class="span-15">
                    <div class="span-15 last">
                        <div class="span-15 last">
                            <h3>Edit your Settings</h3>
                            <h3>${wrongPassword}</h3>
                            <br>
                        </div>
                    </div>

                    <div id="tab-container" class="span-15 last">
                        <div class="span-2 tab " onclick="javascript:getAccount();">
                            <span>Account</span>
                        </div>
                        <div id="tabid" class="span-2 tab tab-active" onclick="javascript:getPassword();">
                            <span>Password</span>
                        </div>
                        <div class="span-2 tab last" onclick="javascript:getImage();">
                            <span>Image</span>
                        </div>
                    </div>
                    <div class="span-15 last">
                        <div id="accountDiv" class="span-15 last add-padding-above-20">
                            <div id='accountInfo'>
                                <br>
                                <form action="/user/accountInfo" id=account_settings_form" method="post">
                                    <div>
                                        <label for="name">Name:</label>
                                        <input type="text" name="name" id="name"  value="${name}"size="30" />
                                    </div>
                                    <br>
                                    <div>
                                        <label for="emailId">Email</label>
                                        <input type="email" name="email" value='${email}' size="30"/>
                                    </div>
                                    <br>
                                    <div class="span-2 last add-margin-above-20">
                                        <input type="submit" name="submit" value="save" size="20"/>
                                    </div>
                                </form>
                            </div>
                        </div>

                        <div id="passwordDiv" class="span-15 last add-padding-above-20">
                            <div id='passwordInfo'>
                              <form action="/user/passwordInfo"  id=account_settings_form" method="post">
                                    <div>
                                        <label for="old_Password">Old Password</label>
                                        <input type="text" name="old_password" id="old_password"  value="" size="30" />
                                        <br>
                                    </div>
                                    <div>
                                        <label for="new_Password">New Password</label>
                                        <input type="password" name="new_password" id="new_password" value='' size="30"/>
                                        <br>
                                    </div>
                                    <div>
                                        <label for="confirm_new_Password">confirm New Password</label>
                                        <input type="password" name="confirm_new_password" id="confirm_new_password" value='' size="30"/>
                                    </div>
                                    <div>
                                        <input type="Submit" value="Change" size="30"/>
                                        <br>
                                    </div>
                              </form>
                            </div>
                        </div>

                        <div id="imageDiv" class="span-15 last add-padding-above-20">
                            <div id='imageInfo'>
                                <form target="_blank" action="/user/imageInfo" enctype="multipart/form-data" method="POST">
                                    <input type="hidden" name="uid" value="${uid}">
                                    <div>
                                        <input type="file" name="file"><br>
                                    </div>
                                    <div>
                                        <br>
                                        <input type="Submit" value="Upload File"><br>
		                            </div>
		                        </form>
                            </div>
                        </div>

                        <script type="text/javascript">
                            $('#accountDiv').hide();
                            $('#imageDiv').hide();
                        </script>
                    </div>
                </div>
                <div id="right-container" class="span-7 last">
                    <div class="span-6 last">
                        <h2>Help for settings</h2>
                    </div>

                    <div class="span-6 last add-margin-above-20">
                        <div class="span-6 last ">
                            <h3>Account</h3>
                            <p>
                                 From here you can change your basic account info, language settings, and your tweet privacy and location settings."
                            </p>
                            <hr>
                        </div>

                        <div class="span-6 last ">
                            <h3>Tips</h3>
                            <p>
                                Change your Twitter name or emailId anytime without affecting your existing tweets,  direct messages, or other data. After changing it, make sure to let your followers know so you'll continue receiving all of your messages with your new email.
                            </p>
                            <hr>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>




























