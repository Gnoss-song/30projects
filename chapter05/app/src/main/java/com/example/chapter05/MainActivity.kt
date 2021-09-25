package com.example.chapter05

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.chapter05.databinding.ActivityMainBinding
import kotlinx.coroutines.selects.select

class MainActivity : AppCompatActivity() {

    private val imageViewList: List<ImageView> by lazy {
        mutableListOf<ImageView>().apply {
            add(binding.imageView11)
            add(binding.imageView12)
            add(binding.imageView13)
            add(binding.imageView21)
            add(binding.imageView21)
            add(binding.imageView21)
        }
    }

    lateinit var binding: ActivityMainBinding

    private val imageUriList : MutableList<Uri> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAddPhotoButton()
        initStartPhotoFrameModeButton()
    }

    private fun initAddPhotoButton() {
        binding.addPhotoButton.setOnClickListener {
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED -> {
                    //권한이 부여되었을때
                    navigatePhotos()
                }
                shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                    //교육용 팝업 확인 후 권한 팝업을 띄우는 기능
                    showPermissionContextPopup()
                }
                else -> {
                    //권한을 요청하는 팝업
                    requestPermissions(
                        arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1000
                    )
                }
            }
        }
    }


    private fun initStartPhotoFrameModeButton() {
        binding.startPhotoFrameModeButton.setOnClickListener {
            val intent = Intent(this,PhotoFrameActivity::class.java)
            imageUriList.forEachIndexed { index, uri ->
                intent.putExtra("photo$index",uri.toString())
            }
            intent.putExtra("photoListSize",imageUriList.size)
            startActivity(intent)

        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1000 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //권한이 부여되었으므로 실행
                    navigatePhotos()
                } else {
                    Toast.makeText(this, "권한을 거부하셨습니다.", Toast.LENGTH_SHORT).show()
                }

            }
            else -> {

            }
        }
    }

    private fun navigatePhotos() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, 2000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK){
            return
        }
        when (requestCode) {
            2000 -> {
                val selectedImageUri : Uri? = data?.data
                if (selectedImageUri != null){
                    if (imageUriList.size ==6){
                        Toast.makeText(this,"이미 사진이 가득 찼습니다.",Toast.LENGTH_SHORT).show()
                        return
                    }
                    imageUriList.add(selectedImageUri)
                    imageViewList[imageUriList.size - 1].setImageURI(selectedImageUri)
                }else{
                    Toast.makeText(this,"사진을 가져오지 못했습니다.",Toast.LENGTH_SHORT).show()
                }
            }
            else -> {
                //에러처리
                Toast.makeText(this,"사진을 가져오지 못했습니다.",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showPermissionContextPopup() {
        AlertDialog.Builder(this)
            .setTitle("권한이 필요합니다.")
            .setMessage("전자액자에 앱어서 사진을 불러오기 위해 권한이 필요합니다.")
            .setPositiveButton("동의하기") { _, _ ->
                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1000)
            }
            .setNegativeButton("취소하기") { _, _ ->
            }
            .show()
    }

}