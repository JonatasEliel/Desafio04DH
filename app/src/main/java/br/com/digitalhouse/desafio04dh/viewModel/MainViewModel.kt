package br.com.digitalhouse.desafio04dh.viewModel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.com.digitalhouse.desafio04dh.model.Game
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext
    val gameList = MutableLiveData<ArrayList<Game>>()
    private val games: ArrayList<Game> = arrayListOf()
    private val database = FirebaseFirestore.getInstance()
    var oAuth = FirebaseAuth.getInstance()
    val coll = database.collection("games")
    val storage =
        FirebaseStorage.getInstance().getReference((System.currentTimeMillis() / 1000).toString())

    fun verifyConnection(): Boolean {
        val conn = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return (conn.activeNetwork == null)
    }

    fun getData() {
        games.clear()
        viewModelScope.launch {
            database.collection("games").get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        val game = Game(
                            Uri.parse(document.data["image"].toString()),
                            document.data["name"].toString(),
                            document.data["year"].toString().toInt(),
                            document.data["description"].toString()
                        )

                        games.add(game)
                    }

                    gameList.value = games
                } else {
                    Toast.makeText(
                        context,
                        "Não foi possível acessar a base de dados",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    fun showMsg(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT)
    }
}