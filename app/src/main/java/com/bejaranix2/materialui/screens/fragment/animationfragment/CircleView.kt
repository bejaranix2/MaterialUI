package com.bejaranix2.materialui.screens.fragment.animationfragment

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.Configuration
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.PathInterpolator
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.animation.doOnEnd
import com.bejaranix2.materialui.R
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.sin

class CircleView(context: Context, attrs: AttributeSet): ConstraintLayout(context,  attrs) {

    private val mSwipeStyle: Int
    private var mViewHolders: ArrayList<CircleAdapter.CircleViewHolder> = arrayListOf()
    private var mCenterViewVertical = 0
    private var mCenterViewHorizontal = 0
    private var mPositionCircleUnit = 0f
    var mContext: Context? = null
    var mInteractionMode = InteractionMode.SPIN
    private var viewClicked: CircleAdapter.CircleViewHolder? = null
    private var initialViewClickedPosition: Point3D? = null
    var circleListener:CircleListener? = null
    private var currentSpinPosition = -1

    var mAdapter: CircleAdapter<in CircleAdapter.CircleViewHolder>? = null
        set(value) {
            field = value
            configView()
        }

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CircleView,
            0, 0
        ).apply {
            try {
                mSwipeStyle = getInteger(R.styleable.CircleView_swipeStyle, 0)
                Log.d("mSwipeStyle", "$mSwipeStyle")
            } finally {
                recycle()
            }
        }
    }

    private fun configView() {
        mAdapter?.apply {
            circleView = this@CircleView}
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

    private fun populate() {
        bringToFront()
        invalidate()
        setGestureListener()
        mAdapter?.apply {
            val numElem = getItemCount()
            for (index in 0 until numElem) {
                createElement(index, this)
            }
            mPositionCircleUnit = 2f / mViewHolders.size
            currentSpinPosition = 0
        }
        setItemInitialPosition()
    }

    private fun createElement(index: Int, adapter: CircleAdapter<in CircleAdapter.CircleViewHolder>){
        val viewType = adapter.getItemViewType(index)
        val viewHolder = adapter.onCreateViewHolder(
            this,
            viewType
        ) as CircleAdapter.CircleViewHolder
        this@CircleView.addView(viewHolder.view)
        mViewHolders.add(viewHolder)
        adapter.onBindViewHolder(mViewHolders[index], index)
        val gestureListener = ChildGestureListener(viewHolder)
        var detector = GestureDetector(mContext, gestureListener)
        gestureListener.detector = detector
        viewHolder.view.setOnTouchListener(gestureListener)
    }

    private fun setGestureListener() {
        val gestureListener = ViewGestureListener()
        var detector = GestureDetector(mContext, gestureListener)
        gestureListener.detector = detector
        setOnTouchListener(gestureListener)
    }

    private fun setItemInitialPosition(){
        val duration=200
        ValueAnimator.ofFloat(0f, duration.toFloat()).apply {
            this.duration = duration.toLong()
            start()
        }.doOnEnd {
            mViewHolders.forEachIndexed { index, circleViewHolder ->
                animateElem(
                    circleViewHolder.view,
                    index,
                    mViewHolders.size,
                    0f,
                    currentSpinPosition,
                    currentSpinPosition
                )
            }
        }
        triggerCurrentIndex()
    }

    private fun animatedAddElement(){
        Log.d("animatedAddElement","animatedAddElement")
        val newPositionCircleUnit = 2f / (mViewHolders.size+1)
        val duration: Int = 500
        mInteractionMode = InteractionMode.LOCK
        ValueAnimator.ofFloat(0f, duration.toFloat()).apply {
            this.duration = duration.toLong()
            interpolator = PathInterpolator(0f, 0.8f, 0.2f, 1.0f)
            addUpdateListener {
                iterateAddElement(duration, it.animatedValue as Float, newPositionCircleUnit)
            }
            start()
        }.doOnEnd {
            mPositionCircleUnit = newPositionCircleUnit
            addNewElementUI()
        }
    }

    private fun iterateAddElement(duration:Int, animatedValue: Float, newPositionCircleUnit: Float){
        mViewHolders.forEachIndexed { index, circleViewHolder ->
            val initialRadian = (index - currentSpinPosition) * mPositionCircleUnit
            val finalRadian = ((index - currentSpinPosition) * newPositionCircleUnit)
            animateFixDelete(
                circleViewHolder.view,
                initialRadian,
                finalRadian,
                animatedValue,
                duration.toFloat()
            )
        }
    }

    private fun addNewElementUI(){
        mAdapter?.apply {
            Log.d("addNewElementUI", "addNewElementUI")
            val adapter = this
            val duration=200
            createElement(mViewHolders.size, adapter)
            val index = mViewHolders.lastIndex
            val circleViewHolder = mViewHolders[index]
            circleViewHolder.view.alpha = 0f
            ValueAnimator.ofFloat(0f, duration.toFloat()).apply {
                this.duration = duration.toLong()
                addUpdateListener {
                    animateElem(
                        circleViewHolder.view,
                        index,
                        mViewHolders.size,
                        0f,
                        currentSpinPosition,
                        currentSpinPosition
                    )}
                start()

            }.doOnEnd {
                circleViewHolder.view.alpha = 1f
                mInteractionMode = InteractionMode.SPIN
            }
        }
    }

    private fun animateFixPosition(newIndex:Int){
        if(newIndex < mViewHolders.size) {
            val duration: Int = 1000
            mInteractionMode = InteractionMode.LOCK
            ValueAnimator.ofFloat(0f, duration.toFloat()).apply {
                this.duration = duration.toLong()
                interpolator = PathInterpolator(0f, 0.8f, 0.2f, 1.0f)
                addUpdateListener {
                    iterateFixPosition(duration, animatedValue as Float, newIndex)
                }
                start()
            }.doOnEnd {
                mInteractionMode = InteractionMode.SPIN
                currentSpinPosition = newIndex
                triggerCurrentIndex()
                triggerStopSpin()
            }
        }
    }

    private fun iterateFixPosition(duration:Int, animatedValue: Float, newIndex:Int){
        mViewHolders.forEachIndexed { index, circleViewHolder ->
            val initialRadian = (index - currentSpinPosition) * mPositionCircleUnit
            val finalRadian = ((index - newIndex) * mPositionCircleUnit)
            Log.d(
                "iterateFixPosition",
                "$index $newIndex $currentSpinPosition $initialRadian, $finalRadian $mPositionCircleUnit"
            )
            animateFixDelete(
                circleViewHolder.view,
                initialRadian,
                finalRadian,
                animatedValue,
                duration.toFloat()
            )
        }

    }

    private fun iterateFixAfterDelete(duration:Float, elementDeleted: Int){
        val lastPositionCircleUnit = 2f / (mViewHolders.size +1)

        mViewHolders.forEachIndexed { index, circleViewHolder ->
            var tempInitialRadian = when{
                (currentSpinPosition == elementDeleted)->{ // se eliminó el de en medio
                    when{
                        index < currentSpinPosition -> lastPositionCircleUnit * ((index - currentSpinPosition))
                        else -> lastPositionCircleUnit * (index + 1 - currentSpinPosition)
                    }
                }
                (elementDeleted > currentSpinPosition)->{
                    when {
                        (index >= elementDeleted) -> lastPositionCircleUnit * ((index+1 - currentSpinPosition))
                        else -> lastPositionCircleUnit * ((index - currentSpinPosition))
                    }
                }
                else ->{
                    when {
                        (index in 0 until elementDeleted) -> { lastPositionCircleUnit * ((index  - currentSpinPosition))}
                        (index in elementDeleted until currentSpinPosition) -> { lastPositionCircleUnit * ((index +1 - currentSpinPosition))}
                        else -> lastPositionCircleUnit * ((index + 1 - currentSpinPosition))
                    }
                }

            }
            var tempFinalRadian =  mPositionCircleUnit * ((index - currentSpinPosition))
            if(elementDeleted < currentSpinPosition){
                tempFinalRadian =  mPositionCircleUnit * ((index +1 - currentSpinPosition))
            }
            ValueAnimator.ofFloat(0f,duration).apply {
                this.duration = duration.toLong()
                addUpdateListener {
                    animateFixDelete(circleViewHolder.view,tempInitialRadian ,tempFinalRadian, it.animatedValue as Float, duration)
                }
                start()
            }.doOnEnd {
                mInteractionMode = InteractionMode.SPIN
                triggerCurrentIndex()
                triggerStopSpin()
            }
        }
    }

    private fun animateFixDelete(v: View, initialRadian:Float, finalRadian:Float, animatedValue: Float, finalAnimatedValue:Float){
        val radianUnit = (finalRadian - initialRadian) / finalAnimatedValue
        val currentRadian = (radianUnit * animatedValue)+initialRadian
        v.translationX =  (mCenterViewHorizontal * cos(currentRadian*Math.PI)).toFloat() - (v.width/2)
        v.translationY =  (mCenterViewHorizontal * sin(currentRadian*Math.PI)).toFloat() -(v.height/2)  + (mCenterViewVertical)
    }


    private fun animateElem(v: View, index:Int, size:Int, animatedValue: Float, currentPosition: Int, intPosition:Int){
        v.translationX =  (mCenterViewHorizontal * cos((((index+size-currentPosition) * mPositionCircleUnit)+animatedValue)*Math.PI)).toFloat() - (v.width/2)
        v.translationY =  (mCenterViewHorizontal * sin((((index+size-currentPosition) * mPositionCircleUnit)+animatedValue)*Math.PI)).toFloat() -(v.height/2)  + (mCenterViewVertical)
        val translationZ = max((size - abs(intPosition-index) ).toFloat(), abs(intPosition-index).toFloat() )
        v.translationZ =  translationZ
    }

    enum class InteractionMode{ SPIN, DELETE_DRAG, DELETE, LOCK }


    inner class ChildGestureListener(private val viewHolder:CircleAdapter.CircleViewHolder): GestureDetector.SimpleOnGestureListener(),OnTouchListener {
        var detector: GestureDetector? = null

        override fun onLongPress(e: MotionEvent?) {
            super.onLongPress(e)
            viewClicked = viewHolder
            viewClicked?.apply {
                initialViewClickedPosition = Point3D(view.x, view.y, view.z)
            }
            mInteractionMode = InteractionMode.DELETE_DRAG
            animateViewLongPressed()
            isEnabled = false
        }

        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent?,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            when(mInteractionMode) {
                InteractionMode.SPIN -> animateSpinFling(velocityX, velocityY)
            }

            return super.onFling(e1, e2, velocityX, velocityY)
        }

        override fun onTouch(v: View?, event: MotionEvent?): Boolean {
            event?.apply {
                detector?.onTouchEvent(event)
                when(this.action){
                    MotionEvent.ACTION_UP -> {
                        if(mInteractionMode == InteractionMode.DELETE_DRAG) {
                            Log.d("ACTION_UP", "${event.x} ${event.y}")
                            animateViewLongReleased(500)
                        }
                    }
                    MotionEvent.ACTION_MOVE -> {
                        Log.d("ACTION_MOVE", "${event.x} ${event.y}")
                        if(mInteractionMode == InteractionMode.DELETE_DRAG) {
                            viewClicked?.apply {
                                if(view.alpha < 0.5f){
                                    mInteractionMode = InteractionMode.DELETE
                                    deleteElemAnimation()
                                }else{
                                    animateDeleteDrag(event)
                                }
                            }
                        }
                    }

                }
            }
            return true
        }
    }

    private fun searchElement(viewHolder: CircleAdapter.CircleViewHolder):Int?{
        var currPosition:Int = -1
        mViewHolders.forEachIndexed { index, circleViewHolder ->
            if(viewHolder == circleViewHolder)
                currPosition = index
        }
        return currPosition
    }
    private fun triggerDeleteListener(){
        circleListener?.let {cListener ->
            viewClicked?.let {  vClicked ->
            searchElement(vClicked)?.let {ind->
                if(cListener.deletedElementListener(ind,  DeleteAction())){
                    removeFromScreenElem()
                }
            }
            } }?:removeFromScreenElem()
    }

    private fun triggerCurrentIndex(){
        circleListener?.let {
            it.currentSpinningPositionListener(currentSpinPosition)
        }
    }

    private fun triggerStopSpin(){
        circleListener?.let {
            it.stoppedSpinListener(currentSpinPosition)
        }

    }

    private fun deleteElemAnimation() {
        initialViewClickedPosition?.let {clickedPos ->
            viewClicked?.let {view ->
            Log.d("deleteElemAnimation","deleteElemEnimation")
            val distanceX = view.view.x - clickedPos.x
            val distanceY = view.view.y - clickedPos.y
            val biggerDistance = abs(max(distanceX, distanceY))
            val vector = Pair(distanceX/biggerDistance,distanceY/biggerDistance)
            val animator = ValueAnimator.ofFloat(0f, 1000f)
                .apply {
                    duration = 1000
                    addUpdateListener {
                        view.view.x += vector.first * it.animatedValue as Float
                        view.view.y += vector.second* it.animatedValue as Float
                        if((view.view.x  > width + view.view.width || view.view.x < 0 - view.view.width)
                            &&(view.view.y  > height + view.view.height || view.view.y < 0 - view.view.height)){
                            cancel()
                        }
                    }
                    start()
                }
            animator.doOnEnd {
                triggerDeleteListener()
            }

        } }

    }



    private fun removeFromScreenElem(){
        val elemDeleted = mViewHolders.indexOf(viewClicked)
        mViewHolders.remove(viewClicked)
        this.removeView(viewClicked?.view)
        mPositionCircleUnit = 2f / mViewHolders.size
        viewClicked = null
        initialViewClickedPosition = null
        isEnabled = true
        iterateFixAfterDelete(1000f, elemDeleted)
        currentSpinPosition = if( currentSpinPosition > elemDeleted) (currentSpinPosition - 1) else  currentSpinPosition%mViewHolders.size
    }

    inner class ViewGestureListener: GestureDetector.SimpleOnGestureListener(),OnTouchListener{

        var detector:GestureDetector? = null

        override fun onLongPress(e: MotionEvent?) {
            super.onLongPress(e)
        }

        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent?,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            if(mInteractionMode ==InteractionMode.SPIN)
                animateSpinFling(velocityX, velocityY)
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

        override fun onSingleTapUp(e: MotionEvent?): Boolean {
            Log.d("onsingletapup", "onsingletapup")
            return super.onSingleTapUp(e)
        }

        override fun onTouch(v: View?, event: MotionEvent?): Boolean {
            event?.apply {
                detector?.onTouchEvent(event)
                when (event.action) {
                    MotionEvent.ACTION_UP -> {
                        Log.d("ACTION_UP", "${event.x} ${event.y}")
                        mInteractionMode = InteractionMode.SPIN
                        viewClicked = null
                    }
                }
            }
            return true
        }

    }


    fun animateViewLongPressed(){
        viewClicked?.apply {
            view.scaleX *=1.2F
            view.scaleY *=1.2F
        }
    }

    fun animateViewLongReleased(intervals: Int){
        Log.d("animateViewLongReleased", "pre animateViewLongReleased")

        viewClicked?.apply {
            initialViewClickedPosition?.let {point3D ->
                Log.d("animateViewLongReleased", "animateViewLongReleased")
                mInteractionMode = InteractionMode.LOCK
                val tempX = view.x
                val tempY = view.y
                val differenceX = tempX-point3D.x
                val differenceY = tempY-point3D.y
                view.alpha = 1f
                view.translationZ = point3D.z
                view.scaleX /= 1.2F
                view.scaleY /= 1.2F

                val animator = ValueAnimator.ofFloat(0f, intervals.toFloat())
                    .apply {
                        duration = intervals.toLong()
                        addUpdateListener {
                            view.x = tempX - ((differenceX / intervals)* it.animatedValue as Float)
                            view.y = tempY - ((differenceY / intervals)* it.animatedValue as Float)
                        }
                        start()
                    }
                animator.doOnEnd {
                    Log.d("scale","scale")
                    initialViewClickedPosition = null
                    mInteractionMode = InteractionMode.SPIN
                    viewClicked = null
                }
            }
        }
    }

    fun animateDeleteDrag(event: MotionEvent){
        viewClicked?.apply {
            Log.d("animateDeleteDrag","${event.x} ${event.y}")
            if(initialViewClickedPosition == null){
                initialViewClickedPosition = Point3D(view.x,view.y, view.z)
            }
            view.x += (event.x)- (view.width/2)
            view.y += (event.y) - (view.height/2)
            initialViewClickedPosition?.apply {
                val xDistance = width - this.x
                val yDistance = height - this.y
                val alphaHorizontal =
                    ((xDistance - abs(view.x - this.x)) / xDistance)
                val alphaVertical =
                    ((yDistance - abs(view.y - this.y)) / yDistance)
                view.alpha = if(alphaHorizontal < 0 || alphaVertical < 0) 0f else alphaHorizontal * alphaVertical

            }
            view.translationZ = (mViewHolders.size * 2).toFloat()
        }
    }

    fun animateSpinFling(velocityX: Float, velocityY: Float){
        val tempCurrentPosition = currentSpinPosition//mCurrentPosition
        val endAnimation = (velocityY  / (2 * Math.PI * (mCenterViewHorizontal / 4)).toFloat())
        val (exactAnim, duration) = when(mSwipeStyle){
            0->{Pair(endAnimation - (endAnimation%mPositionCircleUnit),5000L )}
            else->{Pair(if(endAnimation > 0) mPositionCircleUnit else -mPositionCircleUnit, 1000L)}
        }
        val animator = ValueAnimator.ofFloat(0f,exactAnim)
            .apply {
                this.duration = duration
                interpolator = PathInterpolator(0f, 0.8f, 0.2f, 1.0f)
                addUpdateListener {
                    val animValue = this.animatedValue as Float
                    val intPosition = (mViewHolders.size - (((animValue * 1000)/(mPositionCircleUnit * 1000).toInt() % mViewHolders.size ).toInt()) + tempCurrentPosition) %mViewHolders.size
                    if(currentSpinPosition != intPosition){
                        currentSpinPosition = intPosition
                        triggerCurrentIndex()
                    }
                    mViewHolders.forEachIndexed { index, viewHolder ->

                        animateElem(
                            viewHolder.view,
                            index,
                            mViewHolders.size,
                            animValue,
                            tempCurrentPosition,
                            intPosition
                        )
                    }
                }
                start()
            }
        animator.doOnEnd {
            triggerStopSpin()
        }
    }

    inner class DeleteAction{

        private var locked = false
        fun delete(){
            synchronized(this) {
                if(!locked) {
                    locked = true
                    removeFromScreenElem()
                }
            }
        }

        fun undo(){
            synchronized(this) {
                if(!locked) {
                    locked = true
                    animateViewLongReleased(1000)
                }
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        mCenterViewHorizontal = width / 2
        mCenterViewVertical = height / 2
    }



    fun fixPosition(index: Int) = animateFixPosition(index)

    fun addElement(){
        if(mInteractionMode == InteractionMode.SPIN) {
            animatedAddElement()
        }
    }

}

data class Point3D(val x:Float, val y:Float, val z:Float)
