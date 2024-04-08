package com.example.apppatitasidatsjm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.apppatitasidatsjm.repository.VoluntarioRepository
import com.example.apppatitasidatsjm.retrofit.response.RegistroResponse

class MascotaViewModel: ViewModel() {

    private var repository = VoluntarioRepository()
    var registroResponse: LiveData<RegistroResponse> = repository.registroResponse

    fun registrarVoluntario(idPersona: Int) {
        registroResponse = repository.registrarVoluntario(idPersona)
    }
}