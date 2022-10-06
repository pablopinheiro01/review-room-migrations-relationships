package br.com.alura.orgs.ui.activity

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import br.com.alura.orgs.databinding.ActivityDetalheUsuarioLogadoBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

class DetalheDoUsuarioActivity : UsuarioBaseActivity() {

    private val binding by lazy {
        ActivityDetalheUsuarioLogadoBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        lifecycleScope.launch {
            usuario
            .filterNotNull()
            .collect {
                binding.activityDeatlheUsuarioNome.text = it.nome
            }
        }

        binding.activityDetalheUsuarioBotaoSair.setOnClickListener {
            lifecycleScope.launch {
                deslogaUsuario()
            }
        }

    }
}