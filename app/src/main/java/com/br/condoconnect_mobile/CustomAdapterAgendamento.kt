package com.br.condoconnect_mobile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class CustomAdapterAgendamento(
    private var dataSet: MutableList<Agendamento>,
    private val onDelete: (Agendamento) -> Unit,
    private val onEdit: (Agendamento) -> Unit,
    private val onDetail: (Agendamento) -> Unit
) : RecyclerView.Adapter<CustomAdapterAgendamento.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val evento: TextView = view.findViewById(R.id.evento)
        val data: TextView = view.findViewById(R.id.data)
        val deletar: ImageButton = view.findViewById(R.id.deletar)
        val editar: ImageButton = view.findViewById(R.id.editar)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_agendamento, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val agendamento = dataSet[position]
        viewHolder.evento.text = agendamento.evento
        viewHolder.data.text = agendamento.data

        viewHolder.deletar.setOnClickListener {
            onDelete(agendamento)
        }

        viewHolder.editar.setOnClickListener {
            onEdit(agendamento)
        }

        viewHolder.itemView.setOnClickListener {
            onDetail(agendamento)
        }
    }

    override fun getItemCount() = dataSet.size

    fun removeItem(agendamento: Agendamento) {
        dataSet.remove(agendamento)
        notifyDataSetChanged()
    }

    fun updateAgendamentos(novaLista: MutableList<Agendamento>) {
        dataSet.clear()
        dataSet.addAll(novaLista)
        notifyDataSetChanged()
    }
}
