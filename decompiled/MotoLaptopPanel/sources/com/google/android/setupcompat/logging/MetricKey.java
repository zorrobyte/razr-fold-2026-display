package com.google.android.setupcompat.logging;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.setupcompat.internal.Preconditions;
import com.google.android.setupcompat.internal.Validations;
import com.google.android.setupcompat.util.ObjectUtils;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes.dex */
public final class MetricKey implements Parcelable {
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() { // from class: com.google.android.setupcompat.logging.MetricKey.1
        @Override // android.os.Parcelable.Creator
        public MetricKey createFromParcel(Parcel parcel) {
            return new MetricKey(parcel.readString(), parcel.readString());
        }

        @Override // android.os.Parcelable.Creator
        public MetricKey[] newArray(int i) {
            return new MetricKey[i];
        }
    };
    private static final Pattern METRIC_KEY_PATTERN = Pattern.compile("^[a-zA-Z][a-zA-Z0-9_]+");
    private static final Pattern SCREEN_COMPONENTNAME_PATTERN = Pattern.compile("^([a-z]+[.])+[A-Z][a-zA-Z0-9]+");
    private static final Pattern SCREEN_NAME_PATTERN = Pattern.compile("^[a-zA-Z][a-zA-Z0-9_]+");
    private final String name;
    private final String screenName;

    private MetricKey(String str, String str2) {
        this.name = str;
        this.screenName = str2;
    }

    public static Bundle fromMetricKey(MetricKey metricKey) {
        Preconditions.checkNotNull(metricKey, "MetricKey cannot be null.");
        Bundle bundle = new Bundle();
        bundle.putInt("MetricKey_version", 1);
        bundle.putString("MetricKey_name", metricKey.name());
        bundle.putString("MetricKey_screenName", metricKey.screenName());
        return bundle;
    }

    public static MetricKey get(String str, Activity activity) {
        String className = activity.getComponentName().getClassName();
        Validations.assertLengthInRange(str, "MetricKey.name", 5, 30);
        Preconditions.checkArgument(METRIC_KEY_PATTERN.matcher(str).matches(), "Invalid MetricKey, only alpha numeric characters are allowed.");
        return new MetricKey(str, className);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MetricKey)) {
            return false;
        }
        MetricKey metricKey = (MetricKey) obj;
        return ObjectUtils.equals(this.name, metricKey.name) && ObjectUtils.equals(this.screenName, metricKey.screenName);
    }

    public int hashCode() {
        return ObjectUtils.hashCode(this.name, this.screenName);
    }

    public String name() {
        return this.name;
    }

    public String screenName() {
        return this.screenName;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.name);
        parcel.writeString(this.screenName);
    }
}
