package com.motorola.mobiledesktop.core.bean;

import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class AppInfo {
    public int displayId;
    public String pkg;
    public String pkgAndUserId;
    public int taskId;
    public int userId;

    public AppInfo(int i2, String str, int i3, int i4, String str2) {
        this.taskId = i2;
        this.pkg = str;
        this.displayId = i3;
        this.userId = i4;
        this.pkgAndUserId = str2;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AppInfo)) {
            return false;
        }
        AppInfo appInfo = (AppInfo) obj;
        return this.taskId == appInfo.taskId && this.displayId == appInfo.displayId && this.userId == appInfo.userId && Objects.equals(this.pkg, appInfo.pkg);
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.taskId), this.pkg, Integer.valueOf(this.displayId), Integer.valueOf(this.userId));
    }

    public String toString() {
        return "AppInfo{taskId=" + this.taskId + ", pkg='" + this.pkg + "', displayId=" + this.displayId + ", userId=" + this.userId + ", pkgAndUserId=" + this.pkgAndUserId + '}';
    }
}
