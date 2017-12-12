<?php
//Done
//IN: userID, userPassword, OUT: userID, success
  $db_server = "localhost";
  $db_id = "csy9608";
  $db_password = "tjdud9608";
  $db_name = "csy9608";

  $con = mysqli_connect($db_server, $db_id , $db_password, $db_name);

  $userID = $_POST['userID'];
  $userPassword = $_POST['userPassword'];
  $statement = mysqli_prepare($con, "SELECT userID FROM USER WHERE userID = ?  AND userPassword = ?");
  mysqli_stmt_bind_param($statement, "ss", $userID, $userPassword);
  mysqli_stmt_execute($statement);

  mysqli_stmt_store_result($statement);
  mysqli_stmt_bind_result($statement, $userID);

  $response = array();
  $response["success"] = false;

  if(mysqli_stmt_fetch($statement)){
    $response["success"] = true;
    $response['userID'] = $userID;
  }

    mysqli_close($con);
    echo json_encode($response);
 ?>
