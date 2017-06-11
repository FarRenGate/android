package com.example.user.circles;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;

/**
 * Created by User on 04/06/17.
 */

public class GameManager {
    public static final int MAX_ENEMIES = 10;
    public static final String YOU_LOSE = "YOU LOSE!";
    public static final String YOU_WIN = "YOU WIN!";
    private MainCircle mainCircle;
    private ArrayList<EnemyCircle> enemyCircles;
    private CanvasView canvasView;
    private static int width;
    private static int height;


    public GameManager(CanvasView canvasView, int w, int h) {
        this.canvasView=canvasView;
        width=w;
        height=h;
        initMainCircle();
        initEnemyCircles();

    }

    private void initEnemyCircles() {
        enemyCircles = new ArrayList<>();
        SimpleCircle mainCircleArea = mainCircle.getCircleArea();
        for (int i=0; i< MAX_ENEMIES; i++) {
            EnemyCircle circle;

            do {
                circle=EnemyCircle.getRandomCircle();
            } while (circle.isIntesect(mainCircleArea)||circle.checkBounds());
            enemyCircles.add(circle);
        }
        calculateAndSetCirclesColor();
    }

    private void calculateAndSetCirclesColor() {
        for (EnemyCircle circle : enemyCircles) {
            circle.setEnemyOrFoodColor(mainCircle);
        }
    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }


    private void initMainCircle() {
        mainCircle = new MainCircle(width/2,height/2);
    }

    public void onDraw() {
       canvasView.drawCircle(mainCircle);
       for (EnemyCircle circle: enemyCircles) {
           canvasView.drawCircle(circle);
       }
    }

    public void onTouchEvent(int x, int y) {
        mainCircle.moveMainCircleWhenTouchAt(x,y);
        checkCollisions();
        moveCircles();
    }

    private void checkCollisions() {
        SimpleCircle circleForDel = null;
        for (EnemyCircle circle : enemyCircles) {
            if (mainCircle.isIntesect(circle)) {
                if (circle.isSmallerThan(mainCircle)) {
                    mainCircle.growRadius(circle);
                    circleForDel=circle;
                    calculateAndSetCirclesColor();
                    break;
                } else {
                    gameEnd(YOU_LOSE);
                }

            }
        }
        if (circleForDel!=null) {
            enemyCircles.remove(circleForDel);
        }
        if (enemyCircles.isEmpty()) {
            gameEnd(YOU_WIN);
        }
    }

    private void gameEnd(String text) {
        canvasView.showMessage(text);
        mainCircle.initRadius();
        initEnemyCircles();
        canvasView.redraw();
    }

    private void moveCircles() {
        for (EnemyCircle circle : enemyCircles) {
            circle.moveOneStep();
        }
    }
}
