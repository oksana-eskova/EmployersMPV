package com.oieskova.employers2.data;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.annotation.Nullable;

public class Speciality {
        @SerializedName("specialty_id")
        @Expose
        private int specialtyId;
        @SerializedName("name")
        @Expose
        private String name;

        public int getSpecialtyId() {
            return specialtyId;
        }

        public void setSpecialtyId(int specialtyId) {
            this.specialtyId = specialtyId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    @Override
    public int hashCode() {
        return specialtyId;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
            if(!(obj instanceof Speciality)){
                return false;
            }
            Speciality speciality=(Speciality) obj;
            if (specialtyId==speciality.getSpecialtyId()){
                return true;
            }else return false;
    }
}

