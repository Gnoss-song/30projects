package com.example.chapter03

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.EditText
import androidx.core.content.edit
import androidx.core.widget.addTextChangedListener

class DiaryActivity : AppCompatActivity() {

    //MainThread에 연결된 handler생성
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary)

        val diaryEditText = findViewById<EditText>(R.id.diaryEditText)
        val detailPreferences = getSharedPreferences("detail",0)

        //SharedPreference에 저장되어있는 글을 불러오는 공간.
        diaryEditText.setText(detailPreferences.getString("detail",""))


        //아래 람다가 한글자마다 실행되는 것을 막기 위하여 쓰레드 활용 Runnable Interface구현
        val runnable = Runnable {
            getSharedPreferences("diary",0).edit{
                putString("detail",diaryEditText.text.toString())
            }
        }
        //Text가 변화될때마다 안의 람다가 실행된다.
        //handler를 이용하여 0.5초 마다 runnable을 실행하게 만들어 놓았따.
        diaryEditText.addTextChangedListener {
            handler.removeCallbacks(runnable)
            handler.postDelayed(runnable,500)

        }
    }
}