package com.android.settingslib.widget;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.preference.R$styleable;
import com.android.settingslib.widget.mainswitch.R$id;
import com.android.settingslib.widget.mainswitch.R$layout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class MainSwitchBar extends LinearLayout implements CompoundButton.OnCheckedChangeListener {
    private final View mFrameView;
    protected TextView mSummaryView;
    protected CompoundButton mSwitch;
    private final List mSwitchChangeListeners;
    protected TextView mTextView;

    public class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator CREATOR = new Parcelable.Creator() { // from class: com.android.settingslib.widget.MainSwitchBar.SavedState.1
            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        boolean mChecked;
        boolean mVisible;

        private SavedState(Parcel parcel) {
            super(parcel);
            this.mChecked = ((Boolean) parcel.readValue(null)).booleanValue();
            this.mVisible = ((Boolean) parcel.readValue(null)).booleanValue();
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public String toString() {
            return "MainSwitchBar.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " checked=" + this.mChecked + " visible=" + this.mVisible + "}";
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeValue(Boolean.valueOf(this.mChecked));
            parcel.writeValue(Boolean.valueOf(this.mVisible));
        }
    }

    public MainSwitchBar(Context context) {
        this(context, null);
    }

    public MainSwitchBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MainSwitchBar(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public MainSwitchBar(Context context, AttributeSet attributeSet, int i, int i2) throws Exception {
        super(context, attributeSet, i, i2);
        this.mSwitchChangeListeners = new ArrayList();
        boolean zIsExpressiveTheme = SettingsThemeHelper.isExpressiveTheme(context);
        LayoutInflater.from(context).inflate(zIsExpressiveTheme ? R$layout.settingslib_expressive_main_switch_bar : R$layout.settingslib_main_switch_bar, this);
        setFocusable(true);
        setClickable(true);
        this.mFrameView = findViewById(R$id.frame);
        this.mTextView = (TextView) findViewById(R$id.switch_text);
        if (zIsExpressiveTheme) {
            this.mSummaryView = (TextView) findViewById(R$id.switch_summary);
        }
        this.mSwitch = (CompoundButton) findViewById(R.id.switch_widget);
        addOnSwitchChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.android.settingslib.widget.MainSwitchBar$$ExternalSyntheticLambda0
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                this.f$0.lambda$new$0(compoundButton, z);
            }
        });
        if (this.mSwitch.getVisibility() == 0) {
            this.mSwitch.setOnCheckedChangeListener(this);
        }
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.Preference, 0, 0);
            setTitle(typedArrayObtainStyledAttributes.getText(R$styleable.Preference_android_title));
            if (zIsExpressiveTheme) {
                setSummary(typedArrayObtainStyledAttributes.getText(R$styleable.Preference_android_summary));
            }
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(CompoundButton compoundButton, boolean z) {
        setChecked(z);
    }

    private void propagateChecked(boolean z) {
        setBackground(z);
        Iterator it = this.mSwitchChangeListeners.iterator();
        while (it.hasNext()) {
            ((CompoundButton.OnCheckedChangeListener) it.next()).onCheckedChanged(this.mSwitch, z);
        }
    }

    private void setBackground(boolean z) {
        this.mFrameView.setActivated(z);
        this.mTextView.setActivated(z);
    }

    public void addOnSwitchChangeListener(CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        if (this.mSwitchChangeListeners.contains(onCheckedChangeListener)) {
            return;
        }
        this.mSwitchChangeListeners.add(onCheckedChangeListener);
    }

    protected boolean callPreChangeListener() {
        return true;
    }

    public boolean isChecked() {
        return this.mSwitch.isChecked();
    }

    public boolean isShowing() {
        return getVisibility() == 0;
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        propagateChecked(z);
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.mSwitch.setChecked(savedState.mChecked);
        setChecked(savedState.mChecked);
        setBackground(savedState.mChecked);
        setVisibility(savedState.mVisible ? 0 : 8);
        this.mSwitch.setOnCheckedChangeListener(savedState.mVisible ? this : null);
        requestLayout();
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.mChecked = this.mSwitch.isChecked();
        savedState.mVisible = isShowing();
        return savedState;
    }

    @Override // android.view.View
    public boolean performClick() {
        if (callPreChangeListener()) {
            this.mSwitch.performClick();
        }
        return super.performClick();
    }

    public void setChecked(boolean z) {
        CompoundButton compoundButton = this.mSwitch;
        if (compoundButton != null) {
            compoundButton.setChecked(z);
        }
        setBackground(z);
    }

    @Override // android.view.View
    public void setEnabled(boolean z) {
        TextView textView;
        super.setEnabled(z);
        this.mTextView.setEnabled(z);
        this.mSwitch.setEnabled(z);
        this.mFrameView.setEnabled(z);
        this.mFrameView.setActivated(isChecked());
        this.mTextView.setActivated(isChecked());
        if (!SettingsThemeHelper.isExpressiveTheme(getContext()) || (textView = this.mSummaryView) == null) {
            return;
        }
        textView.setEnabled(z);
    }

    public void setSummary(CharSequence charSequence) {
        TextView textView = this.mSummaryView;
        if (textView != null) {
            textView.setText(charSequence);
            this.mSummaryView.setVisibility(TextUtils.isEmpty(charSequence) ? 8 : 0);
        }
    }

    public void setTitle(CharSequence charSequence) {
        TextView textView = this.mTextView;
        if (textView != null) {
            textView.setText(charSequence);
        }
    }
}
