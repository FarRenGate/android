package com.example.user.circles;

import android.graphics.Color;

import java.util.Random;

/**
 * Created by User on 04/06/17.
 */

public class EnemyCircle extends SimpleCircle {

    public static final int FROM_RADIUS = 10;
    public static final int TO_RADIUS = 100;
    public static final int ENEMY_COLOR = Color.RED;
    public static final int FOOD_COLOR = Color.GREEN;
    public static final int MAX_SPEED = 5;
    private int dx;
    private int dy;

    public EnemyCircle(int x, int y, int radius, int dx, int dy) {
        super(x, y, radius);
        this.dx=dx;
        this.dy=dy;
    }

    public static EnemyCircle getRandomCircle() {
        Random random = new Random();
        int x = random.nextInt( GameManager.getWidth());
        int y = random.nextInt( GameManager.getHeight());
        int dx = -MAX_SPEED +random.nextInt(2*MAX_SPEED);
        int dy = -MAX_SPEED +random.nextInt(2*MAX_SPEED);
        int radius = FROM_RADIUS + random.nextInt(TO_RADIUS-FROM_RADIUS);
        EnemyCircle enemyCircle = new EnemyCircle(x,y,radius, dx, dy);
        return enemyCircle;
    }

    public void setEnemyOrFoodColor(MainCircle mainCircle) {
        if (isSmallerThan(mainCircle)) {
            setColor(FOOD_COLOR);
        } else {
            setColor(ENEMY_COLOR);
        }
    }

    public boolean isSmallerThan(SimpleCircle circle) {
        return this.radius<=circle.getRadius();
    }

    public void moveOneStep() {
        x += dx;
        y += dy;
        checkBounds();
    }

    public boolean checkBounds() {
        boolean intersect = false;
        if (x+radius>GameManager.getWidth()||x<radius) {
            dx=-dx;
            intersect=true;
        }
        if (y+radius>GameManager.getHeight()||y<radius) {
            dy=-dy;
            intersect=true;
        }
        return intersect;
    }
}
