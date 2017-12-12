<?php
// Done
// IN: boardID, undo, userID  OUT: success, boardLikes
  $db_server = "localhost";
  $db_id = "csy9608";
  $db_password = "tjdud9608";
  $db_name = "csy9608";

  $con = mysqli_connect($db_server, $db_id , $db_password, $db_name);
  $boardID = $_POST['boardID'];
  $userID = $_POST['userID'];
  $exist = true;

  $response = array();
  $response['success'] = true;

  $query0 = "SELECT likedUser FROM BOARD WHERE likedUser LIKE '%/$userID/%' AND boardID=$boardID";
  $result0 = mysqli_query($con, $query0);
  if (mysql_num_rows($result0)==0) {
      $exist = false;
  }


  if($exist==true && isset($_POST['undo'])){
    $query = "UPDATE BOARD SET boardLikes=boardLikes-1, likedUser=REPLACE(likedUser, '/$userID/'', '') WHERE boardID=$boardID";
  }
  else if($exist==false && !isset($_POST['undo'])){
    $query = "UPDATE BOARD SET boardLikes=boardLikes+1, likedUser=CONCAT(likedUser, '/$userID/')  WHERE boardID=$boardID";
  }
  else{
        $response['success']=false;
  }

  if($response['success']==true){
    $result = mysqli_query($con, $query);

    if($result){
      $query2 = "SELECT boardLikes FROM BOARD WHERE boardID=$boardID";
      $result2 = mysqli_query($con, $query2);
      if($row = mysqli_fetch_array($result2))
        $response['boardLikes'] = $row['boardLikes'];
    }
    else{
      $response['success'] = false;
    }
  }

  mysqli_close($con);
  echo json_encode($response);
 ?>
