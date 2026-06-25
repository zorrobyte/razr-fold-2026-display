package com.android.settingslib.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import androidx.core.content.ContextCompat;
import androidx.preference.Preference;
import com.android.settingslib.widget.preference.statusbanner.R$drawable;
import com.android.settingslib.widget.preference.statusbanner.R$layout;
import com.android.settingslib.widget.preference.statusbanner.R$styleable;
import com.android.settingslib.widget.theme.R$color;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: StatusBannerPreference.kt */
/* JADX INFO: loaded from: classes.dex */
public final class StatusBannerPreference extends Preference {
    private BannerStatus buttonLevel;
    private String buttonText;
    private BannerStatus iconLevel;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* JADX INFO: compiled from: StatusBannerPreference.kt */
    public final class BannerStatus {
        private static final /* synthetic */ EnumEntries $ENTRIES;
        private static final /* synthetic */ BannerStatus[] $VALUES;
        public static final BannerStatus GENERIC = new BannerStatus("GENERIC", 0);
        public static final BannerStatus LOW = new BannerStatus("LOW", 1);
        public static final BannerStatus MEDIUM = new BannerStatus("MEDIUM", 2);
        public static final BannerStatus HIGH = new BannerStatus("HIGH", 3);
        public static final BannerStatus OFF = new BannerStatus("OFF", 4);
        public static final BannerStatus LOADING_DETERMINATE = new BannerStatus("LOADING_DETERMINATE", 5);
        public static final BannerStatus LOADING_INDETERMINATE = new BannerStatus("LOADING_INDETERMINATE", 6);

        private static final /* synthetic */ BannerStatus[] $values() {
            return new BannerStatus[]{GENERIC, LOW, MEDIUM, HIGH, OFF, LOADING_DETERMINATE, LOADING_INDETERMINATE};
        }

        static {
            BannerStatus[] bannerStatusArr$values = $values();
            $VALUES = bannerStatusArr$values;
            $ENTRIES = EnumEntriesKt.enumEntries(bannerStatusArr$values);
        }

        private BannerStatus(String str, int i) {
        }

        public static BannerStatus valueOf(String str) {
            return (BannerStatus) Enum.valueOf(BannerStatus.class, str);
        }

        public static BannerStatus[] values() {
            return (BannerStatus[]) $VALUES.clone();
        }
    }

    /* JADX INFO: compiled from: StatusBannerPreference.kt */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[BannerStatus.values().length];
            try {
                iArr[BannerStatus.LOW.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[BannerStatus.MEDIUM.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[BannerStatus.HIGH.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[BannerStatus.OFF.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public StatusBannerPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0, 12, null);
        context.getClass();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public StatusBannerPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        context.getClass();
        BannerStatus bannerStatus = BannerStatus.GENERIC;
        this.iconLevel = bannerStatus;
        this.buttonLevel = bannerStatus;
        this.buttonText = "";
        setLayoutResource(R$layout.settingslib_expressive_preference_statusbanner);
        setSelectable(false);
        initAttributes(context, attributeSet, i);
    }

    public /* synthetic */ StatusBannerPreference(Context context, AttributeSet attributeSet, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i, (i3 & 8) != 0 ? 0 : i2);
    }

    private final int getBackgroundColor(BannerStatus bannerStatus) {
        int i = WhenMappings.$EnumSwitchMapping$0[bannerStatus.ordinal()];
        return i != 1 ? i != 2 ? i != 3 ? i != 4 ? ContextCompat.getColor(getContext(), R$color.settingslib_materialColorPrimary) : ContextCompat.getColor(getContext(), com.android.settingslib.widget.preference.statusbanner.R$color.settingslib_expressive_color_status_level_off) : ContextCompat.getColor(getContext(), com.android.settingslib.widget.preference.statusbanner.R$color.settingslib_expressive_color_status_level_high) : ContextCompat.getColor(getContext(), com.android.settingslib.widget.preference.statusbanner.R$color.settingslib_expressive_color_status_level_medium) : ContextCompat.getColor(getContext(), com.android.settingslib.widget.preference.statusbanner.R$color.settingslib_expressive_color_status_level_low);
    }

    private final Drawable getIconDrawable(BannerStatus bannerStatus) {
        int i = WhenMappings.$EnumSwitchMapping$0[bannerStatus.ordinal()];
        if (i == 1) {
            return ContextCompat.getDrawable(getContext(), R$drawable.settingslib_expressive_icon_status_level_low);
        }
        if (i == 2) {
            return ContextCompat.getDrawable(getContext(), R$drawable.settingslib_expressive_icon_status_level_medium);
        }
        if (i == 3) {
            return ContextCompat.getDrawable(getContext(), R$drawable.settingslib_expressive_icon_status_level_high);
        }
        if (i != 4) {
            return null;
        }
        return ContextCompat.getDrawable(getContext(), R$drawable.settingslib_expressive_icon_status_level_off);
    }

    private final void initAttributes(Context context, AttributeSet attributeSet, int i) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.StatusBanner, i, 0);
        setIconLevel(toBannerStatus(typedArrayObtainStyledAttributes.getInteger(R$styleable.StatusBanner_iconLevel, 0)));
        if (getIcon() == null) {
            setIcon(getIconDrawable(this.iconLevel));
        } else {
            updateIconTint(this.iconLevel);
        }
        setButtonLevel(toBannerStatus(typedArrayObtainStyledAttributes.getInteger(R$styleable.StatusBanner_buttonLevel, 0)));
        String string = typedArrayObtainStyledAttributes.getString(R$styleable.StatusBanner_buttonText);
        if (string == null) {
            string = "";
        }
        setButtonText(string);
        typedArrayObtainStyledAttributes.recycle();
    }

    private final void setButtonText(String str) {
        this.buttonText = str;
        notifyChanged();
    }

    private final BannerStatus toBannerStatus(int i) {
        switch (i) {
            case 1:
                return BannerStatus.LOW;
            case 2:
                return BannerStatus.MEDIUM;
            case 3:
                return BannerStatus.HIGH;
            case 4:
                return BannerStatus.OFF;
            case 5:
                return BannerStatus.LOADING_DETERMINATE;
            case 6:
                return BannerStatus.LOADING_INDETERMINATE;
            default:
                return BannerStatus.GENERIC;
        }
    }

    private final void updateIconTint(BannerStatus bannerStatus) {
        Drawable icon = getIcon();
        if (icon != null) {
            icon.setTintList(ColorStateList.valueOf(getBackgroundColor(bannerStatus)));
        }
    }

    public final void setButtonLevel(BannerStatus bannerStatus) {
        bannerStatus.getClass();
        this.buttonLevel = bannerStatus;
        notifyChanged();
    }

    public final void setIconLevel(BannerStatus bannerStatus) {
        bannerStatus.getClass();
        this.iconLevel = bannerStatus;
        updateIconTint(bannerStatus);
        notifyChanged();
    }
}
