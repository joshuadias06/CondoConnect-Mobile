package com.br.condoconnect_mobile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class CustomAdapter(
    private var dataSet: MutableList<Produto>,
    private val onDelete: (Produto) -> Unit,
    private val onEdit: (Produto) -> Unit,
    private val onDetail: (Produto) -> Unit // Novo callback para detalhes
) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nome: TextView = view.findViewById(R.id.nomeProduto)
        val preco: TextView = view.findViewById(R.id.precoProduto)
        val imagem: ImageView = view.findViewById(R.id.imagemProduto)
        val deletar: ImageButton = view.findViewById(R.id.deletar) // Botão de deletar
        val editar: ImageButton = view.findViewById(R.id.editar) // Botão de editar
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_produto, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val produto = dataSet[position]
        viewHolder.nome.text = produto.nome_produto
        viewHolder.preco.text = "R$ ${produto.preco_produto}"
        Picasso.get().load(produto.imagem_produto).into(viewHolder.imagem)

        // Configurar o click do botão de deletar
        viewHolder.deletar.setOnClickListener {
            onDelete(produto) // Chama o callback de deletar
        }

        // Configurar o click do botão de editar
        viewHolder.editar.setOnClickListener {
            onEdit(produto) // Chama o callback de editar
        }

        // Configurar o click do item para ver detalhes
        viewHolder.itemView.setOnClickListener {
            onDetail(produto) // Chama o callback de detalhes
        }
    }

    override fun getItemCount() = dataSet.size

    fun removeItem(produto: Produto) {
        dataSet.remove(produto)
        notifyDataSetChanged() // Notifica a RecyclerView que os dados mudaram
    }

    fun updateProdutos(novaLista: MutableList<Produto>) {
        dataSet.clear() // Limpa a lista atual
        dataSet.addAll(novaLista) // Adiciona a nova lista
        notifyDataSetChanged() // Notifica a RecyclerView que os dados mudaram
    }
}
