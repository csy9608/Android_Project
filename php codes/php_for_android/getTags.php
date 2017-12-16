<?php
  $db_server = "localhost";
  $db_id = "csy9608";
  $db_password = "tjdud9608";
  $db_name = "csy9608";

  $con = mysqli_connect($db_server, $db_id , $db_password, $db_name);

  $temp = $_POST['temper'];
  $query0 = "SELECT tempID FROM TEMPERATURE WHERE $temp<=tempMax AND $temp>tempMin";
  $result0 = mysqli_query($db, $query0);
  $tempID = mysqli_fetch_array($result0)['tempID'];
  echo "$query0"
  echo "$tempID\n";

  $query1 = "SELECT imageTags FROM BOARD WHERE tempID=$tempID";
  $result1 = mysqli_query($db, $query1);
  echo "$query1"

  $tags = array();

  while ($row = mysqli_fetch_array($result1))
  {
    $imageTags = explode("#",$row['imageTags']);
    echo $imageTags."\n";
    /*  foreach($imageTag as $imageTags){
        if(!array_key_exists($imageTag,$tags))
          $tags["$imageTag"] = 0;
        else {
          $tags["$imageTag"] = $tags[$imageTag]+1;
        }
      }
    }
    */
  }

//  arsort($tags);

//  $response = array();
//  $response['tags']='';

/*
  for($i = 0; $i <= 3; $i++) {
      $response['tags'] = $response['tags'].'#'.$tags[i]." ";
  }
*/
  mysqli_close($con);
  echo json_encode($tags);
 ?>

 <!DOCTYPE html>
 <html>
   <head>
     <meta charset="utf-8">
     <title></title>
   </head>
   <body>
     <form  action="getTags.php" method="post">
        <input type="number" name="temper">
        <input type="submit">
     </form>
   </body>
 </html>
