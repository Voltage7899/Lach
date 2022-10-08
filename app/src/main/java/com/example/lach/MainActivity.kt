package com.example.lach

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.lach.databinding.ActivityMainBinding
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity(),ListAdapter.ClickListener {
    lateinit var binding: ActivityMainBinding
    var ListAdapter:ListAdapter?=null
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomMenu.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.adminPanel -> {
                    val admin = Intent(this, RegAvtorization::class.java)
                    startActivity(admin)
                }
                R.id.basket->{
                    val admin = Intent(this, Basket::class.java)
                    startActivity(admin)
                }
            }

            true
        }

        binding.recUser.layoutManager= GridLayoutManager(this,2)
        ListAdapter = ListAdapter(this)
        binding.recUser.adapter=ListAdapter
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
    override fun onClick(kino: ProductModel) {
        startActivity(Intent(this, BuyProduct::class.java).apply {
            putExtra("kinoName",kino.name)

        })
    }
    override fun onStart() {
        super.onStart()
        ListAdapter?.loadListToAdapter(getData())
    }
}