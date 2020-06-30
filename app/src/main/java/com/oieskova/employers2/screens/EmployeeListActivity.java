package com.oieskova.employers2.screens;

import androidx.appcompat.app.AppCompatActivity;


import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.oieskova.employers2.R;
import com.oieskova.employers2.adapters.EmployeesAdapter;
import com.oieskova.employers2.data.Employee;
import com.oieskova.employers2.data.EmployeeViewModel;

import java.util.ArrayList;
import java.util.List;

public class EmployeeListActivity extends AppCompatActivity {
   private EmployeeViewModel viewModel;
   private int specialityId;
   private RecyclerView recyclerViewEmployees;
   private EmployeesAdapter adapter;
   private List<Employee> employeesForSpeciality;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_list);
        recyclerViewEmployees=findViewById(R.id.recyclerViewEmployees);
        Intent intent=getIntent();
        specialityId=intent.getIntExtra("specialityId",0);

        adapter=new EmployeesAdapter();
        adapter.setEmployees(new ArrayList<Employee>());
        recyclerViewEmployees.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewEmployees.setAdapter(adapter);

        viewModel= ViewModelProviders.of(this).get(EmployeeViewModel.class);
        viewModel.getEmployees().observe(this, new Observer<List<Employee>>() {
             @Override
             public void onChanged(List<Employee> employees) {
                 employeesForSpeciality=EmployeeViewModel.getEmployeesForSpeciality(employees,specialityId);
                 adapter.setEmployees(employeesForSpeciality);
             }
         });
         viewModel.getErrors().observe(this, new Observer<Throwable>() {
             @Override
             public void onChanged(Throwable throwable) {
                 Toast.makeText(EmployeeListActivity.this,"Error",Toast.LENGTH_SHORT).show();
             }
         });
         adapter.setListener(new EmployeesAdapter.OnEmployeeClickListener() {
             @Override
             public void onEmployeeClick(int position) {
                    Intent intent=new Intent(EmployeeListActivity.this,EmployeeDetailActivity.class);
                    intent.putExtra("employeeId", employeesForSpeciality.get(position).getId());
                    startActivity(intent);
             }
         });

    }


}
