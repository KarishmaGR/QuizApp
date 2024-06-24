package com.example.firebasecrudapplication.adapter

import android.content.Context
import android.content.Intent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import android.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.firebasecrudapplication.R
import com.example.firebasecrudapplication.activities.QuestionActivity
import com.example.firebasecrudapplication.models.Quize
import com.example.firebasecrudapplication.utils.ColorPicker
import com.example.firebasecrudapplication.utils.IconPicker

class QuizeAdapter(val context: Context, val quizes:List<Quize>):RecyclerView.Adapter<QuizeAdapter.QuizeViewHolder>(){





    inner class QuizeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var textViewTitle:TextView=itemView.findViewById(R.id.quizTitle)
        var iconView: ImageView =itemView.findViewById(R.id.quizIcon)
        var cardContainer:CardView = itemView.findViewById(R.id.cardContainer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizeViewHolder {
        val view  = LayoutInflater.from(context).inflate(R.layout.quize_item,parent,false)
        return QuizeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return quizes.size
    }

    override fun onBindViewHolder(holder: QuizeViewHolder, position: Int) {
        holder.textViewTitle.text=quizes[position].title
        holder.cardContainer.setCardBackgroundColor(Color.parseColor(ColorPicker.getColor()))
        holder.iconView.setImageResource(IconPicker.getIcon())

        holder.itemView.setOnClickListener{
            Toast.makeText(context,quizes[position].title, Toast.LENGTH_SHORT).show()
            val intent = Intent(context, QuestionActivity::class.java)
            intent.putExtra("DATE",quizes[position].title)
            context.startActivity(intent)

        }
    }

}