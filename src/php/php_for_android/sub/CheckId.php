<?php
// Done
// IN:  userID, OUT: exists
  $db_server = "localhost";
  $db_id = "id";
  $db_password = "pwd";
  $db_name = "name";

  $con = mysqli_connect($db_server, $db_id , $db_password, $db_name);
  $userID = $_POST['userID'];

  $query = "SELECT userID FROM USER WHERE userID='$userID'";
  $result = mysqli_query($con, $query);

  $response = array();
  $response['exits'] = false;

  if($row = mysqli_fetch_array($result)){
      $response['exits'] = true;
  }

  mysqli_close($con);
  echo json_encode($response);
 ?>
