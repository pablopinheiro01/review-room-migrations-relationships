package br.com.alura.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.lifecycleScope
import br.com.alura.orgs.database.AppDatabase
import br.com.alura.orgs.extensions.vaiPara
import br.com.alura.orgs.model.Usuario
import br.com.alura.orgs.preferences.dataStore
import br.com.alura.orgs.preferences.usuarioPreferences
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class UsuarioBaseActivity : AppCompatActivity() {

    private val usuarioDao by lazy {
        AppDatabase.instancia(this).usuarioDao()
    }

    private var _usuario: MutableStateFlow<Usuario?> = MutableStateFlow(null)
    protected var usuario: StateFlow<Usuario?> = _usuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            verificaUsuarioLogado()
        }
    }

    protected suspend fun deslogaUsuario() {
        dataStore.edit { preferences ->
            preferences.remove(usuarioPreferences)
        }
    }

    private suspend fun buscaUsuario(usuarioId: String) {
        _usuario.value = usuarioDao.buscaPorId(usuarioId).firstOrNull()
    }

    private suspend fun verificaUsuarioLogado() {
        dataStore.data.collect { preferences ->
            preferences[usuarioPreferences]?.let { usuarioId ->
                buscaUsuario(usuarioId)
            } ?: vaiParaLogin()
        }
    }

    private fun vaiParaLogin() {
        vaiPara(LoginActivity::class.java){
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) //remove a tela do topo
        }
        finish()
    }





}