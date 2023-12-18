package com.example.miniproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.database.Cursor;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(@Nullable Context context) {
        super(context, "MiniProjectDB.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Users (" +
                "user_id Text PRIMARY KEY, " +
                "password Text)");
        db.execSQL("CREATE TABLE Patients (" +
                "user_id Text, " +
                "patient_id Text, " +
                "name Text, " +
                "age Text, " +
                "gender Text, "+
                "address Text,"+
                "phone_no Text," +
                "PRIMARY KEY (user_id,patient_id)," +
                "FOREIGN KEY(user_id) REFERENCES Users(user_id) ON DELETE CASCADE)");
        db.execSQL("CREATE TABLE Doctors (" +
                "doctor_id Text PRIMARY KEY, " +
                "name Text, " +
                "department Text, " +
                "designation Text, "+
                "gender Text, "+
                "phone_no Text )" );
        db.execSQL("CREATE TABLE Doctor_Slots (" +
                "doctor_id Text, " +
                "slot_id Text, " +
                "date Text, " +
                "avail_slots Text, " +
                "PRIMARY KEY (doctor_id,date,slot_id,avail_slots)," +
                "FOREIGN KEY(doctor_id) REFERENCES Doctors(doctor_id) ON DELETE CASCADE)");
        db.execSQL("CREATE TABLE Appointments (" +
                "patient_id Text, " +
                "patient_name Text, " +
                "doctor_id Text, " +
                "doctor_name Text, " +
                "date Text, " +
                "time Text, " +
                "symptoms Text, " +
                "PRIMARY KEY (patient_id, doctor_id,date, time)," +
                "FOREIGN KEY(patient_id) REFERENCES Patients(patient_id) ON DELETE CASCADE)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
//        db.execSQL("DROP TABLE IF Exists Users");
//        db.execSQL("DROP TABLE IF EXISTS Patients");
//        db.execSQL("DROP TABLE IF EXISTS Doctors");
//        db.execSQL("DROP TABLE IF EXISTS Doctor_Slots");
//        db.execSQL("DROP TABLE IF EXISTS Appointments");
    }

    //Inserting user details - Signup
    public Boolean insert_user(String u_id, String password, String name, String age, String gender, String address, String ph_no) {
        SQLiteDatabase db = this.getWritableDatabase();

        //Check if user ID already exists
        Cursor record_user = db.rawQuery("SELECT * FROM Users WHERE user_id = ?", new
                String[]{u_id});
        if (record_user.getCount() > 0)
            return false;
        Long result;
        //Insert record into Users
        ContentValues content_value_user = new ContentValues();
        content_value_user.put("user_id", u_id);
        content_value_user.put("password", password);
        result = db.insert("Users", null, content_value_user);
        if(result == -1) return false;

        //Get last patient id
        Cursor record_patient = db.rawQuery("SELECT * FROM Patients", null);
        String patient_id = "";
        int num;
        if (record_patient.moveToFirst()) {
            record_patient.moveToLast();
            int i = record_patient.getColumnIndex("patient_id");
            patient_id = record_patient.getString(i);
            System.out.println("Patient ID: "+patient_id);
            Pattern pattern = Pattern.compile("P_(\\d+)");
            Matcher matcher = pattern.matcher(patient_id);
            if (matcher.find()) {
                String integerValue = matcher.group(1);
                num = Integer.parseInt(integerValue);
                System.out.println("Patient No: "+num);
                num += 1;
            }
            else
                num = 1; //First Patient
        }
        else
            num = 1; //First Patient
        patient_id = "P_"+String.valueOf(num);
        System.out.println("FINAL PATIENT ID: "+patient_id);
        //Insert record into patients
        ContentValues content_value_patient = new ContentValues();
        content_value_patient.put("user_id", u_id);
        content_value_patient.put("patient_id", patient_id);
        content_value_patient.put("name", name);
        content_value_patient.put("gender", gender);
        content_value_patient.put("address", address);
        content_value_patient.put("phone_no", ph_no);
        result = db.insert("Patients", null, content_value_patient);
        if(result == -1) return false;
        return true;
    }

    //Validating user credentials - Login
    public int validate_user(String u_id, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        //Check if user ID already exists
        Cursor record_user = db.rawQuery("SELECT * FROM Users WHERE user_id = ?", new
                String[]{u_id});
        System.out.println("User Record: "+record_user.getCount());
        if (record_user.getCount() <= 0)
            return -1; //User not present
        if(record_user.moveToFirst()) {
            //User present and get password
            int i = record_user.getColumnIndex("password");
            String pass = record_user.getString(i);
            i = record_user.getColumnIndex("user_id");
            String uid = record_user.getString(i);
            System.out.println("User ID: " + uid);
            System.out.println("Password: " + pass);
            if (pass.equals(password))
                return 1; //Successful login
            else
                return 0; //Password incorrect
        }
        else
            return -1;
    }
    //To insert a doctor
    public Boolean insert_doctor_record(String name, String department, String
            designation, String gender, String phone_no) {
        //To get db connection
        SQLiteDatabase db = this.getWritableDatabase();

        //Get last patient id
        Cursor record_doctor = db.rawQuery("SELECT * FROM Doctors", null);
        String doctor_id = "";
        int num;
        if (record_doctor.moveToFirst()) {
            record_doctor.moveToLast();
            int i = record_doctor.getColumnIndex("doctor_id");
            doctor_id = record_doctor.getString(i);
            System.out.println("Doctor ID: "+doctor_id);
            Pattern pattern = Pattern.compile("D_(\\d+)");
            Matcher matcher = pattern.matcher(doctor_id);
            if (matcher.find()) {
                String integerValue = matcher.group(1);
                num = Integer.parseInt(integerValue);
                System.out.println("Doctor No: "+num);
                num += 1;
            }
            else
                num = 1; //First Patient
        }
        else
            num = 1; //First Patient
        doctor_id = "D_"+String.valueOf(num);
        System.out.println("FINAL DOCTOR ID: "+doctor_id);

        //To write into db
        ContentValues content_value = new ContentValues();
        content_value.put("doctor_id", doctor_id);
        content_value.put("name", name);
        content_value.put("department", department);
        content_value.put("designation", designation);
        content_value.put("gender", gender);
        content_value.put("phone_no", phone_no);
        //Execute insertion
        Long result = db.insert("Doctors", null, content_value);
        if(result == -1) return false;
        return true;
    }

    //To delete a doctor
    public Boolean delete_doctor_record(String id) {
        //To get db connection
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor record = db.rawQuery("SELECT * FROM Doctors WHERE doctor_id = ?", new
                String []{id});
        //Execute deletion if there is a record to be deleted
        if(record.getCount() > 0)
        {
            int result = db.delete("Doctors", "doctor_id = ?", new String []{id});
            if(result == -1) return false;
            else return true;
        }
        return false;
    }

    //Retrieve all Doctor Records
    public Cursor retrieve_all_records() {
        //To get db connection
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor records = db.rawQuery("SELECT * FROM Doctors", null);
        return records;
    }

    //Retrieve Patient Details for a user
    public Cursor retrieve_patient(String u_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor record_patient = db.rawQuery("SELECT * FROM Patients WHERE user_id = ?", new
                String[]{u_id});
        return record_patient;
    }
    //Retrieve Doctor Details given id
    public Cursor retrieve_docdetails(String doctor_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor record = db.rawQuery("SELECT * FROM Doctors WHERE doctor_id = ?", new
                String[]{doctor_id});
        return record;
    }
    //Retrieve Appointment Details for a patient
    public Cursor retrieve_patient_appointments(String patient_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor record = db.rawQuery("SELECT * FROM Appointments WHERE patient_id = ?", new
                String[]{patient_id});
        return record;
    }
    //Retrieve Appointment Details for a doctor
    public Cursor retrieve_doctor_appointments(String doctor_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor record = db.rawQuery("SELECT * FROM Appointments WHERE doctor_id = ?", new
                String[]{doctor_id});
        return record;
    }

    //Insert Slots
    public Boolean insert_slots(String doctor_id, String slot_id, String date, String avail_slots) {
        //To get db connection
        SQLiteDatabase db = this.getWritableDatabase();
        //To write into db
        ContentValues content_value = new ContentValues();
        content_value.put("doctor_id", doctor_id);
        content_value.put("slot_id", slot_id);
        content_value.put("date", date);
        content_value.put("avail_slots", avail_slots);

        //Execute insertion
        Long result = db.insert("Doctor_Slots", null, content_value);
        if(result == -1) return false;
        return true;
    }

    public Boolean insert_appointments(String patient_id, String patient_name, String doctor_id, String doctor_name, String time, String date, String symptoms) {
        //To get db connection
        SQLiteDatabase db = this.getWritableDatabase();
        //To write into db
        ContentValues content_value = new ContentValues();
        content_value.put("patient_id", patient_id);
        content_value.put("patient_name", patient_name);
        content_value.put("doctor_id", doctor_id);
        content_value.put("doctor_name", doctor_name);
        content_value.put("date", date);
        content_value.put("time", time);
        content_value.put("symptoms", symptoms);

        //Execute insertion
        Long result = db.insert("Appointments", null, content_value);
        if(result == -1) return false;
        return true;
    }
    //Retrieval
    public Cursor retrieve_doctor_from_department(String department) {
        //To get db connection
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor record = db.rawQuery("SELECT * FROM Doctors WHERE department = ?", new String []{department});
        return record;
    }

    public Cursor retrieve_slots(String doctor_id, String date) {
        //To get db connection
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor record = db.rawQuery("SELECT * FROM Doctor_Slots WHERE doctor_id = ? and date = ?", new String []{doctor_id, date});
        return record;
    }

}