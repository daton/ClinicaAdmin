package clinicamovil.unitec.org.clinicaadmin

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.fasterxml.jackson.databind.ObjectMapper

import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.client.RestTemplate
import org.springframework.http.HttpMethod
import org.springframework.http.converter.StringHttpMessageConverter
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType


class MainActivity : AppCompatActivity() {

    var estatus = Estatus()
    public var clave: Clave? = null;

    var token:String?=null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            val channelId = getString(R.string.default_notification_channel_id)
            val channelName = getString(R.string.default_notification_channel_name)
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager!!.createNotificationChannel(NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW))
        }

        // If a notification message is tapped, any data accompanying the notification
        // message is available in the intent extras. In this sample the launcher
        // intent is fired when the notification is tapped, so any accompanying data would
        // be handled here. If you want a different intent fired, set the click_action
        // field of the notification message to the desired intent. The launcher intent
        // is used when no click_action is specified.
        //
        // Handle possible data accompanying notification message.
        // [START handle_data_extras]
        if (intent.extras != null) {
            for (key in intent.extras!!.keySet()) {
                val value = intent.extras!!.get(key)
                Log.d(TAG, "Key: $key Value: $value")
            }
        }
        // [END handle_data_extras]

        val subscribeButton = findViewById<Button>(R.id.subscribeButton)
        subscribeButton.setOnClickListener {
            // [START subscribe_topics]
            FirebaseMessaging.getInstance().subscribeToTopic("news")
            // [END subscribe_topics]

            // Log and toast
            val msg = getString(R.string.msg_subscribed)
            Log.d(TAG, msg)
            Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show()
        }

        val logTokenButton = findViewById<Button>(R.id.logTokenButton)
        logTokenButton.setOnClickListener {
            // Get token
            token = FirebaseInstanceId.getInstance().token

            TareaClimaUsuarioX().execute(null, null, null)

            // Log and toast
            val msg = getString(R.string.msg_token_fmt, token)
            Log.d(TAG, msg)
            Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show()
        }

        val botonMapa=findViewById<Button>(R.id.irAMapa)
        botonMapa.setOnClickListener{
            var intent= Intent(applicationContext, MapsActivity::class.java)
            startActivity(intent)
        }

        //Navegacion del incidencias
        val botonIncidencias=findViewById<Button>(R.id.ingresar)
        botonIncidencias.setOnClickListener{
            var intent2=Intent(applicationContext,IncidenciasActivity::class.java)
            startActivity(intent2)
        }


    }

    companion object {

        private val TAG = "MainActivity"
    }





    inner class TareaClimaUsuarioX : AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg p0: Void?): Void? {



            var url2="http://unitecmx.herokuapp.com/api/mensajeria"
            // var url2="http://192.168.100.7:9000/api/mensajeria"
     hacerPost(url2);
            //  println("DESPUES DE REST:"+usuarios.get(0).email);
            return null
        }

        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            Toast.makeText(applicationContext,estatus.mensaje, Toast.LENGTH_LONG).show();

        }

        fun hacerPost(url: String): String {
clave= Clave();
            clave?.token=token;
            //Headers
            val httpHeaders = HttpHeaders()
            httpHeaders.contentType = MediaType("application", "json")
            //creamos la entidad a enviar
            val mensajeriaHttpEntity = HttpEntity(clave, httpHeaders)
            //Creamoe una instancia de restemplate
            val restTemplate = RestTemplate()
            //Agregamos los convertidores json
            //Agregamos los convertidores de jackson
            restTemplate.messageConverters.add(MappingJackson2HttpMessageConverter())
            restTemplate.messageConverters.add(StringHttpMessageConverter())

            //Hacemos el post y obtenemos nuestra respuesta
            val responseEntity = restTemplate.exchange(url, HttpMethod.POST, mensajeriaHttpEntity, String::class.java)
            return responseEntity.body

        }
    }

}
