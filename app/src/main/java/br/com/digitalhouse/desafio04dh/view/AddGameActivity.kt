package br.com.digitalhouse.desafio04dh.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import br.com.digitalhouse.desafio04dh.R
import br.com.digitalhouse.desafio04dh.databinding.ActivityAddGameBinding
import br.com.digitalhouse.desafio04dh.model.Game
import br.com.digitalhouse.desafio04dh.viewModel.MainViewModel
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation

class AddGameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddGameBinding
    private lateinit var gameSent: Game
    private val viewModel: MainViewModel by viewModels()
    private val imgCode = 0
    private val game: MutableMap<String, String> = HashMap()
    private var img = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpOnClickListeners()

        if (intent != null && intent.extras != null) {
            gameSent = Game(
                Uri.parse(intent.getStringExtra("gamePhoto")),
                intent.getStringExtra("gameName")!!,
                intent.getIntExtra("gameYear", 1900),
                intent.getStringExtra("gameDescription")!!
            )

            populateData(gameSent)
        }
    }

    private fun setUpOnClickListeners() {
        binding.btnSaveGame.setOnClickListener {
            saveGame(
                binding.edName.toString(),
                binding.edCreatedAt.toString().toInt(),
                binding.edDescription.toString()
            )
        }

        binding.fBtnAddPhoto.setOnClickListener {
            addPhoto()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == imgCode) {
            val uploadTask = viewModel.storage.putFile(data!!.data!!)
            uploadTask.continueWithTask {
                viewModel.storage!!.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val uri = task.result

                    game["image"] = uri.toString()
                    img = true

                    Picasso.get().load(uri)
                        .fit()
                        .centerCrop()
                        .transform(CropCircleTransformation())
                        .into(binding.fBtnAddPhoto)
                }
            }
        }
    }

    private fun populateData(gameSent: Game) {
        img = true
        game["image"] = gameSent.img.toString()

        Picasso.get().load(gameSent.img)
            .fit()
            .centerCrop()
            .transform(CropCircleTransformation())
            .into(binding.fBtnAddPhoto)
        binding.edName.setText(gameSent.name)
        binding.edCreatedAt.setText(gameSent.year)
        binding.edDescription.setText(gameSent.description)
    }

    private fun addPhoto() {
        val intent = Intent()
        intent.type = "image/"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Imagem do Jogo"), imgCode)
    }

    private fun saveGame(name: String, year: Int, description: String) {
        if (img) {
            game["name"] = name
            game["year"] = year.toString()
            game["description"] = description

            viewModel.coll.document(name).set(game).addOnCompleteListener {
                Log.i("saveGame", "Adicionado")
            }.addOnFailureListener {
                Log.w("saveGame", "Adicionado")
            }

            viewModel.getData()
            finish()
        } else {
            viewModel.showMsg("Adicione uma imagem.")
        }
    }
}