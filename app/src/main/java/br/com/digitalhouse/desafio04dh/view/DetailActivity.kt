package br.com.digitalhouse.desafio04dh.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import br.com.digitalhouse.desafio04dh.R
import br.com.digitalhouse.desafio04dh.model.Game
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    private lateinit var game: Game

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setUpOnClickListeners()

        game = Game(
            Uri.parse(intent.getStringExtra("gamePhoto")),
            intent.getStringExtra("gameName")!!,
            intent.getIntExtra("gameYear", 1900).toString().toInt(),
            intent.getStringExtra("gameDescription")!!
        )

        Picasso.get().load(game.img)
            .fit()
            .centerCrop()
            .transform(CropCircleTransformation()).into(ivGamePhoto)
        tvGameName.text = game.name
        tvGameYear.text = game.year.toString()
        tvGameDescription.text = game.description
    }

    private fun setUpOnClickListeners(){
        ivBack.setOnClickListener {
            finish()
        }

        fBtnEditGame.setOnClickListener {
            val intent = Intent(this, AddGameActivity::class.java)

            intent.putExtra("gamePhoto", game.img.toString())
            intent.putExtra("gameName", game.name)
            intent.putExtra("gameYear", game.year.toString())
            intent.putExtra("gameDescription", game.description)

            startActivity(intent)
            finish()
        }
    }
}