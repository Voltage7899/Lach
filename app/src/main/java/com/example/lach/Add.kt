package com.example.lach

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.lach.databinding.ActivityAddBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso

class Add : AppCompatActivity() {
    private lateinit var binding: ActivityAddBinding
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addMovie.setOnClickListener {


            val product=ProductModel(binding.addName.text.toString(),binding.addDesc.text.toString(),binding.addPrice.text.toString(),binding.addLink.text.toString())

            database.getReference("Product").addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.child(product.name.toString()).exists()){
                        Toast.makeText(this@Add,"Продукт уже существует", Toast.LENGTH_SHORT).show()
                    }
                    else{

                        database.getReference("Product").child(product.name.toString()).setValue(product)
                        finish()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }
        binding.addImage.setOnClickListener {
            try {
                Picasso.get().load(binding.addLink.text.toString()).fit().into(binding.addImage)
            }catch (ex:Exception){
                Toast.makeText(this,"Нет ссылки на картинку", Toast.LENGTH_SHORT).show()
            }

        }

    }
}