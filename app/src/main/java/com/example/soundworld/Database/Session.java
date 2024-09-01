package com.example.soundworld.Database;
/*
 * @author: Dearseven
 * @description:
 */

public class Session {
    int session;
    String session_title;

    public int getSession() {
        return session;
    }

    public Session(int session, String session_title) {
        this.session = session;
        this.session_title = session_title;
    }

    public void setSession(int session) {
        this.session = session;
    }

    public String getSession_title() {
        return session_title;
    }

    public void setSession_title(String session_title) {
        this.session_title = session_title;
    }
}
