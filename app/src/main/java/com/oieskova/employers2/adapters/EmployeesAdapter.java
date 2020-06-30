package com.oieskova.employers2.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oieskova.employers2.R;
import com.oieskova.employers2.data.Employee;
import com.oieskova.employers2.utils.DataUtils;

import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EmployeesAdapter extends RecyclerView.Adapter<EmployeesAdapter.EmployeeViewHolder> {
    private List<Employee> employees;
    private OnEmployeeClickListener listener;

    public interface OnEmployeeClickListener{
        void onEmployeeClick(int position);
    }
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public void setListener(OnEmployeeClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.employer_item,parent,false);
        return new EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
            Employee employee=employees.get(position);
            holder.textViewEmployeeName.setText(employee.getFName()+" "+employee.getLName());
            int age= DataUtils.ageOfEmployee(employee.getBirthday());
            if(age!=0){
                holder.textViewEmployeeAge.setText("Возраст: "+age);
            }
    }

    @Override
    public int getItemCount() {
        return employees.size();
    }

     class EmployeeViewHolder extends RecyclerView.ViewHolder{
        private TextView textViewEmployeeName;
        private TextView textViewEmployeeAge;
        public EmployeeViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewEmployeeName=itemView.findViewById(R.id.textViewEmployeeName);
            textViewEmployeeAge=itemView.findViewById(R.id.textViewEmployeeAge);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        listener.onEmployeeClick(getAdapterPosition());
                    }
                }
            });
        }
    }

}
