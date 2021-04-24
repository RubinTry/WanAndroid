package cn.rubintry.module_login

import android.graphics.LinearGradient
import android.graphics.Shader
import android.widget.TextView
import java.lang.IllegalArgumentException


/**
 * 设置渐变色
 *
 * @param colors
 * @param positions
 */
fun TextView.setGradientColor(colors: IntArray , positions: FloatArray){
    if(colors.size != positions.size){
        throw IllegalArgumentException("colors's size and position's size must be equal.")
    }
    val mLinearGradient = LinearGradient(0f , 0f , (this.paint.textSize * this.text.length).toFloat() , 0f , colors , positions , Shader.TileMode.CLAMP)
    this.paint.shader = mLinearGradient
    this.invalidate()
}