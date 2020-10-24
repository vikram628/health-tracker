package com.healthtracker.empoweringtechnologies.healthtracker;
import java.util.Date;

public class WeightData {
    private String _weight;
    private String  _height;
    private String _waist;
    private String _weightdate;


    public WeightData(String weight, String height, String waist, String weightdate ){
        this._weight = weight;
        this._height = height;
        this._waist = waist;
        this._weightdate = weightdate;
    }

    public String  get_weight() {
        return _weight;
    }

    public String get_height() {
        return _height;
    }

    public String get_waist() {
        return _waist;
    }

    public String get_weightdate() {
        return _weightdate;
    }

}


