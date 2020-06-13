package com.bejaranix2.materialui.screens.fragment.animationfragment

import android.animation.ValueAnimator
import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.drawable.AnimatedVectorDrawable
import android.util.Log
import android.view.*
import android.view.animation.PathInterpolator
import android.widget.TextView
import android.widget.Toast
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


    constructor(
        toolbarManager:ToolbarViewManager, fragmentAnimationBinding: FragmentAnimationBinding,
        fragment: AnimationFragment, inflater: LayoutInflater){
        mToolbarManager = toolbarManager
        mFragment = fragment
        mFragmentAnimationBinding = fragmentAnimationBinding
        mFragmentAnimationBinding.manager = this
        mInflater = inflater
        configView()
    }

    private fun configView(){
        createCards()
    }

    fun nextFragment(v:View){
        Navigation.findNavController(mFragmentAnimationBinding.root).navigate(AnimationFragmentDirections.actionAnimationFragmentToMotionFragment())
    }


    private fun createCards(){
        val listOfElem = arrayListOf<SingleData>()
        listOfElem.add(SingleData("0", "Content bla bla"))
        listOfElem.add(SingleData("1", "khkjhjkli"))
        listOfElem.add(SingleData("2", "uuu uuuu"))
        listOfElem.add(SingleData("3", "xxx"))
        listOfElem.add(SingleData("4", "kjhj -ghg"))
        listOfElem.add(SingleData("5", "bah bah"))
        listOfElem.add(SingleData("6", ":D"))
        listOfElem.add(SingleData("7", "  -  "))
        listOfElem.add(SingleData("8", "...."))
        listOfElem.add(SingleData("9", "000000000"))
        listOfElem.add(SingleData("10", "LL.**+LK"))
        listOfElem.add(SingleData("11", "LL.**+LK"))
        listOfElem.add(SingleData("12", "LL.**+LK"))
        listOfElem.add(SingleData("13", "LL.**+LK"))
        listOfElem.add(SingleData("14", "LL.**+LK"))

        val adapter = SingleCicleAdapter(listOfElem,mInflater)

        mFragmentAnimationBinding.circleView.mContext = mFragment.context
        mFragmentAnimationBinding.circleView.mAdapter = adapter
        mFragmentAnimationBinding.circleView.circleListener = object : CircleListener{
            override fun deletedElementListener(
                index: Int,
                action: CircleView.DeleteAction
            ): Boolean {
                removeDialog(action)
                return false
            }

            override fun currentSpinningPositionListener(current: Int) {
                mFragmentAnimationBinding.text.text = "$current"
            }

            override fun stoppedSpinListener(current: Int) {
                Toast.makeText(mFragment.context, "${listOfElem[current].title} - ${listOfElem[current].text}", Toast.LENGTH_SHORT).show()
            }

        }

        mFragmentAnimationBinding.fixButton.setOnClickListener {
            Toast.makeText(mFragment.context, "Fix", Toast.LENGTH_SHORT).show()
            mFragmentAnimationBinding.circleView.fixPosition(5)
            false
        }

        mFragmentAnimationBinding.addButton.setOnClickListener {
            Toast.makeText(mFragment.context, "Add", Toast.LENGTH_SHORT).show()
            mFragmentAnimationBinding.circleView?.apply {
                adapter.addElement(SingleData("10", "LL.**+LK"))
            }
            false
        }
    }

    fun removeDialog(action: CircleView.DeleteAction){
        AlertDialog.Builder(mFragment.context)
            .setTitle("do you want to delete the item?")
            .setCancelable(false)
            .setPositiveButton("YES", DialogInterface.OnClickListener { dialog, which ->
                action.delete()
            }).setNegativeButton("NO", DialogInterface.OnClickListener { dialog, which ->
                action.undo()
            })
            .show()
    }

    fun moveObject(v:View){
        Log.d("moveobject", "move")
        focusItem = viewArr.size - (mov % viewArr.size)



         viewArr.forEachIndexed { index, view ->
             animateElem(
                 view,
                 index,
                 viewArr.size ,
                 0f,
                 mov
             )
         }

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