package com.example.myalarmapp

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.myalarmapp.databinding.ActivityAlarmBinding

class AlarmActivity : AppCompatActivity(),GestureDetector.OnGestureListener {
    private lateinit var binding: ActivityAlarmBinding
    private var movedRight = false
    private lateinit var gestureDetector: GestureDetector

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        showOnLockScreenAndTurnScreenOn()
        super.onCreate(savedInstanceState)
        binding = ActivityAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d("AlarmActivityNull","${intent.getStringExtra("hour")}:${intent.getStringExtra("time")}")

        binding.tvAlarmTime.text = "${intent.getStringExtra("hour")}:${intent.getStringExtra("time")}"
        binding.tvAlarmName.text = "Будильник"

        gestureDetector = GestureDetector(this,this)

        binding.icTurnOff.setOnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)
            true
        }

        animationView()

    }

    private fun animationView() {
        val alphaAnimation = AnimationUtils.loadAnimation(this, R.anim.animation_alpha)
        val putAsideAnimation = AnimationUtils.loadAnimation(this, R.anim.animation_put_aside)
        val turnOffAnimation = AnimationUtils.loadAnimation(this, R.anim.animation_turn_off)

        putAsideAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {
                binding.bgAnimationPutAside.visibility = View.VISIBLE
            }

            override fun onAnimationEnd(p0: Animation?) {
                binding.bgAnimationPutAside.startAnimation(alphaAnimation)
            }

            override fun onAnimationRepeat(p0: Animation?) {

            }

        })

        binding.bgAnimationPutAside.startAnimation(putAsideAnimation)

        turnOffAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {
                binding.bgAnimationTurnOff.visibility = View.VISIBLE
            }

            override fun onAnimationEnd(p0: Animation?) {
                binding.bgAnimationTurnOff.startAnimation(alphaAnimation)
            }

            override fun onAnimationRepeat(p0: Animation?) {

            }

        })

        binding.bgAnimationTurnOff.startAnimation(turnOffAnimation)

        alphaAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {

            }

            override fun onAnimationEnd(p0: Animation?) {
                if (binding.bgAnimationPutAside.isVisible) {
                    binding.bgAnimationPutAside.visibility = View.GONE
                    binding.bgAnimationTurnOff.startAnimation(turnOffAnimation)
                } else {
                    binding.bgAnimationTurnOff.visibility = View.GONE
                    binding.bgAnimationPutAside.startAnimation(putAsideAnimation)
                }
            }

            override fun onAnimationRepeat(p0: Animation?) {

            }

        })
    }

    private fun showOnLockScreenAndTurnScreenOn() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true)
            setTurnScreenOn(true)
        } else {
            window.addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        or WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
            )
        }
    }

    override fun onDown(p0: MotionEvent): Boolean {
        return true
    }

    override fun onShowPress(p0: MotionEvent) {
    }

    override fun onSingleTapUp(p0: MotionEvent): Boolean {
        return false
    }

    override fun onScroll(e1: MotionEvent, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
        binding.icTurnOff.x = binding.icTurnOff.x - distanceX
        if (distanceX>0){
            binding.icTurnOff.setImageResource(R.drawable.ic_alarm_off)
            movedRight = true
            val anim = TranslateAnimation(0f,binding.icTurnOff.width.toFloat(),0f,0f)
            anim.duration = 300
            binding.icTurnOff.startAnimation(anim)
        }else{
            binding.icTurnOff.setImageResource(R.drawable.ic_stop)
            movedRight = false
            val anim = TranslateAnimation(binding.icTurnOff.x,0f,0f,0f)
            anim.duration = 300
            binding.icTurnOff.startAnimation(anim)

        }
        return true
    }

    override fun onLongPress(p0: MotionEvent) {
    }

    override fun onFling(p0: MotionEvent, p1: MotionEvent, p2: Float, p3: Float): Boolean {
        return false
    }

}