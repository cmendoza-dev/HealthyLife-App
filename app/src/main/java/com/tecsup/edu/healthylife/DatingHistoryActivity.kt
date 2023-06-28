package com.tecsup.edu.healthylife

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tecsup.edu.healthylife.adapter.CitasAdapter
import com.tecsup.edu.healthylife.view_model.CitasViewModel


class DatingHistoryActivity : AppCompatActivity() {
    private lateinit var viewModel: CitasViewModel
    private lateinit var citasAdapter: CitasAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_citas)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        citasAdapter = CitasAdapter(emptyList(), emptyList())
        recyclerView.adapter = citasAdapter


        viewModel = ViewModelProvider(this).get(CitasViewModel::class.java)
        viewModel.citasList.observe(this) { citas ->
            citasAdapter.citasList = citas
            citasAdapter.notifyDataSetChanged()
        }

        viewModel.doctoresList.observe(this) { doctores ->
            citasAdapter.doctoresList = doctores
            citasAdapter.notifyDataSetChanged()
        }

        viewModel.fetchData()
    }
}

