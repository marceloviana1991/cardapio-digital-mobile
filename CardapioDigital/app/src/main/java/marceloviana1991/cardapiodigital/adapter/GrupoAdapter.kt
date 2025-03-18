package marceloviana1991.cardapiodigital.adapter

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import marceloviana1991.cardapiodigital.R
import marceloviana1991.cardapiodigital.dto.GrupoResponse
import android.util.Base64


class GrupoAdapter(
    private val grupos: List<GrupoResponse>
) : RecyclerView.Adapter<GrupoAdapter.GrupoViewHolder>() {

    class GrupoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nome: TextView = itemView.findViewById(R.id.textViewGrupo)
        val imagem: ImageView = itemView.findViewById(R.id.imageViewGrupo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GrupoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_grupo, parent, false)
        return GrupoViewHolder(view)
    }

    override fun getItemCount(): Int = grupos.size

    override fun onBindViewHolder(holder: GrupoViewHolder, position: Int) {
        val grupo = grupos[position]
        holder.nome.setText(grupo.nome)

        val decodedBytes = Base64.decode(grupo.imagem, Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
        holder.imagem.setImageBitmap(bitmap)

    }

}



