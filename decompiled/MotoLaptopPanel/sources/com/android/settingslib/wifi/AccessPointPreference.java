package com.android.settingslib.wifi;

import android.content.Context;
import android.graphics.drawable.StateListDrawable;
import android.os.Looper;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;
import androidx.preference.Preference;
import com.android.settingslib.R$attr;
import com.android.settingslib.R$dimen;
import com.android.settingslib.R$layout;
import com.android.settingslib.R$string;

/* JADX INFO: loaded from: classes.dex */
public class AccessPointPreference extends Preference {
    private AccessPoint mAccessPoint;
    private final int mBadgePadding;
    private int mDefaultIconResId;
    private boolean mForSavedNetworks;
    private final StateListDrawable mFrictionSld;
    private final IconInjector mIconInjector;
    private int mLevel;
    private final Runnable mNotifyChanged;
    private TextView mTitleView;
    private int mWifiSpeed;
    private static final int[] STATE_SECURED = {R$attr.state_encrypted};
    private static final int[] STATE_METERED = {R$attr.state_metered};
    private static final int[] FRICTION_ATTRS = {R$attr.wifi_friction};
    private static final int[] WIFI_CONNECTION_STRENGTH = {R$string.accessibility_no_wifi, R$string.accessibility_wifi_one_bar, R$string.accessibility_wifi_two_bars, R$string.accessibility_wifi_three_bars, R$string.accessibility_wifi_signal_full};

    class IconInjector {
        private final Context mContext;

        public IconInjector(Context context) {
            this.mContext = context;
        }
    }

    public abstract class UserBadgeCache {
    }

    public AccessPointPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mForSavedNetworks = false;
        this.mWifiSpeed = 0;
        this.mNotifyChanged = new Runnable() { // from class: com.android.settingslib.wifi.AccessPointPreference.1
            @Override // java.lang.Runnable
            public void run() {
                AccessPointPreference.this.notifyChanged();
            }
        };
        this.mFrictionSld = null;
        this.mBadgePadding = 0;
        this.mIconInjector = new IconInjector(context);
    }

    AccessPointPreference(AccessPoint accessPoint, Context context, UserBadgeCache userBadgeCache, int i, boolean z, StateListDrawable stateListDrawable, int i2, IconInjector iconInjector) {
        super(context);
        this.mForSavedNetworks = false;
        this.mWifiSpeed = 0;
        this.mNotifyChanged = new Runnable() { // from class: com.android.settingslib.wifi.AccessPointPreference.1
            @Override // java.lang.Runnable
            public void run() {
                AccessPointPreference.this.notifyChanged();
            }
        };
        setLayoutResource(R$layout.preference_access_point);
        setWidgetLayoutResource(getWidgetLayoutResourceId());
        this.mAccessPoint = accessPoint;
        this.mForSavedNetworks = z;
        accessPoint.setTag(this);
        this.mLevel = i2;
        this.mDefaultIconResId = i;
        this.mFrictionSld = stateListDrawable;
        this.mIconInjector = iconInjector;
        this.mBadgePadding = context.getResources().getDimensionPixelSize(R$dimen.wifi_preference_badge_padding);
    }

    static CharSequence buildContentDescription(Context context, Preference preference, AccessPoint accessPoint) {
        CharSequence title = preference.getTitle();
        CharSequence summary = preference.getSummary();
        if (!TextUtils.isEmpty(summary)) {
            title = TextUtils.concat(title, ",", summary);
        }
        int level = accessPoint.getLevel();
        if (level >= 0) {
            int[] iArr = WIFI_CONNECTION_STRENGTH;
            if (level < iArr.length) {
                title = TextUtils.concat(title, ",", context.getString(iArr[level]));
            }
        }
        CharSequence[] charSequenceArr = new CharSequence[3];
        charSequenceArr[0] = title;
        charSequenceArr[1] = ",";
        charSequenceArr[2] = accessPoint.getSecurity() == 0 ? context.getString(R$string.accessibility_wifi_security_type_none) : context.getString(R$string.accessibility_wifi_security_type_secured);
        return TextUtils.concat(charSequenceArr);
    }

    private void postNotifyChanged() {
        TextView textView = this.mTitleView;
        if (textView != null) {
            textView.post(this.mNotifyChanged);
        }
    }

    static void setTitle(AccessPointPreference accessPointPreference, AccessPoint accessPoint) {
        accessPointPreference.setTitle(accessPoint.getTitle());
    }

    protected int getWidgetLayoutResourceId() {
        return R$layout.access_point_friction_widget;
    }

    @Override // androidx.preference.Preference
    protected void notifyChanged() {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            postNotifyChanged();
        } else {
            super.notifyChanged();
        }
    }
}
