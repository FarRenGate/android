package com.example.android.snake;

import android.icu.util.ULocale;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by User on 11/06/17.
 *
 * describes general logic of the game
 */

public class SnakeGame {
    private CanvasView canvasView;
    private static int width;
    private static int height;
    private Snake snake;
    private Food food;

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }

    public SnakeGame(CanvasView canvasView, int width, int height) {
        this.canvasView = canvasView;
        this.width = width-1;
        this.height = height-1;
        snake = new Snake(width/2,height/2);
        food = new Food();
        generateFood();


    }

    public void onDraw() {
        for (BasicPoint point : snake) {
            canvasView.drawPoint(point);
        }
        canvasView.drawPoint(food);

    }

    public void moveSnake() {
        snake.move();
        if (food.isInsideTheSnake(snake)) {
            snake.eat(food);
            generateFood();
        }
    }

    public void directSnake(Directions direction) {
        snake.setDirection(direction);
    }

    public boolean isGameOver() {
        return snake.isDead();

    }

    private void generateFood() {
        do {
            food.generate();
        } while (food.isInsideTheSnake(snake));
    }
}
