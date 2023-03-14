package com.example.firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.firebase.databinding.ActivityEmployeeDetailsBinding
import com.google.firebase.database.FirebaseDatabase

class EmployeeDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEmployeeDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmployeeDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setValueToView()

        binding.btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("empId").toString()
            )
        }

        binding.btnUpdate.setOnClickListener {
            val empId = binding.editTextId.text.toString()
            val empName = binding.editTextName.text.toString()
            val empAge = binding.editTextAge.text.toString()
            val empSalary = binding.editTextSalary.text.toString()
            updateEmpData(
                empId,
                empName,
                empAge,
                empSalary
            )
        }
    }

    private fun updateEmpData(id: String, name: String, age: String, salary: String) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Employees").child(id)
        val empData = EmployeeModel(id, name, age, salary)
        dbRef.setValue(empData)
        Toast.makeText(this, "Update success", Toast.LENGTH_SHORT).show()
    }

    private fun deleteRecord(id: String) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Employees").child(id)
        val mTask = dbRef.removeValue()
        mTask.addOnSuccessListener {
            Toast.makeText(this, "Delete Success", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, FetchingActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{
            Toast.makeText(this, "Delete Failure", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setValueToView() {
        binding.editTextId.setText(intent.getStringExtra("empId"))
        binding.editTextName.setText(intent.getStringExtra("empName"))
        binding.editTextAge.setText(intent.getStringExtra("empAge"))
        binding.editTextSalary.setText(intent.getStringExtra("empSalary"))

    }
}