package com.healthtracker.empoweringtechnologies.healthtracker;


public class HealthGoalData {
    private String _healthgoal;
    private String _targetdate;

    public HealthGoalData(String healthgoal,  String targetdate){
        this._healthgoal = healthgoal;
        this._targetdate = targetdate;
    }

    public String  get_thealthgoal() {
        return _healthgoal;
    }

    public String get_targetdate() {
        return _targetdate;
    }

}
