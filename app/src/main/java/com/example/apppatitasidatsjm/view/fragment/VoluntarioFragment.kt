package com.example.apppatitasidatsjm.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.apppatitasidatsjm.databinding.FragmentVoluntarioBinding
import com.example.apppatitasidatsjm.model.db.entity.PersonaEntity
import com.example.apppatitasidatsjm.retrofit.response.RegistroResponse
import com.example.apppatitasidatsjm.util.AppMensaje
import com.example.apppatitasidatsjm.util.TipoMensaje
import com.example.apppatitasidatsjm.viewmodel.PersonaViewModel
import com.example.apppatitasidatsjm.viewmodel.VoluntarioViewModel

class VoluntarioFragment : Fragment(), View.OnClickListener {

    var _binding: FragmentVoluntarioBinding? = null
    private val binding get() = _binding!!
    private lateinit var voluntarioViewModel: VoluntarioViewModel
    private lateinit var personaViewModel: PersonaViewModel
    private lateinit var personaEntity: PersonaEntity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentVoluntarioBinding.inflate(inflater, container, false)
        voluntarioViewModel = ViewModelProvider(requireActivity())
            .get(VoluntarioViewModel::class.java)
        personaViewModel = ViewModelProvider(requireActivity())
            .get(PersonaViewModel::class.java)
        personaViewModel.obtener().observe(viewLifecycleOwner, Observer {
            persona -> persona?.let {
                if(persona.esvoluntario == "1") {
                    formVoluntario()
                } else {
                    personaEntity = persona
                }
            }
        })
        binding.btnRegistrarVoluntario.setOnClickListener(this)
        voluntarioViewModel.registroResponse.observe(viewLifecycleOwner, Observer {
            respuestaRegistroVoluntario(it)
        })
        return binding.root
    }

    private fun respuestaRegistroVoluntario(it: RegistroResponse) {
        if(it.rpta) {
            val nuevaPersonaEntity = PersonaEntity(
                personaEntity.id, personaEntity.nombres, personaEntity.apellidos,
                personaEntity.email, personaEntity.celular, personaEntity.usuario,
                personaEntity.password, "1"
            )
            personaViewModel.actualizar(nuevaPersonaEntity)
            formVoluntario()
        }
        AppMensaje.enviarMensaje(binding.root, it.mensaje, TipoMensaje.SUCCESFULL)
        binding.btnRegistrarVoluntario.isEnabled = true
    }

    override fun onClick(v: View?) {
        if(binding.cbAceptarTerminos.isChecked) {
            binding.btnRegistrarVoluntario.isEnabled = false
            voluntarioViewModel.registrarVoluntario(personaEntity.id)
        } else {
            AppMensaje.enviarMensaje(binding.root,
                "Acepte los términos y condiciones para ser voluntario",
                TipoMensaje.ERROR)
        }
    }

    private fun formVoluntario() {
        binding.cbAceptarTerminos.visibility = View.GONE
        binding.btnRegistrarVoluntario.visibility = View.GONE
        binding.textView4.visibility = View.GONE
        binding.textView3.text = "Gracias por su compromiso como voluntario"
    }
}