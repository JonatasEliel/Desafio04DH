package br.com.digitalhouse.desafio04dh.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import br.com.digitalhouse.desafio04dh.R
import br.com.digitalhouse.desafio04dh.adapter.AdapterGame
import br.com.digitalhouse.desafio04dh.databinding.ActivityMainBinding
import br.com.digitalhouse.desafio04dh.model.Game
import br.com.digitalhouse.desafio04dh.viewModel.MainViewModel

class MainActivity : AppCompatActivity(), AdapterGame.OnClickListenerGame {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private val adapterGame = AdapterGame(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (viewModel.verifyConnection()) {
            viewModel.showMsg("Sem conexão com a Internet")
        } else {
            setUpSettings()

            viewModel.gameList.observeForever{
                adapterGame.addList(it)
            }

            binding.fbtnAddGame.setOnClickListener {
                startActivity(Intent(this@MainActivity, AddGameActivity::class.java))
            }
        }
    }

    override fun onStart() {
        super.onStart()

        viewModel.getData()
    }

    private fun setUpSettings(){
        binding.rvGames.adapter = adapterGame
        binding.rvGames.setHasFixedSize(true)
    }

    override fun onClickGame(position: Int) {
        val intent = Intent(this@MainActivity, AddGameActivity::class.java)
        val game = viewModel.gameList.value!![position]

        intent.putExtra("gamePhoto", game.img)
        intent.putExtra("gameName", game.name)
        intent.putExtra("gameYear", game.year)
        intent.putExtra("gameDescription", game.description)

        startActivity(intent)
    }
}