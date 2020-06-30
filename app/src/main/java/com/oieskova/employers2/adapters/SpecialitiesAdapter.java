package com.oieskova.employers2.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oieskova.employers2.R;
import com.oieskova.employers2.data.Speciality;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SpecialitiesAdapter extends RecyclerView.Adapter<SpecialitiesAdapter.SpecialitiesViewHolder> {
    private List<Speciality> specialities;
    private OnSpecialityClickListener listener;

    public interface OnSpecialityClickListener{
        void onSpecialityClick(int position);
    }

    public void setListener(OnSpecialityClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public SpecialitiesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.speciality_item,parent,false);
        return new SpecialitiesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpecialitiesViewHolder holder, int position) {
        Speciality speciality=specialities.get(position);
        holder.textViewIdSpeciality.setText(""+speciality.getSpecialtyId());
        holder.textViewNameSpeciality.setText(speciality.getName());
    }

    @Override
    public int getItemCount() {
        return specialities.size();
    }

    public class SpecialitiesViewHolder extends RecyclerView.ViewHolder{
        private TextView textViewIdSpeciality;
        private TextView textViewNameSpeciality;

        public SpecialitiesViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewIdSpeciality=itemView.findViewById(R.id.textViewIdSpeciality);
            textViewNameSpeciality=itemView.findViewById(R.id.textViewNameSpeciality);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        listener.onSpecialityClick(getAdapterPosition());
                    }
                }
            });
        }
    }

    public List<Speciality> getSpecialities() {
        return specialities;
    }

    public void setSpecialities(List<Speciality> specialities) {
        this.specialities = specialities;
        notifyDataSetChanged();
    }
}
