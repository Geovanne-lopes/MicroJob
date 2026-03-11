package br.com.fiap.microjob.viewmodel

import androidx.lifecycle.ViewModel
import br.com.fiap.microjob.model.MockData

/**
 * ViewModel para a tela de perfil do usuário.
 */
class ProfileViewModel : ViewModel() {

    val currentUser = MockData.defaultUser
}
