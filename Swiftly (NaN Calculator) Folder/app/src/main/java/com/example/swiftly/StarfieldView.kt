package com.example.swiftly

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.math.sin
import kotlin.random.Random

class StarfieldView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private val paint = Paint().apply { isAntiAlias = true }
    private val stars = mutableListOf<Star>()
    private var frame = 0f

    data class Star(
        val x: Float,
        val y: Float,
        val radius: Float,
        val speed: Float,
        val offset: Float
    )

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        stars.clear()
        repeat(120) {
            stars.add(
                Star(
                    x = Random.nextFloat() * w,
                    y = Random.nextFloat() * h,
                    radius = Random.nextFloat() * 2.5f + 0.5f,
                    speed = Random.nextFloat() * 0.05f + 0.02f,
                    offset = Random.nextFloat() * Math.PI.toFloat() * 2f
                )
            )
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        frame += 0.05f
        stars.forEach { star ->
            val alpha = ((sin((frame * star.speed + star.offset).toDouble()) + 1) / 2 * 200 + 55).toInt()
            paint.color = Color.argb(alpha, 192, 132, 252)
            canvas.drawCircle(star.x, star.y, star.radius, paint)
        }
        invalidate()
    }
}