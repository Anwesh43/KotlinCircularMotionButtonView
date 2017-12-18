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
    data class CircularMotionButtonHolder(var i:Int,var x:Float,var y:Float,var r:Float) {
        fun draw(canvas:Canvas,paint:Paint) {
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = r/12
            paint.color = Color.parseColor("#1A237E")
            canvas.drawCircle(0f,0f,r,paint)
        }
        fun contains():Boolean = false
        fun handleTap(x:Float,y:Float):Boolean = x>=this.x-r && x<=this.x+r && y>=this.y-r && y<=this.y+r
    }
}