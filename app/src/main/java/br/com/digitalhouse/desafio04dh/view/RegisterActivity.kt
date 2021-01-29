package br.com.digitalhouse.desafio04dh.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import br.com.digitalhouse.desafio04dh.databinding.ActivityRegisterBinding
import br.com.digitalhouse.desafio04dh.viewModel.MainViewModel

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpOnClickListeners()
    }

    private fun setUpOnClickListeners() {
        binding.btnCreateAccount.setOnClickListener {
            if (binding.edPassword == binding.edPasswordRepeat) {
                viewModel.oAuth.createUserWithEmailAndPassword(
                    binding.edEmail.toString(),
                    binding.edPassword.toString()
                ).addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        startActivity(Intent(this@RegisterActivity, MainActivity::class.java))
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