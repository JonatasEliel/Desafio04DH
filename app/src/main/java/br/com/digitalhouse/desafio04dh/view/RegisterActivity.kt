package br.com.digitalhouse.desafio04dh.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.digitalhouse.desafio04dh.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpOnClickListeners()
    }

    private fun setUpOnClickListeners() {
        binding.btnCreateAccount.setOnClickListener {
            startActivity(Intent(this@RegisterActivity, MainActivity::class.java))
        }
    }
}