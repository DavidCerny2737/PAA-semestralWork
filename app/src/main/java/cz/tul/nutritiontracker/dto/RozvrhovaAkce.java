package cz.tul.nutritiontracker.dto;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class RozvrhovaAkce {

    private String name;
    private String department;
    private String subject;

    private static final String TAG = RozvrhovaAkce.class.getSimpleName();

    public RozvrhovaAkce(JSONObject object) {
        try {
            this.name= object.getString("nazev");
            this.department = object.getString("katedra");
            this.subject = object.getString("predmet");
        }catch(final JSONException e){
            Log.e(TAG, "Json parsing error: " + e.getMessage());
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
