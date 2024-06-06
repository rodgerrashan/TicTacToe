package com.aurora.ttt;

public class Player {

    private String playerID;
    private int score;

    public Player(String playerID){
        this.playerID = playerID;
        this.score = 0;
    }
    public  String getPlayerID() {
        return this.playerID;
    }
    public int getScore() {
        return this.score;
    }
    public void setScore(int score) {
        this.score = score;
        System.out.println("$ score updated "+this.playerID+ " "+this.score);
    }
    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }

}
