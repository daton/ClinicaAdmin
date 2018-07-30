package clinicamovil.unitec.org.clinicaadmin

import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.client.RestTemplate

class LoginActivity : AppCompatActivity() {

    var academico=Academico()
    var estatus=Estatus()

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
            TareaAutenticar().execute(null,null,null)
        }
    }
     inner class TareaAutenticar:AsyncTask<Void,Void,Void>(){
         override fun onPostExecute(result: Void?) {
             super.onPostExecute(result)
            Toast.makeText(applicationContext,"El rol es "+academico.rol,Toast.LENGTH_LONG).show()
         }

         override fun doInBackground(vararg params: Void?): Void ?{
             var url2="https://node74674-env-8686050.whelastic.net/api/academico/"+33868

             val restTemplate = RestTemplate()
             restTemplate.messageConverters.add(MappingJackson2HttpMessageConverter())


             val maper = ObjectMapper()
             //  usuarios = maper.readValue(estring, object : TypeReference<ArrayList<Usuario>>() {})

             val respuesta = restTemplate.getForObject(url2,  String::class.java)
             academico = maper.readValue(respuesta,Academico::class.java )
             print("El rol es"+academico.rol)

             println("DESPUES DE REST");
             return null

         }


         override fun onPreExecute() {
             super.onPreExecute()
         }
     }
}
