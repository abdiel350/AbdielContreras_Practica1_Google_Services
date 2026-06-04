package es.ua.eps.raw_filmoteca.data

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import es.ua.eps.raw_filmoteca.databinding.ItemFilmBinding

//-------------------------------------
class FilmsArrayAdapter(context: Context?, resource: Int, objects: List<Film>?)
    : ArrayAdapter<Film>(context!!, resource, objects!!) {

    //---------------------------------
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val bindings: ItemFilmBinding =
            if (convertView != null) ItemFilmBinding.bind(convertView)
            else ItemFilmBinding.inflate(LayoutInflater.from(context), parent, false)

        // use binding
        getItem(position)?.let { film ->
            film.loadImageTo(bindings.cover)
            bindings.title.text = film.title
            bindings.director.text = film.director
            Log.d("Item", film.title.toString())
        }

        return bindings.root
    }
}
