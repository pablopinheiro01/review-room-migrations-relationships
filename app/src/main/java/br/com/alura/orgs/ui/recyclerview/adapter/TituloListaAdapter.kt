package br.com.alura.orgs.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.orgs.databinding.TituloItemBinding

class TituloListaAdapter(
    private val context: Context,
    titulos: List<String> = emptyList()
) : RecyclerView.Adapter<TituloListaAdapter.ViewHolder>() {

    private val titulos = titulos.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = TituloItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val titulo = titulos[position]
        holder.vincula(titulo)
    }

    override fun getItemCount(): Int = titulos.size

    inner class ViewHolder(private val binding: TituloItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun vincula(nome: String){
            binding.activityListaTodosUsuario.text = nome
        }
    }

    fun atualiza(titulo: String){
        this.titulos.clear()
        this.titulos.add(titulo)
        notifyDataSetChanged()
    }
}