package com.oieskova.employers2.convertors;

import com.google.gson.Gson;
import com.oieskova.employers2.data.Speciality;

import java.util.ArrayList;
import java.util.List;

import androidx.room.TypeConverter;

public class Convertor {
    @TypeConverter
    public String listOfSpecialitiesToString(List<Speciality> specialities){
        return new Gson().toJson(specialities);
    }
    @TypeConverter
    public List<Speciality> stringToListOfSpecialities(String specialitiesAsString){
        Gson gson=new Gson();
        ArrayList objects=gson.fromJson(specialitiesAsString, ArrayList.class);
        ArrayList<Speciality> specialities=new ArrayList<>();
        for(Object object:objects){
            Speciality speciality=gson.fromJson(object.toString(),Speciality.class);
            specialities.add(speciality);
        }
        return specialities;
    }

}
