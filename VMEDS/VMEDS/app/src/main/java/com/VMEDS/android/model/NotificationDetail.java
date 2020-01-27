package com.VMEDS.android.model;

/**
 * Created by Yogesh on 5/25/2017.
 */

public class NotificationDetail {
    public String id, title, description, time;

    public NotificationDetail(String id, String title, String description, String time) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.time = time;
    }
}
