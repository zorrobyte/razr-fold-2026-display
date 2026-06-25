package com.android.launcher3.icons;

/* JADX INFO: loaded from: classes.dex */
public class ExtraFgHolder {
    private String mFgName;
    private boolean mIsCalendar = false;

    public String get() {
        return this.mFgName;
    }

    public boolean isCalendar() {
        return this.mIsCalendar;
    }

    public void set(String str) {
        this.mFgName = str;
    }

    public void setCalendar(boolean z) {
        this.mIsCalendar = z;
    }
}
