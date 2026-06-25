package com.motorola.multivolume;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public final class AppVolumeState implements Parcelable {
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() { // from class: com.motorola.multivolume.AppVolumeState.1
        @Override // android.os.Parcelable.Creator
        public AppVolumeState createFromParcel(Parcel parcel) {
            return new AppVolumeState(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public AppVolumeState[] newArray(int i) {
            return new AppVolumeState[i];
        }
    };
    public int MAX_PROGRESS;
    public int MIN_PROGRESS;
    public boolean active;
    public int appLevel;
    public boolean forceToShow;
    public boolean foreground;
    public boolean foregroundSettings;
    public Drawable icon;
    public String label;
    public int lastSetLevel;
    public String packageName;
    public int packagePid;
    public int packageUid;
    public double percentage;
    public boolean playing;
    public int progress;
    public double ratio;
    public boolean shouldBeVisible;
    public double storedPercentage;
    public long timeInMills;
    public int uiType;
    public long userAttempt;

    public AppVolumeState() {
        this.packageUid = -1;
        this.packagePid = -1;
        this.packageName = null;
        this.label = null;
        this.icon = null;
        this.uiType = 1;
        this.playing = false;
        this.foreground = false;
        this.foregroundSettings = false;
        this.active = false;
        this.shouldBeVisible = false;
        this.forceToShow = false;
        this.timeInMills = -1L;
        this.userAttempt = -1L;
        this.MAX_PROGRESS = 1500;
        this.MIN_PROGRESS = 0;
        this.progress = -1;
        this.appLevel = -1;
        this.lastSetLevel = -1;
        this.ratio = -1.0d;
        this.storedPercentage = -1.0d;
        this.percentage = -1.0d;
    }

    protected AppVolumeState(Parcel parcel) {
        this.packageUid = -1;
        this.packagePid = -1;
        this.packageName = null;
        this.label = null;
        this.icon = null;
        this.uiType = 1;
        this.playing = false;
        this.foreground = false;
        this.foregroundSettings = false;
        this.active = false;
        this.shouldBeVisible = false;
        this.forceToShow = false;
        this.timeInMills = -1L;
        this.userAttempt = -1L;
        this.MAX_PROGRESS = 1500;
        this.MIN_PROGRESS = 0;
        this.progress = -1;
        this.appLevel = -1;
        this.lastSetLevel = -1;
        this.ratio = -1.0d;
        this.storedPercentage = -1.0d;
        this.percentage = -1.0d;
        this.packageUid = parcel.readInt();
        this.packagePid = parcel.readInt();
        this.packageName = parcel.readString();
        this.label = parcel.readString();
        Bitmap bitmap = (Bitmap) parcel.readParcelable(AppVolumeState.class.getClassLoader());
        if (bitmap != null) {
            this.icon = new BitmapDrawable(bitmap);
        }
        this.uiType = parcel.readInt();
        this.playing = parcel.readBoolean();
        this.foreground = parcel.readBoolean();
        this.foregroundSettings = parcel.readBoolean();
        this.active = parcel.readBoolean();
        this.shouldBeVisible = parcel.readBoolean();
        this.forceToShow = parcel.readBoolean();
        this.timeInMills = parcel.readLong();
        this.userAttempt = parcel.readLong();
        this.MAX_PROGRESS = parcel.readInt();
        this.MIN_PROGRESS = parcel.readInt();
        this.progress = parcel.readInt();
        this.appLevel = parcel.readInt();
        this.lastSetLevel = parcel.readInt();
        this.ratio = parcel.readDouble();
        this.storedPercentage = parcel.readDouble();
        this.percentage = parcel.readDouble();
    }

    public AppVolumeState copy() {
        AppVolumeState appVolumeState = new AppVolumeState();
        appVolumeState.packageUid = this.packageUid;
        appVolumeState.packagePid = this.packagePid;
        appVolumeState.packageName = this.packageName;
        appVolumeState.label = this.label;
        appVolumeState.icon = this.icon;
        appVolumeState.uiType = this.uiType;
        appVolumeState.playing = this.playing;
        appVolumeState.foreground = this.foreground;
        appVolumeState.foregroundSettings = this.foregroundSettings;
        appVolumeState.active = this.active;
        appVolumeState.shouldBeVisible = this.shouldBeVisible;
        appVolumeState.forceToShow = this.forceToShow;
        appVolumeState.timeInMills = this.timeInMills;
        appVolumeState.userAttempt = this.userAttempt;
        appVolumeState.MAX_PROGRESS = this.MAX_PROGRESS;
        appVolumeState.MIN_PROGRESS = this.MIN_PROGRESS;
        appVolumeState.progress = this.progress;
        appVolumeState.appLevel = this.appLevel;
        appVolumeState.lastSetLevel = this.lastSetLevel;
        appVolumeState.ratio = this.ratio;
        appVolumeState.storedPercentage = this.storedPercentage;
        appVolumeState.percentage = this.percentage;
        return appVolumeState;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getAbbreviation() {
        return this.label + ":" + this.packageName + ":" + this.packageUid + ":" + this.packagePid + '[' + this.progress + ".." + this.appLevel + ".." + this.ratio + ".." + this.storedPercentage + ".." + this.percentage + ".." + this.playing + ".." + this.foreground + ".." + this.foregroundSettings + ".." + this.active + ".." + this.shouldBeVisible + ".." + this.forceToShow + ".." + this.timeInMills + ']';
    }

    public String toString() {
        return "label: " + this.label + ", uid: " + this.packageUid + ", packageName: " + this.packageName + ", playing: " + this.playing + ", foreground: " + this.foreground + ", foregroundSettings: " + this.foregroundSettings + ", active: " + this.active + ", shouldBeVisible: " + this.shouldBeVisible + ", forceToShow: " + this.forceToShow + ", progress: " + this.progress + ", appLevel: " + this.appLevel + ", lastSetLevel: " + this.lastSetLevel + ", ratio: " + this.ratio + ", storedPercentage: " + this.storedPercentage + ", percentage: " + this.percentage + ", userAttempt: " + this.userAttempt + ", timeInMills: " + this.timeInMills;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.packageUid);
        parcel.writeInt(this.packagePid);
        parcel.writeString(this.packageName);
        parcel.writeString(this.label);
        Drawable drawable = this.icon;
        if (drawable == null || !(drawable instanceof BitmapDrawable)) {
            parcel.writeParcelable(null, i);
        } else {
            parcel.writeParcelable(((BitmapDrawable) drawable).getBitmap(), i);
        }
        parcel.writeInt(this.uiType);
        parcel.writeBoolean(this.playing);
        parcel.writeBoolean(this.foreground);
        parcel.writeBoolean(this.foregroundSettings);
        parcel.writeBoolean(this.active);
        parcel.writeBoolean(this.shouldBeVisible);
        parcel.writeBoolean(this.forceToShow);
        parcel.writeLong(this.timeInMills);
        parcel.writeLong(this.userAttempt);
        parcel.writeInt(this.MAX_PROGRESS);
        parcel.writeInt(this.MIN_PROGRESS);
        parcel.writeInt(this.progress);
        parcel.writeInt(this.appLevel);
        parcel.writeInt(this.lastSetLevel);
        parcel.writeDouble(this.ratio);
        parcel.writeDouble(this.storedPercentage);
        parcel.writeDouble(this.percentage);
    }
}
