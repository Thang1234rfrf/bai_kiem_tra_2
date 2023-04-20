package com.example.thigk2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.net.toFile
import com.example.thigk2.databinding.ActivityThemdataBinding
import com.example.thigk2.modelthem
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

class themdata : AppCompatActivity() {
    lateinit var binding: ActivityThemdataBinding
    // references
    lateinit var dbRef : DatabaseReference
    var path : String? = ""
    private val REQUEST_CODE_PICK_IMAGE = 100
    private val imgPost: AppCompatImageView by lazy {
        findViewById(R.id.imageView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_themdata)
        binding = ActivityThemdataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // create table
        dbRef = FirebaseDatabase.getInstance().getReference("product")

        // btn save

        // btn choose pic
        binding.anh.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE)
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri = data.data
            // firebase store
            val storage = FirebaseStorage.getInstance()
            val storageRef = storage.reference.child("images/"+selectedImageUri?.lastPathSegment.toString())
            val file = selectedImageUri.toString()
            Log.e("test",file)
            val imageRef = storageRef.child(file)
            // Xử lý ảnh được chọn ở đây, ví dụ hiển thị ảnh bằng ImageView
            binding.imageView.visibility = View.VISIBLE
            imgPost.setImageURI(selectedImageUri)
            binding.Them.setOnClickListener {
                val uploadTask = storageRef.putFile(selectedImageUri!!)

                val TenSP = binding.TenSP.text.toString()
                val LoaiSP = binding.LoaiSP.text.toString()
                val GiaSP = binding.GiaSP.text.toString()


                // check data
                var count:Int = 0
                if (TenSP.isEmpty()){
                    binding.TenSP.error = "Nhập Tên Sản Phẩm"
                    count++
                }
                if (LoaiSP.isEmpty()){
                    binding.LoaiSP.error = "Nhập Loại Sản Phẩm"
                    count++
                }
                if (GiaSP.isEmpty()){
                    binding.GiaSP.error = "Nhập Giá Của Sản Phẩm"
                    count++
                }

                // push data
                if (count==0){
                    uploadTask.addOnSuccessListener { taskSnapshot ->
                        // Upload thành công, lấy đường dẫn tới file trên Firebase Storage
                        taskSnapshot.metadata?.reference?.downloadUrl?.addOnSuccessListener { uri ->
                            val downloadUrl = uri.toString()
                            val id = dbRef.push().key!!
                            val product = modelthem(id,TenSP,LoaiSP,GiaSP,downloadUrl)
                            // notification
                            dbRef.child(id).setValue(product)
                                .addOnCompleteListener {
                                    Toast.makeText(this,"Data inserted!",Toast.LENGTH_SHORT).show()
                                }
                                .addOnFailureListener {err->
                                    Toast.makeText(this,"Data inserted error!!! ${err.message}",Toast.LENGTH_SHORT).show()
                                }
                        }
                    }.addOnFailureListener { exception ->
                        // Upload thất bại
                    }


                }
                binding.TenSP.setText("")
                binding.LoaiSP .setText("")
                binding.GiaSP .setText("")
                binding.imageView.visibility = View.GONE
            }


        }
    }
}