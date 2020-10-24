package com.healthtracker.empoweringtechnologies.healthtracker;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
//import android.icu.text.DateFormat;
//import android.icu.text.SimpleDateFormat;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.Toast;

import java.util.Date;

public class MyDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 5;
    private static final String DATABASE_NAME = "HealthData.db";
    public static final String TABLE_BPDATA = "BPData";
    public static final String COLUMN_ROWID = "_id";
    public static final String COLUMN_SYSTOLIC = "Systolic";
    public static final String COLUMN_DIASTOLIC = "Diastolic";
    public static final String COLUMN_HEARTRATE = "Heartrate";
    public static final String COLUMN_NOTES = "Notes";
    public static final String COLUMN_BPDATE = "BPDate";

    /* Medications */
    public static final String TABLE_MEDICATION = "MedicationsData";
    public static final String COLUMN_MEDICATION = "Medication";
    public static final String COLUMN_DOSAGE = "Dosage";
    //public static final String COLUMN_FREQUENCY = "Frequency";
    public static final String COLUMN_TIME1 = "Time1";
    public static final String COLUMN_TIME2 = "Time2";
    public static final String COLUMN_TIME3 = "Time3";
    public static final String COLUMN_TIME4 = "Time4";

    public static final String TABLE_LIPID = "LipidInfo";
    public static final String COLUMN_TOTALCHOLESTEROL = "TotalCholesterol";
    public static final String COLUMN_LDL = "LDL";
    public static final String COLUMN_HDL = "HDL";
    public static final String COLUMN_Triglycerides = "Triglycerides";
    public static final String COLUMN_LIPIDDATE = "LipidDate";

    public static final String TABLE_HEALTHGOAL = "HealthGoalsInfo";
    public static final String COLUMN_GOAL = "Goal";
    public static final String COLUMN_TARGETDATE = "TargetDate";

    public static final String TABLE_APPOINTMENTS = "AppointmentInfo";
    public static final String COLUMN_APPOINTMENTDATE = "AppointmentDate";
    public static final String COLUMN_CHECKUP = "Checkup";
    public static final String COLUMN_HOSPITAL = "Hospital";

    /* Appointments */
    public static final String TABLE_WEIGHT = "Weight";
    public static final String COLUMN_WEIGHT = "Weight";
    public static final String COLUMN_HEIGHT = "Height";
    public static final String COLUMN_WAIST = "Waist";
    public static final String COLUMN_WEIGHTDATE = "WeightDate";

    public static final String TABLE_KIDNEYFUNCTION = "KidneyFunction";
    public static final String COLUMN_GFR = "GFR";
    public static final String COLUMN_CREATININE = "CREATININE";
    public static final String COLUMN_KIDNEYFUNCTIONDATE = "KidneyFunctionDate";

    public static final String TABLE_PROFILE = "Profile";
    public static final String COLUMN_NAME = "ProfileName";
    public static final String COLUMN_GENDER = "Gender";
    public static final String COLUMN_DOB = "DOB";
    public static final String COLUMN_PICTUREPATH = "PicturePath";

    public static final String TABLE_THYROID = "Thyroid";
    public static final String COLUMN_TSH = "TSH";
    public static final String COLUMN_FREET4 = "FreeT4";
    public static final String COLUMN_FREET3 = "FreeT3";
    public static final String COLUMN_THYROIDTESTDATE = "ThyroidDate";

    public static final String TABLE_GLUCOSEDATA = "GlucoseData";
    public static final String COLUMN_GLUCOSE = "Glucose";
    public static final String COLUMN_GLUCOSETESTDATE = "GlucoseDate";


    public MyDBHandler(Context context, String
            name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String table_query = " CREATE TABLE " + TABLE_BPDATA + "(" +
                COLUMN_ROWID + " integer PRIMARY KEY autoincrement," +
                COLUMN_SYSTOLIC + " INT, " +
                COLUMN_DIASTOLIC + " INT, " +
                COLUMN_HEARTRATE + " INT, " +
                COLUMN_NOTES + " TEXT, " +
                COLUMN_BPDATE + " DATE " +
                ");";
        db.execSQL(table_query);


        String Medication_table_query = " CREATE TABLE " + TABLE_MEDICATION + "(" +
                COLUMN_ROWID + " integer PRIMARY KEY autoincrement," +
                COLUMN_MEDICATION + " TEXT, " +
                COLUMN_DOSAGE + " TEXT, " +
               // COLUMN_FREQUENCY + " INT, " +
                COLUMN_TIME1 + " TEXT, " +
                COLUMN_TIME2 + " TEXT, " +
                COLUMN_TIME3 + " TEXT," +
                COLUMN_TIME4 + " TEXT " +
                ");";
        db.execSQL(Medication_table_query);


        String Lipid_table_query = " CREATE TABLE " + TABLE_LIPID + "(" +
                COLUMN_ROWID + " integer PRIMARY KEY autoincrement," +
                COLUMN_TOTALCHOLESTEROL + " TEXT, " +
                COLUMN_LDL + " TEXT, " +
                COLUMN_HDL + " TEXT, " +
                COLUMN_Triglycerides + " TEXT, " +
                COLUMN_LIPIDDATE + " TEXT " +
                ");";
        db.execSQL(Lipid_table_query);

        String Weight_table_query = " CREATE TABLE " + TABLE_WEIGHT + "(" +
                COLUMN_ROWID + " integer PRIMARY KEY autoincrement," +
                COLUMN_WEIGHT + " TEXT, " +
                COLUMN_HEIGHT + " TEXT, " +
                COLUMN_WAIST + " TEXT, " +
                COLUMN_WEIGHTDATE + " TEXT " +
                ");";
        db.execSQL(Weight_table_query);

        String Appointment_table_query = " CREATE TABLE " + TABLE_APPOINTMENTS + "(" +
                COLUMN_ROWID + " integer PRIMARY KEY autoincrement," +
                COLUMN_APPOINTMENTDATE + " TEXT, " +
                COLUMN_CHECKUP + " TEXT, " +
                COLUMN_HOSPITAL + " TEXT " +
                ");";
        db.execSQL(Appointment_table_query);


        String Profile_table_query = " CREATE TABLE " + TABLE_PROFILE + "(" +
                COLUMN_ROWID + " integer PRIMARY KEY autoincrement," +
                COLUMN_NAME + " TEXT, " +
                COLUMN_GENDER + " TEXT, " +
                COLUMN_DOB + " TEXT, " +
                COLUMN_PICTUREPATH + " TEXT " +
                ");";
        db.execSQL(Profile_table_query);

        String HealthGoal_table_query = " CREATE TABLE " + TABLE_HEALTHGOAL + "(" +
                COLUMN_ROWID + " integer PRIMARY KEY autoincrement," +
                COLUMN_GOAL + " TEXT, " +
                COLUMN_TARGETDATE + " TEXT " +
                ");";
        db.execSQL(HealthGoal_table_query);

        String KidneyFunction_table_query = " CREATE TABLE " + TABLE_KIDNEYFUNCTION + "(" +
                COLUMN_ROWID + " integer PRIMARY KEY autoincrement," +
                COLUMN_GFR + " TEXT, " +
                COLUMN_CREATININE + " TEXT, " +
                COLUMN_KIDNEYFUNCTIONDATE + " TEXT " +
                ");";
        db.execSQL(KidneyFunction_table_query);

        String ThyroidFunction_table_query = " CREATE TABLE " + TABLE_THYROID + "(" +
                COLUMN_ROWID + " integer PRIMARY KEY autoincrement," +
                COLUMN_TSH + " TEXT, " +
                COLUMN_FREET4 + " TEXT, " +
                COLUMN_FREET3 + " TEXT, " +
                COLUMN_THYROIDTESTDATE + " TEXT " +
                ");";
        db.execSQL(ThyroidFunction_table_query);

        String GlucoseFunction_table_query = " CREATE TABLE " + TABLE_GLUCOSEDATA + "(" +
                COLUMN_ROWID + " integer PRIMARY KEY autoincrement," +
                COLUMN_GLUCOSE+ " TEXT, " +
                COLUMN_GLUCOSETESTDATE + " TEXT " +
                ");";
        db.execSQL(GlucoseFunction_table_query);
    }




    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BPDATA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LIPID);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WEIGHT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_APPOINTMENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HEALTHGOAL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KIDNEYFUNCTION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_THYROID);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GLUCOSEDATA);
        onCreate(db);
    }

    /* SQLite database call to save activity data */
    public void SaveBPData(BPData bpdata) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_SYSTOLIC, bpdata.get_systolic());
        values.put(COLUMN_DIASTOLIC, bpdata.get_diastolic());
        values.put(COLUMN_HEARTRATE, bpdata.get_heartrate());
        values.put(COLUMN_NOTES, bpdata.get_notes());
        values.put(COLUMN_BPDATE, bpdata.get_bpdate().toString());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_BPDATA, null, values);
        db.close();
    }

    /* SQLite database call to save activity data */
    public void SaveThyroidData(ThyroidData thyroiddata) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_TSH, thyroiddata.get_tsh());
        values.put(COLUMN_FREET4, thyroiddata.get_freet4());
        values.put(COLUMN_FREET3, thyroiddata.get_freet3());
        values.put(COLUMN_THYROIDTESTDATE, thyroiddata.get_thyroiddate().toString());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_THYROID, null, values);
        db.close();
    }

    /* SQLite database call to save activity data */
    public void SaveGlucoseData(GlucoseData glucosedata) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_GLUCOSE, glucosedata.get_glucose());
        values.put(COLUMN_GLUCOSETESTDATE, glucosedata.get_glucosedate().toString());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_GLUCOSEDATA, null, values);
        db.close();
    }

    public void SaveKidneyFunctionData(KidneyFunctionData kidneyfunctiondata) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_GFR, kidneyfunctiondata.get_gfr());
        values.put(COLUMN_CREATININE, kidneyfunctiondata.get_creatinine());
        values.put(COLUMN_KIDNEYFUNCTIONDATE, kidneyfunctiondata.get_kidneyfunctiondate().toString());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_KIDNEYFUNCTION, null, values);
        db.close();
    }


    public void SaveHealthGoalData(HealthGoalData helthgoaldata) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_GOAL, helthgoaldata.get_thealthgoal());
        values.put(COLUMN_TARGETDATE, helthgoaldata.get_targetdate().toString());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_HEALTHGOAL, null, values);
        db.close();
    }



    public void SaveProfile(ProfileData profiledata) {
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, profiledata.get_profilename());
        values.put(COLUMN_GENDER, profiledata.get_gender());
        values.put(COLUMN_DOB, profiledata.get_dob());
        values.put(COLUMN_PICTUREPATH, profiledata.get_profilepicturepath());
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM  " + TABLE_PROFILE);
        db.insert(TABLE_PROFILE, null, values);
        db.close();
    }

    public void SaveLipidData(LipidData lipiddata) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_TOTALCHOLESTEROL, lipiddata.get_totalcholesterol());
        values.put(COLUMN_LDL, lipiddata.get_ldl());
        values.put(COLUMN_HDL, lipiddata.get_hdl());
        values.put(COLUMN_Triglycerides, lipiddata.get_triglycerides());
        values.put(COLUMN_LIPIDDATE, lipiddata.get_lipiddate());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_LIPID, null, values);
        db.close();
    }

    public void SaveAppointmentData(AppointmentData appointmentData) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_APPOINTMENTDATE, appointmentData.get_appointmentdate());
        values.put(COLUMN_CHECKUP, appointmentData.get_checkup());
        values.put(COLUMN_HOSPITAL, appointmentData.get_hospital());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_APPOINTMENTS, null, values);
        db.close();
    }


    /* SQLite database call to save activity data */
    public void SaveWeightData(WeightData weightdata) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_WEIGHT, weightdata.get_weight());
        values.put(COLUMN_HEIGHT, weightdata.get_height());
        values.put(COLUMN_WAIST, weightdata.get_waist());
        values.put(COLUMN_WEIGHTDATE, weightdata.get_weightdate());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_WEIGHT, null, values);
        db.close();
    }


    /* SQLite database call to save activity data */
    public void SaveMedications(MedicationData medicationdata) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_MEDICATION, medicationdata.get_medication());
        values.put(COLUMN_DOSAGE, medicationdata.get_dosage());
        //values.put(COLUMN_FREQUENCY, medicationdata.get_frequency());
        values.put(COLUMN_TIME1, medicationdata.get_time1());
        values.put(COLUMN_TIME2, medicationdata.get_time2());
        values.put(COLUMN_TIME3, medicationdata.get_time3());
        values.put(COLUMN_TIME4, medicationdata.get_time4());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_MEDICATION, null, values);
        db.close();
    }

    /* SQLite database call to update activity data.  */
    public void UpdateMedications(MedicationData medicationdata, int idVal) {
        String update_query = "UPDATE " + TABLE_MEDICATION + " SET  " + COLUMN_MEDICATION + " = '" + medicationdata.get_medication() + "'," +
                COLUMN_DOSAGE + " = '" + medicationdata.get_dosage() + "'," +
                //COLUMN_FREQUENCY + " = '" + medicationdata.get_frequency() + "'," +
                COLUMN_TIME1 + " = '" + medicationdata.get_time1() + "'," +
                COLUMN_TIME2 + " = '" + medicationdata.get_time2() + "'," +
                COLUMN_TIME3 + " = '" + medicationdata.get_time3() + "'," +
                COLUMN_TIME4 + " = '" + medicationdata.get_time4() + "'" +
                " WHERE " + COLUMN_ROWID + " =" + idVal;
        try {
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL(update_query);
            db.close();
        } catch (Exception ex) {
            Log.e("Error", "Error" + ex);

        }
    }

    public void UpdateThyroidData(ThyroidData thyroiddata, int idVal) {
        String update_query = "UPDATE " + TABLE_THYROID + " SET  " + COLUMN_TSH + " = '" + thyroiddata.get_tsh() + "'," +
                COLUMN_FREET4 + " = '" + thyroiddata.get_freet4() + "'," +
                COLUMN_FREET3 + " = '" + thyroiddata.get_freet3() + "'," +
                COLUMN_THYROIDTESTDATE + " = '" + thyroiddata.get_thyroiddate() + "'" +
                " WHERE " + COLUMN_ROWID + " =" + idVal;
        try {
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL(update_query);
            db.close();
        } catch (Exception ex) {
            Log.e("Error", "Error" + ex);

        }
    }

    public void UpdateGlucoseData(GlucoseData glucosedata, int idVal) {
        String update_query = "UPDATE " + TABLE_GLUCOSEDATA + " SET  " + COLUMN_GLUCOSE + " = '" + glucosedata.get_glucose() + "'," +
                COLUMN_TIME4 + " = '" + glucosedata.get_glucosedate() + "'" +
                " WHERE " + COLUMN_ROWID + " =" + idVal;
        try {
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL(update_query);
            db.close();
        } catch (Exception ex) {
            Log.e("Error", "Error" + ex);

        }
    }

    public void UpdateProfilePicturePath(String PicturePath) {
        String update_query = "UPDATE " + TABLE_PROFILE + " SET  " + COLUMN_PICTUREPATH + " = '" + PicturePath + "'";
        try {
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL(update_query);
            db.close();
        } catch (Exception ex) {
            Log.e("Error", "Error" + ex);

        }
    }


    public void UpdateKidneyFunction(KidneyFunctionData kidneyfunctiondata, int idVal) {
        String update_query = "UPDATE " + TABLE_KIDNEYFUNCTION+ " SET  " + COLUMN_GFR + " = '" + kidneyfunctiondata.get_gfr() + "'," +
                COLUMN_CREATININE + " = '" + kidneyfunctiondata.get_creatinine() + "'," +
                COLUMN_KIDNEYFUNCTIONDATE + " = '" + kidneyfunctiondata.get_kidneyfunctiondate() + "'" +
                " WHERE " + COLUMN_ROWID + " =" + idVal;
        try {
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL(update_query);
            db.close();
        } catch (Exception ex) {
            Log.e("Error", "Error" + ex);

        }
    }

    public void UpdateAppointments(AppointmentData appointmentData, int idVal) {
        String update_query = "UPDATE " + TABLE_APPOINTMENTS + " SET  " + COLUMN_APPOINTMENTDATE + " = '" + appointmentData.get_appointmentdate() + "'," +
                COLUMN_CHECKUP+ " = '" + appointmentData.get_checkup() + "'," +
                COLUMN_HOSPITAL+ " = '" + appointmentData.get_hospital() + "'" +
                     " WHERE " + COLUMN_ROWID + " =" + idVal;
        try {
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL(update_query);
            db.close();
        } catch (Exception ex) {
            Log.e("Error", "Error" + ex);

        }
    }

    /* SQLite database call to update activity data.  */
    public void UpdateBPData(BPData bpdata, int idVal) {
        String update_query = "UPDATE " + TABLE_BPDATA + " SET  " + COLUMN_SYSTOLIC + " = '" + bpdata.get_systolic() + "'," +
                COLUMN_DIASTOLIC + " = '" + bpdata.get_diastolic() + "'," +
                //COLUMN_FREQUENCY + " = '" + medicationdata.get_frequency() + "'," +
                COLUMN_HEARTRATE + " = '" + bpdata.get_heartrate() + "'," +
                COLUMN_NOTES + " = '" + bpdata.get_notes() + "'," +
                COLUMN_BPDATE + " = '" + bpdata.get_bpdate() + "'" +
                " WHERE " + COLUMN_ROWID + " =" + idVal;
        try {
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL(update_query);
            db.close();
        } catch (Exception ex) {
            Log.e("Error", "Error" + ex);

        }
    }

    public void UpdateHealthGoalData(HealthGoalData healthgoaldata, int idVal) {
        String update_query = "UPDATE " + TABLE_HEALTHGOAL + " SET  " + COLUMN_GOAL + " = '" + healthgoaldata.get_thealthgoal() + "'," +
                COLUMN_TARGETDATE + " = '" + healthgoaldata.get_targetdate() + "'" +
                " WHERE " + COLUMN_ROWID + " =" + idVal;
        try {
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL(update_query);
            db.close();
        } catch (Exception ex) {
            Log.e("Error", "Error" + ex);

        }
    }

    /* SQLite database call to update activity data.  */
    public void UpdateWeightData(WeightData weightdata, int idVal) {
        String update_query = "UPDATE " + TABLE_WEIGHT + " SET  " + COLUMN_WEIGHT + " = '" + weightdata.get_weight() + "'," +
                COLUMN_HEIGHT + " = '" + weightdata.get_height() + "'," +
                COLUMN_WAIST + " = '" + weightdata.get_waist() + "'," +
                COLUMN_WEIGHTDATE + " = '" + weightdata.get_weightdate() + "'" +
                " WHERE " + COLUMN_ROWID + " =" + idVal;
        try {
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL(update_query);
            db.close();
        } catch (Exception ex) {
            Log.e("Error", "Error" + ex);

        }
    }

    public void UpdateLipidData(LipidData lipiddata, int idVal) {
        String update_query = "UPDATE " + TABLE_LIPID + " SET  " + COLUMN_TOTALCHOLESTEROL + " = '" + lipiddata.get_totalcholesterol() + "'," +
                COLUMN_LDL + " = '" + lipiddata.get_ldl() + "'," +
                COLUMN_HDL + " = '" + lipiddata.get_hdl() + "'," +
                COLUMN_Triglycerides + " = '" + lipiddata.get_triglycerides() + "'," +
                COLUMN_LIPIDDATE + " = '" + lipiddata.get_lipiddate() + "'" +
                " WHERE " + COLUMN_ROWID + " =" + idVal;
        try {
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL(update_query);
            db.close();
        } catch (Exception ex) {
            Log.e("Error", "Error" + ex);

        }
    }

    /* SQLite database call to save activity data
    public void SaveWeight(Weight weight) {
        ContentValues values = new ContentValues();       values.put(COLUMN_WEIGHT, weight.get_weight());
        values.put(COLUMN_WEIGHTDATE, weight.get_date());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_WEIGHT, null, values);
        db.close();
    }


    public void UpdateWeight(Weight weight, int idVal) {
        String update_query = "UPDATE " + TABLE_WEIGHT + " SET  " + COLUMN_WEIGHT + " = '" + weight.get_weight() + "'," +
                COLUMN_WEIGHTDATE + " = '" + weight.get_date() + "'," +
                " WHERE " + COLUMN_ROWID + " =" + idVal;
        try {
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL(update_query);
            db.close();
        } catch (Exception ex) {
            Log.e("Error", "Error" + ex);

        }
    }
    */

    /* SQLite database call to get activity data for home page */
    public Cursor FetchMedicationInfo() {
        SQLiteDatabase db = getWritableDatabase();
        Cursor mCursor = db.query(TABLE_MEDICATION, new String[]{COLUMN_ROWID,
                        //COLUMN_MEDICATION, COLUMN_DOSAGE, COLUMN_FREQUENCY, COLUMN_TIME1,COLUMN_TIME2,COLUMN_TIME3,COLUMN_TIME4},
                        COLUMN_MEDICATION, COLUMN_DOSAGE, COLUMN_TIME1,COLUMN_TIME2,COLUMN_TIME3,COLUMN_TIME4},
                null, null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor FetchKidneyFunctionInfo(String limit) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor mCursor = db.query(TABLE_KIDNEYFUNCTION, new String[]{COLUMN_ROWID,
                        //COLUMN_MEDICATION, COLUMN_DOSAGE, COLUMN_FREQUENCY, COLUMN_TIME1,COLUMN_TIME2,COLUMN_TIME3,COLUMN_TIME4},
                        COLUMN_GFR, COLUMN_CREATININE, COLUMN_KIDNEYFUNCTIONDATE},
                null, null, null, null, COLUMN_KIDNEYFUNCTIONDATE + " DESC ",limit);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }


    public Cursor FetchThyroidFunctionInfo(String limit) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor mCursor = db.query(TABLE_THYROID, new String[]{COLUMN_ROWID,
                        COLUMN_TSH, COLUMN_FREET4, COLUMN_FREET3,COLUMN_THYROIDTESTDATE},
                null, null, null, null, COLUMN_THYROIDTESTDATE + " DESC ",limit);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor FetchGlucoseFunctionInfo(String limit) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor mCursor = db.query(TABLE_GLUCOSEDATA, new String[]{COLUMN_ROWID,
                        COLUMN_GLUCOSE, COLUMN_GLUCOSETESTDATE},
                null, null, null, null, COLUMN_GLUCOSETESTDATE + " DESC ",limit);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }


    /* SQLite database call to get activity data for home page */
    public Cursor FetchProfileData(String limit) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor mCursor = db.query(TABLE_PROFILE, new String[]{COLUMN_ROWID,
                        //COLUMN_MEDICATION, COLUMN_DOSAGE, COLUMN_FREQUENCY, COLUMN_TIME1,COLUMN_TIME2,COLUMN_TIME3,COLUMN_TIME4},
                        COLUMN_NAME, COLUMN_GENDER,COLUMN_DOB, COLUMN_PICTUREPATH},
                null, null, null, null, null,limit);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor FetchAppointmentInfo(String limit) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor mCursor = db.query(TABLE_APPOINTMENTS, new String[]{COLUMN_ROWID,
                         COLUMN_APPOINTMENTDATE, COLUMN_CHECKUP, COLUMN_HOSPITAL},
                null, null, null, null, COLUMN_APPOINTMENTDATE + " ASC ",limit);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }


    /* SQLite database call to get activity data for home page */
    public Cursor FetchLipidInfo(String limit) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor mCursor = db.query(TABLE_LIPID, new String[]{COLUMN_ROWID,
                        COLUMN_TOTALCHOLESTEROL, COLUMN_LDL, COLUMN_HDL, COLUMN_Triglycerides,COLUMN_LIPIDDATE},
                null, null, null, null, COLUMN_LIPIDDATE + " DESC ",limit);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor FetchHealthGoalInfo(String limit) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor mCursor = db.query(TABLE_HEALTHGOAL, new String[]{COLUMN_ROWID,
                        COLUMN_GOAL, COLUMN_TARGETDATE},
                null, null, null, null, COLUMN_TARGETDATE + " ASC ",limit);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    /* SQLite database call to get activity data for home page */
    public Cursor FetchWeightInfo(String limit) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor mCursor = db.query(TABLE_WEIGHT, new String[]{COLUMN_ROWID,
                        COLUMN_WEIGHT, COLUMN_HEIGHT, COLUMN_WAIST, COLUMN_WEIGHTDATE},
                null, null, null, null, COLUMN_WEIGHTDATE + " DESC ",limit);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    /* SQLite database call to get activity data for home page */
    public Cursor FetchBPInfo(String limit) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor mCursor = db.query(TABLE_BPDATA, new String[]{COLUMN_ROWID,
                        COLUMN_SYSTOLIC, COLUMN_DIASTOLIC, COLUMN_HEARTRATE, COLUMN_NOTES, COLUMN_BPDATE},
                null, null, null, null, COLUMN_BPDATE + " DESC ",limit);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    /* SQLite database call to get report data */
    /*
    public Cursor FetchReportData() {
        SQLiteDatabase db = getWritableDatabase();

        String report_query = "SELECT  ROWID AS _id, " + COLUMN_ORG + ", sum(" + COLUMN_HOURS + ") AS " + COLUMN_ORG_HOURS + " FROM " + TABLE_HOURS + " WHERE " + COLUMN_HOURS + "> 0 " + " GROUP BY " + COLUMN_ORG + " Order BY " + COLUMN_ORG_HOURS + " DESC";

        Cursor cursor = db.rawQuery(report_query, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
*/

    /* SQLite database call to get activity data for home page
    public Cursor FetchWeightData(String limit) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor mCursor = db.query(TABLE_WEIGHT, new String[]{COLUMN_ROWID,
                        COLUMN_WEIGHT,  COLUMN_WEIGHTDATE},
                null, null, null, null, COLUMN_WEIGHTDATE + " DESC ",limit);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
    */
    /* SQLite database call to delete an acitivty*/
    public void DeletAll() {
        String delete_query = "DELETE FROM " + TABLE_WEIGHT ;
        try {
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL(delete_query);
            db.close();
        } catch (Exception ex) {
            Log.e("Error", "Error" + ex);

        }
    }
    /* SQLite database call to delete an acitivty*/
    public void DeleteItem(int id) {
        String delete_query = "DELETE FROM " + TABLE_MEDICATION + " WHERE " + COLUMN_ROWID + " =" + id;
        try {
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL(delete_query);
            db.close();
        } catch (Exception ex) {
            Log.e("Error", "Error" + ex);

        }
    }

    /* SQLite database call to delete an acitivty*/
    public void DeleteBPItem(int id) {
        String delete_query = "DELETE FROM " + TABLE_BPDATA + " WHERE " + COLUMN_ROWID + " =" + id;
        try {
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL(delete_query);
            db.close();
        } catch (Exception ex) {
            Log.e("Error", "Error" + ex);

        }
    }

    /* SQLite database call to delete an acitivty*/
    public void DeleteWeightItem(int id) {
        String delete_query = "DELETE FROM " + TABLE_WEIGHT + " WHERE " + COLUMN_ROWID + " =" + id;
        try {
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL(delete_query);
            db.close();
        } catch (Exception ex) {
            Log.e("Error", "Error" + ex);

        }
    }

    /* SQLite database call to delete an acitivty*/
    public void DeleteLipidItem(int id) {
        String delete_query = "DELETE FROM " + TABLE_LIPID + " WHERE " + COLUMN_ROWID + " =" + id;
        try {
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL(delete_query);
            db.close();
        } catch (Exception ex) {
            Log.e("Error", "Error" + ex);

        }
    }

    public void DeleteKidneyFunctionItem(int id) {
        String delete_query = "DELETE FROM " + TABLE_KIDNEYFUNCTION + " WHERE " + COLUMN_ROWID + " =" + id;
        try {
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL(delete_query);
            db.close();
        } catch (Exception ex) {
            Log.e("Error", "Error" + ex);

        }
    }

    public void DeleteThyroidFunctionItem(int id) {
        String delete_query = "DELETE FROM " + TABLE_THYROID + " WHERE " + COLUMN_ROWID + " =" + id;
        try {
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL(delete_query);
            db.close();
        } catch (Exception ex) {
            Log.e("Error", "Error" + ex);

        }
    }

    public void DeleteGlucoseFunctionItem(int id) {
        String delete_query = "DELETE FROM " + TABLE_GLUCOSEDATA + " WHERE " + COLUMN_ROWID + " =" + id;
        try {
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL(delete_query);
            db.close();
        } catch (Exception ex) {
            Log.e("Error", "Error" + ex);

        }
    }

    public void DeleteAppointmentItem(int id) {
        String delete_query = "DELETE FROM " + TABLE_APPOINTMENTS+ " WHERE " + COLUMN_ROWID + " =" + id;
        try {
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL(delete_query);
            db.close();
        } catch (Exception ex) {
            Log.e("Error", "Error" + ex);

        }
    }


    public Date getMinDate(Cursor cursor, String colname)
    {
        Date minDate = null;
        DateFormat formatter = new SimpleDateFormat("MM /dd /yyyy");
        cursor.moveToFirst();
        try {

            minDate = (Date) formatter.parse(cursor.getString(cursor.getColumnIndex(colname)));
        }
        catch(Exception e)
        {

        }

        for (int i=0; i<cursor.getCount(); i++) {
            try {
                Date yi = (Date) formatter.parse(cursor.getString(cursor.getColumnIndex(colname)));
                if (minDate.compareTo(yi)>0) minDate = yi;
            }
            catch(Exception e) {
            }

            cursor.moveToNext();
        }

        return minDate;
    }



    public Date getMaxDate(Cursor cursor, String colname)
    {
        Date maxDate = null;
        DateFormat formatter = new SimpleDateFormat("MM /dd /yyyy");
        cursor.moveToFirst();
        try {

            maxDate = (Date) formatter.parse(cursor.getString(cursor.getColumnIndex(colname)));
        }
        catch(Exception e)
        {
        }

        for (int i=0; i<cursor.getCount(); i++) {
            try {
                Date yi = (Date) formatter.parse(cursor.getString(cursor.getColumnIndex(colname)));
                if (maxDate.compareTo(yi) <0) maxDate = yi;
            }
            catch(Exception e) {
            }

            cursor.moveToNext();
        }

        return maxDate;
    }
}
