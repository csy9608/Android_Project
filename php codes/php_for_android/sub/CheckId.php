<?php
// Done
// IN:  userID, OUT: exists
  $db_server = "localhost";
  $db_id = "csy9608";
  $db_password = "tjdud9608";
  $db_name = "csy9608";

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
