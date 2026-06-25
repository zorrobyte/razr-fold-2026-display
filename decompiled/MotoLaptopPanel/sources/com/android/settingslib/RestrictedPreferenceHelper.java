package com.android.settingslib;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.UserHandle;
import android.util.AttributeSet;
import android.util.TypedValue;
import androidx.preference.Preference;
import com.android.settingslib.RestrictedLockUtils;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class RestrictedPreferenceHelper {
    private String mAttrUserRestriction;
    private final Context mContext;
    private boolean mDisabledByAdmin;
    private boolean mDisabledByEcm;
    private Intent mDisabledByEcmIntent;
    private boolean mDisabledByMoto;
    private boolean mDisabledSummary;
    RestrictedLockUtils.EnforcedAdmin mEnforcedAdmin;
    private final List mMotoUserRestrictionList;
    private final Preference mPreference;
    String packageName;
    int uid;

    public RestrictedPreferenceHelper(Context context, Preference preference, AttributeSet attributeSet) {
        this(context, preference, attributeSet, null, -1);
    }

    public RestrictedPreferenceHelper(Context context, Preference preference, AttributeSet attributeSet, String str, int i) {
        CharSequence text;
        this.mAttrUserRestriction = null;
        this.mDisabledSummary = false;
        this.mDisabledByEcmIntent = null;
        this.mMotoUserRestrictionList = new ArrayList();
        this.mContext = context;
        this.mPreference = preference;
        this.packageName = str;
        this.uid = i;
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.RestrictedPreference);
            TypedValue typedValuePeekValue = typedArrayObtainStyledAttributes.peekValue(R$styleable.RestrictedPreference_userRestriction);
            if (typedValuePeekValue == null || typedValuePeekValue.type != 3) {
                text = null;
            } else {
                int i2 = typedValuePeekValue.resourceId;
                text = i2 != 0 ? context.getText(i2) : typedValuePeekValue.string;
            }
            String string = text == null ? null : text.toString();
            this.mAttrUserRestriction = string;
            if (RestrictedLockUtilsInternal.hasBaseUserRestriction(context, string, UserHandle.myUserId())) {
                this.mAttrUserRestriction = null;
                return;
            }
            TypedValue typedValuePeekValue2 = typedArrayObtainStyledAttributes.peekValue(R$styleable.RestrictedPreference_useAdminDisabledSummary);
            if (typedValuePeekValue2 != null) {
                this.mDisabledSummary = typedValuePeekValue2.type == 18 && typedValuePeekValue2.data != 0;
            }
            String string2 = typedArrayObtainStyledAttributes.getString(R$styleable.RestrictedPreference_motoUserRestriction);
            if (string2 != null) {
                for (String str2 : string2.split("\\|")) {
                    this.mMotoUserRestrictionList.add(str2.strip());
                }
            }
            this.mDisabledByMoto = false;
        }
    }

    public boolean performClick() {
        if (this.mDisabledByAdmin) {
            RestrictedLockUtils.sendShowAdminSupportDetailsIntent(this.mContext, this.mEnforcedAdmin);
            return true;
        }
        if (!this.mDisabledByEcm) {
            return false;
        }
        this.mContext.startActivity(this.mDisabledByEcmIntent);
        return true;
    }

    public void useAdminDisabledSummary(boolean z) {
        this.mDisabledSummary = z;
    }
}
