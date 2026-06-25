package com.android.settingslib.inputmethod;

import android.content.Context;
import android.text.TextUtils;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class InputMethodSubtypePreference extends SwitchWithNoTextPreference {
    private final boolean mIsSystemLanguage;
    private final boolean mIsSystemLocale;

    InputMethodSubtypePreference(Context context, String str, CharSequence charSequence, Locale locale, Locale locale2) {
        super(context);
        setPersistent(false);
        setKey(str);
        setTitle(charSequence);
        if (locale == null) {
            this.mIsSystemLocale = false;
            this.mIsSystemLanguage = false;
        } else {
            boolean zEquals = locale.equals(locale2);
            this.mIsSystemLocale = zEquals;
            this.mIsSystemLanguage = zEquals || TextUtils.equals(locale.getLanguage(), locale2.getLanguage());
        }
    }
}
