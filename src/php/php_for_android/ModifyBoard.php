<?php
//IN: userID, boardID, encoded_string,imageTags, delete  OUT: success, authority

  $db_server = "localhost";
  $db_id = "id";
  $db_password = "pwd";
  $db_name = "name";

  $response = array();
  $response['success'] = false;
  $response['authority'] = false;

  $boardID = $_POST['boardID'];
  $userID = $_POST['userID'];

  $con = mysqli_connect($db_server, $db_id , $db_password, $db_name);

  $check_id = "SELECT userID FROM BOARD WHERE boardID=$boardID"; // check user ID
  $result = mysqli_query($con, $check_id);
  $row = mysqli_fetch_array($result);
  $board_userID = $row['userID'];

  if( (strcmp($userID, $board_userID)==0) || (strcmp($userID, "Admin")==0)){
    $response['authority'] = true;
  }

  if($response['authority']){
    if(isset($_POST['delete'])){
      $query0 = "SELECT imagePath FROM BOARD WHERE boardID=$boardID";
      $result0 = mysqli_query($con, $query0);
      $row0 = mysqli_fetch_array($result0);
      $imagePath = $row0['imagePath'];
      $imagePath = str_replace('http://csy9608.cafe24.com/','' ,$imagePath);

      if(unlink($imagePath)){
        $query1 = "DELETE FROM BOARD WHERE boardID=$boardID";

        if(mysqli_query($con, $query1))
          $response['success'] = true;
      }
    }

    else{
      if(isset($_POST['encoded_string'])){
        $encoded_string = $_POST['encoded_string'];
        $query0 = "SELECT imagePath FROM BOARD WHERE boardID=$boardID";
        $result0 = mysqli_query($con, $query0);

        $row0 = mysqli_fetch_array($result0);
        $imagePath = $row0['imagePath'];
        $imagePath = str_replace('http://csy9608.cafe24.com/','' ,$imagePath);

        $encoded_string = str_replace('data:image/png;base64,', '', $encoded_string);
        $encoded_string = str_replace(' ', '+', $encoded_string);
        $decoded_string = base64_decode($encoded_string);

        file_put_contents($imagePath, $decoded_string);
      }

        $imageTags = $_POST['imageTags'];

        $query2 = "UPDATE BOARD SET imageTags='$imageTags' WHERE boardID=$boardID";

        if(mysqli_query($con, $query2))
          $response['success'] = true;
    }

  }
  mysqli_close($con);
  echo json_encode($response);

 ?>
