package com.motorola.taskbar.settings.prefs;

import android.content.Context;
import android.hardware.input.InputManager;
import android.hardware.input.InputSettings;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.SeekBar;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import com.motorola.taskbar.guide.R$id;

/* JADX INFO: loaded from: classes2.dex */
public class SeekbarPreference extends Preference {
    private InputManager mIm;
    private int mOldSpeed;
    private SeekBar mSeekbar;

    public SeekbarPreference(Context context) {
        this(context, null);
    }

    public SeekbarPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SeekbarPreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public SeekbarPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mIm = (InputManager) getContext().getSystemService("input");
        this.mOldSpeed = getPointerSpeed();
        Log.d("SeekbarPreference", "mOldSpeed: " + this.mOldSpeed);
        Log.d("SeekbarPreference", "MIN_POINTER_SPEED: -7");
    }

    private int getPointerSpeed() {
        return InputSettings.getPointerSpeed(getContext());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setPointerSpeed(Context context, int i) {
        InputSettings.setPointerSpeed(context, i);
    }

    @Override // androidx.preference.Preference
    public void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        SeekBar seekBar = (SeekBar) preferenceViewHolder.findViewById(R$id.seekbar);
        this.mSeekbar = seekBar;
        seekBar.setMax(14);
        this.mSeekbar.setProgress(this.mOldSpeed + 7);
        this.mSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.motorola.taskbar.settings.prefs.SeekbarPreference.1
            private boolean tracking = false;

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar2, int i, boolean z) {
                if (!z || this.tracking) {
                    return;
                }
                SeekbarPreference seekbarPreference = SeekbarPreference.this;
                seekbarPreference.setPointerSpeed(seekbarPreference.getContext(), seekBar2.getProgress() - 7);
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar2) {
                this.tracking = true;
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar2) {
                this.tracking = false;
                SeekbarPreference seekbarPreference = SeekbarPreference.this;
                seekbarPreference.setPointerSpeed(seekbarPreference.getContext(), seekBar2.getProgress() - 7);
            }
        });
    }

    public void updatePointerSpeed() {
        this.mSeekbar.setProgress(getPointerSpeed() + 7);
    }
}
