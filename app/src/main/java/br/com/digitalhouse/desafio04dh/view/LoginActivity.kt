package br.com.digitalhouse.desafio04dh.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import br.com.digitalhouse.desafio04dh.R
import br.com.digitalhouse.desafio04dh.viewModel.MainViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setUpOnClickListeners()
    }

    override fun onStart() {
        super.onStart()

        if (viewModel.oAuth.currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun setUpOnClickListeners() {
        btnLogin.setOnClickListener {
            val email = edEmail.text.toString()
            val password = edPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()){
                viewModel.oAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        viewModel.showMsg("Olá ${task.result?.user!!.email}")
                        startActivity(Intent(this, MainActivity::class.java))
                    } else {
                        viewModel.showMsg("Por favor, verifique seus dados.")
                    }
                }
            } else {
                viewModel.showMsg("Preencha todos os dados")
            }
        }

        btnRegister.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }
    }
}