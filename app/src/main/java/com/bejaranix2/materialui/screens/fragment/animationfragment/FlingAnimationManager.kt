package com.bejaranix2.materialui.screens.fragment.animationfragment

import android.app.Activity
import android.graphics.Point
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ViewTreeObserver
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.FlingAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import com.bejaranix2.materialui.databinding.FragmentAnimationBinding
import com.bejaranix2.materialui.screens.activity.MainActivity
import com.bejaranix2.materialui.screens.activity.ToolbarViewManager
import com.bejaranix2.materialui.screens.common.viewmanager.BaseViewManager

class FlingAnimationManager: BaseViewManager {

    private lateinit var mToolbarManager: ToolbarViewManager
    private lateinit var mFragmentAnimationBinding: FragmentAnimationBinding
    private lateinit var mFragment: AnimationFragment
    private lateinit var mActivity:Activity

    constructor(toolbarViewManager: ToolbarViewManager, fragmentAnimationBinding: FragmentAnimationBinding, fragment: AnimationFragment, activity: Activity){
        mToolbarManager = toolbarViewManager
        mFragmentAnimationBinding = fragmentAnimationBinding
        mFragment = fragment
        mActivity = activity
        springListener()
        flingListener()
    }

    private val screenSize by lazy(LazyThreadSafetyMode.NONE){
        val size = Point()
        mActivity.windowManager.defaultDisplay.getSize(size)
        size
    }

    val mForce: SpringForce by lazy(LazyThreadSafetyMode.NONE){
        SpringForce(0f).apply {
            stiffness = SpringForce.STIFFNESS_VERY_LOW
            dampingRatio = SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY
        }
    }

    val springX: SpringAnimation by lazy(LazyThreadSafetyMode.NONE){
        SpringAnimation(mFragmentAnimationBinding.firstConfImage, DynamicAnimation.TRANSLATION_X).setSpring(mForce)
    }

    val springY: SpringAnimation by lazy(LazyThreadSafetyMode.NONE){
        SpringAnimation(mFragmentAnimationBinding.firstConfImage, DynamicAnimation.TRANSLATION_Y).setSpring(mForce)
    }

    var xCorner: Float = -1f
    var yCorner: Float = -1f

    val flingX: FlingAnimation by lazy(LazyThreadSafetyMode.NONE){
        FlingAnimation(mFragmentAnimationBinding.secondConfImage, DynamicAnimation.TRANSLATION_X).setFriction(2f)
    }
    val flingY: FlingAnimation by lazy(LazyThreadSafetyMode.NONE){
        FlingAnimation(mFragmentAnimationBinding.secondConfImage, DynamicAnimation.TRANSLATION_Y).setFriction(2f)
    }


    fun springListener(){
        mFragmentAnimationBinding.firstConfImage.setOnTouchListener{ view, motionEvent ->
            when(motionEvent?.action){
                MotionEvent.ACTION_DOWN->{
                    Log.d("ACTION_DOWN","xd")
                    xCorner = motionEvent.rawX - view.x
                    yCorner = motionEvent.rawY - view.y
                    springX.cancel()
                    springY.cancel()
                }

                MotionEvent.ACTION_MOVE->{
                    mFragmentAnimationBinding.firstConfImage.x = motionEvent.rawX -xCorner
                    mFragmentAnimationBinding.firstConfImage.y = motionEvent.rawY -yCorner
                    Log.d("ACTION_MOVE","${mFragmentAnimationBinding.firstConfImage.x}, ${mFragmentAnimationBinding.firstConfImage.y} ")
                }
                MotionEvent.ACTION_UP->{
                    Log.d("ACTION_UP","XD")
                    springX.start()
                    springY.start()
                }
            }
            true

        }
    }

    private val mListener = object: GestureDetector.SimpleOnGestureListener(){
        override fun onDown(e: MotionEvent?): Boolean {
            return true
        }

        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent?,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            flingX.setStartVelocity(velocityX)
            flingY.setStartVelocity(velocityY)
            flingX.start()
            flingY.start()
            return true
        }
    }


    fun flingListener(){
        flingX.addEndListener { animation, canceled, value, velocity ->
            if(flingY.isRunning)flingY.cancel()
        }
        flingY.addEndListener { animation, canceled, value, velocity ->
            if(flingX.isRunning)flingX.cancel()
        }

        mFragmentAnimationBinding.secondConfImage.viewTreeObserver.addOnGlobalLayoutListener(object: ViewTreeObserver.OnGlobalLayoutListener{
            override fun onGlobalLayout() {
                Log.d("onGlobalLayout", "onGlobalLayout")

                flingX.setMinValue(0f).setMaxValue((screenSize.x - mFragmentAnimationBinding.secondConfImage.width).toFloat())
                flingY.setMinValue(0f).setMaxValue((screenSize.y - mFragmentAnimationBinding.secondConfImage.height).toFloat())
                mFragmentAnimationBinding.secondConfImage.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }})

        val gestureDetector = GestureDetector(mFragment.context,mListener)

        mFragmentAnimationBinding.secondConfImage.setOnTouchListener { v, event ->
            Log.d("setOnTouchListener", "setOnTouchListener")
            gestureDetector.onTouchEvent(event)
        }

        }

}