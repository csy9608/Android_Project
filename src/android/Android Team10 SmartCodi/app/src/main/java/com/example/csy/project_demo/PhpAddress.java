package com.example.csy.project_demo;

/**
 * Created by csy on 2017-11-30.
 */

public class PhpAddress {
    final static String Detail = "http://csy9608.cafe24.com/Detail.php"; //; IN: boardID / OUT: boardID, boardDate, imagePath, imageTags
    final static String Login = "http://csy9608.cafe24.com/Login.php"; //  IN: userID, userPassword / OUT: userID, success
    final static String MainPage = "http://csy9608.cafe24.com/MainPage.php"; // IN: start, align_likes, temperature, limit / OUT: boardID, imagePath, imageTags, boardLikes, end
    final static String MyPage ="http://csy9608.cafe24.com/MyPage.php"; // IN: userID, start, align_likes, limit / OUT: boardID, imagePath, imageTags, boardLikes, end
    final static String SignUp = "http://csy9608.cafe24.com/SignUp.php"; //  IN: userID, userPassword, userName, userAge, userGender / OUT: userID, success
    final static String Upload = "http://csy9608.cafe24.com/Upload.php"; // IN: userID, temperature, encoded_string(이미지 base64 encoded string), imageTags / OUT: boardID, success
    final static String CheckId = "http://csy9608.cafe24.com/CheckId.php"; // IN:  userID / OUT: exists
    final static String UpdateLike = "http://csy9608.cafe24.com/UpdateLike.php"; //  IN: boardID / OUT: success
    final static String ModifyMyInfo = "http://csy9608.cafe24.com/ModifyMyInfo.php"; // IN: set, delete,  userID, userName, (userPassword), userAge, userGender /  OUT: (userID, userName, userAge, userGender), success
    final static String ModifyBoard = "http://csy9608.cafe24.com/ModifyBoard.php"; //  IN: userID, boardID, (temperature, encoded_string, imageTags,) delete /  OUT: success, authority
    final static String SearchPage = "http://csy9608.cafe24.com/SearchPage.php";
    final static String GetFreqTags = "http://csy9608.cafe24.com/GetFreqTags.php";
}
