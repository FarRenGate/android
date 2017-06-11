package com.example.android.snake;

import android.graphics.Color;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by User on 11/06/17.
 *
 * describes snake "food"
 */

public class Food extends BasicPoint {
    private static final int FOOD_COLOR = Color.GREEN;

    //initially, food is placed outside the game field, then it moves randomly to any unoccupied spot
    public Food(int x, int y) {
        super(-1, -1);
        this.color=FOOD_COLOR;
    }

    public Food() {
        super(-1, -1);
        this.color=FOOD_COLOR;
    }

    //shifts food to random place
    public void generate() {
        Random random = new Random();
        x = random.nextInt( SnakeGame.getWidth());
        y = random.nextInt( SnakeGame.getHeight());
    }


    public boolean isInsideTheSnake (Snake snake) {
        boolean isInsideTheSnake=false;
        for (BasicPoint point : snake) {
            if (point.isCoincide(this)) isInsideTheSnake=true;
        }
        return isInsideTheSnake;
    }


}
