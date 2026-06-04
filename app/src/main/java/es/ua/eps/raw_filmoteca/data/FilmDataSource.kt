package es.ua.eps.raw_filmoteca.data

import es.ua.eps.raw_filmoteca.R

//-------------------------------------
object FilmDataSource {
    val films: MutableList<Film> = mutableListOf<Film>()

    //---------------------------------
    init {
        var f = Film()
        f.title      = "Regreso al futuro"
        f.director   = "Robert Zemeckis"
        f.imageResId = R.drawable.regresoalfuturo
        f.comments   = ""
        f.format     = Film.Format.Digital
        f.genre      = Film.Genre.SciFi
        f.imdbUrl    = "http://www.imdb.com/title/tt0088763"
        f.year       = 1985
        films.add(f)

        f = Film()
        f.title = "Los Cazafantasmas"
        f.director = "Ivan Reitman"
        f.imageResId = R.drawable.loscazafantasmas
        f.comments = ""
        f.format = Film.Format.BlueRay
        f.genre = Film.Genre.Comedy
        f.imdbUrl = "https://www.imdb.com/title/tt0087332"
        f.year = 1984
        films.add(f)

        f = Film()
        f.title = "La princesa prometida"
        f.director = "Rob Reiner"
        //f.imageUrl = "http://www.imdb.com/title/tt0088763"//"https://pics.filmaffinity.com/the_princess_bride-741508250-large.jpg"
        f.imageUrl = "https://es.web.img2.acsta.net/pictures/19/07/03/16/08/2300654.jpg"
        f.comments = ""
        f.format = Film.Format.Digital
        f.genre = Film.Genre.Fantasy
        f.imdbUrl = "https://www.filmaffinity.com/es/film579602.html"
        f.year = 1987
        films.add(f)

        // Añade tantas películas como quieras!
    }

    //---------------------------------
    fun add() {
        films.add(Film())
    }

    //---------------------------------
    fun add(f: Film) {
        films.add(f)
    }
}