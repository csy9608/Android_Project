<?php
// IN: boardID, userID OUT: boardID, boardDate, imagePath, imageTags

  $db_server = "localhost";
  $db_id = "id";
  $db_password = "pwd";
  $db_name = "name";

  $con = mysqli_connect($db_server, $db_id , $db_password, $db_name);
  $boardID = $_POST['boardID'];
  $userID = $_POST['userID'];

  $query = "SELECT * , IF(likedUser LIKE '%/$userID/%', 'true', 'false')liked FROM BOARD WHERE boardID=$boardID";
  $result = mysqli_query($con, $query);

  $response = mysqli_fetch_array($result);

  mysqli_close($con);
  echo json_encode($response);
 ?>
