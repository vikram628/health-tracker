package com.healthtracker.empoweringtechnologies.healthtracker;
import java.util.Date;

public class ThyroidData {
    private String _tsh;
    private String  _freet4;
    private String _freet3;
    private String _thyroiddate;

    public ThyroidData(String tsh, String freet4, String freet3,  String thyroiddate ){
        this._tsh = tsh;
        this._freet4 = freet4;
        this._freet3 = freet3;
        this._thyroiddate = thyroiddate;
    }

    public String  get_tsh() {
        return _tsh;
    }

    public String get_freet4() {
        return _freet4;
    }

    public String get_freet3() {
        return _freet3;
    }

    public String get_thyroiddate() {
        return _thyroiddate;
    }

}


