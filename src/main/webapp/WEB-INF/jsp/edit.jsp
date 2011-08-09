<html>
    <head>
        <title>Edit Profile</title>
        <%@include file = "head include.txt"%>
        <script src="https://ajax.googleapis.com/ajax/libs/prototype/1.7.0.0/prototype.js" type="text/javascript"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/scriptaculous/1.9.0/scriptaculous.js" type="text/javascript"></script>
        <script src="/static/js/cropper.js" type="text/javascript"></script>
        <script type="text/javascript" charset="utf-8">
                // setup the callback function
                function onEndCrop( coords, dimensions ) {
                    $( 'x1' ).value = coords.x1;
                    $( 'y1' ).value = coords.y1;
                    $( 'x2' ).value = coords.x2;
                    $( 'y2' ).value = coords.y2;
                    $( 'width' ).value = dimensions.width;
                    $( 'height' ).value = dimensions.height;
                }

                // basic example
                Event.observe(
                    window,
                    'load',
                    function() {
                        new Cropper.Img(
                            'testImage',
                            {
                                onEndCrop: onEndCrop
                            }
                        );
                    }
                );
        </script>
        <style type="text/css">
                label {
                    clear: left;
                    margin-left: 50px;
                    float: left;
                    width: 5em;
                }

                html, body {
                    margin: 0;
                }

                #testWrap {
                    margin: 20px 0 0 50px; /* Just while testing, to make sure we return the correct positions for the image & not the window */
                }
        </style>


    </head>

    <body>
        <div class="container">
            <jsp:include page="navigationHeader.jsp"></jsp:include>

            <div id="left-right-container" class="add-margin-above-20 span-24">
                <div id="left-container" class="span-15">
                    <div class="span-15 last">
                        <div class="span-15 last">
                            <h3>Edit your Settings</h3>
                        </div>
                    </div>

                    <div id="tab-container" class="span-15 last">
                        <div class="span-2 tab tab-active" onclick="javascript:makeTabActiveOnEdit(0);">
                            <span>Account</span>
                        </div>
                        <div class="span-2 tab" onclick="javascript:makeTabActiveOnEdit(1);">
                            <span>Password</span>
                        </div>
                        <div class="span-2 tab " onclick="javascript:makeTabActiveOnEdit(2);">
                            <span>Image</span>
                        </div>
                    </div>

                    <div class="span-15 last">
                        <div id="accountDiv" class="span-15 last add-padding-above-20">
                            <div id = "personal-error" class = "error-box prepend-2 span-11 append-2 last">
                                <div id = "personal-error-content" class = "error-content">
                                    ${personalMessage}
                                </div>
                            </div>
                            <script type = "text/javascript">
                                if("${personalMessage}" == "") {
                                    dojo.style("personal-error", "display", "none");
                                }
                            </script>
                            <div id='accountInfo' class = "span-15 add-padding-above-20 last">
                                <form action="/user/edit/personalDetails" method="post">
                                    <div class = "span-9 append-6 last">
                                        <label for="name" class = "span-2 add-margin-above-10">Name</label>
                                        <input type="text" name="name" id = "name" value = "${name}" class = "span-6" />
                                    </div>
                                    <div class = "span-9 append-6 last add-margin-above-20">
                                        <label for="email" class = "span-2 add-margin-above-10">Email</label>
                                        <input type="email" name="email" id = "email" value='${email}' class = "span-6"/>
                                    </div>
                                    <div class="span-2 append-13 last add-margin-above-20">
                                        <input type="submit" name="submit" value="Save" />
                                    </div>
                                </form>
                            </div>
                        </div>

                        <div id="passwordDiv" class="span-15 last add-padding-above-20">
                            <div id = "password-error" class = "error-box prepend-2 span-11 append-2">
                                <div id = "password-error-content" class = "error-content">
                                    ${passwordMessage}
                                </div>
                            </div>
                            <script type = "text/javascript">
                                if("${passwordMessage}" == "") {
                                    dojo.style("password-error", "display", "none");
                                }
                            </script>

                            <div id='passwordInfo' class = "span-15 last add-padding-above-20">
                              <form action="/user/edit/password" method="post">
                                    <div class = "span-11 append-4 last add-margin-above-20">
                                        <label for="old_password" class = "span-3 add-margin-above-10">Old Password</label>
                                        <input type="password" name="old_password" id="old_password"  class = "span-6" />
                                    </div>
                                    <div class = "span-11 append-4 last add-margin-above-20">
                                        <label for="new_password" class = "span-3 add-margin-above-10">New Password</label>
                                        <input type="password" name="new_password" id="new_password" class = "span-6"/>
                                    </div>
                                    <div class = "span-11 append-3 last add-margin-above-20">
                                        <label for="confirm_password" class = "span-3">Confirm Password</label>
                                        <input type="password" name="confirm_password" id="confirm_password" class = "span-6"/>
                                    </div>
                                    <div class = "span-5 append-10 last add-margin-above-20">
                                        <input type="Submit" value="Change" class = "span-2"/>
                                    </div>
                              </form>
                            </div>
                        </div>

                        <div id="imageDiv" class="span-15 last add-padding-above-20">
                            <div id='imageInfo'>
                                <form  action="/user/imageInfo" enctype="multipart/form-data" method="POST">
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
                            <div id="cropDiv" class="span-15 last add-padding-above-20">
                                <div id='cropInfo'>
                                    <div id="">
                                        <h3 align="center">want to crop your image</h3>
                                        <img  src='${src}' alt="test image" id="testImage" width="600" height="600" />
                                    </div>
                                    <form  id="photoCrapper" action="/crop" method="post">
                                        <input type="hidden" name="x1" id="x1" value="0"/>
                                        <input type="hidden" name="y1" id="y1" value="0"/>
                                        <input type="hidden" name="x2" id="x2" value="0"/>
                                        <input type="hidden" name="y2" id="y2" value="0"/>
                                        <input type="hidden" name="width" id="width" value="600"/>
                                        <input type="hidden" name="height" id="height" value="600"/>
                                        <input type="hidden" name="file" value="${uid}"  id="uid"/>
                                        <input type="submit" name ="submit" value="submit" />
                                    </form>
                                </div>
                            </div>
                        </div>

                        <script type="text/javascript">
                            makeTabActiveOnEdit(${active});
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




























