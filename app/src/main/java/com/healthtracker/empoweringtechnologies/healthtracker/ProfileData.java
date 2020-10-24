package com.healthtracker.empoweringtechnologies.healthtracker;

import java.util.Date;

public class ProfileData {
    private String _profilename;
    private String _gender;
    private String  _dob;
    private String _profilepicturepath;


    public ProfileData(String profilename, String gender, String dob, String profilepicturepath){
        this._profilename = profilename;
        this._gender = gender;
        this._dob = dob;
        this._profilepicturepath = profilepicturepath;
    }

    public String  get_profilename() {
        return _profilename;
    }

    public String get_gender() {
        return _gender;
    }

    public String get_dob() {
        return _dob;
    }

    public String get_profilepicturepath() {
        return _profilepicturepath;
    }
}



