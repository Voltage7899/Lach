package com.example.lach

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.lach.databinding.ActivityBuyProductBinding
import com.google.firebase.database.*
import com.squareup.picasso.Picasso

class BuyProduct : AppCompatActivity() {
    lateinit var binding: ActivityBuyProductBinding
    private var kino:ProductModel?=null
    private var database: DatabaseReference = FirebaseDatabase.getInstance().getReference()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityBuyProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name= intent.getStringExtra("kinoName").toString()


        getKino(name)

        binding.buyBuy.setOnClickListener {

            val id = userModel.currentuser?.phone.toString()
            val shop=ShopModel(id,kino?.name,kino?.description,kino?.price,kino?.link)
            database.child("Basket").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.child(id).child(kino?.name.toString()).exists()){
                        Toast.makeText(this@BuyProduct,"У вас уже есть товар", Toast.LENGTH_LONG).show()
                    }
                    else{
                        database.child("Basket").child(id).child(kino?.name.toString()).setValue(shop)
                        Toast.makeText(this@BuyProduct,"Вы купили товар", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }

    }
    fun getKino(name:String){

        database.child("Product").child(name).get().addOnSuccessListener {
            kino=it.getValue(ProductModel::class.java)

            Picasso.get().load(kino?.link).fit().into(binding.descImage)

            binding.descName.setText(kino?.name)
            binding.descDesc.setText(kino?.description)
            binding.descPrice.setText(kino?.price)
        }
    }

}