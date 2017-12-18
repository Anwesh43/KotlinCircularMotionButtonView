package ui.anwesome.com.circularmotionbuttonview

/**
 * Created by anweshmishra on 18/12/17.
 */
import android.graphics.*
import android.view.*
import android.content.*
import java.util.concurrent.ConcurrentLinkedQueue

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
            canvas.drawCircle(x,y,r,paint)
        }
        fun contains():Boolean = false
        fun handleTap(x:Float,y:Float):Boolean = x>=this.x-r && x<=this.x+r && y>=this.y-r && y<=this.y+r
    }
    data class CircularMotionButton(var i:Int = 0,var x:Float,var y:Float,var r:Float) {
        val state = CircularMotionButtonState()
        fun draw(canvas:Canvas,paint:Paint) {
            paint.style = Paint.Style.FILL
            paint.color = Color.parseColor("#7B1FA2")
            canvas.drawCircle(x,y,r,paint)
        }
        fun update(stopcb:(Float,Int)->Unit) {
            state.update(this,stopcb)
        }
        fun startUpdating(holder:CircularMotionButtonHolder,startcb:()->Unit) {
            state.startUpdating(holder,this,startcb)
        }
    }
    data class CircularMotionButtonState(var deg:Float = 0f,var r:Float = 0f,var destJ:Int = 0,var scale:Float = 0f,var dir:Int = 0,var oDeg:Float  =0f) {
        fun update(button:CircularMotionButton,stopcb:(Float,Int)->Unit) {
            scale += 0.1f*dir
            deg = oDeg + 180f*scale
            if(scale>1) {
                scale = 1f
                dir = 0
                stopcb(scale,1)
                deg = oDeg+180f
            }

            button.x = r*Math.cos(deg*Math.PI/180).toFloat()
            button.y = r*Math.sin(deg*Math.PI/180).toFloat()
        }
        fun startUpdating(holder:CircularMotionButtonHolder,button:CircularMotionButton,startcb:()->Unit) {
            var r = Math.abs(button.x-holder.x)/2
            if(holder.x > button.x) {
                deg = 180f
            }
            dir = 1
            startcb()
        }
    }
    data class CircularMotionButtonContainer(var w:Float,var h:Float,var n:Int = 3) {
        var holders:ConcurrentLinkedQueue<CircularMotionButtonHolder> = ConcurrentLinkedQueue()
        var button:CircularMotionButton = CircularMotionButton(x=w/2,y=h/2,r=Math.min(w,h)/3)
        init {
            var gap = w/(2*n+1)
            var x = 3*gap/2
            button.x = x
            button.r = gap/2
            for(i in 0..n-1) {
                holders.add(CircularMotionButtonHolder(i,x,h/2,gap/2))
                x+=2*gap
            }
        }
        fun draw(canvas:Canvas,paint:Paint) {
            holders.forEach {
                it.draw(canvas,paint)
            }
        }
        fun update(stopcb:(Float,Int)->Unit){
            button.update(stopcb)
        }
        fun handleTap(x:Float,y:Float,startcb:()->Unit) {
            holders.forEach {
                if(it.handleTap(x,y)) {
                    button.startUpdating(it,startcb)
                    return
                }
            }
        }
    }
    data class CircularMotionButtonAnimator(var container:CircularMotionButtonContainer,var view:CircularMotionButtonView) {
        var animated = false
        fun draw(canvas:Canvas,paint:Paint) {
            canvas.drawColor(Color.parseColor("#212121"))
            container.draw(canvas,paint)
        }
        fun update() {
            if(animated) {
                container.update({scale,j ->
                    animated = false
                })
                try {
                    Thread.sleep(50)
                    view.invalidate()
                }
                catch(ex:Exception) {

                }
            }
        }
        fun handleTap(x:Float,y:Float) {
            if(!animated) {
                container.handleTap(x,y,{
                    animated = true
                    view.postInvalidate()
                })
            }
        }
    }
}