package com.example.firebasecrudapplication.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.firebasecrudapplication.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
   private lateinit var auth:FirebaseAuth
   private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        auth = FirebaseAuth.getInstance()
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            loginUser()
        }

        binding.btnSignUp.setOnClickListener {
            val intent = Intent(this, Register_Activity::class.java)
            startActivity(intent)

            finish()
        }

        }

    private fun loginUser(){
        val email:String = binding.etEmailAddress.text.toString()
        val password:String = binding.etPassword.text.toString()


        if(email.isBlank() || password.isBlank()){
            Toast.makeText(this,"Email/Password cannot be empty",Toast.LENGTH_SHORT).show()
            return
        }
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this){
            if(it.isSuccessful){
                Toast.makeText(this,"Login Successful",Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                Toast.makeText(this,"Login Failed",Toast.LENGTH_SHORT).show()
            }
        }

    }
    }
