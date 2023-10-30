package com.madinafinal.adminmadinaeshop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.madinafinal.adminmadinaeshop.adapter.MenuItemAdapter
import com.madinafinal.adminmadinaeshop.databinding.ActivityAllItemBinding
import com.madinafinal.adminmadinaeshop.model.AllMenu

class AllItemActivity : AppCompatActivity() {

    //database
    private lateinit var databaseReference: DatabaseReference
    private lateinit var database: FirebaseDatabase

    private var menuItem: ArrayList<AllMenu> = ArrayList()

    private val binding: ActivityAllItemBinding by lazy {
        ActivityAllItemBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //initilize firebase database
        databaseReference = FirebaseDatabase.getInstance().reference

        retrieveMenuItem()

        binding.backButton.setOnClickListener {
            finish()
        }





    }

    private fun retrieveMenuItem() {
     database = FirebaseDatabase.getInstance()
        val foodRef: DatabaseReference = databaseReference.child("menu")

        //fetch from database
        foodRef.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                // Clear existing data before populating
                menuItem.clear()

                //loop for through each food item
                for (foodSnapshot in snapshot.children){
                    val menuItems= foodSnapshot.getValue(AllMenu::class.java)
                    menuItems?.let {
                        menuItem.add(it)
                    }
                }
                setAdapter()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("DatabaseError", "Error: Error ${error.message} ")
            }


        })
    }
    private fun setAdapter() {
        val adapter = MenuItemAdapter(this@AllItemActivity,menuItem,databaseReference){ position->
            deleteMenuItems(position)
        }

        binding.MenuRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.MenuRecyclerView.adapter = adapter
    }

    private fun deleteMenuItems(position: Int) {
        val menuItemDelete = menuItem[position]
        val menuItemKey = menuItemDelete.key
        val foodMenuReference = database.reference.child("menu").child(menuItemKey!!)
        foodMenuReference.removeValue().addOnCompleteListener { task ->
            if (task.isSuccessful){
               menuItem.removeAt(position)
                binding.MenuRecyclerView.adapter?.notifyItemRemoved(position)
                Toast.makeText(this, "Item Deleted", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, "Item not Delete", Toast.LENGTH_SHORT).show()
            }
        }
    }
}