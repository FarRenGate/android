package com.example.android.minesweeper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener{
    private static int NUMBER_OF_BOMBS;
    private static int WIDTH=100;
    private static int HEIGHT=100;

    TextView tv_bombs_left;
    EditText et_set_bombs;
    Button b_0, b_1, b_2,b_3, b_4, b_5,b_6, b_7, b_8,b_9, b_10, b_11,b_12, b_13, b_14, b_15,  b_16, b_17, b_18, b_19, b_start_game;
    Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        tv_bombs_left = (TextView) findViewById(R.id.tv_bombs_left);
        et_set_bombs = (EditText) findViewById(R.id.et_set_bombs);
        initAllButtons();

    }

    @Override
    public void onClick(View v) {

        if (v.getId()==R.id.b_start_game) {
            NUMBER_OF_BOMBS=0;
            try {
                NUMBER_OF_BOMBS=Integer.parseInt(et_set_bombs.getText().toString());
            } catch (Exception e) {
                NUMBER_OF_BOMBS=0;
            }
            game = new Game(WIDTH,HEIGHT,NUMBER_OF_BOMBS);
            b_start_game.setText("Restart game");
            redraw();
            return;
        }

        if (game==null) {
            return;
        }

        game.click(Integer.parseInt(v.getTag().toString()));
        redraw();
        if (!game.isInGame()) {
            game=null;
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (game==null) {
            return true;
        }
        game.rightClick(Integer.parseInt(v.getTag().toString()));
        redraw();
        if (!game.isInGame()) {
            game=null;
        }
        return true;
    }

    void initAllButtons () {
        b_0 = (Button) findViewById(R.id.b_0);
        b_1 = (Button) findViewById(R.id.b_1);
        b_2 = (Button) findViewById(R.id.b_2);
        b_3 = (Button) findViewById(R.id.b_3);
        b_4 = (Button) findViewById(R.id.b_4);
        b_5 = (Button) findViewById(R.id.b_5);
        b_6 = (Button) findViewById(R.id.b_6);
        b_7 = (Button) findViewById(R.id.b_7);
        b_8 = (Button) findViewById(R.id.b_8);
        b_9 = (Button) findViewById(R.id.b_9);
        b_10 = (Button) findViewById(R.id.b_10);
        b_11 = (Button) findViewById(R.id.b_11);
        b_12 = (Button) findViewById(R.id.b_12);
        b_13 = (Button) findViewById(R.id.b_13);
        b_14 = (Button) findViewById(R.id.b_14);
        b_15 = (Button) findViewById(R.id.b_15);
        b_16 = (Button) findViewById(R.id.b_16);
        b_17 = (Button) findViewById(R.id.b_17);
        b_18 = (Button) findViewById(R.id.b_18);
        b_19 = (Button) findViewById(R.id.b_19);

        b_start_game = (Button) findViewById(R.id.b_start_game);

        b_0.setOnClickListener(this);
        b_1.setOnClickListener(this);
        b_2.setOnClickListener(this);
        b_3.setOnClickListener(this);
        b_4.setOnClickListener(this);
        b_5.setOnClickListener(this);
        b_6.setOnClickListener(this);
        b_7.setOnClickListener(this);
        b_8.setOnClickListener(this);
        b_9.setOnClickListener(this);
        b_10.setOnClickListener(this);
        b_11.setOnClickListener(this);
        b_12.setOnClickListener(this);
        b_13.setOnClickListener(this);
        b_14.setOnClickListener(this);
        b_15.setOnClickListener(this);
        b_16.setOnClickListener(this);
        b_17.setOnClickListener(this);
        b_18.setOnClickListener(this);
        b_19.setOnClickListener(this);

        b_start_game.setOnClickListener(this);

        b_0.setOnLongClickListener(this);
        b_1.setOnLongClickListener(this);
        b_2.setOnLongClickListener(this);
        b_3.setOnLongClickListener(this);
        b_4.setOnLongClickListener(this);
        b_5.setOnLongClickListener(this);
        b_6.setOnLongClickListener(this);
        b_7.setOnLongClickListener(this);
        b_8.setOnLongClickListener(this);
        b_9.setOnLongClickListener(this);
        b_10.setOnLongClickListener(this);
        b_11.setOnLongClickListener(this);
        b_12.setOnLongClickListener(this);
        b_13.setOnLongClickListener(this);
        b_14.setOnLongClickListener(this);
        b_15.setOnLongClickListener(this);
        b_16.setOnLongClickListener(this);
        b_17.setOnLongClickListener(this);
        b_18.setOnLongClickListener(this);
        b_19.setOnLongClickListener(this);
    }

    void redraw(){
        b_0.setText(game.getCellState(Integer.parseInt(b_0.getTag().toString())));
        b_1.setText(game.getCellState(Integer.parseInt(b_1.getTag().toString())));
        b_2.setText(game.getCellState(Integer.parseInt(b_2.getTag().toString())));
        b_3.setText(game.getCellState(Integer.parseInt(b_3.getTag().toString())));
        b_4.setText(game.getCellState(Integer.parseInt(b_4.getTag().toString())));
        b_5.setText(game.getCellState(Integer.parseInt(b_5.getTag().toString())));
        b_6.setText(game.getCellState(Integer.parseInt(b_6.getTag().toString())));
        b_7.setText(game.getCellState(Integer.parseInt(b_7.getTag().toString())));
        b_8.setText(game.getCellState(Integer.parseInt(b_8.getTag().toString())));
        b_9.setText(game.getCellState(Integer.parseInt(b_9.getTag().toString())));
        b_10.setText(game.getCellState(Integer.parseInt(b_10.getTag().toString())));
        b_11.setText(game.getCellState(Integer.parseInt(b_11.getTag().toString())));
        b_12.setText(game.getCellState(Integer.parseInt(b_12.getTag().toString())));
        b_13.setText(game.getCellState(Integer.parseInt(b_13.getTag().toString())));
        b_14.setText(game.getCellState(Integer.parseInt(b_14.getTag().toString())));
        b_15.setText(game.getCellState(Integer.parseInt(b_15.getTag().toString())));
        b_16.setText(game.getCellState(Integer.parseInt(b_16.getTag().toString())));
        b_17.setText(game.getCellState(Integer.parseInt(b_17.getTag().toString())));
        b_18.setText(game.getCellState(Integer.parseInt(b_18.getTag().toString())));
        b_19.setText(game.getCellState(Integer.parseInt(b_19.getTag().toString())));

        tv_bombs_left.setText(game.toString());

    }



}
