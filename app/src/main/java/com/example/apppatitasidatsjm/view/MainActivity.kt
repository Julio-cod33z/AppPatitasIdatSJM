package com.example.apppatitasidatsjm.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.apppatitasidatsjm.R
import com.example.apppatitasidatsjm.databinding.ActivityMainBinding
import com.example.apppatitasidatsjm.retrofit.response.LoginResponse
import com.example.apppatitasidatsjm.util.AppMensaje
import com.example.apppatitasidatsjm.util.TipoMensaje
import com.example.apppatitasidatsjm.viewmodel.AuthViewModel

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        authViewModel.loginResponse.observe(this, Observer {
            response -> obtenerDatosLogin(response!!)
        })
        binding.btnIngresar.setOnClickListener(this)
        binding.btnRegistrar.setOnClickListener(this)

    }

    private fun obtenerDatosLogin(response: LoginResponse) {
        if(response.rpta) {
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
}