package com.paintapp

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.paintapp.MainActivity.Companion.paintBrush
import com.paintapp.MainActivity.Companion.path


class PaintView: View{

    var params : ViewGroup.LayoutParams?=null // responsible for height and width of the canvas with respect to parent layout

    companion object{


        var pathList=ArrayList<Path>()
        var colorList=ArrayList<Int>()
        var currentBrush= Color.BLACK;
        val Sizes = ArrayList<Float>()
        var currentSize: Float = 8.toFloat()
    }
    constructor(context: Context) : this(context, null){
        init()
    }
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0){
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }


    public fun init(){             //initialize our brush and get the paint brush
        paintBrush.isAntiAlias = true       // making the texture smooth of our strokes
        paintBrush.color= currentBrush      //to set the current color
        paintBrush.style=Paint.Style.STROKE     // style of brush which stroke
        paintBrush.strokeJoin=Paint.Join.ROUND  // stokes rounded edge
        paintBrush.strokeWidth= currentSize     // brush width which the size of the brush

        params =ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT) // initialize our brush
    }



    override fun onTouchEvent(event: MotionEvent): Boolean {    // register the movement of finger over screen
        var x= event.x
        var y =event.y

        when(event.action){                         // when is switch statement in kotlin perform certain operation according to the actions event
            MotionEvent.ACTION_DOWN ->{
                path.moveTo(x,y)
                return true
            }
            MotionEvent.ACTION_MOVE->{
                path.lineTo(x,y)
                pathList.add(path)
                colorList.add(currentBrush)


            }
            else ->return false
        }
        postInvalidate()  //inform the non-UI threads that sometimes some changes have been done on the UI
        return false;
    }

    override fun onDraw(canvas: Canvas) {     //Draw

        for (i in pathList.indices){
            paintBrush.setColor(colorList[i])       //color
            canvas.drawPath(pathList[i], paintBrush) //path

            invalidate() // inform the UI about the changes
        }
    }

}

