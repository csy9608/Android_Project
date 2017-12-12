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

  $response = array();
  $response['success'] = false;

  if(isset($_POST['undo'])){
    $query = "UPDATE BOARD SET boardLikes=boardLikes-1, likedUser=REPLACE(likedUser, '/$userID/'', '') WHERE boardID=$boardID";
  }
  else{
    $query = "UPDATE BOARD SET boardLikes=boardLikes+1, likedUser=CONCAT(likedUser, '/$userID/')  WHERE boardID=$boardID";
  }

  $result = mysqli_query($con, $query);

  if($result){
    $response['success'] = true;
    $query2 = "SELECT boardLikes FROM BOARD WHERE boardID=$boardID";
    $result2 = mysqli_query($con, $query2);
    if($row = mysqli_fetch_array($result2))
      $response['boardLikes'] = $row['boardLikes'];
  }

  mysqli_close($con);
  echo json_encode($response);
 ?>
