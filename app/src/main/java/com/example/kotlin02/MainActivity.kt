package com.example.kotlin02

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
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
    private var gestureDetectorCompat: GestureDetectorCompat? = null
    private var id = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        gestureDetectorCompat = GestureDetectorCompat(this, this)
        btn_test.setOnClickListener { id = 0 }
    }

    override fun onShowPress(e: MotionEvent?) {
        return
    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        Log.d("datnt", "onSingleTapUp: ")
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
            if (id > 100) {
                tv_result.setTextColor(ContextCompat.getColor(this, R.color.purple_700))
            } else {
                tv_result.setTextColor(ContextCompat.getColor(this, R.color.black))
            }
        } else {
            id--
        }
        tv_result.text = id.toString()
        return true
    }

    override fun onLongPress(e: MotionEvent?) {
        return
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        gestureDetectorCompat!!.onTouchEvent(event)
        if (event != null) {
            if (event.action == MotionEvent.ACTION_UP) {
                if (id > 0) {
                    Thread {
                        Thread.sleep(1500)
                        if (event.action != MotionEvent.ACTION_MOVE) {
                            for (i in 1..id) {
                                Log.d("datnt", "onFling: $id")
                                id--
                                Thread.sleep(105)
                                this@MainActivity.runOnUiThread {
                                    this.tv_result.text = id.toString()
                                }
                                if (id == 0) {
                                    break
                                }
                            }
                        } else {
                            Log.d("datnt", "onTouchEvent: donothing")
                        }
                    }.start()
                } else {
                    Thread {
                        Thread.sleep(1500)
                        if (event.action != MotionEvent.ACTION_MOVE) {
                            for (i in id..-1) {
                                Log.d("datnt", "onFling: $id")
                                id++
                                Thread.sleep(105)
                                this@MainActivity.runOnUiThread {
                                    this.tv_result.text = id.toString()
                                }
                                if (id == 0) {
                                    break
                                }
                            }
                        } else {
                            Log.d("datnt", "onTouchEvent: donothing")
                        }
                    }.start()
                }

            }
        }
        return super.onTouchEvent(event)
    }
}






