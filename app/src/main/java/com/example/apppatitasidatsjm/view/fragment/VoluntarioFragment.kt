package com.example.apppatitasidatsjm.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.apppatitasidatsjm.databinding.FragmentVoluntarioBinding
import com.example.apppatitasidatsjm.util.AppMensaje
import com.example.apppatitasidatsjm.util.TipoMensaje
import com.example.apppatitasidatsjm.viewmodel.VoluntarioViewModel

class VoluntarioFragment : Fragment(), View.OnClickListener {

    var _binding: FragmentVoluntarioBinding? = null
    private val binding get() = _binding!!
    private lateinit var voluntarioViewModel: VoluntarioViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentVoluntarioBinding.inflate(inflater, container, false)
        voluntarioViewModel = ViewModelProvider(requireActivity())
            .get(VoluntarioViewModel::class.java)
        binding.btnRegistrarVoluntario.setOnClickListener(this)
        return binding.root
    }

    override fun onClick(v: View?) {
        if(binding.cbAceptarTerminos.isChecked) {
            binding.btnRegistrarVoluntario.isEnabled = false
            //voluntarioViewModel.registrarVoluntario()
        } else {
            AppMensaje.enviarMensaje(binding.root,
                "Acepte los términos y condiciones para ser voluntario",
                TipoMensaje.ERROR)
        }
    }
}