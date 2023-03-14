package com.example.firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.firebase.databinding.ActivityInsertionBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class InsertionActivity : AppCompatActivity() {

    private lateinit var dbRef: DatabaseReference

    private lateinit var binding: ActivityInsertionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInsertionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbRef = FirebaseDatabase.getInstance().getReference("Employees")

        binding.btnSaveData.setOnClickListener {
            saveEmployeeData()
        }
    }

    private fun saveEmployeeData() {
        val empName = binding.editTextName.text.toString()
        val empAge = binding.editTextAge.text.toString()
        val empSalary = binding.editTextSalary.text.toString()

        if(empName.isEmpty()){
            binding.editTextName.error = "Please Enter Name"
        }
        if(empAge.isEmpty()){
            binding.editTextAge.error = "Please Enter Age"
        }
        if(empSalary.isEmpty()){
            binding.editTextSalary.error = "Please Enter Salary"
        }

        val empId = dbRef.push().key!!
        val employee = EmployeeModel(empId, empName, empAge, empSalary)

        dbRef.child(empId).setValue(employee)
            .addOnCompleteListener{
                Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener{
                Toast.makeText(this, "Insert Failed", Toast.LENGTH_SHORT).show()
            }
    }
}