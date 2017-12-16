<?php
  $db_server = "localhost";
  $db_id = "csy9608";
  $db_password = "tjdud9608";
  $db_name = "csy9608";

  $con = mysqli_connect($db_server, $db_id , $db_password, $db_name);

  $temp = $_POST['temper'];
  $query0 = "SELECT tempID FROM TEMPERATURE WHERE $temp<=tempMax AND $temp>tempMin";
  $result0 = mysqli_query($con, $query0);
  $row0 = mysqli_fetch_array($result0);
  $tempID = $row0['tempID'];

  $query1 = "SELECT imageTags AS imageTags FROM BOARD WHERE tempID=$tempID";
  $result1 = mysqli_query($con, $query1);

  $words = "";
  while ($row = mysqli_fetch_array($result1))
  {
    $words .= $row['imageTags'];
  }

  $array = explode("#",$words);
  $array = array_count_values($array);

  arsort($array);
  $response['tags']="";

  $i=0;
  while($i<3 && $tag=each($array))
  {
     $response['tags'].= " #".$tag['key'];
     ++$i;
   }

  mysqli_close($con);
  echo json_encode($response);
 ?>
