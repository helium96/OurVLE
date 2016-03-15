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

package com.stoneapp.ourvlemoodle2.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.stoneapp.ourvlemoodle2.R;
import com.stoneapp.ourvlemoodle2.models.MoodlePost;
import com.stoneapp.ourvlemoodle2.util.TimeUtils;

import java.util.List;

public class PostListAdapter  extends RecyclerView.Adapter<PostListAdapter.PostListViewHolder> {
    public static class PostListViewHolder extends RecyclerView.ViewHolder {
        TextView username;
        TextView subject;
        TextView message;
        TextView posttime;
        ImageView postImage;

        public PostListViewHolder(View itemView) {
            super(itemView);
            username = (TextView)itemView.findViewById(R.id.postUser);
            subject = (TextView)itemView.findViewById(R.id.postTitle);
            message = (TextView)itemView.findViewById(R.id.postMessage);
            posttime = (TextView)itemView.findViewById(R.id.postDate);
            postImage = (ImageView)itemView.findViewById(R.id.postImage);
        }
    }

    private List<MoodlePost> postList;
    private Context ctxt;

    public PostListAdapter(List<MoodlePost> postList, Context ctxt) {
        this.postList = postList;
        this.ctxt = ctxt;
    }

    @Override
    public PostListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_post_item, parent, false);

        return new PostListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PostListViewHolder holder, int position) {

        MoodlePost post = postList.get(position);

        String subject = post.getSubject();
        if (subject == null)
            holder.subject.setText("");
        else
            holder.subject.setText(subject);


        String username = post.getUserfullname();
        if (username == null)
            holder.username.setText("");
        else
            holder.username.setText(username);


        String message = post.getMessage();
        if (message == null)
            holder.message.setText("");
        else
            holder.message.setText(message);

        //Extracts image from string to show in text view
        CharSequence format_message = Html.fromHtml(message, new Html.ImageGetter() {
            @Override
            public Drawable getDrawable(String source) {
                try {
                    //InputStream is = (InputStream) new URL(source).getContent();
                    //Drawable d = Drawable.createFromStream(is, "sc name");
                    //d.setBounds(0,0,50,50);
                    //return d;
                    Drawable drawFromPath;
                    int path =
                            ctxt.getResources().getIdentifier(source, "drawable",
                                    ctxt.getPackageName());
                    drawFromPath = ContextCompat.getDrawable(ctxt.getApplicationContext(), path);
                    drawFromPath.setBounds(0, 0, drawFromPath.getIntrinsicWidth(),
                            drawFromPath.getIntrinsicHeight());
                    return drawFromPath;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }, null);

        if (message == null)
            holder.message.setText("");
        else
            holder.message.setText(format_message);

        int time = post.getModified();
        holder.posttime.setText(TimeUtils.getTime(time));

        char firstLetter = username.toUpperCase().charAt(0);
        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color2 = generator.getColor(username);
        TextDrawable drawable2 = TextDrawable.builder()
                .beginConfig()
                .textColor(Color.WHITE)
                .useFont(Typeface.DEFAULT)
                .toUpperCase()
                .endConfig()
                .buildRound(firstLetter + "", color2);

        holder.postImage.setImageDrawable(drawable2);
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public void updatePosts(List<MoodlePost> newPosts) {
        postList = newPosts;
        notifyDataSetChanged();
    }
}
