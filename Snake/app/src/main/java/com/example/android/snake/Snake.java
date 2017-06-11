package com.example.android.snake;


import java.util.ArrayList;
import java.util.Iterator;

import static com.example.android.snake.Directions.*;

/**
 * Created by User on 10/06/17.
 *
 * describes the snake
 */

public class Snake implements Iterable<BasicPoint> {
    private final static int INITIAL_LENGTH = 5;
    private final static Directions INITIAL_DIRECTION = RIGHT;

    private int dx, dy;
    private ArrayList<BasicPoint> snake;
    private Directions direction=NONE;

    public Snake(int x, int y) {
        snake=new ArrayList<>();
        for (int i=0; i<INITIAL_LENGTH; i++) {
            snake.add(new BasicPoint(x-i,y));
        }
        setDirection(INITIAL_DIRECTION);
    }

    //move the snake: adds head and deletes last point
    public void move () {
        determineCoordinateChange();
        BasicPoint newHead = new BasicPoint(snake.get(0).getX(),snake.get(0).getY());
        newHead.shift(dx,dy);
        snake.add(0,newHead);
        snake.remove(snake.size()-1);
    }


    //translates Directions to actual coordinates change
    public void determineCoordinateChange () {
        switch (this.direction) {
            case UP: dy=-1; dx=0; break;
            case DOWN: dy=1; dx=0; break;
            case LEFT: dx=-1; dy=0; break;
            case RIGHT: dx=1; dy=0; break;
            default: break;
        }
    }


    //sets new direction of snake move
    public void setDirection (Directions direction) {
        if (direction!=NONE) {
            switch (this.direction) {
                case UP:
                    if (direction == LEFT || direction == RIGHT) this.direction = direction;
                    break;
                case DOWN:
                    if (direction == LEFT || direction == RIGHT) this.direction = direction;
                    break;
                case LEFT:
                    if (direction == UP || direction == DOWN) this.direction = direction;
                    break;
                case RIGHT:
                    if (direction == UP || direction == DOWN) this.direction = direction;
                    break;
                default:
                    this.direction = direction;
                    break;

            }
        }
    }

    //kills the snake if it crosses itself
    public boolean isDead() {
        boolean isDead = false;
        for (int i=1; i<snake.size(); i++) {
            if  (snake.get(0).isCoincide(snake.get(i))) isDead=true;
        }
        return isDead;
    }

    //add the new point to the head of the snake
    public void eat(Food food) {
        snake.add(0,new BasicPoint(food.getX()+dx,food.getY()+dy));
    }

    public Iterator<BasicPoint> iterator() {
        return snake.iterator();
    }
}
