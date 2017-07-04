package com.example.android.minesweeper;

/**
 * Created by User on 29/06/17.
 */

public class Coordinates {
    public int x;
    public int y;


    public Coordinates(Coordinates coordinates) {
        this.x = coordinates.x;
        this.y = coordinates.y;
    }


    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinates() {
        this.x = 0;
        this.y = 0;
    }

    @Override
    public int hashCode() {
        return x+y;
    }

    public boolean equals (Object o) {

        if (o == this) return true;
        if (!(o instanceof Coordinates)) {
            return false;
        }
        Coordinates coordinates = (Coordinates) o;
        return x==coordinates.x&&y==coordinates.y;
    }

}
