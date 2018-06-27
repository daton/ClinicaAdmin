package clinicamovil.unitec.org.clinicaadmin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class IncidenciasActivity : AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_incidencias)

        //Navegacion del incidencias
        val botonIncidencias=findViewById<Button>(R.id.obtenerPosicion)
        botonIncidencias.setOnClickListener{
              Toast.makeText(applicationContext,"Mensajito",Toast.LENGTH_LONG).show()
        }
    }
}
