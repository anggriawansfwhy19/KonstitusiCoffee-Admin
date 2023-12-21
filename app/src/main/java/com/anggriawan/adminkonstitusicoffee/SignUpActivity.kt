package com.anggriawan.adminkonstitusicoffee

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import com.anggriawan.adminkonstitusicoffee.databinding.ActivitySignUpBinding
import com.anggriawan.adminkonstitusicoffee.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class SignUpActivity : AppCompatActivity() {

    private lateinit var userName: String
    private lateinit var nameOfCoffeeShop: String
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private val binding: ActivitySignUpBinding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = Firebase.auth
        database = Firebase.database.reference



        binding.createButton.setOnClickListener {

            userName = binding.nameOwner.text.toString().trim()
            nameOfCoffeeShop = binding.coffeeshopName.text.toString().trim()
            password = binding.passwordSign.text.toString().trim()
            email = binding.emailSign.text.toString().trim()

            if (userName.isBlank() || nameOfCoffeeShop.isBlank() || email.isBlank() || password.isBlank()){
                Toast.makeText(this, "Please fill all details", Toast.LENGTH_SHORT).show()
            }else{
                createAccount(email,password)
            }

        }
        binding.alreadyHaveAccount.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        val locationList =
            arrayOf("Jakarta", "Bandung", "Cikarang", "Medan", "Bekasi", "Tangerang", "Rumah Eki")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, locationList)
        val autoCompleteTextView = binding.listOfLocation
        autoCompleteTextView.setAdapter(adapter)
    }

    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful){
                Toast.makeText(this,"Account Created Succesfully", Toast.LENGTH_SHORT).show()
                saveUserData()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            else{
                Toast.makeText(this, "Account Creation Failed", Toast.LENGTH_SHORT).show()
                Log.d("Account", "createAccount: Failure", task.exception)
            }
        }
    }

    private fun saveUserData() {
        userName = binding.nameOwner.text.toString().trim()
        nameOfCoffeeShop = binding.coffeeshopName.text.toString().trim()
        password = binding.passwordSign.text.toString().trim()
        email = binding.emailSign.text.toString().trim()

        val user = UserModel(userName, nameOfCoffeeShop, email, password)
        val userId = FirebaseAuth.getInstance().currentUser!!.uid

        database.child("user").child(userId).setValue(user)
    }
}