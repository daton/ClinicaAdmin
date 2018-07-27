package clinicamovil.unitec.org.clinicaadmin

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
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.app_bar_menu.*


class MenuActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

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

    }

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
}
