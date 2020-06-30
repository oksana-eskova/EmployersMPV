package com.oieskova.employers2.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.oieskova.employers2.R;
import com.oieskova.employers2.adapters.SpecialitiesAdapter;
import com.oieskova.employers2.data.Employee;
import com.oieskova.employers2.data.EmployeeViewModel;
import com.oieskova.employers2.data.Speciality;

import java.util.ArrayList;
import java.util.List;

public class SpecialitiesListActivity extends AppCompatActivity {
    private RecyclerView recyclerViewSpecialities;
    private SpecialitiesAdapter adapter;
    private EmployeeViewModel viewModel;
    private List<Speciality> specialities;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialities_list);
        recyclerViewSpecialities=findViewById(R.id.recyclerViewSpecialities);
        recyclerViewSpecialities.setLayoutManager(new LinearLayoutManager(this));
        adapter=new SpecialitiesAdapter();
        adapter.setSpecialities(new ArrayList<Speciality>());
        recyclerViewSpecialities.setAdapter(adapter);
        viewModel= ViewModelProviders.of(this).get(EmployeeViewModel.class);
        viewModel.getEmployees().observe(this, new Observer<List<Employee>>() {
            @Override
            public void onChanged(List<Employee> employees) {
                specialities=EmployeeViewModel.getSpecialities(employees);
                adapter.setSpecialities(specialities);
            }
        });
        viewModel.getErrors().observe(this, new Observer<Throwable>() {
            @Override
            public void onChanged(Throwable throwable) {
                if(throwable!=null) {
                    Toast.makeText(SpecialitiesListActivity.this, "Ошибка загрузки данных", Toast.LENGTH_SHORT).show();
                    viewModel.clearErrors();
                }
            }
        });
        viewModel.loadData();
        adapter.setListener(new SpecialitiesAdapter.OnSpecialityClickListener() {
            @Override
            public void onSpecialityClick(int position) {
                    Intent intent=new Intent(SpecialitiesListActivity.this,EmployeeListActivity.class);
                    intent.putExtra("specialityId", specialities.get(position).getSpecialtyId());
                    startActivity(intent);
            }
        });
    }
}
