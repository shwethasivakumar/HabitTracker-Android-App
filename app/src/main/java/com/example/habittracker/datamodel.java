package com.example.habittracker;

public class datamodel {
    private String Habitname;
    private String Habitdescription;

    public datamodel(){

    }

    public datamodel(String habitname, String habitdescription) {
        Habitname = habitname;
        Habitdescription = habitdescription;
    }

    public String getHabitname() {
        return Habitname;
    }

    public void setHabitname(String habitname) {
        Habitname = habitname;
    }

    public String getHabitdescription() {
        return Habitdescription;
    }

    public void setHabitdescription(String habitdescription) {
        Habitdescription = habitdescription;
    }
}
