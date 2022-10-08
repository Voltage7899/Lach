package com.example.lach

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lach.databinding.ActivityBasketBinding
import com.google.firebase.database.FirebaseDatabase

class Basket : AppCompatActivity() {
    lateinit var binding: ActivityBasketBinding
    var ListAdapter:BasketAdapter?=null
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBasketBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.ticketBottomMenu.selectedItemId = R.id.basket
        binding.ticketBottomMenu.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    val home = Intent(this, MainActivity::class.java)
                    startActivity(home)
                }
                R.id.adminPanel->{
                    val admin = Intent(this, RegAvtorization::class.java)
                    startActivity(admin)
                }
            }

            true
        }

        binding.recTickets.layoutManager= LinearLayoutManager(this)
        ListAdapter = BasketAdapter()
        binding.recTickets.adapter=ListAdapter
        ListAdapter?.loadListToAdapter(getData())
    }
    fun getData():ArrayList<ShopModel>{



        val List=ArrayList<ShopModel>()
        database.getReference("Basket").child(userModel.currentuser?.phone.toString()).get().addOnSuccessListener {
            for (i in it.children){
                var ticket=i.getValue(ShopModel::class.java)
                if(ticket!=null){
                    List.add(ticket)
                    ListAdapter?.loadListToAdapter(List)
                }

            }
        }
        return List
    }

    override fun onStart() {
        super.onStart()
        ListAdapter?.loadListToAdapter(getData())
    }
}