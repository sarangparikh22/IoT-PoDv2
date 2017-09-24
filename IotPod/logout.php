<?php
    session_start();
    session_destroy();

?>

<!DOCTYPE html>
<html>
    <head>
        <title>Youa are logged out!</title>
        <script type="text/javascript">
        	function a(){

        		window.location = "index.php"
        	}
        </script>
    </head>
  
    	<body onload="a()">
       <!-- <a href="index.php">Login</a> -->
    </body>
</html>