package kr.co.chapter01

import android.content.Intent
import android.widget.Toast


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import kr.co.chapter01.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.resultButton.setOnClickListener {
            if (binding.heightEditText.text.isEmpty() || binding.weightEditText.text.isEmpty()){
                Toast.makeText(this,"빈 값이 있습니다.",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // 예외처리가 끝난 후의 상황. 아래로는 빈 값이 올 수 없음.
            val height = binding.heightEditText.text.toString().toInt()
            val weight = binding.weightEditText.text.toString().toInt()

            val intent = Intent(this,ResultActivity::class.java)
            intent.putExtra("height",height)
            intent.putExtra("weight",weight)
            startActivity(intent)
        }
    }
}