package com.ucatolica.toffeecompaeroemocional

data class Paciente(var apellido1: String?= "", var apellido2: String?= "", var correoPrincipal: String?= "", var correoSecundario: String?= "", var direccionLaboral: String?= "", var direccionResidencia: String?= "",
                    var entidadSalud: String?= "", var estrato: String?= "", var fechaNacimiento: String?= "", var genero: String?= "", var lugarNacimiento: String?= "", var nombre: String?= "",
                    var nombreContacto: String?= "", var orientacionSexual: String?= "", var politica: Boolean? = true, var rol: String?= "", var servicioSalud: String?= "", var telefonoContacto: Long? = 0,
                    var terminos: Boolean?= true)
