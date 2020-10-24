package com.healthtracker.empoweringtechnologies.healthtracker;
import java.util.Date;

public class GlucoseData {
    private String _glucose;
    private String _glucosedate;

    public GlucoseData(String glucose, String glucosedate ){
        this._glucose = glucose;
        this._glucosedate = glucosedate;
    }

    public String  get_glucose() {
        return _glucose;
    }

    public String get_glucosedate() {
        return _glucosedate;
    }

}


