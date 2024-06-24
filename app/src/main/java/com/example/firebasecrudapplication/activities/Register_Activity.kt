package com.example.firebasecrudapplication.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.firebasecrudapplication.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class Register_Activity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)

        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        binding.btnSignUp.setOnClickListener { signUpUser() }
        binding.tvAlreadyHaveAnAccount.setOnClickListener{
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }
       }

    private fun signUpUser(){
        val email:String = binding.edEmailAddress.text.toString()
        val password:String = binding.edPassword.text.toString()
        val confirmPass:String = binding.edConfirmPassword.text.toString()

        if(email.isBlank() || password.isBlank() || confirmPass.isBlank()){
            Toast.makeText(this, "Email and Password can't be blank", Toast.LENGTH_SHORT).show()
            return
        }

        if(password != confirmPass){
            Toast.makeText(this,"password and confirm password do not match",Toast.LENGTH_SHORT).show()
            return
        }



        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this) {task->
            if (task.isSuccessful) {
                Toast.makeText(this, "Successfully Singed Up", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            else{

                Toast.makeText(this, "Registration failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()

            }
        }



    }

    }


