package com.example.firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebase.adapter.EmpAdapter
import com.example.firebase.databinding.ActivityFetchingBinding
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class FetchingActivity : AppCompatActivity() {

    private lateinit var ds: ArrayList<EmployeeModel>
    private lateinit var dbRef: DatabaseReference
    private lateinit var binding: ActivityFetchingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetching)
        binding = ActivityFetchingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvEmp.layoutManager = LinearLayoutManager(this)
        binding.rvEmp.setHasFixedSize(true)
        ds = arrayListOf<EmployeeModel>()
        getInfoEmp()

    }

    private fun getInfoEmp() {
        binding.rvEmp.visibility = View.GONE
        binding.txtLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Employees")
        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                ds.clear()
                if(snapshot.exists()){
                    for(empSnap in snapshot.children){
                        val empData = empSnap.getValue(EmployeeModel::class.java)
                        ds.add(empData!!)
                    }
                    val mAdapter = EmpAdapter(ds)
                    binding.rvEmp.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : EmpAdapter.onItemClickListener{
                        override fun onItemCLick(position: Int) {
                            val i = Intent(this@FetchingActivity, EmployeeDetailsActivity::class.java)
                            i.putExtra("empId", ds[position].empId)
                            i.putExtra("empName", ds[position].empName.toString())
                            i.putExtra("empAge", ds[position].empAge)
                            i.putExtra("empSalary", ds[position].empSalary)
                            startActivity(i)
                        }
                    })

                    binding.rvEmp.visibility = View.VISIBLE
                    binding.txtLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}