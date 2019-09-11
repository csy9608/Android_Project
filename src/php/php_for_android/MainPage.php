<?php
//Done Maybe? Tag need to check Tags
//IN: start, align_likes, temperature, limit, userID OUT: boardID, imagePath, imageTags, boardLikes, end

  $db_server = "localhost";
  $db_id = "id";
  $db_password = "pwd";
  $db_name = "name";

  $con = mysqli_connect($db_server, $db_id , $db_password, $db_name);
  $start = $_POST['start'];
  $align_likes = $_POST['align_likes'];
  $temperature = $_POST['temperature'];
  $limit = $_POST['limit']; // counts

  $query1 = "SELECT tempID FROM TEMPERATURE WHERE tempMin<=$temperature AND tempMax>$temperature";
  $result1 = mysqli_query($con, $query1);
  $row1 = mysqli_fetch_array($result1);
  $tempID = $row1['tempID'];
  $userID=$_POST['userID'];

  if(isset($userID)){

    if(isset($align_likes)){
        $query2 = "SELECT boardID,imagePath,imageTags,boardLikes, IF(likedUser LIKE '%/$userID/%', 'true', 'false')liked FROM BOARD_LIKEST WHERE tempID=$tempID LIMIT $start, $limit";
    }
    else{
        $query2 = "SELECT boardID, imagePath, imageTags, boardLikes, IF(likedUser LIKE '%/$userID/%', 'true', 'false')liked FROM BOARD_LATEST WHERE tempID=$tempID LIMIT $start, $limit";
    }

    $result2 = mysqli_query($con, $query2);
    $response = array();

    $response['boardID'] = array();
    $response['imagePath'] = array();
    $response['imageTags'] = array();
    $response['boardLikes'] = array();
    $response['liked'] = array();

    while ($row2 = mysqli_fetch_array($result2)) {
      array_push($response['boardID'], $row2['boardID']);
      array_push($response['imagePath'], $row2['imagePath']);
      array_push($response['imageTags'], $row2['imageTags']);
      array_push($response['boardLikes'], $row2['boardLikes']);
      array_push($response['liked'], $row2['liked']);
    }

    $response['end'] = $start + $limit;
  }

  mysqli_close($con);
  echo json_encode($response);

?>
