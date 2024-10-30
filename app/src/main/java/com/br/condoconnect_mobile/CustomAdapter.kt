package com.br.condoconnect_mobile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class CustomAdapter(private val dataSet: List<Produto>) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nome: TextView = view.findViewById(R.id.nomeProduto)
        val preco: TextView = view.findViewById(R.id.precoProduto)
        val imagem: ImageView = view.findViewById(R.id.imagemProduto)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_produto, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val produto = dataSet[position]
        viewHolder.nome.text = produto.PRODUTO_NOME
        viewHolder.preco.text = "R$ ${produto.PRODUTO_PRECO}"
        Picasso.get().load(produto.PRODUTO_IMAGEM).into(viewHolder.imagem)
    }

    override fun getItemCount() = dataSet.size
}
