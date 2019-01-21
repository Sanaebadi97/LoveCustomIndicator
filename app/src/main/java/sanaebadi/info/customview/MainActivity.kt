package sanaebadi.info.customview

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.View
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import sanaebadi.info.customview.databinding.ActivityMainBinding
import java.io.ByteArrayOutputStream
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var cheers: CustomIndicator
    private lateinit var heart: CustomIndicator
    private lateinit var cake: CustomIndicator
    private var bitmap: Bitmap? = null

    companion object {
        private var IMAGE_REQUEST = 777

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        /*Init Views*/
        this.cheers = binding.cheers
        this.heart = binding.heart
        this.cake = binding.cake


        /*Handlers*/
        val handlers = MyHandler()
        binding.handler = handlers

        /*set image For Images*/
        cheers.setImage(R.mipmap.cheers)
        heart.setImage(R.mipmap.hearts)
        cake.setImage(R.mipmap.cake)

        /*Set Enable For View*/
        cheers.isEnableView(false)
        heart.isEnableView(false)
        cake.isEnableView(false)
    }

    /*Click Listener For Images*/
    inner class MyHandler {
        @SuppressLint("SetTextI18n")
        fun onCheersClick(view: View) {
            cheers.isEnableView(true)
            heart.isEnableView(false)
            cake.isEnableView(false)

            binding.txtResult.text=getString(R.string.cheers)
        }

        fun onHeartsClick(view: View) {
            cheers.isEnableView(false)
            heart.isEnableView(true)
            cake.isEnableView(false)
            binding.txtResult.text=getString(R.string.heart)
        }

        fun onCakeClick(view: View) {
            cheers.isEnableView(false)
            heart.isEnableView(false)
            cake.isEnableView(true)
            binding.txtResult.text=getString(R.string.cake)
        }

        fun onSelectImage(view: View) {
            selectImage()
        }
    }

    /*Method for select Image From Gallery*/
    private fun selectImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {

            val path = data.data
            try {
                bitmap = MediaStore.Images.Media.getBitmap(contentResolver, path)
                binding.imgProfile.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }


    /*Compress and Decode The Image To Upload IN The Server*/
    private fun imageToString(): String {

        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val imageByte = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(imageByte, Base64.DEFAULT)
    }

}

