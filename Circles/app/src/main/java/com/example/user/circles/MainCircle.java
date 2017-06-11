package com.example.user.circles;

import android.graphics.Color;

/**
 * Created by User on 04/06/17.
 */

public class MainCircle extends SimpleCircle {
    public static final int INIT_RAD = 50;
    public static final int RELATIVE_SPEED = 30;
    public static final int OUR_COLOR = Color.BLUE;

    public MainCircle(int x, int y) {
        super(x, y, INIT_RAD);
        setColor(OUR_COLOR);
    }


    public void moveMainCircleWhenTouchAt(int x1, int y1) {
        int dx = (x1-x)*RELATIVE_SPEED/GameManager.getWidth();
        int dy = (y1-y)*RELATIVE_SPEED/GameManager.getHeight();
        x+=dx;
        y+=dy;
    }

    public void initRadius() {
        radius=INIT_RAD;
    }

    public void growRadius(EnemyCircle circle) {
        radius=(int)Math.sqrt(Math.pow(radius,2)+Math.pow(circle.radius,2));
    }
}
