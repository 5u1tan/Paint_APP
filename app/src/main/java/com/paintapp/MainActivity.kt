@file:Suppress("DEPRECATION")

package com.paintapp

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.*
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.paintapp.PaintView.Companion.colorList
import com.paintapp.PaintView.Companion.currentBrush
import com.paintapp.PaintView.Companion.pathList
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.widget.*

class MainActivity : AppCompatActivity() {



    var pickedPhoto : Uri? = null
    var pickedBitMap : Bitmap? = null
    companion object{
        var path = Path()
        var paintBrush= Paint()
    }


    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()



        val redbtn = findViewById<ImageButton>(R.id.redColor)
        val bluebtn = findViewById<ImageButton>(R.id.blueColor)
        val blackbtn = findViewById<ImageButton>(R.id.blackColor)
        val greenbtn = findViewById<ImageButton>(R.id.greenColor)
        val yellowbtn = findViewById<ImageButton>(R.id.yellowColor)
        val whitebtn = findViewById<ImageButton>(R.id.whiteColor)
        val eraser = findViewById<ImageButton>(R.id.eraserColor)
        val seekBar=findViewById<SeekBar>(R.id.seekBar)
        val seekBar2=findViewById<SeekBar>(R.id.seekBar2)
        val painView=findViewById<RelativeLayout>(R.id.canvas)
        val imageView=findViewById<ImageView>(R.id.ImageView)
        val smallbtn=findViewById<ImageButton>(R.id.ib_small_brush)
        val mediumbtn=findViewById<ImageButton>(R.id.ib_medium_brush)
        val largebtn=findViewById<ImageButton>(R.id.ib_large_brush)


        smallbtn.setOnClickListener {
            Toast.makeText(this,"small brush", Toast.LENGTH_SHORT).show()
            paintBrush.strokeWidth=8.toFloat()
        }
        mediumbtn.setOnClickListener {
            Toast.makeText(this,"medium brush", Toast.LENGTH_SHORT).show()
            paintBrush.strokeWidth=16.toFloat()
        }
        largebtn.setOnClickListener {
            Toast.makeText(this,"large brush", Toast.LENGTH_SHORT).show()
        paintBrush.strokeWidth=24.toFloat()}

            redbtn.setOnClickListener {
            Toast.makeText(this,"Red", Toast.LENGTH_SHORT).show()
            paintBrush.color=Color.RED
            currentColor(paintBrush.color)
        }

        bluebtn.setOnClickListener {
            Toast.makeText(this,"Blue", Toast.LENGTH_SHORT).show()
            paintBrush.color=Color.BLUE
            currentColor(paintBrush.color)
        }

        blackbtn.setOnClickListener {
            Toast.makeText(this,"Black", Toast.LENGTH_SHORT).show()
            paintBrush.color=Color.BLACK
            currentColor(paintBrush.color)
        }

        greenbtn.setOnClickListener {
            Toast.makeText(this,"Green", Toast.LENGTH_SHORT).show()
            paintBrush.color=Color.GREEN
            currentColor(paintBrush.color)
        }

        yellowbtn.setOnClickListener {
            Toast.makeText(this,"Yellow", Toast.LENGTH_SHORT).show()
            paintBrush.color=Color.YELLOW
            currentColor(paintBrush.color)
        }

        whitebtn.setOnClickListener {
            Toast.makeText(this,"White", Toast.LENGTH_SHORT).show()
            paintBrush.color=Color.WHITE
            currentColor(paintBrush.color)
        }
        eraser.setOnClickListener {
            Toast.makeText(this,"Eraser", Toast.LENGTH_SHORT).show()
            pathList.clear()
            colorList.clear()
            path.reset()
            PaintView.Sizes.clear()

        }

        seekBar.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            var opacity = 0
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                painView.setAlpha(progress /100f);
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })
        seekBar2.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            var opacity = 0
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                imageView.setAlpha(progress /100f);
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })

    }
    private fun currentColor(color: Int){
        currentBrush = color
        path =Path()
    }

    fun pickPhoto(view: View){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                1)
        } else {
            val galeriIntext = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galeriIntext,2)
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 1) {
            if (grantResults.size > 0  && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val galeriIntext = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(galeriIntext,2)
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val imageView=findViewById<ImageView>(R.id.ImageView)
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            pickedPhoto = data.data
            if (pickedPhoto != null) {
                if (Build.VERSION.SDK_INT >= 28) {
                    val source = ImageDecoder.createSource(this.contentResolver,pickedPhoto!!)
                    pickedBitMap = ImageDecoder.decodeBitmap(source)
                    imageView.setImageBitmap(pickedBitMap)
                }
                else {
                    pickedBitMap = MediaStore.Images.Media.getBitmap(this.contentResolver,pickedPhoto)
                    imageView.setImageBitmap(pickedBitMap)
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


}