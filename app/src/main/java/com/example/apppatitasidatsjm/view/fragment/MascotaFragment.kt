package com.example.apppatitasidatsjm.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.apppatitasidatsjm.databinding.FragmentMascotaBinding
import com.example.apppatitasidatsjm.viewmodel.MascotaViewModel


class MascotaFragment : Fragment() {

    private var _binding: FragmentMascotaBinding? = null
    private val binding get() = _binding!!
    private lateinit var mascotaViewModel: MascotaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMascotaBinding.inflate(inflater, container, false)
        mascotaViewModel = ViewModelProvider(requireActivity()).get(MascotaViewModel::class.java)
        return binding.root
    }
}