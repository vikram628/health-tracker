package com.healthtracker.empoweringtechnologies.healthtracker;

import java.util.Date;

public class BPData {
    private String _systolic;
    private String _diastolic;
    private String _heartrate;
    private String _notes;
    private String _bpdate;


    public BPData(String systolic, String diastolic, String heartrate, String notes, String bpdate) {
        this._systolic = systolic;
        this._diastolic = diastolic;
        this._heartrate = heartrate;
        this._notes = notes;
        this._bpdate = bpdate;
    }

    public String get_systolic() {
        return _systolic;
    }

    public String get_diastolic() {
        return _diastolic;
    }

    public String get_heartrate() {
        return _heartrate;
    }

    public String get_notes() {
        return _notes;
    }

    public String get_bpdate() {
        return _bpdate;
    }

}


