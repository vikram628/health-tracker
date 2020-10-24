package com.healthtracker.empoweringtechnologies.healthtracker;

import java.util.Date;

public class KidneyFunctionData {
    private String _gfr;
    private String  _creatinine;
    private String _kidneyfunctiondate;

    public KidneyFunctionData(String gfr, String creatinine, String kidneyfunctiondate ){
        this._gfr = gfr;
        this._creatinine = creatinine;
        this._kidneyfunctiondate = kidneyfunctiondate;
    }

    public String  get_gfr() {
        return _gfr;
    }

    public String get_creatinine() {
        return _creatinine;
    }

    public String get_kidneyfunctiondate() {
        return _kidneyfunctiondate;
    }

}


