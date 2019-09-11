<?php
// IN: set, delete,  userID, userName, (userPassword), userAge, userGender,  OUT: (userID, userName, userAge, userGender), success

  $db_server = "localhost";
  $db_id = "id";
  $db_password = "pwd";
  $db_name = "name";

  $userID = $_POST['userID'];

  $response = array();
  $response['success'] = false;

  $con = mysqli_connect($db_server, $db_id , $db_password, $db_name);

  if(isset($_POST['delete'])){
    $query = "DELETE FROM USER WHERE userID='$userID'";
    if(mysqli_query($con, $query))
      $response['success'] = true;
  }

  else if(isset($_POST['set'])){
      $userName = $_POST['userName'];
      $userAge = $_POST['userAge'];
      $userGender = $_POST['userGender'];

      if(isset($userPassword)){
        $userPassword = $_POST['userPassword'];
        $statement = mysqli_prepare($con, "UPDATE USER SET userPassword=?, userName=?, userAge=?, userGender=? WHERE userID=?");
        mysqli_stmt_bind_param($statement, "ssiss",$userPassword, $userName, $userAge, $userGender, $userID);
      }
      else{
        $statement = mysqli_prepare($con, "UPDATE USER SET userName=?, userAge=?, userGender=? WHERE userID=?");
        mysqli_stmt_bind_param($statement, "siss", $userName, $userAge, $userGender, $userID);
      }

      if(mysqli_stmt_execute($statement))
        $response['success'] = true;
  }
  else{
      $query = "SELECT * FROM USER WHERE userID='$userID'";
      $result = mysqli_query($con, $query);

      while ($row = mysqli_fetch_array($result)) {
        $response["success"] = true;
        $response["userID"] = $row['userID'];
        $response["userName"] = $row['userName'];
        $response["userAge"] = $row['userAge'];
        $response["userGender"] = $row['userGender'];
      }

  }
    mysqli_close($con);
    echo json_encode($response);

 ?>
