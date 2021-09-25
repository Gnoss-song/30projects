package com.example.chapter05

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.chapter05.databinding.ActivityPhotoFrameBinding
import kotlin.concurrent.timer

class PhotoFrameActivity : AppCompatActivity() {
    lateinit var binding: ActivityPhotoFrameBinding
    private val photolist = mutableListOf<Uri>()
    private var currentPosition = 0
    private var timer : Tiemr? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotoFrameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getPhotoUriFromIntent()
        startTimer()
    }

    private fun getPhotoUriFromIntent() {
        val size = intent.getIntExtra("photoListSize", 0)
        for (i in 0..size) {
            intent.getStringExtra("photo$i")?.let {
                photolist.add(Uri.parse(it))
            }
        }
    }
    private fun startTimer(){
        timer = timer(period = 5* 1000){
            runOnUiThread {
                val current = currentPosition
                val next = if (photolist.size<= currentPosition +1) 0 else currentPosition+1

                binding.backgroundPhotoImageView.apply {
                    setImageURI(photolist[current])
                    alpha = 0f
                    setImageURI(photolist[next])
                    animate()
                        .alpha(1.0f)
                        .setDuration(1000)
                        .start()
                }
                currentPosition = next
            }
        }
    }

    override fun onStop() {
        super.onStop()
        timer?.cancel()
    }

    override fun onStart() {
        super.onStart()
        startTimer()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
    }
}