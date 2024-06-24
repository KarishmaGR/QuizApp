package com.example.firebasecrudapplication.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.firebasecrudapplication.databinding.ActivityIntroBinding
import com.google.firebase.auth.FirebaseAuth

class Intro_Activity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityIntroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        if(auth.currentUser!=null){
            Toast.makeText(this,"You are already login in",Toast.LENGTH_SHORT).show()
            redirect("MAIN")
        }
     binding.getStarted.setOnClickListener {
        redirect("LOGIN")
    }

        }

    private fun redirect(name:String){
        val intent = when(name){
            "MAIN"-> Intent(this, MainActivity::class.java)
            "LOGIN"->Intent(this, Login::class.java)
            else-> throw Exception("No Path Exist")
        }
        startActivity(intent)
        finish()
    }
    }
