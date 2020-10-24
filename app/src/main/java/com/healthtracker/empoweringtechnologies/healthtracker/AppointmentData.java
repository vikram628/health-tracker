package com.healthtracker.empoweringtechnologies.healthtracker;


public class AppointmentData {

    private String _appointmentdate;
    private String _checkup;
    private String _hospital;

    public AppointmentData(String appointmentdate, String checkup, String hospital) {
        this._appointmentdate = appointmentdate;
        this._checkup = checkup;
        this._hospital = hospital;
    }

    public String get_appointmentdate() {
        return _appointmentdate;
    }

    public String get_checkup() {
        return _checkup;
    }

    public String get_hospital() {
        return _hospital;
    }

}
