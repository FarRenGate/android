package com.example.android.minesweeper;

/**
 * Created by User on 27/06/17.
 */

public class Cell {


    private static final int DECREASE_FLAGS=-1;
    private static final int INCREASE_FLAGS=1;

    private boolean containsMine;
    private State state;
    private int neighboringMines;
    private int neighboringFlags;

    public Cell(){
        state=State.CLOSED;
        neighboringMines=0;
        containsMine=false;
    }

    public void addNeighboringMine() {
        neighboringMines++;
    }
    public void addNeighboringFlags(int NumberOfFlags) {
        neighboringFlags+=NumberOfFlags;
    }

    public boolean isSuitableForOpenSurrounding() {
        return neighboringMines==neighboringFlags&&state==State.OPEN;
    }

    public int getNeighboringMines() {
        return neighboringMines;
    }

    public void putMine() {
        containsMine=true;
    }

    public int flag(){
        if (state==State.CLOSED) {
            state=State.FLAGGED;
            return INCREASE_FLAGS;
        } else if (state==State.FLAGGED) {
            state=State.CLOSED;
            return DECREASE_FLAGS;
        }
        return 0;
    }

    public String click(){
        if (this.isClosed()){
            this.open();
        }
        return this.toString();
    }

    public boolean isOpen(){
        return this.state==State.OPEN;
    }

    public boolean isFlagged(){
        return this.state==State.FLAGGED;
    }

    public void open(){
        if (this.state==State.CLOSED) {
            this.state = State.OPEN;
        }
    }

    public boolean isClosed() {
        return  (this.state==State.CLOSED);
    }

    public boolean isExploded() {
        return state==State.OPEN&&containsMine;
    }

    public boolean hasMine(){
        return containsMine;
    }

    @Override
    public String toString() {
        String s;
        if (isFlagged()) {
            s = "F";
        } else if (isClosed()) {
            s = "";
        } else  if (isExploded()) {
            s="M";
        }   else {
            s=String.valueOf(neighboringMines);
        }
        return s;
    }

}
