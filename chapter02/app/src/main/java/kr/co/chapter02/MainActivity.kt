package kr.co.chapter02

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {
    private val clearButton : Button by lazy{
        findViewById(R.id.clearButton)
    }
    private val addButton : Button by lazy{
        findViewById(R.id.addButton)
    }
    private val runButton : Button by lazy{
        findViewById(R.id.runButton)
    }
    private val numberPicker : NumberPicker by lazy{
        findViewById(R.id.numberPicker)
    }

    private val numberTextViewList : List<TextView> by lazy {
        listOf<TextView>(
            findViewById(R.id.firstNumberTextView),
            findViewById(R.id.secondNumberTextView),
            findViewById(R.id.thirdNumberTextView),
            findViewById(R.id.fourthNumberTextView),
            findViewById(R.id.fifthNumberTextView),
            findViewById(R.id.sixthNumberTextView)
        )
    }
    private var didRun = false
    private val pickNumberSet = mutableSetOf<Int>()
    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numberPicker.minValue = 1
        numberPicker.maxValue = 45

        runButton.setOnClickListener {
            val list = getRandomNumber()
            didRun = true

            list.forEachIndexed {index,number ->
                val textview = numberTextViewList[index]
                textview.text = number.toString()
                textview.isVisible = true

                setNumberBackground(number,textview)
            }
        }
        addButton.setOnClickListener {
            if (didRun){
                Toast.makeText(this,"초기화 후 시도해주세요",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (pickNumberSet.size>=5){
                Toast.makeText(this,"번호는 5개까지만 선택할 수 있습니다.",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (pickNumberSet.contains(numberPicker.value)){
                Toast.makeText(this,"이미 추가된 번호입니다.",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val textView = numberTextViewList[pickNumberSet.size]
            textView.isVisible = true
            textView.text = numberPicker.value.toString()
            pickNumberSet.add(numberPicker.value)

            setNumberBackground(numberPicker.value,textView)
        }

        clearButton.setOnClickListener {
            pickNumberSet.clear()
            didRun = false
            numberTextViewList.forEach {
                it.isVisible = false
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setNumberBackground(number:Int, textView: TextView){
        when(number){
            in 1..10 -> textView.background = getDrawable(R.drawable.circle_yellow)
            in 11..20 -> textView.background = getDrawable(R.drawable.circle_blue)
            in 21..30 -> textView.background = getDrawable(R.drawable.circle_red)
            in 31..40 -> textView.background = getDrawable(R.drawable.circle_green)
            else -> textView.background = getDrawable(R.drawable.circle_grey)
        }
    }
    private fun getRandomNumber(): List<Int>{
        val numberList = mutableListOf<Int>().apply {
            for (i in 1..45){
                if (pickNumberSet.contains(i)){
                    continue
                }
                this.add(i)
            }
        }
        numberList.shuffle()
        val newlist = pickNumberSet.toList() + numberList.subList(0,6-pickNumberSet.size)
        return newlist.sorted()
    }
}
