package com.example.android.snake;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import static com.example.android.snake.Directions.*;


/**
 * Created by User on 10/06/17.
 *
 * Responsible for general game handling and drawing
 */

public class CanvasView extends View {
    private static final int FIELD_SIZE = 18;
    private static final int REFRESH_TIME = 1000; //delay in ms between frames

    private static int width;
    private static int height;
    private static int basicPointSize;

    private Canvas canvas;
    private Paint paint;
    private Toast toast;
    private SnakeGame snakeGame;
    private boolean gameOver=false;

    public CanvasView(Context context) {
        super(context);
        initPaint();
   //     runGame.run();
    }

    public CanvasView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();

    }

    private void initPaint() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
    }


//is used to determine the screen size
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.width = w;
        this.height = h;
        setBasicPointSize();
        snakeGame = new SnakeGame(this,Math.round((float)width/basicPointSize) ,Math.round((float)height/basicPointSize));
        runGame.run();
    }

    private void setBasicPointSize () {
        basicPointSize = Math.min(width/FIELD_SIZE,height/FIELD_SIZE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvas=canvas;
        snakeGame.onDraw();
    }

    //is used to draw circles
    public void drawPoint (BasicPoint point) {
        int x = basicPointSize/2+basicPointSize*point.getX();
        int y = basicPointSize/2+basicPointSize*point.getY();
        int radius=point.getDiameter()*basicPointSize/2;
        paint.setColor(point.getColor());
        canvas.drawCircle(x,y,radius,paint);
    }

    //processes screen taps
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Directions direction = returnDirectionAfterRouch(x,y);
            if (!gameOver) {
                snakeGame.directSnake(direction);
            }

            else {
                gameOver=false;
                snakeGame = new SnakeGame(this,width/basicPointSize,height/basicPointSize);
                runGame.run();
            }
        }
        invalidate();
        return true;
    }

    //determines which active area was touched
    private Directions returnDirectionAfterRouch (int x, int y) {
        Directions direction=NONE;
        if (x>width/3&&x<width/3*2) {
            if (y<height/3) direction= UP;
            if (y>height/3*2) direction=  DOWN;
        }

        if (y>height/3&&y<height/3*2) {
            if (x<width/3) direction=  LEFT;
            if (x>width/3*2) direction=  RIGHT;
        }
        return direction;
    }

    // needed to refresh the game periodically
    Handler handler = new Handler(Looper.getMainLooper());
    Runnable runGame = new Runnable(){
        public void run(){
            if (!gameOver) {
                snakeGame.moveSnake();
                gameOver=snakeGame.isGameOver();
                invalidate();
                handler.postDelayed(this, REFRESH_TIME);
            }
            else {
                toast= Toast.makeText(getContext(),"Game Over",Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    };
}
