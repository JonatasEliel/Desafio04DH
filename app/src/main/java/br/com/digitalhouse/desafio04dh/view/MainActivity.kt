package br.com.digitalhouse.desafio04dh.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.digitalhouse.desafio04dh.R
import br.com.digitalhouse.desafio04dh.adapter.AdapterGame
import br.com.digitalhouse.desafio04dh.databinding.ActivityMainBinding
import br.com.digitalhouse.desafio04dh.model.Game

class MainActivity : AppCompatActivity(), AdapterGame.OnClickListenerGame {
    private lateinit var binding: ActivityMainBinding
    var gameList = arrayListOf<Game>()
    val adapterGame = AdapterGame(gameList, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvGames.adapter = adapterGame
        binding.rvGames.setHasFixedSize(true)
    }

    override fun onClickGame(position: Int) {
        val intent = Intent(this@MainActivity, AddGameActivity::class.java)

        intent.putExtra("data", gameList[position])
        startActivity(intent)
    }
}