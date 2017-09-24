<?php
    session_start();

    if(isset($_SESSION['id'])) {
        $username = $_SESSION['username'];
        $userId = $_SESSION['id'];
        //echo "Welcome, {$username}!";
    } else {
        header('Location: index.php');
        die();
    }

?>
<!DOCTYPE html>
<html>

<head>
 <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/style2.css">
  <meta charset="UTF-8">
  <title>Welcome To IoT Pod Console</title>
  
  </script>
</head>

<body>
  


    <div class="container">
    <div class="form">
    <p align="right">
<a href="logout.php" class="btn btn-primary">Logout</a>
</p>
   <h1>IoT Pods</h1>
       <h2>Light 1</h2>
        <a class="btn btn-lg btn-success" id="small-on" href="#" role="button">On</a>
        <a class="btn btn-lg btn-danger" id="small-off" href="#" role="button">Off</a>
      <h2>Smart Lock</h2>
        <a class="btn btn-lg btn-success" id="large-on" href="#" role="button">On</a>
        <a class="btn btn-lg btn-danger" id="large-off" href="#" role="button">Off</a>
        </div>
      </div>

    </div> <!-- /container -->



    <script src="https://code.jquery.com/jquery.js"></script>
    <script src="//netdna.bootstrapcdn.com/bootstrap/3.0.2/js/bootstrap.min.js"></script>

    <script type="text/javascript">
        $("#small-on").click(function() {
            console.log("on")
            $.ajax("http://192.168.43.58/digital/5/0")
        })
        $("#small-off").click(function() {
            console.log("off")
            $.ajax("http://192.168.43.58/digital/5/1")
        })
        $("#large-on").click(function() {
            console.log("on")
            $.ajax("192.168.")
        })
        $("#large-off").click(function() {
            console.log("off")
            $.ajax("192.168.")
        })
    </script>

  <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
<script src='http://cdnjs.cloudflare.com/ajax/libs/modernizr/2.8.3/modernizr.min.js'></script>

    <script src="js/index_landing.js"></script>
</body>
</html>
