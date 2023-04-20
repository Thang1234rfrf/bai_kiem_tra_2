package com.example.thigk2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thigk2.listview
import com.example.thigk2.modelthem
import com.example.thigk2.databinding.ActivityHienthiBinding
import com.google.firebase.database.*

class hienthi : AppCompatActivity() {
    lateinit var binding: ActivityHienthiBinding
    lateinit var productList:MutableList<modelthem>
    lateinit var dbRef : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hienthi)
        binding = ActivityHienthiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter()
    }

    private fun adapter() {
        // set layout for adapter
        binding.rvPersonData.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        binding.rvPersonData.setHasFixedSize(true)

        productList =  mutableListOf()

        // get data person
        information()

    }

    private fun information() {
        binding.rvPersonData.visibility = View.GONE
        binding.txtTitle.visibility = View.GONE
        binding.txtLoad.visibility = View.VISIBLE

        // get data
        dbRef = FirebaseDatabase.getInstance().getReference("product")
        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                productList.clear()
                if (snapshot.exists()){
                    for (data in snapshot.children){
                        val dataItem = data.getValue(modelthem::class.java)
                        productList.add(dataItem!!)

                    }
                    // set data
                    val adapter = listview(productList)
                    val rv = findViewById<RecyclerView>(R.id.rvPersonData)
                    rv.adapter = adapter

                    // item click listener
                    adapter.setOnItemClickListener(object :listview.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@hienthi,ActivityDetailsPerson::class.java)
                            // put data
                            intent.putExtra("id",productList[position].id)
                            intent.putExtra("name",productList[position].tensp)
                            intent.putExtra("type",productList[position].losisp)
                            intent.putExtra("price",productList[position].giasp)
                            intent.putExtra("link",productList[position].linkimg)
                            startActivity(intent)
                        }
                    })

                    // display data
                    binding.rvPersonData.visibility = View.VISIBLE
                    binding.txtTitle.visibility = View.VISIBLE
                    binding.txtLoad.visibility = View.GONE

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}