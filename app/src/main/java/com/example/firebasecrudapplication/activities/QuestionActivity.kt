package com.example.firebasecrudapplication.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebasecrudapplication.R
import com.example.firebasecrudapplication.adapter.OptionAdapter
import com.example.firebasecrudapplication.databinding.ActivityQuestionBinding
import com.example.firebasecrudapplication.models.Questions
import com.example.firebasecrudapplication.models.Quize
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson


class QuestionActivity : AppCompatActivity() {
    private  lateinit var binding :ActivityQuestionBinding
    var quizes : MutableList<Quize>? = null
    var questions: MutableMap<String, Questions>? = null
    var index = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpFirestore()
        setUpEventListener()
    }


    private fun setUpEventListener() {
        binding.btnPrevious.setOnClickListener {
            index--
            bindViews()
        }

        binding.btnNext.setOnClickListener {
            index++
            bindViews()
        }

        binding.btnSubmit.setOnClickListener {
            Log.d("FINALQUIZ", questions.toString())

            val intent = Intent(this, ResultActivity::class.java)
            val json  = Gson().toJson(quizes!![0])
            intent.putExtra("QUIZ", json)
            startActivity(intent)
        }
    }
    private fun setUpFirestore() {
        val firestore = FirebaseFirestore.getInstance()
        var date = intent.getStringExtra("DATE")
        if (date != null) {
            firestore.collection("quizes").whereEqualTo("title", date)
                .get()
                .addOnSuccessListener {
                    if(it != null && !it.isEmpty){
                        quizes = it.toObjects(Quize::class.java)
                        questions = quizes!![0].questions
                        Log.d("Firestore", "Quizes: ${quizes.toString()}")
                        Log.d("Firestore", "Questions: ${questions.toString()}")

                        bindViews()
                    }
                }
        }
    }

    private fun bindViews() {
        if (questions == null) {
            Log.e("bindViews", "Questions map is null")
            return
        }

        // Print all keys in the questions map
        for (key in questions!!.keys) {
            Log.d("bindViews", "Question key: $key")
        }
        binding.btnPrevious.visibility = View.GONE
        binding.btnSubmit.visibility = View.GONE
        binding.btnNext.visibility = View.GONE

        if(index == 1){ //first question
            binding.btnNext.visibility = View.VISIBLE
        }
        else if(index == questions!!.size) { // last question
            binding.btnSubmit.visibility = View.VISIBLE
            binding.btnPrevious.visibility = View.VISIBLE
        }
        else{ // Middle
            binding.btnPrevious.visibility = View.VISIBLE
            binding.btnNext.visibility = View.VISIBLE
        }

        val questionKey = "question$index"
        val question = questions!![questionKey]
        Log.d("bindViews", "Question key: $questionKey, Question: $question")
        question?.let {
            binding.description.text = it.description
            val optionAdapter = OptionAdapter(this, it)
            binding.optionList.layoutManager = LinearLayoutManager(this)
            binding.optionList.adapter = optionAdapter
            binding.optionList.setHasFixedSize(true)
        } ?: run {
            Log.e("bindViews", "Question not found for index: $index")
        }
    }
}