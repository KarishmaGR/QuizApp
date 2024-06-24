package com.example.firebasecrudapplication.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.GridLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.firebasecrudapplication.R
import com.example.firebasecrudapplication.adapter.QuizeAdapter
import com.example.firebasecrudapplication.databinding.MainActivityBinding
import com.example.firebasecrudapplication.models.Quize
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Date

class MainActivity : AppCompatActivity() {
    lateinit var actibarDrawerToggle: ActionBarDrawerToggle
    private lateinit var binding : MainActivityBinding
    private lateinit var adapter: QuizeAdapter
    private var quizList = mutableListOf<Quize>()
    private lateinit var firestore:FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = MainActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setUpView()


        }


    private fun setUpView(){
        setUpFireStore()
        setUpDrawerLayout()
        setUpRecyclerView()
        setUpDatePicker()

    }

    private fun setUpDatePicker() {
        binding.btnDatePicker.setOnClickListener{
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.show(supportFragmentManager,"DatePicker")
            datePicker.addOnPositiveButtonClickListener {
                val dateFormatter = SimpleDateFormat("dd-mm-yyyy")
                val date = dateFormatter.format(Date(it))
                val intent = Intent(this,QuestionActivity::class.java)
                intent.putExtra("DATE",date)
                startActivity(intent)

            }

            datePicker.addOnNegativeButtonClickListener {
                Log.d("DATEPICKER", datePicker.headerText)
            }
            datePicker.addOnCancelListener {
                Log.d("DATEPICKER", "Date Picker Cancelled")
            }

        }
    }

    private fun setUpFireStore() {
        firestore = FirebaseFirestore.getInstance()
        val collectionReference = firestore.collection("quizes")
        collectionReference.addSnapshotListener { value, error ->
            if(value==null || error!=null){
                Toast.makeText(this,"Error while Fetching Data",Toast.LENGTH_SHORT).show()
                return@addSnapshotListener
            }else{
                Log.d("DATA",value.toObjects(Quize::class.java).toString())
                quizList.clear()
                quizList.addAll(value.toObjects(Quize::class.java))
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun setUpRecyclerView(){
        adapter= QuizeAdapter(this,quizList)
        binding.quizeRecyclerView.layoutManager =GridLayoutManager(this,2)
        binding.quizeRecyclerView.adapter = adapter

    }


    private fun setUpDrawerLayout(){
        setSupportActionBar(binding.appBar)
        actibarDrawerToggle = ActionBarDrawerToggle(this,binding.mainDrawer,
            R.string.app_name,
            R.string.app_name
        )
        actibarDrawerToggle.syncState()
        binding.navigationView.setNavigationItemSelectedListener {
            val intent = Intent(this,ProfileActivity::class.java)
            startActivity(intent)
            binding.mainDrawer.closeDrawers()
            true
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (actibarDrawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    }
