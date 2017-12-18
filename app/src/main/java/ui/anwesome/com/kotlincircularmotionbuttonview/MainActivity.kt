package ui.anwesome.com.kotlincircularmotionbuttonview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ui.anwesome.com.circularmotionbuttonview.CircularMotionButtonView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CircularMotionButtonView.create(this)
    }
}
