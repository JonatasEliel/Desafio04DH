package br.com.digitalhouse.desafio04dh.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.digitalhouse.desafio04dh.R
import br.com.digitalhouse.desafio04dh.databinding.ActivityDetailBinding
import br.com.digitalhouse.desafio04dh.model.Game
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var game: Game

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpOnClickListeners()

        game = Game(Uri.parse(intent.getStringExtra("gamePhoto")),
            intent.getStringExtra("gameName")!!,
            intent.getIntExtra("gameYear", 1900).toString().toInt(),
            intent.getStringExtra("descriptionGame")!!)

        Picasso.get().load(game.img)
            .fit()
            .centerCrop()
            .transform(CropCircleTransformation()).into(binding.ivGamePhoto)
        binding.tvGameName.text = game.name
        binding.tvGameYear.text = game.year.toString()
        binding.tvGameDescription.text = game.description
    }

    private fun setUpOnClickListeners(){
        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.fBtnEditGame.setOnClickListener {
            val intent = Intent(this, AddGameActivity::class.java)

            intent.putExtra("gamePhoto", game.img)
            intent.putExtra("gameName", game.name)
            intent.putExtra("gameYear", game.year)
            intent.putExtra("gameDescription", game.description)

            startActivity(intent)
            finish()
        }
    }
}