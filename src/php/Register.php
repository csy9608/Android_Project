<?php
  $db_server = "localhost";
  $db_id = "id";
  $db_password = "pwd";
  $db_name = "name";

  $con = mysqli_connect($db_server, $db_id , $db_password, $db_name);

  $userID = $_POST["userID"];
  $userPassword = $_POST["userPassword"];
  $userName = $_POST["userName"];
  $userAge = $_POST["userAge"];
  $userGender = $_POST["userGender"];

  $statement = mysqli_prepare($con, "INSERT INTO USER VALUES(?, ?, ?, ?, ?)");
  mysqli_stmt_bind_param($statement, "sssis", $userID, $userPassword, $userName, $userAge, $userGender);
  mysqli_stmt_execute($statement);

  $response = array();
  $response["success"] = true;

  echo json_encode($response);
 ?>
