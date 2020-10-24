package com.healthtracker.empoweringtechnologies.healthtracker;
import java.util.Date;


public class LipidData {
    private String _totalcholesterol;
    private String  _ldl;
    private String _hdl;
    private String _triglycerides;
    private String _lipiddate;

    public LipidData(String totalcholesterol, String ldl, String hdl, String triglycerides, String lipiddate){
        this._totalcholesterol = totalcholesterol;
        this._ldl = ldl;
        this._hdl = hdl;
        this._triglycerides = triglycerides;
        this._lipiddate = lipiddate;
    }

    public String  get_totalcholesterol() {
        return _totalcholesterol;
    }

    public String get_ldl() {
        return _ldl;
    }

    public String get_hdl() {
        return _hdl;
    }

    public String get_triglycerides() {
        return _triglycerides;
    }

    public String get_lipiddate() {
        return _lipiddate;
    }
}


