package com.bejaranix2.materialui.screens.fragment.animationfragment

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.PathInterpolator
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.bejaranix2.materialui.R
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.sin

class CircleView(context: Context, attrs: AttributeSet): ConstraintLayout(context,  attrs) {

    private val mSwipeStyle: Int
    private var mViewHolders:ArrayList<CircleAdapter.CircleViewHolder> = arrayListOf()
    private var mCenterViewVertical = 0
    private var mCenterViewHorizontal = 0
    private var mPositionCircleUnit = 0f
    private var mCurrentPosition = 0
    var mContext:Context? = null
    
    var mAdapter:CircleAdapter<in CircleAdapter.CircleViewHolder>? = null

    set(value) {
        field = value
        configView()
    }

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CircleView,
            0, 0).apply {
                try {
                    mSwipeStyle = getInteger(R.styleable.CircleView_swipeStyle, 0)
                } finally {
                    recycle()
                }
        }
    }

    private fun configView() {
        viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                mCenterViewHorizontal = width / 2
                mCenterViewVertical = height / 2
                populate()
            }
        })
    }

    private fun populate(){
        bringToFront()
        invalidate()
        setGestureListener()
        mAdapter?.apply {
            val numElem = getItemCount()
            val viewTypes = arrayListOf<Int>()
            for(index in  0 until numElem){
                viewTypes.add(getItemViewType(index))
                val viewHolder = onCreateViewHolder(this@CircleView, viewTypes[index]) as CircleAdapter.CircleViewHolder
                this@CircleView.addView(viewHolder.view)
                mViewHolders.add(viewHolder)
                onBindViewHolder(mViewHolders[index],index)
            }
            mPositionCircleUnit = 2f / mViewHolders.size
            setItemInitialPosition()
        }
    }

    private fun setGestureListener(){
        val mDetector = GestureDetector(mContext, ViewGestureListener())
        setOnTouchListener { v, event ->
            Log.d("setOnTouchListener","setOnTouchListener")
            mDetector.onTouchEvent(event);
        }
    }


    private fun setItemInitialPosition(){
        mViewHolders.forEachIndexed { index, circleViewHolder ->
            animateElem(circleViewHolder.view,index, mViewHolders.size, 0f, mCurrentPosition )
        }
    }

    private fun animateElem(v: View, index:Int, size:Int, animatedValue: Float, currentPosition: Int){
        v.translationX =  (mCenterViewHorizontal * cos((((index+size-currentPosition) * mPositionCircleUnit)+animatedValue)*Math.PI)).toFloat() - (v.width/2)
        v.translationY =  (mCenterViewHorizontal * sin((((index+size-currentPosition) * mPositionCircleUnit)+animatedValue)*Math.PI)).toFloat() -(v.height/2)  + (mCenterViewVertical)
        val intPosition = (size - (((animatedValue * 1000)/(mPositionCircleUnit * 1000).toInt() % size ).toInt()) + currentPosition) %size
        val translationZ = max((size - abs(intPosition-index) ).toFloat(), abs(intPosition-index).toFloat() )
        v.translationZ =  translationZ
    }


    inner class ViewGestureListener: GestureDetector.SimpleOnGestureListener() {

        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent?,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            Log.d("onFling","$velocityX , $velocityY")
            val tempCurrentPosition = mCurrentPosition
            val endAnimation = (velocityY  / (2 * Math.PI * (mCenterViewHorizontal / 4)).toFloat())
            val exactAnim = endAnimation - (endAnimation%mPositionCircleUnit)
            Log.d("exactAnim","$ $exactAnim $mPositionCircleUnit ${exactAnim*360} $endAnimation")
            mCurrentPosition = abs( mViewHolders.size + tempCurrentPosition - ((exactAnim / mPositionCircleUnit).toInt() % mViewHolders.size))  %mViewHolders.size
            Log.d("mov","$mCurrentPosition")
            ValueAnimator.ofFloat(0f,exactAnim)
                .apply {
                    Log.d("valueAnimator", "${this.animatedValue}")
                    duration = 5000
                    interpolator = PathInterpolator(0f, 0.8f, 0.2f, 1.0f)
                    addUpdateListener {
                        mViewHolders.forEachIndexed { index, viewHolder ->
                            animateElem(
                                viewHolder.view,
                                index,
                                mViewHolders.size,
                                this.animatedValue as Float,
                                tempCurrentPosition
                            )

                        }
                    }
                    start()
                }
            return super.onFling(e1, e2, velocityX, velocityY)
        }


        override fun onScroll(
            e1: MotionEvent?,
            e2: MotionEvent?,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            Log.d("onScroll","$distanceX , $distanceY")
            return super.onScroll(e1, e2, distanceX, distanceY)
        }

        override fun onDown(e: MotionEvent?): Boolean {
            Log.d("ondown", "ondown")
            return true
        }

    }

}