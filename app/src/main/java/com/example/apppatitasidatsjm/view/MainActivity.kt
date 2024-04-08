package com.example.apppatitasidatsjm.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.apppatitasidatsjm.R
import com.example.apppatitasidatsjm.databinding.ActivityMainBinding
import com.example.apppatitasidatsjm.model.db.entity.PersonaEntity
import com.example.apppatitasidatsjm.retrofit.response.LoginResponse
import com.example.apppatitasidatsjm.util.AppMensaje
import com.example.apppatitasidatsjm.util.SharedPreferencesManager
import com.example.apppatitasidatsjm.util.TipoMensaje
import com.example.apppatitasidatsjm.viewmodel.AuthViewModel
import com.example.apppatitasidatsjm.viewmodel.PersonaViewModel

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var authViewModel: AuthViewModel
    private lateinit var personaViewModel: PersonaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        personaViewModel = ViewModelProvider(this).get(PersonaViewModel::class.java)
        authViewModel.loginResponse.observe(this, Observer {
            response -> obtenerDatosLogin(response!!)
        })
        binding.btnIngresar.setOnClickListener(this)
        binding.btnRegistrar.setOnClickListener(this)
    }

    private fun obtenerDatosLogin(response: LoginResponse) {
        if(response.rpta) {
            val personaEntity = PersonaEntity(
                response.idpersona.toInt(), response.nombres, response.apellidos, response.email,
                response.celular, response.usuario, response.password, response.esvoluntario
            )
            if(recordarDatosLogin()) {
                personaViewModel.actualizar(personaEntity)
            } else {
                personaViewModel.insertar(personaEntity)
                if(binding.cbRecordar.isChecked) {
                    SharedPreferencesManager().setSomeBooleanValue("PREF_RECORDAR",
                        true)
                }
            }
            personaViewModel.insertar(personaEntity)
            startActivity((Intent(applicationContext, HomeActivity::class.java)))
        } else {
            AppMensaje.enviarMensaje(binding.root, response.mensaje, TipoMensaje.ERROR)
        }
        binding.btnIngresar.isEnabled = true
        binding.btnRegistrar.isEnabled = true
    }

    override fun onClick(vista: View) {
        when(vista.id) {
            R.id.btnIngresar -> autenticarUsuario()
            R.id.btnRegistrar -> startActivity(Intent(applicationContext,
                RegistroActivity::class.java))
        }
    }

    private fun autenticarUsuario() {
        binding.btnIngresar.isEnabled = false
        binding.btnRegistrar.isEnabled = false
        authViewModel.auntenticarUsuario(binding.etUsuario.text.toString(),
            binding.etPassword.text.toString())
    }

    private fun recordarDatosLogin():Boolean {
        return SharedPreferencesManager().getSomeBooleanValue("PREF_RECORDAR")
    }
}