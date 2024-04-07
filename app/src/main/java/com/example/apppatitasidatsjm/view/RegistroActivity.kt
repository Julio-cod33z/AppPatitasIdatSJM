package com.example.apppatitasidatsjm.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.apppatitasidatsjm.R
import com.example.apppatitasidatsjm.databinding.ActivityRegistroBinding
import com.example.apppatitasidatsjm.retrofit.response.RegistroResponse
import com.example.apppatitasidatsjm.util.AppMensaje
import com.example.apppatitasidatsjm.util.TipoMensaje
import com.example.apppatitasidatsjm.viewmodel.AuthViewModel

class RegistroActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityRegistroBinding
    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        authViewModel.registroResponse.observe(this, Observer {
                response -> obtenerDatosRegistro(response!!)
        })
        binding.btnGuardarUsuario.setOnClickListener(this)
        binding.btnIrLogin.setOnClickListener(this)
    }

    private fun obtenerDatosRegistro(response: RegistroResponse) {
        AppMensaje.enviarMensaje(binding.root,
            response.mensaje, TipoMensaje.SUCCESFULL)
    }

    override fun onClick(vista: View) {
        when(vista.id) {
            R.id.btnGuardarUsuario -> registrarUsuario()
            R.id.btnIrLogin -> startActivity(Intent(applicationContext,
                MainActivity::class.java))
        }
    }

    private fun registrarUsuario() {
        binding.btnGuardarUsuario.isEnabled = false
        binding.btnIrLogin.isEnabled = false
        authViewModel.registrarUsuario(binding.etNombreRegistro.text.toString(),
            binding.etApellidosRegistro.text.toString(),
            binding.etEmailRegistro.text.toString(),
            binding.etCelularRegistro.text.toString(),
            binding.etUsuarioRegistro.text.toString(),
            binding.etPasswordRegistro.text.toString())
    }
}