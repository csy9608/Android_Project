<?php
if(isset($_POST['submit'])){
  $db_server = "localhost";
  $db_id = "id";
  $db_password = "pwd";
  $db_name = "name";

  $con = mysqli_connect($db_server, $db_id , $db_password, $db_name);
  $query = "";
  $result = mysqli_query($db, $query);

  $response = array();
  while ($row = mysqli_fetch_array($result)) {

  }

    mysqli_close($con);
    //  echo json_encode($response);
}
 ?>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title></title>
  </head>
  <body>
    <form action="index.html" method="post">
      <input type="text" name="" value="">
      <input type="submit" name="submit" value="true">
    </form>
  </body>
</html>
