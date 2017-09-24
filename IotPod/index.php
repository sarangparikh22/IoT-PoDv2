<?php
    session_start();

     if(isset($_SESSION['id'])) {
      header('Location: user.php');
    } else {
        // header('Location: index.php');
        // die();
    }

    if (isset($_POST['submit'])) {
        include("connection.php"); //connection.php
        $username = strip_tags($_POST['name']);
        $password = strip_tags($_POST['password']);
        
        $sql = "SELECT id, username, password FROM users WHERE username = '$username' AND activated = '1' LIMIT 1";
        
        $query = mysqli_query($dbCon, $sql); 
         if ($query) {
            $row = mysqli_fetch_row($query); 
            $userId = $row[0];
            $dbUsername = $row[1];
            $dbPassword = $row[2];
           
         }
        if ($username == $dbUsername && $password == $dbPassword) {
            $_SESSION['username'] = $username;
            $_SESSION['id'] = $userId;
            header('Location: user.php');
        } else {
            header('Location: index.php');
        }
    }
?>



<!DOCTYPE html>
<html >
<head>
  <meta charset="UTF-8">
  <title>IoT Pod</title>
  <link href='https://fonts.googleapis.com/css?family=Titillium+Web:400,300,600' rel='stylesheet' type='text/css'>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">

  
      <link rel="stylesheet" href="css/style.css">


</head>

<body>
  <div class="form">
        
        <div id="login">   
          
          <h1>IoT Pod</h1>
          
          <form action="index.php" method="post">
          
            <div class="field-wrap">
            <label>
              Username<span class="req">*</span>
            </label>
            <input type="text" name="name"/>
          </div>
          
          <div class="field-wrap">
            <label>
              Password<span class="req">*</span>
            </label>
            <input type="password" name="password" />
          </div>
          
          <p class="forgot"><a href="#">Need Help?</a></p>
          
     
           <input class="button button-block" type="submit" name="submit" value="Lets Control It!" />
          
          </form>

        </div>
        
      </div><!-- tab-content -->
      
</div> <!-- /form -->
  <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>

    <script src="js/index.js"></script>

</body>
</html>
