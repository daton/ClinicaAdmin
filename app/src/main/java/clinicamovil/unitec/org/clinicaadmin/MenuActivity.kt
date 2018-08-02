package clinicamovil.unitec.org.clinicaadmin

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.location.Location
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.app_bar_menu.*
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.client.RestTemplate
import java.time.LocalDate
import java.time.ZonedDateTime
import java.util.*


class MenuActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    var estatus = Estatus()
    var mensaje=Academico()

var fecha:Calendar?=null

    private lateinit var fusedLocationClient: FusedLocationProviderClient


    @SuppressLint("MissingPermission")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        setSupportActionBar(toolbar)



        //Actrivamos la Localizacion
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

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



        /**************************************************************
        Inicia boton registrar ingreso
         ***************************************************************/
          findViewById<Button>(R.id.botonIngreso).setOnClickListener {

              fusedLocationClient.lastLocation
                      .addOnSuccessListener { location : Location? ->

                          fecha= Calendar.getInstance();
                          var dia=     fecha?.get(Calendar.DAY_OF_WEEK);


                          //Probamos guardar en shared preferences
                          val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return@addOnSuccessListener
                          with (sharedPref.edit()) {
                              putInt("dia", dia!!)
                              commit()
                          }



                          val highScore = sharedPref.getInt("dia",0);

                          Toast.makeText(applicationContext,"Loca "+location?.latitude+" Longi"+location?.longitude+" alti "+location?.altitude+ " Dia es "+dia
                                 + "Con guardardo"+highScore,Toast.LENGTH_LONG).show()



                      }

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
            estatus = maper.readValue(respuesta,Estatus::class.java )
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
}

