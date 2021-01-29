package br.com.digitalhouse.desafio04dh.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import br.com.digitalhouse.desafio04dh.R
import br.com.digitalhouse.desafio04dh.adapter.AdapterGame
import br.com.digitalhouse.desafio04dh.viewModel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), AdapterGame.OnClickListenerGame {
    private val viewModel: MainViewModel by viewModels()
    private val adapterGame = AdapterGame(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (viewModel.verifyConnection()) {
            viewModel.showMsg("Sem conexão com a Internet")
        } else {
            setUpSettings()

            viewModel.gameList.observeForever{
                adapterGame.addList(it)
            }

            fbtnAddGame.setOnClickListener {
                startActivity(Intent(this@MainActivity, AddGameActivity::class.java))
            }
        }
    }

    override fun onStart() {
        super.onStart()

        viewModel.getData()
    }

    private fun setUpSettings(){
        rvGames.adapter = adapterGame
        rvGames.setHasFixedSize(true)
    }

    override fun onClickGame(position: Int) {
        val intent = Intent(this, DetailActivity::class.java)
        val game = viewModel.gameList.value!![position]

        intent.putExtra("gamePhoto", game.img.toString())
        intent.putExtra("gameName", game.name)
        intent.putExtra("gameYear", game.year.toString())
        intent.putExtra("gameDescription", game.description)

        startActivity(intent)
    }
}