package com.motorola.taskbar.settings;

import android.content.ContentResolver;
import android.content.res.Resources;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.motorola.taskbar.guide.R$array;
import com.motorola.taskbar.guide.R$dimen;
import com.motorola.taskbar.guide.R$id;
import com.motorola.taskbar.guide.R$layout;

/* JADX INFO: loaded from: classes2.dex */
public class DisplayFragment extends Fragment implements View.OnClickListener {
    private TextView displayDesc;
    private SeekBar displaySeekBar;
    private TextView fontDesc;
    private SeekBar fontSeekBar;
    private ContentResolver mCr;
    private DisplayDensityUtils mDisplayDensityUtils;
    protected String[] mDisplayScaleEntries;
    protected float[] mDisplayScaleValues;
    protected String[] mFontEntries;
    protected float[] mFontScaleValues;
    public static final String TAG = DisplayFragment.class.getSimpleName();
    private static int DISPLAY_LEVEL_MAX = 3;
    private static int FONT_LEVEL_MAX = 2;
    private int mDisplayLevel = 1;
    private int mFontLevel = 1;
    private boolean displayTrackingStart = false;
    private boolean fontTrackingStart = false;
    private SeekBar.OnSeekBarChangeListener displayListener = new SeekBar.OnSeekBarChangeListener() { // from class: com.motorola.taskbar.settings.DisplayFragment.1
        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
            String str = DisplayFragment.TAG;
            Log.e(str, "display onProgressChanged: progress = " + i + "  fromUser = " + z);
            if (z && !DisplayFragment.this.displayTrackingStart) {
                DisplayFragment.this.commitDisplay(i);
            }
            if (!z && i != DisplayFragment.this.mDisplayLevel) {
                Log.e(str, "onProgressChanged: progress != mDisplayLevel");
                seekBar.setProgress(DisplayFragment.this.mDisplayLevel, false);
            }
            boolean unused = DisplayFragment.this.displayTrackingStart;
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onStartTrackingTouch(SeekBar seekBar) {
            DisplayFragment.this.displayTrackingStart = true;
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onStopTrackingTouch(SeekBar seekBar) {
            DisplayFragment.this.commitDisplay(seekBar.getProgress());
            DisplayFragment.this.displayTrackingStart = false;
        }
    };
    private SeekBar.OnSeekBarChangeListener fontListener = new SeekBar.OnSeekBarChangeListener() { // from class: com.motorola.taskbar.settings.DisplayFragment.2
        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
            String str = DisplayFragment.TAG;
            Log.e(str, "font onProgressChanged: progress = " + i + "  fromUser = " + z);
            if (z && !DisplayFragment.this.fontTrackingStart) {
                DisplayFragment.this.commitFont(i);
            }
            if (!z && i != DisplayFragment.this.mFontLevel) {
                Log.e(str, "onProgressChanged: progress != mFontLevel");
                seekBar.setProgress(DisplayFragment.this.mFontLevel, false);
            }
            boolean unused = DisplayFragment.this.fontTrackingStart;
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onStartTrackingTouch(SeekBar seekBar) {
            DisplayFragment.this.fontTrackingStart = true;
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onStopTrackingTouch(SeekBar seekBar) {
            DisplayFragment.this.commitFont(seekBar.getProgress());
            DisplayFragment.this.fontTrackingStart = false;
        }
    };

    /* JADX INFO: Access modifiers changed from: private */
    public void commitDisplay(int i) {
        if (getContext().getDisplay().getDisplayId() == 0) {
            Log.e(TAG, "not support on default display");
            return;
        }
        this.mDisplayLevel = i;
        Log.e(TAG, "commitDisplay mDisplayLevel = " + this.mDisplayLevel);
        float f = this.mDisplayScaleValues[i];
        int defaultDensity = (int) (((float) this.mDisplayDensityUtils.getDefaultDensity()) * f);
        MotorolaSettingsSystem.putDisplayScale(this.mCr, f, SettingsActivity.sConnectType);
        this.displayDesc.setText(this.mDisplayScaleEntries[i]);
        setDensityForDeskModeDisplay(defaultDensity);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void commitFont(int i) {
        if (getContext().getDisplay().getDisplayId() == 0) {
            Log.e(TAG, "not support on default display");
            return;
        }
        this.mFontLevel = i;
        Log.e(TAG, "commitFont mFontLevel = " + this.mFontLevel);
        MotorolaSettingsSystem.putFontScale(this.mCr, this.mFontScaleValues[i], SettingsActivity.sConnectType);
        this.fontDesc.setText(this.mFontEntries[i]);
        WindowManagerUtil.setFontScale(SettingsActivity.sDisplayId, this.mFontScaleValues[i]);
    }

    public static DisplayFragment getInstance() {
        return new DisplayFragment();
    }

    private void resetLayoutParams(View view, int i) {
        int dimensionPixelOffset = getContext().getResources().getDimensionPixelOffset(R$dimen.icon_padding);
        if (i > 1) {
            int i2 = (dimensionPixelOffset / 4) * (5 - i);
            ImageView imageView = (ImageView) view.findViewById(R$id.btn_display_minus);
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) imageView.getLayoutParams();
            marginLayoutParams.setMarginEnd(i2);
            imageView.setLayoutParams(marginLayoutParams);
            ImageView imageView2 = (ImageView) view.findViewById(R$id.btn_display_plus);
            ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) imageView2.getLayoutParams();
            marginLayoutParams2.setMarginStart(i2);
            imageView2.setLayoutParams(marginLayoutParams2);
            ImageView imageView3 = (ImageView) view.findViewById(R$id.btn_font_minus);
            ViewGroup.MarginLayoutParams marginLayoutParams3 = (ViewGroup.MarginLayoutParams) imageView3.getLayoutParams();
            marginLayoutParams3.setMarginEnd(i2);
            imageView3.setLayoutParams(marginLayoutParams3);
            ImageView imageView4 = (ImageView) view.findViewById(R$id.btn_font_plus);
            ViewGroup.MarginLayoutParams marginLayoutParams4 = (ViewGroup.MarginLayoutParams) imageView4.getLayoutParams();
            marginLayoutParams4.setMarginStart(i2);
            imageView4.setLayoutParams(marginLayoutParams4);
        }
    }

    private void setDensityForDeskModeDisplay(int i) {
        Display display = ((DisplayManager) getContext().getSystemService("display")).getDisplay(SettingsActivity.sDisplayId);
        if (display != null) {
            if ((display.getFlags() & 64) != 0) {
                DisplayDensityUtils.setForcedDisplayDensity(display.getDisplayId(), i);
            }
        } else {
            Log.e(TAG, "setDensityForDeskModeDisplay ERROR for id = " + SettingsActivity.sDisplayId);
            DisplayDensityUtils.setForcedDisplayDensity(SettingsActivity.sDisplayId, i);
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R$id.btn_display_minus) {
            Log.e(TAG, "onClick btn_display_minus");
            int i = this.mDisplayLevel - 1;
            this.mDisplayLevel = i;
            if (i < 0) {
                this.mDisplayLevel = 0;
                return;
            } else {
                commitDisplay(i);
                this.displaySeekBar.setProgress(this.mDisplayLevel);
                return;
            }
        }
        if (id == R$id.btn_display_plus) {
            Log.e(TAG, "onClick btn_display_plus");
            int i2 = this.mDisplayLevel + 1;
            this.mDisplayLevel = i2;
            int i3 = DISPLAY_LEVEL_MAX;
            if (i2 > i3) {
                this.mDisplayLevel = i3;
                return;
            } else {
                commitDisplay(i2);
                this.displaySeekBar.setProgress(this.mDisplayLevel);
                return;
            }
        }
        if (id == R$id.btn_font_minus) {
            Log.e(TAG, "onClick btn_font_minus");
            int i4 = this.mFontLevel - 1;
            this.mFontLevel = i4;
            if (i4 < 0) {
                this.mFontLevel = 0;
                return;
            } else {
                commitFont(i4);
                this.fontSeekBar.setProgress(this.mFontLevel);
                return;
            }
        }
        if (id == R$id.btn_font_plus) {
            Log.e(TAG, "onClick btn_font_plus");
            int i5 = this.mFontLevel + 1;
            this.mFontLevel = i5;
            int i6 = FONT_LEVEL_MAX;
            if (i5 > i6) {
                this.mFontLevel = i6;
            } else {
                commitFont(i5);
                this.fontSeekBar.setProgress(this.mFontLevel);
            }
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        Log.d(TAG, "onCreateView: ");
        Resources resources = getContext().getResources();
        ContentResolver contentResolver = getContext().getContentResolver();
        this.mCr = contentResolver;
        float fontScale = MotorolaSettingsSystem.getFontScale(contentResolver, SettingsActivity.sConnectType);
        this.mFontEntries = resources.getStringArray(R$array.font_size_descriptions);
        String[] stringArray = resources.getStringArray(R$array.entryvalues_font_size);
        FONT_LEVEL_MAX = stringArray.length - 1;
        this.mFontScaleValues = new float[stringArray.length];
        int length = stringArray.length;
        for (int i = 0; i < length; i++) {
            this.mFontScaleValues[i] = Float.parseFloat(stringArray[i]);
            if (fontScale == this.mFontScaleValues[i]) {
                this.mFontLevel = i;
            }
        }
        int i2 = length - 1;
        if (fontScale > this.mFontScaleValues[i2]) {
            this.mFontLevel = i2;
        }
        DisplayDensityUtils displayDensityUtils = new DisplayDensityUtils(getContext());
        this.mDisplayDensityUtils = displayDensityUtils;
        this.mDisplayScaleValues = displayDensityUtils.getValues();
        this.mDisplayLevel = DisplayDensityUtils.getCurrentScaleIndex(getContext());
        this.mDisplayScaleEntries = this.mDisplayDensityUtils.getEntries();
        DISPLAY_LEVEL_MAX = this.mDisplayScaleValues.length - 1;
        Log.e(TAG, "onCreateView mDisplayLevel = " + this.mDisplayLevel + "  , mFontLevel = " + this.mFontLevel + " for display " + getContext().getDisplay().getDisplayId());
        View viewInflate = layoutInflater.inflate(R$layout.display_layout, viewGroup, false);
        resetLayoutParams(viewInflate, this.mDisplayLevel);
        viewInflate.findViewById(R$id.btn_display_minus).setOnClickListener(this);
        viewInflate.findViewById(R$id.btn_display_plus).setOnClickListener(this);
        viewInflate.findViewById(R$id.btn_font_minus).setOnClickListener(this);
        viewInflate.findViewById(R$id.btn_font_plus).setOnClickListener(this);
        this.displayDesc = (TextView) viewInflate.findViewById(R$id.display_desc);
        this.fontDesc = (TextView) viewInflate.findViewById(R$id.font_desc);
        this.displaySeekBar = (SeekBar) viewInflate.findViewById(R$id.display_seekbar);
        this.fontSeekBar = (SeekBar) viewInflate.findViewById(R$id.font_seekbar);
        this.displaySeekBar.setMin(0);
        this.fontSeekBar.setMin(0);
        this.displaySeekBar.setMax(this.mDisplayScaleValues.length - 1);
        this.fontSeekBar.setMax(stringArray.length - 1);
        this.displaySeekBar.setProgress(this.mDisplayLevel);
        this.fontSeekBar.setProgress(this.mFontLevel);
        this.displayDesc.setText(this.mDisplayScaleEntries[this.mDisplayLevel]);
        this.fontDesc.setText(this.mFontEntries[this.mFontLevel]);
        this.displaySeekBar.setOnSeekBarChangeListener(this.displayListener);
        this.fontSeekBar.setOnSeekBarChangeListener(this.fontListener);
        return viewInflate;
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        if (this.displaySeekBar != null) {
            Log.d(TAG, "onPause: displaySeekBar set listener null");
            this.displaySeekBar.setOnSeekBarChangeListener(null);
        }
        if (this.fontSeekBar != null) {
            Log.d(TAG, "onPause: fontSeekBar set listener null");
            this.fontSeekBar.setOnSeekBarChangeListener(null);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        if (this.displaySeekBar != null) {
            Log.d(TAG, "onResume: displaySeekBar set listener displayListener");
            this.displaySeekBar.setOnSeekBarChangeListener(this.displayListener);
        }
        if (this.fontSeekBar != null) {
            Log.d(TAG, "onResume: fontSeekBar set listener fontListener");
            this.fontSeekBar.setOnSeekBarChangeListener(this.fontListener);
        }
    }
}
