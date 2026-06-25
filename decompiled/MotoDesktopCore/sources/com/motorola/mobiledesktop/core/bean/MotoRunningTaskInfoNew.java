package com.motorola.mobiledesktop.core.bean;

import E.a;
import android.app.ActivityManager;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class MotoRunningTaskInfoNew implements Parcelable {
    public static final Parcelable.Creator<MotoRunningTaskInfoNew> CREATOR = new a(8);
    public int displayId;
    public boolean isFocused;
    public boolean isRunning;
    public boolean isVisible;
    public String pkg;
    public int taskId;
    public int userId;

    public MotoRunningTaskInfoNew() {
    }

    public MotoRunningTaskInfoNew(Parcel parcel) {
        this.userId = parcel.readInt();
        this.taskId = parcel.readInt();
        this.isRunning = parcel.readByte() != 0;
        this.displayId = parcel.readInt();
        this.isFocused = parcel.readByte() != 0;
        this.isVisible = parcel.readByte() != 0;
        this.pkg = parcel.readString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void setRunningTaskInfo(ActivityManager.RunningTaskInfo runningTaskInfo) {
        this.userId = runningTaskInfo.userId;
        this.taskId = runningTaskInfo.taskId;
        this.isRunning = runningTaskInfo.isRunning;
        this.displayId = runningTaskInfo.displayId;
        this.isFocused = runningTaskInfo.isFocused;
        this.isVisible = runningTaskInfo.isVisible;
        Intent intent = runningTaskInfo.baseIntent;
        if (intent == null || intent.getComponent() == null) {
            return;
        }
        this.pkg = intent.getComponent().getPackageName();
    }

    public String toString() {
        return "MotoRunningTaskInfoNew{userId=" + this.userId + ", taskId=" + this.taskId + ", isRunning=" + this.isRunning + ", displayId=" + this.displayId + ", isFocused=" + this.isFocused + ", isVisible=" + this.isVisible + ", pkg='" + this.pkg + "'}";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeInt(this.userId);
        parcel.writeInt(this.taskId);
        parcel.writeByte(this.isRunning ? (byte) 1 : (byte) 0);
        parcel.writeInt(this.displayId);
        parcel.writeByte(this.isFocused ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.isVisible ? (byte) 1 : (byte) 0);
        parcel.writeString(this.pkg);
    }
}
