package com.example.kotlin02

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.SystemClock
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GestureDetectorCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.concurrent.schedule

class MainActivity : AppCompatActivity(), GestureDetector.OnGestureListener {
    private var id = 0
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
    private var checkRunnable: Boolean = false
    private val handlerThread: HandlerThread = HandlerThread("MyThread")
    private var gestureDetectorCompat: GestureDetectorCompat? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        handlerThread.start()
        handler = Handler(handlerThread.looper)
        gestureDetectorCompat = GestureDetectorCompat(this, this)
        layout_touchEvent.setOnTouchListener { _, event ->
            gestureDetectorCompat!!.onTouchEvent(event)

            //remove runnable when user touch on screen
            if (checkRunnable) {
                handler.removeCallbacks(runnable)
            }
            //in/de crease when finger don't touch screen anymore
            if (event.action == MotionEvent.ACTION_UP) {
                runnable = Runnable {
                    when {
                        id > 0 -> {
                            checkRunnable = true
                            for (i in 1..id) {
                                id--
                                Thread.sleep(105)
                                this@MainActivity.runOnUiThread {
                                    this.tv_number_show.text = id.toString()
                                }
                                if (id == 0) {
                                    break
                                }
                            }
                        }
                        id < 0 -> {
                            checkRunnable = true
                            for (i in id..-1) {
                                id++
                                Thread.sleep(105)
                                this@MainActivity.runOnUiThread {
                                    this.tv_number_show.text = id.toString()
                                }
                                if (id == 0) {
                                    break
                                }
                            }
                        }
                    }
                }
                // delay to check if user touch screen again
                handler.postDelayed(runnable, 2000)
            }
            true
        }
    }

    private fun incrementNumber() {

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
        tv_number_show.text = id.toString()
        return true
    }

    override fun onLongPress(e: MotionEvent?) {
        return
    }
}








