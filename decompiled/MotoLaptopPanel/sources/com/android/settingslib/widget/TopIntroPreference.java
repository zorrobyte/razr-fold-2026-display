package com.android.settingslib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import androidx.preference.Preference;
import com.android.settingslib.widget.preference.topintro.R$layout;
import com.android.settingslib.widget.theme.R$styleable;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.ranges.RangesKt;

/* JADX INFO: compiled from: TopIntroPreference.kt */
/* JADX INFO: loaded from: classes.dex */
public class TopIntroPreference extends Preference {
    private boolean isCollapsable;
    private int minLines;
    public static final Companion Companion = new Companion(null);
    private static final int[] COLLAPSABLE_TEXT_VIEW_ATTRS = R$styleable.CollapsableTextView;
    private static final int MIN_LINES = R$styleable.CollapsableTextView_android_minLines;
    private static final int IS_COLLAPSABLE = R$styleable.CollapsableTextView_isCollapsable;

    /* JADX INFO: compiled from: TopIntroPreference.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public TopIntroPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0, 12, null);
        context.getClass();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TopIntroPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        context.getClass();
        this.minLines = 2;
        setLayoutResource(R$layout.settingslib_expressive_top_intro);
        initAttributes(context, attributeSet, i);
        setSelectable(false);
    }

    public /* synthetic */ TopIntroPreference(Context context, AttributeSet attributeSet, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i, (i3 & 8) != 0 ? 0 : i2);
    }

    private final void initAttributes(Context context, AttributeSet attributeSet, int i) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, COLLAPSABLE_TEXT_VIEW_ATTRS, i, 0);
        boolean z = typedArrayObtainStyledAttributes.getBoolean(IS_COLLAPSABLE, false);
        this.isCollapsable = z;
        this.minLines = RangesKt.coerceIn(typedArrayObtainStyledAttributes.getInt(MIN_LINES, z ? 2 : 10), 1, 10);
        typedArrayObtainStyledAttributes.recycle();
    }
}
