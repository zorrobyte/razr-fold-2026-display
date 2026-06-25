package com.android.settingslib.widget;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.android.settingslib.widget.theme.R$drawable;
import com.android.settingslib.widget.theme.R$id;
import com.android.settingslib.widget.theme.R$layout;
import com.android.settingslib.widget.theme.R$string;
import com.android.settingslib.widget.theme.R$styleable;
import com.google.android.material.button.MaterialButton;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: CollapsableTextView.kt */
/* JADX INFO: loaded from: classes.dex */
public final class CollapsableTextView extends ConstraintLayout {
    private final MaterialButton collapseButton;
    private final CollapseButtonResources collapseButtonResources;
    private boolean isCollapsable;
    private boolean isCollapsed;
    private boolean isLearnMoreEnabled;
    private final LinkableTextView learnMoreTextView;
    private int minLines;
    private final TextView titleTextView;
    public static final Companion Companion = new Companion(null);
    private static final int gravityAttr = R$styleable.CollapsableTextView_android_gravity;
    private static final int minLinesAttr = R$styleable.CollapsableTextView_android_minLines;
    private static final int isCollapsableAttr = R$styleable.CollapsableTextView_isCollapsable;

    /* JADX INFO: compiled from: CollapsableTextView.kt */
    final class CollapseButtonResources {
        private final Drawable collapseIcon;
        private final String collapseText;
        private final Drawable expandIcon;
        private final String expandText;

        public CollapseButtonResources(Drawable drawable, Drawable drawable2, String str, String str2) {
            drawable.getClass();
            drawable2.getClass();
            str.getClass();
            str2.getClass();
            this.collapseIcon = drawable;
            this.expandIcon = drawable2;
            this.collapseText = str;
            this.expandText = str2;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof CollapseButtonResources)) {
                return false;
            }
            CollapseButtonResources collapseButtonResources = (CollapseButtonResources) obj;
            return Intrinsics.areEqual(this.collapseIcon, collapseButtonResources.collapseIcon) && Intrinsics.areEqual(this.expandIcon, collapseButtonResources.expandIcon) && Intrinsics.areEqual(this.collapseText, collapseButtonResources.collapseText) && Intrinsics.areEqual(this.expandText, collapseButtonResources.expandText);
        }

        public final Drawable getCollapseIcon() {
            return this.collapseIcon;
        }

        public final String getCollapseText() {
            return this.collapseText;
        }

        public final Drawable getExpandIcon() {
            return this.expandIcon;
        }

        public final String getExpandText() {
            return this.expandText;
        }

        public int hashCode() {
            return (((((this.collapseIcon.hashCode() * 31) + this.expandIcon.hashCode()) * 31) + this.collapseText.hashCode()) * 31) + this.expandText.hashCode();
        }

        public String toString() {
            return "CollapseButtonResources(collapseIcon=" + this.collapseIcon + ", expandIcon=" + this.expandIcon + ", collapseText=" + this.collapseText + ", expandText=" + this.expandText + ")";
        }
    }

    /* JADX INFO: compiled from: CollapsableTextView.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public CollapsableTextView(Context context) {
        this(context, null, 0, 6, null);
        context.getClass();
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public CollapsableTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
        context.getClass();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CollapsableTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        context.getClass();
        this.isCollapsable = true;
        this.minLines = 2;
        LayoutInflater.from(context).inflate(R$layout.settingslib_expressive_collapsable_textview, this);
        this.titleTextView = (TextView) findViewById(R.id.title);
        MaterialButton materialButton = (MaterialButton) findViewById(R$id.collapse_button);
        this.collapseButton = materialButton;
        this.learnMoreTextView = (LinkableTextView) findViewById(R$id.settingslib_expressive_learn_more);
        Drawable drawable = context.getDrawable(R$drawable.settingslib_expressive_icon_collapse);
        drawable.getClass();
        Drawable drawable2 = context.getDrawable(R$drawable.settingslib_expressive_icon_expand);
        drawable2.getClass();
        String string = context.getString(R$string.settingslib_expressive_text_collapse);
        string.getClass();
        String string2 = context.getString(R$string.settingslib_expressive_text_expand);
        string2.getClass();
        this.collapseButtonResources = new CollapseButtonResources(drawable, drawable2, string, string2);
        materialButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.settingslib.widget.CollapsableTextView.1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CollapsableTextView.this.isCollapsed = !r2.isCollapsed;
                CollapsableTextView.this.updateView();
            }
        });
        initAttributes(context, attributeSet, i);
    }

    public /* synthetic */ CollapsableTextView(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    private final void centerHorizontally(View view) {
        if (view instanceof MaterialButton) {
            ViewGroup.LayoutParams layoutParams = ((MaterialButton) view).getLayoutParams();
            layoutParams.getClass();
            ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) layoutParams;
            layoutParams2.startToStart = 0;
            layoutParams2.endToEnd = 0;
            return;
        }
        if (view instanceof TextView) {
            ((TextView) view).setGravity(17);
            return;
        }
        ViewGroup.LayoutParams layoutParams3 = view.getLayoutParams();
        layoutParams3.getClass();
        ConstraintLayout.LayoutParams layoutParams4 = (ConstraintLayout.LayoutParams) layoutParams3;
        layoutParams4.startToStart = 0;
        layoutParams4.endToEnd = 0;
    }

    private final void initAttributes(Context context, AttributeSet attributeSet, int i) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.CollapsableTextView, i, 0);
        int i2 = typedArrayObtainStyledAttributes.getInt(gravityAttr, 8388611);
        if (i2 == 1 || i2 == 16 || i2 == 17) {
            centerHorizontally(this.titleTextView);
            centerHorizontally(this.collapseButton);
            centerHorizontally(this.learnMoreTextView);
        }
        this.isCollapsable = typedArrayObtainStyledAttributes.getBoolean(isCollapsableAttr, true);
        this.minLines = typedArrayObtainStyledAttributes.getInt(minLinesAttr, 2);
        typedArrayObtainStyledAttributes.recycle();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateView() {
        if (this.isCollapsed) {
            MaterialButton materialButton = this.collapseButton;
            materialButton.setText(this.collapseButtonResources.getExpandText());
            materialButton.setIcon(this.collapseButtonResources.getExpandIcon());
            this.titleTextView.setMaxLines(this.minLines);
            this.titleTextView.setEllipsize(null);
            this.titleTextView.setScrollBarSize(0);
        } else {
            MaterialButton materialButton2 = this.collapseButton;
            materialButton2.setText(this.collapseButtonResources.getCollapseText());
            materialButton2.setIcon(this.collapseButtonResources.getCollapseIcon());
            this.titleTextView.setMaxLines(10);
            this.titleTextView.setEllipsize(TextUtils.TruncateAt.END);
        }
        this.collapseButton.setVisibility(this.isCollapsable ? 0 : 8);
        this.learnMoreTextView.setVisibility((!this.isLearnMoreEnabled || this.isCollapsed) ? 8 : 0);
    }
}
