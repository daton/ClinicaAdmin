package clinicamovil.unitec.org.clinicaadmin

import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class LoginActivity : AppCompatActivity() {

    var academico=Academico()

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
     inner class TareaAutenticar:AsyncTask<Void,Void,Void>(){
         override fun onPostExecute(result: Void?) {
             super.onPostExecute(result)
         }

         override fun doInBackground(vararg params: Void?): Void {
             TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
         }

         override fun onPreExecute() {
             super.onPreExecute()
         }
     }
}
