<?php
  $db_server = "localhost";
  $db_id = "csy9608";
  $db_password = "tjdud9608";
  $db_name = "csy9608";

  $userID = $_POST["userID"];

  $image = $_POST['image'];
  $name = $_POST['name'];

  $path = "images/$name.png";
  $imageUrl = "http://csy9608.cafe24.com/$path";

  $response = array();

  // if image is not null
  if(empty($image)){
    $response["success"] = false;
  }
  else{
    $con = mysqli_connect($db_server, $db_id , $db_password, $db_name);
    $statement = mysqli_prepare($con, "INSERT INTO BOARD VALUES(, ?, now(), ?, ?)");
    mysqli_stmt_bind_param($statement, "sss", $userID, $imageUrl, $boardCloths);
    mysqli_stmt_execute($statement);

    $decoded_image = base64_decode["$image"];
    file_put_contents($imageUrl, $decoded_image);

    $response["success"] = true;
  }

  echo json_encode($response);
  mysqli_close($con);
 ?>
