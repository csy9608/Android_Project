package com.example.csy.project_demo;

/**
 * Created by csy on 2017-12-04.
 */

public class MainPageItem {
    private int boardID;
    private String imagePath;
    private int boardLikes;
    private String imageTags;

    // consturctor
    public MainPageItem(int boardID, String imagePath, int boardLikes, String imageTags) {
        this.boardID = boardID;
        this.imagePath = imagePath;
        this.boardLikes = boardLikes;
        this.imageTags = imageTags;
    }

    // getter
    public int getBoardID() {
        return boardID;
    }

    public String getImagePath() {
        return imagePath;
    }

    public int getBoardLikes() {
        return boardLikes;
    }

    public String getImageTags() {
        return imageTags;
    }

    // setter
    public void setBoardID(int boardID) {
        this.boardID = boardID;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setBoardLikes(int boardLikes) {
        this.boardLikes = boardLikes;
    }

    public void setImageTags(String imageTags) {
        this.imageTags = imageTags;
    }
}
