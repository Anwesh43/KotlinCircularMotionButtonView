package ui.anwesome.com.circularmotionbuttonview

/**
 * Created by anweshmishra on 18/12/17.
 */
import android.graphics.*
import android.view.*
import android.content.*
class CircularMotionButtonView(ctx:Context):View(ctx) {
    val paint:Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    override fun onDraw(canvas:Canvas) {

    }
    override fun onTouchEvent(event:MotionEvent):Boolean {
        if(event.action == MotionEvent.ACTION_DOWN) {

        }
        return true
    }
}