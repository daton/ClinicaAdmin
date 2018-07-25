package clinicamovil.unitec.org.clinicaadmin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        getSupportActionBar()?.hide();

         findViewById<Button>(R.id.botonRegistrarse).setOnClickListener {
             var i= Intent(application, MainActivity::class.java);
             startActivity(i);
         }


        findViewById<Button>(R.id.botonIngresar).setOnClickListener {
            var i=Intent(applicationContext, MenuActivity::class.java)
            startActivity(i)
        }
    }
}
