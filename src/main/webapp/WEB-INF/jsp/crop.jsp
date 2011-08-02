<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
	<meta http-equiv="Content-Language" content="en-us" />
	<title>Basic cropper test</title>
    <script type="text/javascript" src="/static/js/external_js_file.js"></script>
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
	<div id="testWrap">
		<img src="/image/${uid}.jpg" alt="test image" id="testImage" width="500" height="333" />
	</div>
	<form  id="photoCrapper" action="/crop" onsubmit="self.close();" >
    	<input type="hidden" name="x1" id="x1"/>
		<input type="hidden" name="y1" id="y1"/>
		<input type="hidden" name="x2" id="x2"/>
		<input type="hidden" name="y2" id="y2"/>
		<input type="hidden" name="width" id="width"/>
		<input type="hidden" name="height" id="height"/>
	    <input type="hidden" name="file" value="${uid}"  id="uid"/>

	    <input type="submit" name ="submit" value="submit" />
    </form>
</body>
</html>

