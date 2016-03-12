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

package com.stoneapp.ourvlemoodle2.tasks;

import java.util.ArrayList;
import java.util.List;

import com.stoneapp.ourvlemoodle2.models.MoodleForum;
import com.stoneapp.ourvlemoodle2.models.MoodleCourse;
import com.stoneapp.ourvlemoodle2.rest.MoodleRestForum;

public class ForumSync {

    private String token; //url token

    public ForumSync(String token){
        this.token = token;
    }

    public boolean syncForums(ArrayList<String> courseids){
        ArrayList<MoodleForum>forums;
        MoodleRestForum mrforum = new MoodleRestForum(token);
        forums = mrforum.getForums(courseids); // gets forums from api call

        if (forums  == null) // if there are no forums
            return false;

        if (forums.size() == 0 )
            return false;

        List<MoodleForum> saved_forums;
        List<MoodleCourse> saved_courses;
        MoodleForum forum;

        int len = forums.size();

        for (int i = 0; i < len; i++){
            forum = forums.get(i);

            saved_forums = MoodleForum.find(MoodleForum.class, "forumid = ?", forum.getForumid() + ""); // get forums matching the current forum
            saved_courses = MoodleCourse.find(MoodleCourse.class, "courseid = ?", forum.getCourseid() + ""); // gets courses from database

            if (saved_forums.size() > 0)  // if any forums match the current forum then just overwrite the previously sored one
                forum.setId(saved_forums.get(0).getId());

            if (saved_courses.size() > 0 )
                forum.setCoursename(saved_courses.get(0).getShortname());

            forum.save(); // save the forum
        }

        return true;
    }
}
