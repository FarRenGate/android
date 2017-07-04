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

        for (int x=0; x<width; x++) {
            for (int y = 0; y < height; y++) {
                gameField[x][y]=new Cell(); // and initializing all cells
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
        int x = coordinates.x;
        int y=coordinates.y;
        if (gameField[x][y].isFlagged()) {
            removeFlag(coordinates);
        } else if (gameField[x][y].isClosed()) {
            putFlag(coordinates);
        } else if (gameField[x][y].isOpen()) {
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

    private int coordinatesToFieldNumber (int x, int y) {
        return x+y*width;
    }

    private void startGame(int fieldNumber) {
        isGameStarted=true;
        putMines(fieldNumber);
    }

    private void putMines(int fieldNumber) { // place mines after first click
        listOfMines = new ArrayList<>();
        Random r = new Random();
        int mineX;
        int mineY;

        while (listOfMines.size()<numberOfMines) { // while there is still something to add
            mineX = r.nextInt(width);
            mineY = r.nextInt(height); //rangomly generating mine position within the fieldSize
            if (coordinatesToFieldNumber(mineX,mineY)!=fieldNumber) {// if this is not "clicking" point
                if (!gameField[mineX][mineY].hasMine()){
                    gameField[mineX][mineY].putMine();
                    listOfMines.add(new Coordinates(mineX,mineY));
                }
            }
        }

        /*
        mines.clear();
        mines.add(3);
        mines.add(5);
        numberOfMines=mines.size();
        */



        for (Coordinates mineCoordinates: listOfMines) {// for each mine coordinate
            int x=mineCoordinates.x;
            int y=mineCoordinates.y;
            for (int x2=x-1; x2<=x+1; x2++) // checking all coordinates aroung
                for (int y2=y-1; y2<=y+1; y2++) {
                    if (insideTheFieldAndNotEqualToCoordinates(x2,y2,mineCoordinates)) { // if this is not the mine cell
                        gameField[x2][y2].addNeighboringMine(); // then number in the cell must be increased by 1
                    }
                }

        }
    }

    public void clickOnCell (Coordinates coordinates) {
        int x=coordinates.x;
        int y=coordinates.y;

        if (gameField[x][y].hasMine()&&!gameField[x][y].isFlagged()) {
            gameField[x][y].open();
            for (Coordinates mineCoordinates: listOfMines) {
                gameField[mineCoordinates.x][mineCoordinates.y].open();
            }
            loseGame();
        } else if (gameField[x][y].isClosed()) {// if the cell is not open yet
            gameField[x][y].open(); // open the cell
            fieldsOpen++;//increase the number of open fields
            if (gameField[x][y].getNeighboringMines() == 0) {//if the cell is "0"-cell we must open all surrounding cells
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



    private void clickOnSurroundingCells (Coordinates coordinates) {
        int x=coordinates.x;
        int y=coordinates.y;
        for (int x2 = x - 1; x2 <= x + 1; x2++)
            for (int y2 = y - 1; y2 <= y + 1; y2++) {
                if (insideTheFieldAndNotEqualToCoordinates(x2,y2,coordinates)) {// if the surrounding cell is withing the field and is different cell
                    clickOnCell(new Coordinates(x2,y2));//click on it
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
        int x=coordinates.x;
        int y=coordinates.y;
        for (int x2 = x - 1; x2 <= x + 1; x2++)
            for (int y2 = y - 1; y2 <= y + 1; y2++) {
                if (insideTheFieldAndNotEqualToCoordinates(x2,y2,coordinates)) {// if the surrounding cell is withing the field and is different cell
                    gameField[x2][y2].addNeighboringFlags(numberOfFlags);
                }
            }
    }

    public boolean insideTheFieldAndNotEqualToCoordinates(int x, int y, Coordinates coordinates) {
        return (coordinates.x!=x||coordinates.y!=y)&&
                x>=0&&y>=0&&x<width&&y<height;
    }
}
