package com.google.android.setupcompat.logging;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;
import com.google.android.setupcompat.internal.ClockProvider;
import com.google.android.setupcompat.internal.PersistableBundles;
import com.google.android.setupcompat.internal.Preconditions;
import com.google.android.setupcompat.internal.Validations;
import com.google.android.setupcompat.util.ObjectUtils;

/* JADX INFO: loaded from: classes.dex */
public final class CustomEvent implements Parcelable {
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() { // from class: com.google.android.setupcompat.logging.CustomEvent.1
        @Override // android.os.Parcelable.Creator
        public CustomEvent createFromParcel(Parcel parcel) {
            return new CustomEvent(parcel.readLong(), (MetricKey) parcel.readParcelable(MetricKey.class.getClassLoader()), parcel.readPersistableBundle(MetricKey.class.getClassLoader()), parcel.readPersistableBundle(MetricKey.class.getClassLoader()));
        }

        @Override // android.os.Parcelable.Creator
        public CustomEvent[] newArray(int i) {
            return new CustomEvent[i];
        }
    };
    public static final int MAX_STR_LENGTH = 50;
    static final int MIN_BUNDLE_KEY_LENGTH = 3;
    private final MetricKey metricKey;
    private final PersistableBundle persistableBundle;
    private final PersistableBundle piiValues;
    private final long timestampMillis;

    private CustomEvent(long j, MetricKey metricKey, PersistableBundle persistableBundle, PersistableBundle persistableBundle2) {
        Preconditions.checkArgument(j >= 0, "Timestamp cannot be negative.");
        Preconditions.checkNotNull(metricKey, "MetricKey cannot be null.");
        Preconditions.checkNotNull(persistableBundle, "Bundle cannot be null.");
        Preconditions.checkArgument(!persistableBundle.isEmpty(), "Bundle cannot be empty.");
        Preconditions.checkNotNull(persistableBundle2, "piiValues cannot be null.");
        assertPersistableBundleIsValid(persistableBundle);
        this.timestampMillis = j;
        this.metricKey = metricKey;
        this.persistableBundle = new PersistableBundle(persistableBundle);
        this.piiValues = new PersistableBundle(persistableBundle2);
    }

    private static void assertPersistableBundleIsValid(PersistableBundle persistableBundle) {
        for (String str : persistableBundle.keySet()) {
            Validations.assertLengthInRange(str, "bundle key", MIN_BUNDLE_KEY_LENGTH, 50);
            Object obj = persistableBundle.get(str);
            if (obj instanceof String) {
                Preconditions.checkArgument(((String) obj).length() <= 50, String.format("Maximum length of string value for key='%s' cannot exceed %s.", str, 50));
            }
        }
    }

    public static CustomEvent create(MetricKey metricKey, PersistableBundle persistableBundle) {
        Preconditions.checkArgument(true, "The constructor only support on sdk Q or higher.");
        return create(metricKey, persistableBundle, PersistableBundle.EMPTY);
    }

    public static CustomEvent create(MetricKey metricKey, PersistableBundle persistableBundle, PersistableBundle persistableBundle2) {
        Preconditions.checkArgument(true, "The constructor only support on sdk Q or higher");
        return new CustomEvent(ClockProvider.timeInMillis(), metricKey, PersistableBundles.assertIsValid(persistableBundle), PersistableBundles.assertIsValid(persistableBundle2));
    }

    public static Bundle toBundle(CustomEvent customEvent) {
        Preconditions.checkNotNull(customEvent, "CustomEvent cannot be null");
        Bundle bundle = new Bundle();
        bundle.putInt("CustomEvent_version", 1);
        bundle.putLong("CustomEvent_timestamp", customEvent.timestampMillis());
        bundle.putBundle("CustomEvent_metricKey", MetricKey.fromMetricKey(customEvent.metricKey()));
        bundle.putBundle("CustomEvent_bundleValues", PersistableBundles.toBundle(customEvent.values()));
        bundle.putBundle("CustomEvent_pii_bundleValues", PersistableBundles.toBundle(customEvent.piiValues()));
        return bundle;
    }

    public static String trimsStringOverMaxLength(String str) {
        return str.length() <= 50 ? str : String.format("%s…", str.substring(0, 49));
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CustomEvent)) {
            return false;
        }
        CustomEvent customEvent = (CustomEvent) obj;
        return this.timestampMillis == customEvent.timestampMillis && ObjectUtils.equals(this.metricKey, customEvent.metricKey) && PersistableBundles.equals(this.persistableBundle, customEvent.persistableBundle) && PersistableBundles.equals(this.piiValues, customEvent.piiValues);
    }

    public int hashCode() {
        return ObjectUtils.hashCode(Long.valueOf(this.timestampMillis), this.metricKey, this.persistableBundle, this.piiValues);
    }

    public MetricKey metricKey() {
        return this.metricKey;
    }

    public PersistableBundle piiValues() {
        return this.piiValues;
    }

    public long timestampMillis() {
        return this.timestampMillis;
    }

    public PersistableBundle values() {
        return new PersistableBundle(this.persistableBundle);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.timestampMillis);
        parcel.writeParcelable(this.metricKey, i);
        parcel.writePersistableBundle(this.persistableBundle);
        parcel.writePersistableBundle(this.piiValues);
    }
}
