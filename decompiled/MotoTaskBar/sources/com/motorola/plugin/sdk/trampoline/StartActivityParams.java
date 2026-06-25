package com.motorola.plugin.sdk.trampoline;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes2.dex */
public class StartActivityParams implements Parcelable {
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() { // from class: com.motorola.plugin.sdk.trampoline.StartActivityParams.1
        @Override // android.os.Parcelable.Creator
        public StartActivityParams createFromParcel(Parcel parcel) {
            return new StartActivityParams(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public StartActivityParams[] newArray(int i) {
            return new StartActivityParams[i];
        }
    };
    public int extraFlags;
    public Intent fillInIntent;
    public int flagsMask;
    public int flagsValues;
    public Intent intent;
    public IntentSender intentSender;
    public Bundle options;

    public StartActivityParams(PendingIntent pendingIntent) {
        this.intent = null;
        this.intentSender = null;
        this.fillInIntent = null;
        this.flagsMask = 0;
        this.flagsValues = 0;
        this.extraFlags = 0;
        this.options = null;
        this.intentSender = pendingIntent != null ? pendingIntent.getIntentSender() : null;
    }

    public StartActivityParams(Intent intent) {
        this.intentSender = null;
        this.fillInIntent = null;
        this.flagsMask = 0;
        this.flagsValues = 0;
        this.extraFlags = 0;
        this.options = null;
        this.intent = intent;
    }

    private StartActivityParams(Parcel parcel) {
        this.intent = null;
        this.intentSender = null;
        this.fillInIntent = null;
        this.flagsMask = 0;
        this.flagsValues = 0;
        this.extraFlags = 0;
        this.options = null;
        this.intent = (Intent) parcel.readParcelable(Intent.class.getClassLoader());
        this.intentSender = (IntentSender) parcel.readParcelable(IntentSender.class.getClassLoader());
        this.fillInIntent = (Intent) parcel.readParcelable(Intent.class.getClassLoader());
        this.flagsMask = parcel.readInt();
        this.flagsValues = parcel.readInt();
        this.extraFlags = parcel.readInt();
        this.options = parcel.readBundle(getClass().getClassLoader());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.intent, i);
        parcel.writeParcelable(this.intentSender, i);
        parcel.writeParcelable(this.fillInIntent, i);
        parcel.writeInt(this.flagsMask);
        parcel.writeInt(this.flagsValues);
        parcel.writeInt(this.extraFlags);
        parcel.writeBundle(this.options);
    }
}
