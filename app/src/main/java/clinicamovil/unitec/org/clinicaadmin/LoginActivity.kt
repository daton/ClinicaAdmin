package clinicamovil.unitec.org.clinicaadmin

import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.client.RestTemplate

class LoginActivity : AppCompatActivity() {

    var academico: Academico? = null
    var estatus = Estatus()
    var nip: Int? = 0
    var mensajeError:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        getSupportActionBar()?.hide();

        findViewById<Button>(R.id.botonRegistrarse).setOnClickListener {
            var i = Intent(application, MainActivity::class.java);
            startActivity(i);
        }


        findViewById<Button>(R.id.botonIngresar).setOnClickListener {


            TareaAutenticar().execute(null, null, null)
        }
    }

    inner class TareaAutenticar : AsyncTask<Void, Void, Void>() {
        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            nip = -1
            if (academico == null) {

                var txtError = findViewById<TextView>(R.id.txtErrorNip)
                txtError.visibility = View.VISIBLE
                txtError.text = "NIP incorrecto"
            } else {
                var txtError = findViewById<TextView>(R.id.txtErrorNip)
                txtError.visibility = View.INVISIBLE
                Toast.makeText(applicationContext, "El rol es " + academico?.rol, Toast.LENGTH_SHORT).show()

                var i = Intent(applicationContext, MenuActivity::class.java)

                startActivity(i)
            }
            var progreso = findViewById<ProgressBar>(R.id.progressBar1)
            progreso.visibility = View.GONE

        }

        override fun doInBackground(vararg params: Void?): Void? {
            var url2 = "https://node74674-env-8686050.whelastic.net/api/academico/" + nip
            //  var url2="http://192.168.100.12/api/academico/"+33868

            val restTemplate = RestTemplate()
            restTemplate.messageConverters.add(MappingJackson2HttpMessageConverter())


            val maper = ObjectMapper()
            //  usuarios = maper.readValue(estring, object : TypeReference<ArrayList<Usuario>>() {})

            val respuesta = restTemplate.getForObject(url2, String::class.java)
            if (respuesta != null)
                academico = maper.readValue(respuesta, Academico::class.java)
            else academico = null
            print("El rol es" + academico?.rol)

            println("DESPUES DE REST");
            return null

        }


        override fun onPreExecute() {
            super.onPreExecute()

            var progreso = findViewById<ProgressBar>(R.id.progressBar1)
            progreso.visibility = View.VISIBLE

            try {
                nip = findViewById<EditText>(R.id.textonip).text.toString().toInt();

                Toast.makeText(applicationContext, "UN numero " + nip, Toast.LENGTH_LONG).show()
            } catch (e: NumberFormatException) {

                mensajeError="Introduce tu NIP"

            }
        }
    }
}
