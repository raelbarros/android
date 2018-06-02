package com.cit.ceuapp;

import android.content.Context;
import android.util.Log;

import java.util.HashMap;

public class CeuUser {
    private String uid;
    private String email;
    private String name;
    private String title;
    private String company;
    private String description;
    private Type type;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Type getType() {
        return type;
    }

    public void setType(String type) {
        switch(type) {
            case "0":
                this.type = Type.SUPER_GOD;
            case "1":
                this.type = Type.GOD;
            case "2":
                this.type = Type.CLIENT;
            case "3":
                this.type = Type.EMPLOYEE;
            case "4":
                this.type = Type.END_USER;
            default:
                this.type = Type.END_USER;
        }
    }

    public HashMap<String, String> toHashMap(Context context) {
        HashMap<String, String> userInfo = new HashMap<>();

        userInfo.put(context.getString(R.string.db_code_email), getEmail());
        userInfo.put(context.getString(R.string.db_code_name), name);
        userInfo.put(context.getString(R.string.db_code_title), title);
        userInfo.put(context.getString(R.string.db_code_company), company);
        userInfo.put(context.getString(R.string.db_code_description), description);

        return userInfo;
    }

    public enum Type {
        SUPER_GOD,
        GOD,
        CLIENT,
        EMPLOYEE,
        END_USER;

        @Override
        public String toString() {
            switch(this) {
                case SUPER_GOD:
                    return "0";
                case GOD:
                    return "1";
                case CLIENT:
                    return "2";
                case EMPLOYEE:
                    return  "3";
                case END_USER:
                    return "4";
                default:
                    return "-1";
            }
        }
    }
}
