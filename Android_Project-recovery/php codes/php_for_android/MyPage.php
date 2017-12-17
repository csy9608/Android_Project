<?php
//Done Maybe? Tag need to check Tags
//IN: userID, start, align_likes, limit, OUT: boardID, imagePath, imageTags, boardLikes, end

  $db_server = "localhost";
  $db_id = "csy9608";
  $db_password = "tjdud9608";
  $db_name = "csy9608";

  $con = mysqli_connect($db_server, $db_id , $db_password, $db_name);
  $userID = $_POST['userID'];
  $start = $_POST['start'];
  $align_likes = $_POST['align_likes'];
  $limit = $_POST['limit']; // counts

  if(isset($align_likes)){
      $query2 = "SELECT boardID,imagePath,imageTags,boardLikes FROM BOARD_LIKEST WHERE userID='$userID' LIMIT $start, $limit";
  }
  else{
      $query2 = "SELECT boardID, imagePath, imageTags, boardLikes FROM BOARD_LATEST WHERE userID='$userID' LIMIT $start, $limit";
  }

  $result2 = mysqli_query($con, $query2);
  $response = array();

  $response['boardID'] = array();
  $response['imagePath'] = array();
  $response['imageTags'] = array();
  $response['boardLikes'] = array();

  while ($row2 = mysqli_fetch_array($result2)) {
    array_push($response['boardID'], $row2['boardID']);
    array_push($response['imagePath'], $row2['imagePath']);
    array_push($response['imageTags'], $row2['imageTags']);
    array_push($response['boardLikes'], $row2['boardLikes']);
  }

  $response['end'] = $start + $limit;
  mysqli_close($con);
  echo json_encode($response);
?>
