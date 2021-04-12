package com.example.kotlin02

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.math.abs

class MainActivity : AppCompatActivity(), GestureDetector.OnGestureListener {
    private var id = 0
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
    private lateinit var runnableIncrease: Runnable
    private lateinit var runnableDecrease: Runnable
    private val handlerThread: HandlerThread = HandlerThread("MyThread")
    private var gestureDetectorCompat: GestureDetectorCompat? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        handlerThread.start()
        handler = Handler(handlerThread.looper)
        gestureDetectorCompat = GestureDetectorCompat(this, this)

        // stop increase when user release button
        btn_plus.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                handler.removeCallbacks(runnableIncrease)
                handler.postDelayed(runnable, 2000)
            }
            false
        }

        // stop decrease when user release button
        btn_minus.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                handler.removeCallbacks(runnableDecrease)
                handler.postDelayed(runnable, 2000)
            }
            false
        }
        // increase number really fast
        btn_plus.setOnLongClickListener { handler.postDelayed(runnableIncrease, 50) }

        // decrease number really fast
        btn_minus.setOnLongClickListener { handler.postDelayed(runnableDecrease, 50) }

        //in/de crease number
        runnable = Runnable {
            when {
                id > 0 -> {
                    id--
                    this@MainActivity.runOnUiThread {
                        this.tv_number_show.text = id.toString()
                    }
                    handler.postDelayed(runnable, 50)
                }
                id < 0 -> {
                    id++
                    this@MainActivity.runOnUiThread {
                        this.tv_number_show.text = id.toString()
                    }
                    handler.postDelayed(runnable, 50)
                }
                id == 0 -> handler.removeCallbacks(runnable)

            }
        }

        runnableIncrease = Runnable {
            handler.removeCallbacks(runnable)
            id++
            this@MainActivity.runOnUiThread {
                this.tv_number_show.text = id.toString()
            }
            if (abs(id) >= 100 && abs(id) % 100 == 0) {
                changeTextColor()
            }
            handler.postDelayed(runnableIncrease, 50)
        }

        runnableDecrease = Runnable {
            handler.removeCallbacks(runnable)
            id--
            this@MainActivity.runOnUiThread {
                this.tv_number_show.text = id.toString()
            }
            if (abs(id) >= 100 && abs(id) % 100 == 0) {
                changeTextColor()
            }
            handler.postDelayed(runnableDecrease, 50)
        }

        layout_touch.setOnTouchListener { _, event ->
            gestureDetectorCompat!!.onTouchEvent(event)

            // stop in/de crease when user touch again
            handler.removeCallbacks(runnable)

            if (event.action == MotionEvent.ACTION_UP) {
                handler.postDelayed(runnable, 2000)
            }
            true
        }
    }

    override fun onShowPress(e: MotionEvent?) {
        return
    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        return true
    }

    override fun onDown(e: MotionEvent?): Boolean {
        return true
    }

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent?,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        return true
    }

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent?,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        if (distanceY > 0) {
            id++
        } else {
            id--
        }
        if (abs(id) >= 100 && abs(id) % 100 == 0) {
            changeTextColor()
        }
        tv_number_show.text = id.toString()
        return true
    }

    override fun onLongPress(e: MotionEvent?) {
        return
    }

    private fun changeTextColor() {
        val rnd = Random()
        val color: Int = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
        this.runOnUiThread { this.tv_number_show.setTextColor(color) }
    }
}








