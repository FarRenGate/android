package com.example.android.firstapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /*bFindAge.setOnClickListener{

        }*/
    }

    fun bFindAgeEvent(view: View){
        try {
            val yearOfBirth: Int = etGetDOB.text.toString().toInt()
            val currentYear = Calendar.getInstance().get(Calendar.YEAR)
            val myAge = currentYear - yearOfBirth
            textView.text = myAge.toString()
        } catch (ex:Exception) {
            print(ex.message)
        }

    }
}
