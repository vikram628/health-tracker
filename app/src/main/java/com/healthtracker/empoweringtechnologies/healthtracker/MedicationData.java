package com.healthtracker.empoweringtechnologies.healthtracker;


public class MedicationData {
    private String _medication;
    private String  _dosage;
    //private int _frequency;
    private String _time1;
    private String _time2;
    private String _time3;
    private String _time4;

    public MedicationData(String medication, String dosage,  String time1, String time2,String time3,String time4){
        this._medication = medication;
        this._dosage = dosage;
        //this._frequency = frequency;
        this._time1 = time1;
        this._time2 = time2;
        this._time3 = time3;
        this._time4 = time4;
    }

    public String get_medication() {
        return _medication;
    }

    public String get_dosage() {
        return _dosage;
    }


    public String get_time1() {
        return _time1;
    }

    public String get_time2() {
        return _time2;
    }

    public String get_time3() {
        return _time3;
    }

    public String get_time4() {
        return _time4;
    }

}


