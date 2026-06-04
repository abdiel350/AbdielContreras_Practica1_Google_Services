package es.ua.eps.raw_filmoteca.tools

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ImageView
import java.io.InputStream
import java.net.URL
import java.util.concurrent.Executor
import java.util.concurrent.Executors

//-------------------------------------
interface OnTaskCompleted {
    fun onTaskCompleted(result: Bitmap?)
}

//-------------------------------------
object TaskRunner {
    private val executor: Executor = Executors.newSingleThreadExecutor() // change according to your requirements
    private val handler: Handler = Handler(Looper.getMainLooper())

    //---------------------------------
    fun doInBackground(dstImage: ImageView?, url: String?, listener: OnTaskCompleted? = null) {
        executor.execute {
            var image: Bitmap?

            if(url == null)
                return@execute

            try {
                val input: InputStream = URL(url).openStream()
                image = BitmapFactory.decodeStream(input)
            }
            catch (e: Exception) {
                Log.e("DownloadImageTask", e.message.toString())
                e.printStackTrace()
                return@execute
            }

            handler.post {
                if(image != null) {
                    dstImage?.setImageBitmap(image)
                }
                listener?.onTaskCompleted(image)
            }
        }
    }
}