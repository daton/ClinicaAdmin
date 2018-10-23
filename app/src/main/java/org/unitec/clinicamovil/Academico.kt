package org.unitec.clinicamovil

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

class Academico {
    var nip:Int?=null
    var rol:String?=null
    var nombre:String?=null
    var paterno:String?=null
    var materno:String?=null
    @JsonIgnoreProperties(ignoreUnknown = true)
    var proyeccion: Proyeccion?=null


}