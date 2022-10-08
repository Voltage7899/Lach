package com.example.lach

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lach.databinding.ActivityWorkZoneBinding
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class WorkZone : AppCompatActivity(),ListAdapter.ClickListener {
    lateinit var binding: ActivityWorkZoneBinding
    var ListAdapter:ListAdapter?=null
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkZoneBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.workZoneBottomMenu.selectedItemId = R.id.adminPanel
        binding.workZoneBottomMenu.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    val admin = Intent(this, MainActivity::class.java)
                    startActivity(admin)
                }
                R.id.basket->{
                    val admin = Intent(this, Basket::class.java)
                    startActivity(admin)
                }
            }

            true
        }

        binding.addMode.setOnClickListener {
            startActivity(Intent(this,Add::class.java))
        }
        binding.recAdmin.layoutManager= GridLayoutManager(this,2)
        ListAdapter = ListAdapter(this)
        binding.recAdmin.adapter=ListAdapter
        ListAdapter?.loadListToAdapter(getData())
    }
    fun getData():ArrayList<ProductModel>{



        val List=ArrayList<ProductModel>()
        database.getReference("Product").get().addOnSuccessListener {

                for(item in it.children){
                    var kino=item.getValue(ProductModel::class.java)
                    if(kino!=null){
                        List.add(kino)

                    }
                }



            ListAdapter?.loadListToAdapter(List)
        }
        return List
    }
    override fun onStart() {
        super.onStart()
        ListAdapter?.loadListToAdapter(getData())
    }
}