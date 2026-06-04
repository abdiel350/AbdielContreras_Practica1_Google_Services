package es.ua.eps.raw_filmoteca;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

 import com.bumptech.glide.Glide;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //  Habilitar diseño de borde
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_about);

        if (findViewById(R.id.main) != null) {
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }

        //  Barra superior (Toolbar)
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Filmoteca");
        }

        //  Referencias de la interfaz
        ImageView profileImage = findViewById(R.id.profile_image);
        TextView txtId = findViewById(R.id.user_id);
        TextView txtName = findViewById(R.id.user_name);
        TextView txtEmail = findViewById(R.id.user_email);

        Button btnBack = findViewById(R.id.btn_back);
        Button btnWebsite = findViewById(R.id.btn_website);
        Button btnSupport = findViewById(R.id.btn_support);

        //  Cargar datos de la cuenta de Google
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);

        if (acct != null) {
            txtId.setText("Id: " + acct.getId());
            txtName.setText("Name: " + acct.getDisplayName());
            txtEmail.setText("E-Mail: " + acct.getEmail());

            //  Glide para mostrar la foto de perfil real:
            if (acct.getPhotoUrl() != null) {

                android.util.Log.d("FOTO_LOG", "URL: " + acct.getPhotoUrl().toString()); // Log para ver en la consola si la URL existe realmente

                Glide.with(this)
                        .load(acct.getPhotoUrl())
                        .circleCrop()
                        .into(profileImage);
            } else {
                // Log por si la URL viene vacía
                android.util.Log.e("FOTO_LOG", "La URL de la foto viene NULL");
            }

        }

        // Configurar eventos de los botones

        // Botón BACK
        btnBack.setOnClickListener(v -> finish());

        // Botón GO TO THE WEBSITE (Ejemplo pagina uni)
        btnWebsite.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.ua.es"));
            startActivity(browserIntent);
        });

        // Botón GET SUPPORT (Ejemplo de Intent para enviar email)
        btnSupport.setOnClickListener(v -> {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "soporte@filmoteca.com", null));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Soporte Filmoteca");
            startActivity(Intent.createChooser(emailIntent, "Enviar email..."));
        });
    }

    // Método para la barra superior, cierre la actividad
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}