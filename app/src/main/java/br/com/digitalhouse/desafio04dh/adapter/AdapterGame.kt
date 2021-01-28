package br.com.digitalhouse.desafio04dh.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.digitalhouse.desafio04dh.R
import br.com.digitalhouse.desafio04dh.model.Game
import com.squareup.picasso.Picasso

class AdapterGame(var listener: OnClickListenerGame) : RecyclerView.Adapter<AdapterGame.ViewHolderGame>() {
    private lateinit var gameList: ArrayList<Game>

    interface OnClickListenerGame {
        fun onClickGame(position: Int)
    }

    inner class ViewHolderGame(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var ivGamePhoto: ImageView = itemView.findViewById(R.id.ivGamePhoto)
        var tvGameName: TextView = itemView.findViewById(R.id.tvGameName)
        var tvGameYear: TextView = itemView.findViewById(R.id.tvGameYear)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition

            if (position != RecyclerView.NO_POSITION) {
                listener.onClickGame(position)
            }
        }

    }

    fun addList(list: ArrayList<Game>) {
        gameList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderGame {
        return ViewHolderGame(
            LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolderGame, position: Int) {
        val game: Game = gameList[position]

        Picasso.get().load(game.img).fit().centerCrop().into(holder.ivGamePhoto)
        holder.tvGameName.text = game.name
        holder.tvGameYear.text = game.year.toString()
    }

    override fun getItemCount() = gameList.size
}