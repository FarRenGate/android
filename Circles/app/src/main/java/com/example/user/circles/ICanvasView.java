package com.example.user.circles;

/**
 * Created by User on 04/06/17.
 */

public interface ICanvasView {
    void drawCircle(SimpleCircle circle);

    void redraw();

    void showMessage(String text);
}
