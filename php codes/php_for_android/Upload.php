<?php
//IN: userID, temperature, encoded_string, imageTags,  OUT: boardID, success
  $db_server = "localhost";
  $db_id = "csy9608";
  $db_password = "tjdud9608";
  $db_name = "csy9608";

  $response = array();
  $response['success'] = false;

  if(isset($_POST['encoded_string'])){
    $encoded_string = $_POST['encoded_string'];
    $image_name = date("Ymd").uniqid().".jpg";

    $encoded_string = str_replace('data:image/png;base64,', '', $encoded_string);
    $encoded_string = str_replace(' ', '+', $encoded_string);
    $decoded_string = base64_decode($encoded_string);
    $target = "images/".$image_name;
    $imagePath = "http://csy9608.cafe24.com/".$target;

    if(file_put_contents($target, $decoded_string)){
      $con = mysqli_connect($db_server, $db_id , $db_password, $db_name);
      $userID = $_POST['userID'];
      $imageTags = $_POST['imageTags'];
      $temperature = $_POST['temperature'];

      $query1 = "SELECT tempID FROM TEMPERATURE WHERE tempMin<=$temperature AND tempMax>$temperature";
      $result1 = mysqli_query($con, $query1);

      $row1 = mysqli_fetch_array($result1);
      $tempID = $row1['tempID'];

      $query2 = "INSERT INTO BOARD(userID, boardDate, imagePath, imageTags, tempID) VALUES ('$userID', now(), '$imagePath','$imageTags', $tempID)";
      if(mysqli_query($con, $query2)){
        $response['success'] = true;
        $query3 = "SELECT boardID FROM BOARD WHERE imagePath='$imagePath' AND userID='$userID'";
        $result3 = mysqli_query($con, $query3);
        $row3 = mysqli_fetch_array($result3);
        $response['boardID'] = $row3['boardID'];
      }
      mysqli_close($con);
    }
  }
   echo json_encode($response);

 ?>
