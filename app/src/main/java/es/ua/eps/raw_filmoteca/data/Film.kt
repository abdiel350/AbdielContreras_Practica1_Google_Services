package es.ua.eps.raw_filmoteca.data

import android.graphics.Bitmap
import android.widget.ImageView
import es.ua.eps.raw_filmoteca.Filmoteca
import es.ua.eps.raw_filmoteca.R
import es.ua.eps.raw_filmoteca.tools.OnTaskCompleted
import es.ua.eps.raw_filmoteca.tools.TaskRunner

//-------------------------------------
class Film {
    var imageResId = 0
    var title: String? = null
    var director: String? = null
    var year = 0
    var genre : Genre = Genre.Action
    var format : Format = Format.DVD
    var imdbUrl: String? = null
    var comments: String? = null
    var imageUrl: String? = null
    var image: Bitmap? = null
    var loading = false

    //---------------------------------
    fun clone(): Film {
        val film = Film()

        film.imageResId = imageResId
        film.title = title
        film.director = director
        film.year = year
        film.genre = genre
        film.format = format
        film.imdbUrl = imdbUrl
        film.comments = comments
        film.imageUrl = imageUrl
        film.image = image
        //film.loading = loading

        return film
    }

    //---------------------------------
    override fun toString(): String {
        return title ?: Filmoteca.context?.getString(R.string.no_title) ?: ""
    }

    //---------------------------------
    enum class Format(val value: Int) {
        DVD(0),
        BlueRay(1),
        Digital(2);

        companion object {
            private val map = Format.values().associateBy(Format::value)

            fun fromValue(type: Int) = map[type]
        }
    }

    //---------------------------------
    enum class Genre(val value: Int) {
        Action(0),
        Drama(1),
        Comedy(2),
        Terror(3),
        SciFi(4),
        Fantasy(5);

        companion object {
            private val map = Genre.values().associateBy(Genre::value)

            fun fromValue(type: Int) = map[type]
        }
    }

    //---------------------------------
    fun loadImageTo(dstImageView: ImageView) {
        when {
            image != null -> {
                dstImageView.setImageBitmap(image)
            }

            imageUrl != null -> {
                dstImageView.setImageResource(R.drawable.unavailable)
                if(loading == false) {
                    loading = true
                    TaskRunner.doInBackground(dstImageView, imageUrl, object : OnTaskCompleted {
                        override fun onTaskCompleted(result: Bitmap?) {
                            if (result != null) {
                                image = result
                            }
                            loading = false
                        }
                    })
                }
            }

            imageResId != 0 -> {
                dstImageView.setImageResource(imageResId)
            }

            else -> {}
        }
    }
}