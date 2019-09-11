<?php
// Done
// IN: boardID, undo, userID  OUT: success, boardLikes
  $db_server = "localhost";
  $db_id = "id";
  $db_password = "pwd";
  $db_name = "name";

  $con = mysqli_connect($db_server, $db_id , $db_password, $db_name);
  $boardID = $_POST['boardID'];
  $userID = $_POST['userID'];
  $exist = false;

  $response = array();
  $response['success'] = true;

  $query0 = "SELECT likedUser FROM BOARD WHERE likedUser LIKE '%/$userID/%' AND boardID=$boardID";
  $result0 = mysqli_query($con, $query0);
  if (mysqli_fetch_array($result0) && isset($userID)) {
      $exist = true;
  }

  if($exist == true && isset($_POST['undo'])){
    $query = "UPDATE BOARD SET boardLikes=boardLikes-1,likedUser=REPLACE(likedUser, '/$userID/', '') WHERE boardID=$boardID";
  }
  else if($exist == false && !isset($_POST['undo'])){
    $query = "UPDATE BOARD SET boardLikes=boardLikes+1,likedUser=CONCAT(likedUser, '/$userID/')  WHERE boardID=$boardID";
  }
  else{
    $response['success']=false;
  }

  if($response['success'] == true){
    $result = mysqli_query($con, $query);

    if($result){
      $query2 = "SELECT boardLikes, IF(likedUser LIKE '%/$userID/%', 'true', 'false')liked FROM BOARD WHERE boardID=$boardID";
      $result2 = mysqli_query($con, $query2);
      if($row = mysqli_fetch_array($result2)){
        $response['boardLikes'] = $row['boardLikes'];
        $response['liked'] = $row['liked'];
      }
    }
    else{
      $response['success'] = false;
    }
  }

  mysqli_close($con);
  echo json_encode($response);
 ?>
