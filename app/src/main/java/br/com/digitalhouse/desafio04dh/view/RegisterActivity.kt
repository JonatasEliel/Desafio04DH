package br.com.digitalhouse.desafio04dh.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import br.com.digitalhouse.desafio04dh.R
import br.com.digitalhouse.desafio04dh.viewModel.MainViewModel
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        setUpOnClickListeners()
    }

    private fun setUpOnClickListeners() {
        btnCreateAccount.setOnClickListener {
            val name = edNameRegister.text.toString()
            val email = edEmailRegister.text.toString()
            val password = edPasswordRegister.text.toString()
            val passwordRepeat = edPasswordRepeatRegister.text.toString()

            if (password == passwordRepeat) {
                viewModel.oAuth.createUserWithEmailAndPassword(
                    email,
                    password
                ).addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        startActivity(Intent(this@RegisterActivity, MainActivity::class.java))
                        finish()
                    } else {
                        viewModel.showMsg("Por favor, verifique seus dados.")
                    }
                }
            } else {
                viewModel.showMsg("Por favor, verifique seus dados.")
            }
        }
    }
}