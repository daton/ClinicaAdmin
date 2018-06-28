package clinicamovil.unitec.org.clinicaadmin


import android.annotation.SuppressLint
import android.location.Location
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat

import android.widget.Button
import android.widget.Toast

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


class IncidenciasActivity : AppCompatActivity(),  ActivityCompat.OnRequestPermissionsResultCallback {


    private lateinit var fusedLocationClient: FusedLocationProviderClient


    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_incidencias)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        //Navegacion del incidencias
        val botonIncidencias=findViewById<Button>(R.id.obtenerPosicion)
        botonIncidencias.setOnClickListener{

            fusedLocationClient.lastLocation
                    .addOnSuccessListener { location : Location? ->

                        Toast.makeText(applicationContext,"Loca "+location?.latitude+" Longi"+location?.longitude+" alti "+location?.altitude,Toast.LENGTH_LONG).show()
                    }

        }





    }





}
