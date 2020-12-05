package com.example.demoarchivos

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.io.FileOutputStream
import java.io.InputStream

class MainActivity : AppCompatActivity() {
    val nombreArchivo = "mi_archivo.txt"

    var btnGuardar:Button?= null
    var etInfo:EditText?=null
    var btnLeer:Button?= null
    var tvMostrar:TextView?= null
    val prefs = getPreferences(MODE_PRIVATE)

    var btnLeerP:Button?=null
    var btnGuardarP:Button?=null

    var etClave:EditText?=null
    var etValor:EditText?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initUI()


        btnGuardar!!.setOnClickListener {
            //validar que existe texto en info
            val datos = etInfo!!.text.toString()
            escribirDatos(datos)
        }

        btnLeer!!.setOnClickListener {
            tvMostrar!!.text = leerArchivo(nombreArchivo)
        }

        btnLeerP!!.setOnClickListener {
            val ClaveABuscar = etClave!!.text.toString()
            val datoEncontrado=getDato(ClaveABuscar)
            etValor!!.setText(datoEncontrado)
        }
        btnGuardarP!!.setOnClickListener {
            val claveAGuardar = etClave!!.text.toString()
            val valorAGuardar = etValor!!.text.toString()
            setDato(claveAGuardar,valorAGuardar)
        }


    }
    fun escribirDatos(texto:String){
        val fos:FileOutputStream
        try{
            fos = openFileOutput(nombreArchivo, Context.MODE_PRIVATE)
            fos.write(texto.toByteArray())
            fos.close()
            Log.wtf("Archivos",texto)
        }catch (e:Exception){
            Log.wtf("Archivos","Algo falló ${e.message}")
            e.printStackTrace()
        }
    }

    fun leerArchivo(nombreArchivo:String):String{
        val inputStream:InputStream = openFileInput(nombreArchivo)
        val stringResultado = inputStream.bufferedReader().use { it.readLine() }
        return  stringResultado
    }
    /*
        Métodos para sharedPreferences
     */
    fun setDato(clave:String,valor:String){
        prefs.edit().putString(clave,valor).apply()
    }

    fun getDato(clave: String):String{
        val dato = prefs.getString(clave,"No se encontró ${clave}")
        return dato.toString()
    }

    fun initUI(){
        btnGuardar = findViewById(R.id.btnGuardar)
        etInfo = findViewById(R.id.etinfo)
        btnLeer = findViewById(R.id.btnLeer)
        tvMostrar = findViewById(R.id.tvMostrar)
        etClave = findViewById(R.id.etClave)
        etValor = findViewById(R.id.etValor)
        btnGuardarP = findViewById(R.id.btnGuardarPrefs)
        btnLeerP = findViewById(R.id.btnLeerPrefs)
    }
}