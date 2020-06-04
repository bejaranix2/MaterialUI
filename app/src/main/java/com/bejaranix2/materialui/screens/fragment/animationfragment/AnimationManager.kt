package com.bejaranix2.materialui.screens.fragment.animationfragment

import android.animation.ValueAnimator
import android.graphics.drawable.AnimatedVectorDrawable
import android.util.Log
import android.view.*
import android.view.animation.PathInterpolator
import android.widget.TextView
import androidx.navigation.Navigation
import com.bejaranix2.materialui.R
import com.bejaranix2.materialui.databinding.FragmentAnimationBinding
import com.bejaranix2.materialui.screens.activity.ToolbarViewManager
import com.bejaranix2.materialui.screens.common.viewmanager.BaseViewManager
import kotlin.math.*

class AnimationManager:BaseViewManager {

    private var mToolbarManager: ToolbarViewManager
    private var mFragmentAnimationBinding: FragmentAnimationBinding
    private var mFragment: AnimationFragment
    private lateinit var mAnimation: AnimatedVectorDrawable
    private val  mInflater: LayoutInflater
    private var centerHeight = 0
    private var centerWidth = 0
    private val viewArr = arrayListOf<View>()
    private var factor = 0f
    private var mov = 0
    private var focusItem = 0
    private var centerViewVer = 0f
    private var centerViewHor = 0f


    constructor(
        toolbarManager:ToolbarViewManager, fragmentAnimationBinding: FragmentAnimationBinding,
        fragment: AnimationFragment, inflater: LayoutInflater){
        mToolbarManager = toolbarManager
        mFragment = fragment
        mFragmentAnimationBinding = fragmentAnimationBinding
        mFragmentAnimationBinding.manager = this
        mInflater = inflater
        configView()
//        configAnimation()
    }

    private fun configView(){
        createCards()
/*
        mov = 0
        val mDetector = GestureDetector(mFragment.context, ViewGestureListener())
        mFragmentAnimationBinding.constraintLayout.viewTreeObserver.addOnGlobalLayoutListener(
            object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    mFragmentAnimationBinding.constraintLayout.viewTreeObserver.removeOnGlobalLayoutListener(this)

                    Log.d("configView", "$centerHeight, $centerWidth")
                    createCards()
                }
            })

        mFragmentAnimationBinding.constraintLayout.setOnTouchListener{ _, motionEvent ->
            mDetector.onTouchEvent(motionEvent);
        }*/
    }

    private fun configAnimation(){
/*        mFragmentAnimationBinding.animationImage.apply {
            setBackgroundResource(R.drawable.animation_drawable)
            mAnimation = background as AnimatedVectorDrawable
        }*/
    }

    fun startAnimation(v: View){
        mAnimation.start()
    }

    fun nextFragment(v:View){
        Navigation.findNavController(mFragmentAnimationBinding.root).navigate(AnimationFragmentDirections.actionAnimationFragmentToMotionFragment())
    }


    private fun createCards(){



        val listOfElem = arrayListOf<SingleData>()
        for(x in 0 until  10){
            listOfElem.add(SingleData("$x", "Content bla bla"))
/*            var view = mInflater.inflate(R.layout.card_item, null)
            view.findViewById<TextView>(R.id.text_msg).text = "$x"
            view.setOnClickListener { moveObject(view) }
            Log.d("View click", "viewClick")
            viewArr.add( view)
            mFragmentAnimationBinding.constraintLayout.addView(view)
            if(x == 0){
                centerViewHor = (view.width / 2).toFloat()
                centerViewVer = (view.height / 2).toFloat()
                
            }*/

        }
        mFragmentAnimationBinding.circleView.mContext = mFragment.context
        mFragmentAnimationBinding.circleView.mAdapter = SingleCicleAdapter(listOfElem,mInflater)

        /*
        centerHeight = mFragmentAnimationBinding.constraintLayout.height/2  - centerViewVer   .toInt()
        
        centerWidth = mFragmentAnimationBinding.constraintLayout.width/2 - centerViewHor  .toInt()
        mFragmentAnimationBinding.constraintLayout.bringToFront();
        mFragmentAnimationBinding.constraintLayout.invalidate();
        factor = 2f / viewArr.size
        mov = 0*/
    }

    fun moveObject(v:View){
        Log.d("moveobject", "move")
        focusItem = viewArr.size - (mov % viewArr.size)
        /*ValueAnimator.ofFloat(0f,factor)
            .apply {
            duration = 5000
            interpolator = PathInterpolator(0.25f, 0.1f, 0.25f, 1.0f)
            addUpdateListener {
                viewArr.forEachIndexed { index, view ->
                    animateElem(view, index,it.animatedValue as Float )
                }
            }
            start()
        } */
        /*
        viewArr.forEachIndexed { index, view ->
            mFragmentAnimationBinding.constraintLayout.bringToFront()
            mFragmentAnimationBinding.constraintLayout.invalidate()

            val relativePos = if((index - focusItem) < 0) viewArr.size - (focusItem - index) else (index - focusItem)
            //Log.d("animateElem", "${viewArr.size-relativePos}, $relativePos")
            v.translationZ = max(viewArr.size-relativePos, relativePos).toFloat() * 2
            v.elevation = max(viewArr.size-relativePos, relativePos).toFloat() * 2
            //Log.d("animateElem", "Position ${v.translationX}, ${v.translationY}")
        }                      */



         viewArr.forEachIndexed { index, view ->
             animateElem(
                 view,
                 index,
                 viewArr.size ,
                 0f,
                 mov
             )
         }

        //mov ++
    }

    private fun animateElem(v: View, index:Int,size:Int ,animatedValue: Float,mov: Int){
        v.translationX =  (centerWidth * cos((((index+size-mov) * factor)+animatedValue)*Math.PI)).toFloat() - (v.width/2)
        v.translationY =  (centerWidth * sin((((index+size-mov) * factor)+animatedValue)*Math.PI)).toFloat() -(v.height/2)  + (centerHeight)
        val intPosition = (size - (((animatedValue * 1000)/(factor * 1000).toInt() % size ).toInt()) + mov) %size
        val translationZ = max((size - abs(intPosition-index) ).toFloat(), abs(intPosition-index).toFloat() )
        Log.d("translationz","mov $mov intPosition: $intPosition translationZ $translationZ")
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
            val tempMov = mov
            val endAnimation = (velocityY  / (2 * Math.PI * (centerWidth / 4)).toFloat())
            val exactAnim = endAnimation - (endAnimation%factor)
            Log.d("exactAnim","$ $exactAnim $factor ${exactAnim*360} $endAnimation")
            mov = abs( viewArr.size + mov - ((exactAnim / factor).toInt() % viewArr.size))  %viewArr.size
            Log.d("mov","$mov")                                                            
            ValueAnimator.ofFloat(0f,exactAnim)
                .apply {
                    Log.d("valueAnimator", "${this.animatedValue}")
                    duration = 5000                                             
                    interpolator = PathInterpolator(0f, 0.8f, 0.2f, 1.0f)
                    addUpdateListener {
                        viewArr.forEachIndexed { index, view ->
                            animateElem(
                                view,
                                index,
                                viewArr.size,
                                this.animatedValue as Float,
                                tempMov
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


    }
}