package com.oieskova.employers2.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.oieskova.employers2.R;
import com.oieskova.employers2.data.Employee;
import com.oieskova.employers2.data.EmployeeViewModel;
import com.oieskova.employers2.data.Speciality;
import com.oieskova.employers2.utils.DataUtils;

import java.util.List;

public class EmployeeDetailActivity extends AppCompatActivity {
    private int employeeId;
    private TextView textViewName;
    private TextView textViewLastName;
    private TextView textViewBirthday;
    private TextView textViewAge;
    private TextView textViewSpecialitiesEmployer;
    private EmployeeViewModel viewModel;
    private LiveData<Employee> employee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_detail);
        Intent intent=getIntent();
        employeeId=intent.getIntExtra("employeeId",0);

        textViewName=findViewById(R.id.textViewName);
        textViewLastName=findViewById(R.id.textViewLastName);
        textViewBirthday=findViewById(R.id.textViewBirthday);
        textViewAge=findViewById(R.id.textViewAge);
        textViewSpecialitiesEmployer=findViewById(R.id.textViewSpecialitiesEmployer);

        viewModel= ViewModelProviders.of(this).get(EmployeeViewModel.class);
        employee=viewModel.getEmployeeById(employeeId);
        employee.observe(this, new Observer<Employee>() {
            @Override
            public void onChanged(Employee employee) {
                textViewName.setText(employee.getFName());
                textViewLastName.setText(employee.getLName());
                String birthday=employee.getBirthday();
                if((birthday!=null)&&(!birthday.isEmpty())&&!birthday.equals("null")) {
                    textViewBirthday.setText(birthday);
                }else{
                    textViewBirthday.setText(" ");
                }
                int age= DataUtils.ageOfEmployee(employee.getBirthday());
                if(age!=0) {
                    textViewAge.setText(""+age);
                }else{
                    textViewAge.setText(" ");
                }
                List<Speciality> specialities=employee.getSpecialities();
                StringBuilder specNames=new StringBuilder();
                for(Speciality speciality:specialities){
                    specNames.append(speciality.getName()).append("\n");
                }
                textViewSpecialitiesEmployer.setText(specNames.toString());

            }
        });



    }
}
