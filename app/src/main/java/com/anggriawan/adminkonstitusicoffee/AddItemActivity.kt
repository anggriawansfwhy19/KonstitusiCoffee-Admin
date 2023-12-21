package com.anggriawan.adminkonstitusicoffee

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.anggriawan.adminkonstitusicoffee.databinding.ActivityAddItemBinding
import com.anggriawan.adminkonstitusicoffee.model.AllMenu
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class AddItemActivity : AppCompatActivity() {

    // Coffee Item Details
    private lateinit var coffeeName : String
    private lateinit var coffeePrice : String
    private lateinit var coffeeDescription : String
    private lateinit var coffeeIngredients : String
    private var coffeeImageUri : Uri? = null

    // Firebase
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    private val binding : ActivityAddItemBinding by lazy {
        ActivityAddItemBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // initialize Firebase
        auth = FirebaseAuth.getInstance()
        // Firebase database Instance
        database = FirebaseDatabase.getInstance()

        binding.addItem.setOnClickListener {
            // Get Data from Filed
            coffeeName = binding.coffeeName.text.toString().trim()
            coffeePrice = binding.coffeePrice.text.toString().trim()
            coffeeDescription = binding.description.text.toString().trim()
            coffeeIngredients = binding.ingredient.text.toString().trim()

            if (!(coffeeName.isBlank() || coffeePrice.isBlank() || coffeeDescription.isBlank() || coffeeIngredients.isBlank())){
                uploadData()
                Toast.makeText(this, "Item Add Successfully", Toast.LENGTH_SHORT).show()
                finish()
            }else{
                Toast.makeText(this, "Fill all the Details", Toast.LENGTH_SHORT).show()
            }
        }
        binding.selectImage.setOnClickListener {
            pickImage.launch("image/*")
        }

    binding.backButton.setOnClickListener {
        finish()
    }
    }

    private fun uploadData() {
        // Get a reference to the "menu" node in the database
        val menuRef = database.getReference("menu")

        val newItemKey = menuRef.push().key

        if (coffeeImageUri !=null){

            val storageRef = FirebaseStorage.getInstance().reference
            val imageRef = storageRef.child("menu_images${newItemKey}.jpg")
            val uploadTask = imageRef.putFile(coffeeImageUri!!)

            uploadTask.addOnSuccessListener {
                imageRef.downloadUrl.addOnSuccessListener {
                    downloadUrl ->
                    //
                    val newItem = AllMenu(
                        newItemKey,
                        coffeeName = coffeeName,
                        coffeePrice = coffeePrice,
                        coffeeDescription = coffeeDescription,
                        coffeeIngredients = coffeeIngredients,
                        coffeeImage = downloadUrl.toString(),
                    )
                    newItemKey?.let { 
                        key ->
                        menuRef.child(key).setValue(newItem).addOnSuccessListener {
                            Toast.makeText(this, "data Upload Success", Toast.LENGTH_SHORT).show()
                        }
                            .addOnFailureListener {
                                Toast.makeText(this, "data Upload Filed", Toast.LENGTH_SHORT).show()
                            }
                    }
                }

            }.addOnFailureListener {
                Toast.makeText(this, "Image Upload Failed", Toast.LENGTH_SHORT).show()
            }

        }else {
            Toast.makeText(this, "Please select Image", Toast.LENGTH_SHORT).show()
        }
    }

    private val pickImage=registerForActivityResult(ActivityResultContracts.GetContent()){uri ->
        if (uri !=null)
        {
            binding.selectedImage.setImageURI(uri)
            coffeeImageUri = uri
        }
    }
}