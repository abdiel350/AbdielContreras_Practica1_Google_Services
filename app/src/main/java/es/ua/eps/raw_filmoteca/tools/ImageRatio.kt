package es.ua.eps.raw_filmoteca.tools

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

//-------------------------------------
class ImageRatio @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) :
    AppCompatImageView(context, attrs, defStyle) {

    var ratio: Float = 1.5f

    //---------------------------------
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        if(ratio <= 0)
            return

        val w:Int
        val h:Int

        when {
            widthMeasureSpec > 0 -> {
                w = widthMeasureSpec
                h = (widthMeasureSpec * ratio).toInt()
            }

            heightMeasureSpec > 0 -> {
                w = (heightMeasureSpec / ratio).toInt()
                h = heightMeasureSpec
            }
            else -> {
                return
            }
        }

        setMeasuredDimension(w, h)
    }

}
