package kr.co.chapter01

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import kr.co.chapter01.databinding.ActivityResultBinding
import kotlin.math.pow

/**
 * @author Gnoss
 * @email silmxmail@naver.com
 * @created 2021-08-22
 * @desc
 */
class ResultActivity : AppCompatActivity(){
    private lateinit var binding : ActivityResultBinding
    var resultText ="저체중"
    var bmiText = "0.0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_result)
        binding.result = this
        val height = intent.getIntExtra("height",0)
        val weight = intent.getIntExtra("weight",0)

        val bmi = weight / (height / 100.0).pow(2.0)
        bmiText= bmi.toString()
        resultText = when{
            bmi >= 35.0 -> "고도 비만"
            bmi >= 30.0 -> "중정도 비만"
            bmi >= 25.0 -> "경도 비만"
            bmi >= 23.0 -> "과체중"
            bmi >= 18.5 -> "정상체중"
            else -> "저체중"
        }

    }
}