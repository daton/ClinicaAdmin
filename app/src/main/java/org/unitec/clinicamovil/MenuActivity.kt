package org.unitec.clinicamovil

import android.annotation.SuppressLint
import android.location.Location
import android.os.AsyncTask
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.app_bar_menu.*
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.client.RestTemplate
import java.util.*


class MenuActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    var academico: Academico? = null
    var practica: Practica?=null
    var estatus = Estatus()
    var nip: Int? = 0
    var mensajeError:String?=null


//Para practica
    var dia:Int?=null
    var diaSemana:Int?=null
    var diano:Int?=null;
    var miId:Int?=null
    var miLati:Double?=null
    var miLongi:Double?=null

    var mensaje= Academico()

var fecha:Calendar?=null

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback


    @SuppressLint("MissingPermission")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        setSupportActionBar(toolbar)



        //Actrivamos la Localizacion
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        //La location callback
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                for (location in locationResult.locations){
                    // Update UI with location data
                    // ...
                }
            }
        }


/*
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
*/
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)






        //Mostramos solo el contenido0 principalk
        ocultarTodo()
        val principal = findViewById(R.id.principal) as ConstraintLayout
        principal.visibility = View.VISIBLE




        //
        //Ajustamos el spinner

        var spinner=   findViewById<Spinner>(R.id.spinnerIncidencias)

        // Create an ArrayAdapter using the string array and a default spinner layout
        val adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item)
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
// Apply the adapter to the spinner
        spinner.adapter = adapter
        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                var item=        parent?.getItemAtPosition(position)
                //El siguiente funciona bien
                //Toast.makeText(applicationContext,"Este es "+item,Toast.LENGTH_SHORT).show()
            }

        }

        // Guardamos la incidencia
           findViewById<Button>(R.id.guardarIncidencia).setOnClickListener{
            var item=   spinner?.selectedItem
               Toast.makeText(applicationContext,"Seleccionaste "+item,Toast.LENGTH_SHORT).show();
           }



        /***************************************************************************************************************************************
        Inicia boton registrar ingreso

        ************************************************************************ ***************************************************************/
          findViewById<Button>(R.id.botonIngreso).setOnClickListener {

                      TareaRegistrarse().execute(null,null,null)

          }

        /**************************************************************
        termina boton registrar ingreso
         ***************************************************************/
    } //Termina OnCReate



    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.principal -> {
                // Handle the camera action
                ocultarTodo()
                val principal = findViewById(R.id.principal) as ConstraintLayout
                principal.visibility = View.VISIBLE

            }
            R.id.incidencias -> {
                ocultarTodo()
                val incidencias = findViewById(R.id.incidencias) as ConstraintLayout
                incidencias.visibility = View.VISIBLE




            }
            R.id.clinicas -> {

            }

        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    fun ocultarTodo(){
        val principal = findViewById(R.id.principal) as ConstraintLayout
        val incidencias = findViewById(R.id.incidencias) as ConstraintLayout
        principal.visibility = View.INVISIBLE
        incidencias.visibility = View.INVISIBLE
    }

    inner class TareaMensaje : AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg p0: Void?): Void? {




            var url2="https://jc-elementos.herokuapp.com/api/mensaje"

            val restTemplate = RestTemplate()
            restTemplate.messageConverters.add(MappingJackson2HttpMessageConverter())


            val maper = ObjectMapper()
            //  usuarios = maper.readValue(estring, object : TypeReference<ArrayList<Usuario>>() {})

            val respuesta = restTemplate.postForObject(url2, mensaje, String::class.java)
            estatus = maper.readValue(respuesta, Estatus::class.java )
            print(estatus.mensaje)

            println("DESPUES DE REST");
            return null
        }

        override fun onPreExecute() {
            super.onPreExecute()
            //Se piden las componentes
           // var eCuerpo=     findViewById<EditText>(R.id.textoCuerpo)
          //  mensaje.cuerpo=    eCuerpo.text.toString();
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            Toast.makeText(applicationContext,estatus.mensaje, Toast.LENGTH_LONG).show();
          //  findViewById<EditText>(R.id.textoCuerpo).text=null
        }
    }
/*****************************************************************************************************************
                                     REGISRTO DE PRACTICA-PROFESOR
 ***************************************************************************************************************/
  inner class  TareaRegistrarse :AsyncTask<Void, Void, Void>(){

      override fun onPreExecute() {
          super.onPreExecute()

          //Generamos o llenamos la practica

          obtenerUbicacion()




      }

      override fun doInBackground(vararg params: Void?): Void? {
          try {
              var url2 = "https://node74674-env-8686050.whelastic.net/api/practica"
              //  var url2="http://192.168.100.7:8080/api/practica"

              val restTemplate = RestTemplate()
              restTemplate.messageConverters.add(MappingJackson2HttpMessageConverter())
              print("XXXXXXXXXXXXXXXXXXXXXXXX " + practica?.id);

              val maper = ObjectMapper()
              //  usuarios = maper.readValue(estring, object : TypeReference<ArrayList<Usuario>>() {})

              val respuesta = restTemplate.postForObject(url2, practica, String::class.java)

              estatus = maper.readValue(respuesta, Estatus::class.java)
              // else estatus = null
              print("El rol es" + academico?.rol)

              println("DESPUES DE REST");
          }catch(t:Throwable){
              Toast.makeText(applicationContext,"No tienes internet", Toast.LENGTH_LONG).show();
          }

          return null
      }

      override fun onPostExecute(result: Void?) {
          super.onPostExecute(result)
          Toast.makeText(applicationContext, "mensaje:"+estatus.mensaje, Toast.LENGTH_LONG).show()
      }
  }


    @SuppressLint("MissingPermission")
    fun obtenerUbicacion(){


        // Este es otro cdigo
        val locationRequest = LocationRequest().apply {
          interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        val builder = LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)
       fusedLocationClient.requestLocationUpdates(locationRequest,locationCallback,null);



//termina e4ste es otro codigo





        fusedLocationClient.lastLocation
                .addOnSuccessListener { location : Location? ->

                    fecha= Calendar.getInstance();
                     diaSemana=     fecha?.get(Calendar.DAY_OF_WEEK);

                     diano= fecha?.get(Calendar.DAY_OF_YEAR);
                    var hora=fecha?.get(Calendar.HOUR_OF_DAY);
                    var minuto=fecha?.get(Calendar.MINUTE);

                    Globales.diaano =diano!!
                    Globales.registrados =true
                    miId= Globales.diaano +33868

                    //Probamos guardar en shared preferences
                    /*
                    val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return@addOnSuccessListener
                    with (sharedPref.edit()) {
                        putInt("diasemana", dia!!)

                        commit()
                    }



                    val highScore = sharedPref.getInt("dia",0);*/

                 //   Toast.makeText(applicationContext,"Loca "+location?.latitude+" Longi"+location?.longitude+" alti "+location?.altitude+ " Dia es "+diano
                    //        ,Toast.LENGTH_LONG).show()

      miLati=location?.latitude
      miLongi=location?.longitude



practica= Practica()
                    practica?.id=""+ diano!! +""+33868
                    practica?.dia=diano!!
                    practica?.diaSemana=diaSemana!!
                    practica?.idProfesor=33868
                    practica?.lat= miLati
                    practica?.lon=miLongi
                    practica?.horario=""+hora+":"+minuto
                    print("XXXXXXXXXXXXXXXXXXXXXXXX "+practica?.id);
           Toast.makeText(applicationContext, "Valor horario"+practica?.horario, Toast.LENGTH_SHORT).show();

                }
    }



}

