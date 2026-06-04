package es.ua.eps.raw_filmoteca

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Toast

import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

import es.ua.eps.raw_filmoteca.data.FilmDataSource
import es.ua.eps.raw_filmoteca.data.FilmsArrayAdapter
import es.ua.eps.raw_filmoteca.databinding.ActivityFilmListBinding
import com.google.firebase.messaging.FirebaseMessaging
import android.util.Log

class FilmListActivity :
    BaseActivity(),
    AdapterView.OnItemClickListener {

    private lateinit var bindings: ActivityFilmListBinding

    private lateinit var filmAdapter: FilmsArrayAdapter

    private lateinit var mGoogleSignInClient: GoogleSignInClient

    //-----------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        com.google.firebase.FirebaseApp.initializeApp(this)

        // Google Sign In
        val gso =
            GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN
            )
                .requestEmail()
                .build()

        mGoogleSignInClient =
            GoogleSignIn.getClient(this, gso)

        initUI()

        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val token = task.result
                Log.d("FCM_TOKEN", "Mi token es: $token")

            }
        }
        checkPermission(
            Manifest.permission.INTERNET,
            { _ ->
                filmAdapter.notifyDataSetChanged()
            }
        )
    }


    //-----------------------------------------
    override fun onResume() {
        super.onResume()
        filmAdapter.notifyDataSetChanged()
    }

    //-----------------------------------------
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(
            R.menu.main_menu,
            menu
        )

        return true
    }

    //-----------------------------------------
    override fun onOptionsItemSelected(
        item: MenuItem
    ): Boolean {

        when (item.itemId) {

            // Añadir película
            R.id.menu_add -> {

                val intent =
                    Intent(
                        this,
                        FilmDataActivity::class.java
                    )

                startActivity(intent)

                return true
            }

            // Cerrar sesión
            R.id.menu_close_session -> {

                signOut()

                return true
            }

            // Desconectar aplicación
            R.id.menu_disconnect -> {

                revokeAccess()

                return true
            }

            // Acerca de
            R.id.menu_about -> {

                val intent =
                    Intent(
                        this,
                        AboutActivity::class.java
                    )

                startActivity(intent)

                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    //-----------------------------------------
    private fun signOut() {

        mGoogleSignInClient
            .signOut()
            .addOnCompleteListener(this) {

                Toast.makeText(
                    this,
                    "Sesión cerrada",
                    Toast.LENGTH_SHORT
                ).show()

                irAlLogin()
            }
    }

    //-----------------------------------------
    private fun revokeAccess() {

        mGoogleSignInClient
            .revokeAccess()
            .addOnCompleteListener(this) {

                Toast.makeText(
                    this,
                    "Acceso revocado",
                    Toast.LENGTH_SHORT
                ).show()

                irAlLogin()
            }
    }

    //-----------------------------------------
    private fun irAlLogin() {

        val intent =
            Intent(
                this,
                LoginActivity::class.java
            )

        startActivity(intent)

        finish()
    }

    //-----------------------------------------
    private fun initUI() {

        bindings =
            ActivityFilmListBinding.inflate(
                layoutInflater
            )

        setContentView(bindings.root)

        filmAdapter =
            FilmsArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                FilmDataSource.films
            )

        bindings.list.onItemClickListener = this

        bindings.list.adapter = filmAdapter
    }

    //-----------------------------------------
    override fun onItemClick(
        adapterView: AdapterView<*>?,
        view: View?,
        index: Int,
        l: Long
    ) {

        val intent =
            Intent(
                this,
                FilmDataActivity::class.java
            )

        intent.flags =
            Intent.FLAG_ACTIVITY_REORDER_TO_FRONT

        intent.putExtra(
            FilmDataActivity.EXTRA_FILM_ID,
            index
        )

        startActivity(intent)
    }
}