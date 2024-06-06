package com.aurora.ttt;

public class Model {
    private static int  [] gameGrid;
    private static Status status;
    private int [][] checklist = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};

    public static int getRounds() {
        return rounds;
    }

    public static void addRounds() {
        System.out.println("$ round"+rounds);
        rounds++;
    }
    public static  void resetRounds() {
        rounds = 0;
    }

    private static int rounds = 0;

    public void setWinCells(int[] winCells) {
        this.winCells = winCells;
    }

    private int [] winCells;


    public int[] getWinCells() {
        return winCells;
    }
    public static Status getStatus() {
        return status;
    }

    public static void setStatus(Status status) {
        System.out.println("$ state updated to " + status);
        Model.status = status;

    }


    public int[]getGameGrid() {
        return gameGrid;
    }

    public void setGameGrid(int[] gameGrid) {
        this.gameGrid = gameGrid;
    }

    public Model() {
        System.out.println("$ initiating the model");
        gameGrid = new int[9];
        for (int i = 0;i <9; i++){
            gameGrid[i] = 0;
        }
    }

    public  void makeMove(int index,int userSymbol){
        if (isValidMove(index)){
            gameGrid[index] = userSymbol;
        }

    }

    public boolean isValidMove(int index){
        if (gameGrid[index] == 0){
            return true;
        }
        return false;
    }

    public void showModelArray(){
        for (int i = 0; i < gameGrid.length; i++) {
            System.out.print(gameGrid[i] + " ");
        }
        System.out.println();
    }

    public boolean checkWinner(){
        if(isTie()){
            Model.setStatus( Status.TIE );
            return true;
        }

        for (int j = 0; j< checklist.length; j++) {
            int sum = gameGrid[checklist[j][0]]+gameGrid[checklist[j][1]]+gameGrid[checklist[j][2]];
            if (sum == -3){
                setStatus(Status.USER02_WIN );
                winCells = checklist[j];
                return  true;
            } else if (sum == 3) {
                setStatus(Status.USER01_WIN );
                winCells = checklist[j];
                return true;
            }
        }
        return false;

    }

    public void resetGameGrid(){
        gameGrid = new int[9];
        setWinCells(new int[]{0, 0, 0});

    }

    public boolean isTie(){
        for (int i = 0; i < gameGrid.length; i++) {
            if (gameGrid[i] == 0){
                return false;
            }

        }
        return true;
    }

}
