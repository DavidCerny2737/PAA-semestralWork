package cz.tul.nutritiontracker.dto;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class Ucitel {

    private String name;;
    private String surname;
    private int teacherId;

    private static final String TAG = Ucitel.class.getSimpleName();
    public Ucitel(JSONObject object) {
        try {
            this.name = object.getString("jmeno");
            this.surname = object.getString("prijmeni");
            this.teacherId = object.getInt("ucitIdno");
        }catch(final JSONException e){
            Log.e(TAG, "Json parsing error: " + e.getMessage());
        }

    }

    @Override
    public String toString() {
        return "Ucitel{" +
                "jmeno='" + name + '\'' +
                ", prijmeni='" + surname + '\'' +
                ", ucitIdno=" + teacherId +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public String getFullName(){
        return name + " " + surname;
    }
}
