package com.example.android.tictactoe

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    var board=Array(9){0}
    var activePlayer=1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun bOnClick(view: View) {
        val bSelected = view as Button
        var cellID=0
        when (bSelected.id) {
            R.id.b1 -> cellID=1
            R.id.b2 -> cellID=2
            R.id.b3 -> cellID=3
            R.id.b4 -> cellID=4
            R.id.b5 -> cellID=5
            R.id.b6 -> cellID=6
            R.id.b7 -> cellID=7
            R.id.b8 -> cellID=8
            R.id.b9 -> cellID=9
        }

        PlayGame(bSelected)
        CheckWinner()
    }

    fun PlayGame(bSelected:Button) {
        var cellID=-1
        cellID=returnCellId(bSelected)-1
        board[cellID]=activePlayer

        if (activePlayer==1) {
            bSelected.text="X"
            bSelected.setBackgroundColor(Color.GREEN)
            activePlayer=2
            AutoPlay()
        } else {
            bSelected.text="O"
            bSelected.setBackgroundColor(Color.BLUE)
            activePlayer=1
        }
        bSelected.isEnabled = false
    }

    fun CheckWinner(){
        var winner=0
        if ((board[0]==board[1]&&board[1]==board[2])||
                (board[0]==board[3]&&board[3]==board[6])||
                (board[0]==board[4]&&board[4]==board[8])) {
            winner=board[0]
        }
        if ((board[2]==board[5]&&board[5]==board[8])||
                (board[2]==board[4]&&board[4]==board[6])) {
            winner=board[2]
        }
         if ((board[6]==board[7]&&board[7]==board[8])) {
            winner=board[6]
        }
        if (winner!=0) {
            Toast.makeText(this,"Player "+winner.toString()+" wins",Toast.LENGTH_SHORT).show()
        }
    }

    fun returnCellId(bSelected:Button):Int{
        var cellID=0
        when (bSelected.id) {
            R.id.b1 -> cellID=1
            R.id.b2 -> cellID=2
            R.id.b3 -> cellID=3
            R.id.b4 -> cellID=4
            R.id.b5 -> cellID=5
            R.id.b6 -> cellID=6
            R.id.b7 -> cellID=7
            R.id.b8 -> cellID=8
            R.id.b9 -> cellID=9
        }
        return cellID
    }



    fun AutoPlay(){
        var emptyCells=ArrayList<Int>()
        for (cellID in 0..8) {
            if (board[cellID]==0) emptyCells.add(cellID)
        }
        var r= Random()
        val randIndex=r.nextInt(emptyCells.size)
        val clickedCell = emptyCells[randIndex]

        var bSelected:Button=b1
        when (clickedCell){
            0->bSelected=b1
            1->bSelected=b2
            2->bSelected=b3
            3->bSelected=b4
            4->bSelected=b5
            5->bSelected=b6
            6->bSelected=b7
            7->bSelected=b8
            8->bSelected=b9
        }

        PlayGame(bSelected)
    }
}
