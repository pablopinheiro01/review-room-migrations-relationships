package br.com.alura.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import br.com.alura.orgs.database.AppDatabase
import br.com.alura.orgs.databinding.ActivityTodosProdutosActivityBinding
import br.com.alura.orgs.ui.recyclerview.adapter.ListaProdutosAdapter
import br.com.alura.orgs.ui.recyclerview.adapter.TituloListaAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TodosProdutosActivity: UsuarioBaseActivity() {

    private val concatAdapter = ConcatAdapter()

    private val binding by lazy {
        ActivityTodosProdutosActivityBinding.inflate(layoutInflater)
    }

    private val produtoDao by lazy {
        val db = AppDatabase.instancia(this)
        db.produtoDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        lifecycleScope.launch {
            launch {
                buscaTodos().collect { listUsuarios ->
                    for(user in listUsuarios){
                        Log.i(TAG, "usuario: $user")
                        val adapterProdutos = ListaProdutosAdapter(context = this@TodosProdutosActivity)
                        val adapterTitulo = TituloListaAdapter(context = this@TodosProdutosActivity)
                        configuraAdapter(adapterProdutos)
                        configuraRecyclerView(concatAdapter)
                        launch {
                            produtoDao.buscaTodosDoUsuario(user.id)
                                .collect { listaProdutos ->
//                                binding.activityListaTodosUsuario.text = user.nome
//                                    val text: TextView = TextView(
//                                        this@TodosProdutosActivity
//                                    )
//                                    text.text = user.nome
//                                    binding.root.addView(text)
                                Log.i(TAG, "lista de prod: $listaProdutos")
                                adapterProdutos.atualiza(listaProdutos)
                                adapterTitulo.atualiza(user.nome)
//                                concatAdapter.addAdapter(adapterTitulo)
                                concatAdapter.addAdapter(adapterProdutos)
                                concatAdapter.notifyDataSetChanged()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun configuraAdapter(adapter: ListaProdutosAdapter) {
        adapter.quandoClicaNoItem = {
            val intent = Intent(
                this,
                DetalhesProdutoActivity::class.java
            ).apply {
                putExtra(CHAVE_PRODUTO_ID, it.id)
            }
            startActivity(intent)
        }
    }

    private fun configuraRecyclerView(adapter: ConcatAdapter ) {
        val recyclerView = binding.activityListaTodosProdutosRecyclerView
        recyclerView.adapter = adapter
    }

    companion object{
        const val TAG = "TodosProdAct"
    }

}