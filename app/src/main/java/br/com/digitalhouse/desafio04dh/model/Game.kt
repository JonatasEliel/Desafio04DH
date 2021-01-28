package br.com.digitalhouse.desafio04dh.model

import android.net.Uri

data class Game(
    val img: Uri,
    val name: String,
    val year: Int,
    val description: String
)
