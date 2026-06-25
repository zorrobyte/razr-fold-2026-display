package com.android.settingslib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import androidx.preference.PreferenceGroup;
import com.android.settingslib.widget.preference.banner.R$layout;
import com.android.settingslib.widget.preference.banner.R$styleable;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: BannerMessagePreferenceGroup.kt */
/* JADX INFO: loaded from: classes.dex */
public final class BannerMessagePreferenceGroup extends PreferenceGroup {
    public static final Companion Companion = new Companion(null);
    private final List childPreferences;
    private Drawable collapseIcon;
    private String collapseKey;
    private String collapseTitle;
    private String expandKey;
    private String expandTitle;

    /* JADX INFO: compiled from: BannerMessagePreferenceGroup.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public BannerMessagePreferenceGroup(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0, 12, null);
        context.getClass();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BannerMessagePreferenceGroup(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        context.getClass();
        this.childPreferences = new ArrayList();
        setPersistent(false);
        setLayoutResource(R$layout.settingslib_banner_message_preference_group);
        initAttributes(context, attributeSet, i);
    }

    public /* synthetic */ BannerMessagePreferenceGroup(Context context, AttributeSet attributeSet, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i, (i3 & 8) != 0 ? 0 : i2);
    }

    private final void initAttributes(Context context, AttributeSet attributeSet, int i) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.BannerMessagePreferenceGroup, i, 0);
        this.expandKey = typedArrayObtainStyledAttributes.getString(R$styleable.BannerMessagePreferenceGroup_expandKey);
        this.expandTitle = typedArrayObtainStyledAttributes.getString(R$styleable.BannerMessagePreferenceGroup_expandTitle);
        this.collapseKey = typedArrayObtainStyledAttributes.getString(R$styleable.BannerMessagePreferenceGroup_collapseKey);
        this.collapseTitle = typedArrayObtainStyledAttributes.getString(R$styleable.BannerMessagePreferenceGroup_collapseTitle);
        this.collapseIcon = typedArrayObtainStyledAttributes.getDrawable(R$styleable.BannerMessagePreferenceGroup_collapseIcon);
        typedArrayObtainStyledAttributes.recycle();
    }
}
