package com.anggriawan.adminkonstitusicoffee

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.anggriawan.adminkonstitusicoffee.adapter.MenuItemAdapter
import com.anggriawan.adminkonstitusicoffee.databinding.ActivityAllItemBinding
import com.anggriawan.adminkonstitusicoffee.model.AllMenu
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AllItemActivity : AppCompatActivity() {

    private lateinit var databaseReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private var menuItems: ArrayList<AllMenu> = ArrayList()

    private val binding : ActivityAllItemBinding by lazy {
        ActivityAllItemBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.backButton.setOnClickListener {
            finish()
        }

        databaseReference = FirebaseDatabase.getInstance().reference
        retrieveMenuItem()

    }

    private fun retrieveMenuItem() {
        database = FirebaseDatabase.getInstance()
        val coffeeRef: DatabaseReference = database.reference.child("menu")

        //fetch data from database
        coffeeRef.addListenerForSingleValueEvent(object :ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                // Clear existing data before populating
                menuItems.clear()

                // loop for through each food item
                for (coffeeSnapshot in snapshot.children){
                    val menuItem = coffeeSnapshot.getValue(AllMenu::class.java)
                    menuItem?.let {
                        menuItems.add(it)
                    }
                }
                setAdapter()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("DatabaseError", "Error: ${error.message}")
            }
        })
    }
    private fun setAdapter() {
        val adapter = MenuItemAdapter(this@AllItemActivity, menuItems, databaseReference){position ->
            deleteMenuItems(position)
        }
        binding.menuRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.menuRecyclerView.adapter = adapter
    }

    private fun deleteMenuItems(position: Int) {
        val menuItemToDelete = menuItems[position]
        val menuItemKey = menuItemToDelete.key
        val coffeeMenuReference = database.reference.child("menu").child(menuItemKey!!)
        coffeeMenuReference.removeValue().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                menuItems.removeAt(position)
                binding.menuRecyclerView.adapter?.notifyItemRemoved(position)
            }else{
                Toast.makeText(this, "Item not Deleted", Toast.LENGTH_SHORT).show()
            }
        }
    }

}