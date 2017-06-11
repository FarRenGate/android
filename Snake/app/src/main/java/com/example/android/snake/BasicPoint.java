package com.example.android.snake;

import android.graphics.*;

/**
 * Created by User on 10/06/17.
 *
 * just describes a basic circle
 */

public class BasicPoint {
    private static final int DEFAULT_COLOR = Color.BLACK;
    protected static final int DEFAULT_DIAMETER = 1;

    protected int x;
    protected int y;
    protected int diameter;
    protected int color;

    public BasicPoint(int x, int y) {
        this.x=x;
        this.y=y;
        this.diameter=DEFAULT_DIAMETER;
        this.color=DEFAULT_COLOR;
    }

    public int getDiameter() {
        return diameter;
    }

    public int getColor() {
        return color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public void shift (int dx, int dy) {
        this.x+=dx;
        this.y+=dy;
        if (x>SnakeGame.getWidth()) x=0;
        if (x<0) x=SnakeGame.getWidth();
        if (y>SnakeGame.getHeight()) y=0;
        if (y<0) y=SnakeGame.getHeight();
    }




    public boolean isCoincide (BasicPoint basicPoint) {
        return (basicPoint.x==x&&basicPoint.y==y);
    }
}
