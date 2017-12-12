<?php
// IN: boardID, userID OUT: boardID, boardDate, imagePath, imageTags

  $db_server = "localhost";
  $db_id = "csy9608";
  $db_password = "tjdud9608";
  $db_name = "csy9608";

  $con = mysqli_connect($db_server, $db_id , $db_password, $db_name);
  $boardID = $_POST['boardID'];
  $query = "SELECT * , IF(likedUser LIKE '%/$userID/%', 'true', 'false')liked FROM BOARD WHERE boardID=$boardID";
  $result = mysqli_query($con, $query);

  $response = mysqli_fetch_array($result);

  mysqli_close($con);
  echo json_encode($response);
 ?>
