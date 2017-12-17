package com.example.csy.project_demo;

import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.List;

/**
 * Created by csy on 2017-12-04.
 */

public class MyPageItem {
    MyPageSubItem[] myPageSubItems = new MyPageSubItem[3];

    public MyPageItem(MyPageSubItem[] myPageSubItems) {
        this.myPageSubItems = myPageSubItems;
    }

    public MyPageItem() {}

    public MyPageSubItem[] getMyPageSubItems() {
        return myPageSubItems;
    }

    public MyPageSubItem getMyPageSubItem(int index) {
        return myPageSubItems[index];
    }

    public void setMyPageSubItems(int index, MyPageSubItem myPageSubItem) {
        this.myPageSubItems[index] = myPageSubItem;
    }

    public void setMyPageSubItems(MyPageSubItem[] myPageSubItems) {
        this.myPageSubItems = myPageSubItems;
    }

    public static class MyPageSubItem{
        private int boardID;
        private String imagePaths;
        private int boardLikes;

        public MyPageSubItem(int boardID, String imagePaths, int boardLikes) {
            this.boardID = boardID;
            this.imagePaths = imagePaths;
            this.boardLikes = boardLikes;
        }

        public int getBoardID() {
            return boardID;
        }

        public String getImagePaths() {
            return imagePaths;
        }

        public int getBoardLikes() {
            return boardLikes;
        }

        public void setBoardID(int boardID) {
            this.boardID = boardID;
        }

        public void setImagePaths(String imagePaths) {
            this.imagePaths = imagePaths;
        }

        public void setBoardLikes(int boardLikes) {
            this.boardLikes = boardLikes;
        }
    }
}
