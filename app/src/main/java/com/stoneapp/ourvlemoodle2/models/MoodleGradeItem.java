/*
 * Copyright 2016 Matthew Stone and Romario Maxwell.
 *
 * This file is part of OurVLE.
 *
 * OurVLE is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * OurVLE is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with OurVLE.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.stoneapp.ourvlemoodle2.models;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import java.util.ArrayList;

public class MoodleGradeItem extends SugarRecord<MoodleGradeItem> {

    @SerializedName("activityid")
    String courseid;    //The ID of the activity or "course" for the course grade item

    @SerializedName("itemnumber")
    int itemnumber;    //Will be 0 unless the module has multiple grades

    @SerializedName("scaleid")
    int scaleid;  //The ID of the custom scale or 0

    @SerializedName("name")
    String name;  //The module name

    @SerializedName("grademin")
    double grademin;    //Minimum grade

    @SerializedName("grademax")
    double grademax;   //Maximum grade

    @SerializedName("gradepass")
    double gradepass;    //The passing grade threshold

    @SerializedName("locked")
    int locked;   //Is the grade item locked?

    @SerializedName("hidden")
    int hidden;  //Is the grade item hidden?

    @SerializedName("grades")
    ArrayList<MoodleGrade>grades;


    public String getCourseid() {
        return courseid;
    }


    public int getItemnumber() {
        return itemnumber;
    }



    public int getScaleid() {
        return scaleid;
    }

    public double getGrademin() {
        return grademin;
    }

    public String getName() {
        return name;
    }

    public double getGrademax() {
        return grademax;
    }

    public double getGradepass() {
        return gradepass;
    }

    public int getLocked() {
        return locked;
    }

    public int getHidden() {
        return hidden;
    }

    public ArrayList<MoodleGrade> getGrades() {
        return grades;
    }
}
