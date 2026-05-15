package com.example.afterthoughts

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RadialGradient
import android.graphics.Shader
import android.util.AttributeSet
import android.view.View
import kotlin.math.sin

class CandleView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : View(context, attrs) {

    private val paint = Paint()
    private var frame = 0f

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        frame += 0.04f

        val cx = width / 2f
        val cy = height * 0.38f

        val flicker = (sin(frame * 2.3f) * 0.15f + sin(frame * 3.7f) * 0.1f)

        drawGlow(canvas, cx, cy, 200f + flicker * 40f, 0.06f)
        drawGlow(canvas, cx, cy, 130f + flicker * 30f, 0.12f)
        drawGlow(canvas, cx, cy, 70f + flicker * 20f, 0.22f + flicker * 0.05f)

        invalidate()
    }

    private fun drawGlow(canvas: Canvas, cx: Float, cy: Float, radius: Float, alpha: Float) {
        val gradient = RadialGradient(
            cx, cy, radius,
            intArrayOf(
                Color.argb((alpha * 255).toInt(), 255, 210, 80),
                Color.argb(0, 200, 120, 20)
            ),
            floatArrayOf(0f, 1f),
            Shader.TileMode.CLAMP
        )
        paint.shader = gradient
        canvas.drawCircle(cx, cy, radius, paint)
    }
}