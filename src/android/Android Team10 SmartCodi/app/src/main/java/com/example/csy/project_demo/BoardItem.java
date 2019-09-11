package com.example.csy.project_demo;

/**
 * Created by csy on 2017-12-04.
 */

public class BoardItem {
    private int boardID;
    private String imagePath;
    private int boardLikes;
    private String imageTags;
    private boolean liked;

    // consturctor
    public BoardItem(int boardID, String imagePath, int boardLikes, String imageTags, boolean liked) {
        this.boardID = boardID;
        this.imagePath = imagePath;
        this.boardLikes = boardLikes;
        this.imageTags = imageTags;
        this.liked = liked;
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

    public boolean getLiked() {
        return liked;
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

    public void setLiked(boolean liked) {
        this.liked = liked;
    }
}

