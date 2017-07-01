package com.example.android.minesweeper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by User on 27/06/17.
 *
 * note: i goes for x, j goes for y!
 */

public class Game {

    private static final int PUT_FLAG=1;
    private static final int REMOVE_FLAG=-1;
    private static int numberOfMines;
    private static int width;
    private static int height;
    private static int fieldsTotal;
    private static boolean isGameStarted;
    private static boolean isWin;
    private static boolean isLost;

    private static Cell[][] gameField;

    private int flagsPut;
    private int fieldsOpen;

    private List<Coordinates> listOfMines;

    public Game(int width, int height, int numberOfMines) {
       initGame(width,height,numberOfMines);
    }

    private void initGame(int width, int height, int numberOfMines){
        initSizes(width,height);
        setNumberOfMines(numberOfMines);
        initField();
        flagsPut=0;
        fieldsOpen=0;
        isGameStarted=false;
        isWin=false;
        isLost=false;
    }

    private void initSizes(int w, int h) {
        if (w>1) { // size must be >1
            width=w;
        }
        else {
            width=2;
        }

        if (h>1) {
            height=h;
        }
        else {
            height=2;
        }

        fieldsTotal=width*height;
    }

    private void setNumberOfMines(int mines) {
        if ((mines>(width-1)*(height-1))||mines==0) { // Number of mines must be >0 and < some value (here (X-1)*(Y-1))
           numberOfMines=(width-1)*(height-1);
        }
        else {
            numberOfMines = mines;
        }
    }

    private void initField(){
        gameField = new Cell[width][height]; // creating array of cells

        for (int i=0; i<width; i++) {
            for (int j = 0; j < height; j++) {
                gameField[i][j]=new Cell(); // and initializing all cells
            }
        }
    }

    public String getCellState(int fieldNumber) {
        Coordinates coordinates = fieldNumberToCoordinates(fieldNumber);
        return gameField[coordinates.x][coordinates.y].toString();
    }

    public void click (int fieldNumber) {
        Coordinates coordinates = fieldNumberToCoordinates(fieldNumber);
        if (!isWin&&!isLost) {
            if (!isGameStarted) {
                startGame(fieldNumber);
            }
            if (gameField[coordinates.x][coordinates.y].isClosed()) {
                clickOnCell(coordinates);
                if (checkIfGameWon()) {
                    winGame();
                }
            }
        }
    }

    private boolean checkIfGameWon(){
        boolean allMinesMarked=flagsPut==numberOfMines&&(flagsPut+fieldsOpen==fieldsTotal);
        boolean onlyMinesLeftUnmarked=(fieldsTotal-fieldsOpen)==(numberOfMines);
        return allMinesMarked||onlyMinesLeftUnmarked;
    }

    private void winGame(){
        isWin=true;
        isLost=false;
        isGameStarted=false;
    }

    private void loseGame(){
        isLost=true;
        isWin=false;
        isGameStarted=false;
    }


    public int bombsLeft(){
        return numberOfMines-flagsPut;
    }

    public void rightClick (int fieldNumber) {

        if (!isGameStarted) {
            startGame(fieldNumber);
        }
        Coordinates coordinates = fieldNumberToCoordinates(fieldNumber);
        if (gameField[coordinates.x][coordinates.y].isFlagged()) {
            removeFlag(coordinates);
        } else if (gameField[coordinates.x][coordinates.y].isClosed()) {
            putFlag(coordinates);
        } else if (gameField[coordinates.x][coordinates.y].isOpen()) {
            openSurroundingCells(coordinates);
        }
        if (checkIfGameWon()) {
            winGame();
        }
    }

    private Coordinates fieldNumberToCoordinates (int fieldNumber) {
        Coordinates coordinates = new Coordinates();
        coordinates.x=fieldNumber%width;
        coordinates.y=(fieldNumber-coordinates.x)/width;
        return coordinates;
    }

    private void startGame(int fieldNumber) {
        isGameStarted=true;
        putMines(fieldNumber);
    }

    private void putMines(int fieldNumber) { // place mines after first click
        Set<Integer> mines = new TreeSet<>();// TreeSet of mines to ensure no repetition of positions
        listOfMines = new ArrayList<>();
        Random r = new Random();
        Coordinates coordinates;

        while (mines.size()<numberOfMines) { // while there is still something to add
            int putMine = r.nextInt(fieldsTotal);//rangomly generating mine position within the fieldSize
            if (putMine!=fieldNumber) {// if this is not "clicking" point
                mines.add(putMine); // add mine coordinates to the mine array
            }
        }

        /*
        mines.clear();
        mines.add(3);
        mines.add(5);
        numberOfMines=mines.size();
        */


        for (Integer mine: mines) { // for each mine coordinate
            coordinates = fieldNumberToCoordinates(mine);
            listOfMines.add(coordinates);
            try {
                gameField[coordinates.x][coordinates.y].putMine(); // put a mine there

            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (Integer mine: mines) {// for each mine coordinate
            coordinates = fieldNumberToCoordinates(mine);
            for (int i=coordinates.x-1; i<=coordinates.x+1; i++) // checking all coordinates aroung
                for (int j=coordinates.y-1; j<=coordinates.y+1; j++) {
                    if ((coordinates.x!=i||coordinates.y!=j)&&i>=0&&j>=0&&i<width&&j<height) { // if this is not the mine cell
                        gameField[i][j].addNeighboringMine(); // then number in the cell must be increased by 1
                    }
                }

        }

        mines.clear();

    }

    public void clickOnCell (Coordinates coordinates) {

        if (gameField[coordinates.x][coordinates.y].hasMine()&&!gameField[coordinates.x][coordinates.y].isFlagged()) {
            gameField[coordinates.x][coordinates.y].open();
            for (Coordinates coordinates1: listOfMines) {
                gameField[coordinates1.x][coordinates1.y].open();
            }
            loseGame();
        } else if (gameField[coordinates.x][coordinates.y].isClosed()) {// if the cell is not open yet
            gameField[coordinates.x][coordinates.y].open(); // open the cell
            fieldsOpen++;//increase the number of open fields
            if (gameField[coordinates.x][coordinates.y].getNeighboringMines() == 0) {//if the cell is "0"-cell we must open all surrounding cells
               clickOnSurroundingCells(coordinates);

            }

        }

    }

    public String toString() {
        String s;
        if (isWin) {
            s="You won";
        } else if (isLost) {
            s="You lost";
        } else {
            s=String.valueOf(bombsLeft());
        }
        return s;
    }

    public boolean isInGame(){
        return !isWin&&!isLost;
    }

    private void openSurroundingCells(Coordinates coordinates){
        if (gameField[coordinates.x][coordinates.y].isSuitableForOpenSurrounding()) {
           clickOnSurroundingCells(coordinates);
        }
    }

    private boolean isInField(Coordinates coordinates) {
        return coordinates.x>=0&&coordinates.x<width&&coordinates.y>=0&&coordinates.y<height;
    }

    private boolean isInField(int x, int y) {
        return x>=0&&x<width&&y>=0&&y<width;
    }

    private void clickOnSurroundingCells (Coordinates coordinates) {
        for (int i = coordinates.x - 1; i <= coordinates.x + 1; i++)
            for (int j = coordinates.y - 1; j <= coordinates.y + 1; j++) {
                Coordinates newClick = new Coordinates(i, j);
                if (isInField(newClick) && !newClick.equals(coordinates)) {// if the surrounding cell is withing the field and is different cell
                    clickOnCell(newClick);//click on it
                }
            }
    }

    private void putFlag(Coordinates coordinates) {
        gameField[coordinates.x][coordinates.y].flag();
        flagsPut++;
        recalculateSurroundingFlags(coordinates,PUT_FLAG);
    }

    private void removeFlag(Coordinates coordinates) {
        gameField[coordinates.x][coordinates.y].flag();
        flagsPut--;
        recalculateSurroundingFlags(coordinates,REMOVE_FLAG);
    }

    private void recalculateSurroundingFlags(Coordinates coordinates, int numberOfFlags) {
        for (int i = coordinates.x - 1; i <= coordinates.x + 1; i++)
            for (int j = coordinates.y - 1; j <= coordinates.y + 1; j++) {
                Coordinates newClick = new Coordinates(i, j);
                if (isInField(newClick) && !newClick.equals(coordinates)) {// if the surrounding cell is withing the field and is different cell
                    gameField[newClick.x][newClick.y].addNeighboringFlags(numberOfFlags);
                }
            }
    }
}
