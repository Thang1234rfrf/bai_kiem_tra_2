package com.example.thigk2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.thigk2.databinding.ActivityMainBinding
import com.example.thigk2.databinding.ActivityThemdataBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.Them1.setOnClickListener {
            val intentInsertData = Intent(this, themdata::class.java)
            Toast.makeText(this, "ojihgyg", Toast.LENGTH_SHORT).show()
            startActivity(intentInsertData)}

    binding.hienthi.setOnClickListener {
            val hienth = Intent(this, hienthi::class.java)
            startActivity(hienth)}
        }
    }
