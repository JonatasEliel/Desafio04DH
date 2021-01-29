package br.com.digitalhouse.desafio04dh.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import br.com.digitalhouse.desafio04dh.databinding.ActivityLoginBinding
import br.com.digitalhouse.desafio04dh.viewModel.MainViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpOnClickListeners()
    }

    override fun onStart() {
        super.onStart()

        if (viewModel.oAuth.currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun setUpOnClickListeners() {
        binding.btnLogin.setOnClickListener {
            viewModel.oAuth.signInWithEmailAndPassword(
                binding.edEmail.toString(),
                binding.edPassword.toString()
            ).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    viewModel.showMsg("Olá ${viewModel.oAuth.currentUser?.email}")
                    startActivity(Intent(this, MainActivity::class.java))
                } else {
                    viewModel.showMsg("Por favor, verifique seus dados.")
                }
            }
        }

        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }
    }
}