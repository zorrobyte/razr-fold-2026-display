package com.motorola.taskbar.settings;

import android.R;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.DisplayInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.motorola.android.provider.MotorolaSettings;
import com.motorola.internal.app.MotoDesktopManager;
import com.motorola.taskbar.guide.R$id;
import com.motorola.taskbar.guide.R$layout;
import com.motorola.taskbar.guide.R$string;
import com.motorola.taskbar.settings.ModeFragment;
import com.motorola.taskbar.settings.MotorolaSettingsSystem;
import com.motorola.taskbar.settings.utils.Utils;
import com.motorola.taskbar.utils.ExtendedFunction;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public class ModeFragment extends Fragment implements View.OnClickListener, DisplayManager.DisplayListener {
    private static final String TAG = ModeFragment.class.getSimpleName();
    private Display.Mode activeMode;
    private ListPopupWindow listPopupWindow;
    private DisplayManager mDisplayManager;
    private LinearLayout mPhoneUiLayout;
    private RadioButton mRBDPhone;
    private RadioButton mRBDesktop;
    private RadioButton mRBLand;
    private RadioButton mRBMirror;
    private RadioButton mRBPortrait;
    private TextView mTvOrientation;
    private TextView mTvResolution;
    private List lists = new ArrayList();
    private List mSupportedModes = new ArrayList();
    private boolean isTablet = false;

    class ResolutionAdapter extends BaseAdapter {
        public static /* synthetic */ void $r8$lambda$RlGfT6EwE8PZjVpoFdQRYMFZ80U(View view) {
        }

        ResolutionAdapter() {
        }

        @Override // android.widget.Adapter
        public int getCount() {
            return ModeFragment.this.lists.size();
        }

        @Override // android.widget.Adapter
        public Object getItem(int i) {
            return null;
        }

        @Override // android.widget.Adapter
        public long getItemId(int i) {
            return 0L;
        }

        @Override // android.widget.Adapter
        public View getView(int i, View view, ViewGroup viewGroup) {
            TextView textView = (TextView) View.inflate(ModeFragment.this.getContext(), R.layout.simple_list_item_1, null);
            textView.setText((CharSequence) ModeFragment.this.lists.get(i));
            if (((String) ModeFragment.this.lists.get(i)).equals(ModeFragment.this.mTvResolution.getText())) {
                textView.setEnabled(false);
                textView.setOnClickListener(new View.OnClickListener() { // from class: com.motorola.taskbar.settings.ModeFragment$ResolutionAdapter$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        ModeFragment.ResolutionAdapter.$r8$lambda$RlGfT6EwE8PZjVpoFdQRYMFZ80U(view2);
                    }
                });
            }
            return textView;
        }
    }

    public static /* synthetic */ int $r8$lambda$6LqMYqlg1J2iYDomBPTe8v3j_gs(Display.Mode mode, Display.Mode mode2) {
        int physicalWidth = mode.getPhysicalWidth();
        int physicalHeight = mode.getPhysicalHeight();
        int iMax = Math.max(physicalWidth, physicalHeight);
        int iMin = Math.min(physicalWidth, physicalHeight);
        int physicalWidth2 = mode2.getPhysicalWidth();
        int physicalHeight2 = mode2.getPhysicalHeight();
        int iMax2 = Math.max(physicalWidth2, physicalHeight2);
        int iCompare = Integer.compare(Math.min(physicalWidth2, physicalHeight2), iMin);
        int iCompare2 = Integer.compare(iMax2, iMax);
        int iCompare3 = Float.compare(mode2.getRefreshRate(), mode.getRefreshRate());
        int iCompare4 = Integer.compare(physicalWidth2 / physicalHeight2, physicalWidth / physicalHeight);
        if (iCompare != 0) {
            return iCompare;
        }
        if (iCompare2 != 0) {
            return iCompare2;
        }
        if (iCompare3 != 0) {
            return iCompare3;
        }
        if (iCompare4 != 0) {
            return iCompare4;
        }
        return 0;
    }

    private DisplayInfo getCurrentDisplayInfo() {
        if (getActivity() == null) {
            return null;
        }
        DisplayInfo displayInfo = new DisplayInfo();
        getActivity().getDisplay().getDisplayInfo(displayInfo);
        Log.d(TAG, "getCurrentDisplayInfo: " + displayInfo.toString());
        return displayInfo;
    }

    private static String getDisplayModeText(Display.Mode mode, boolean z) {
        if (z) {
            return Math.max(mode.getPhysicalWidth(), mode.getPhysicalHeight()) + " x " + Math.min(mode.getPhysicalWidth(), mode.getPhysicalHeight());
        }
        return Math.min(mode.getPhysicalWidth(), mode.getPhysicalHeight()) + " x " + Math.max(mode.getPhysicalWidth(), mode.getPhysicalHeight());
    }

    public static ModeFragment getInstance() {
        return new ModeFragment();
    }

    private float getRefreshRate() {
        Display.Mode mode = this.activeMode;
        if (mode == null || mode.getRefreshRate() <= 0.0f) {
            return 60.0f;
        }
        return this.activeMode.getRefreshRate();
    }

    private void handleItemClick(int i) {
        this.mTvResolution.setText((CharSequence) this.lists.get(i));
        MotorolaSettingsSystem.putDisplayScale(getContext().getContentResolver(), DisplayDensityUtils.getDefaultDisplayScale(), SettingsActivity.sConnectType);
        Display.Mode mode = (Display.Mode) this.mSupportedModes.get(i);
        if (this.mRBLand.isChecked() && mode.getPhysicalWidth() > mode.getPhysicalHeight()) {
            MotorolaSettingsSystem.setDisplayModeSetting(getContext().getContentResolver(), SettingsActivity.sConnectType, mode.getPhysicalWidth(), mode.getPhysicalHeight(), mode.getRefreshRate());
        }
        if (!this.mRBPortrait.isChecked() || mode.getPhysicalHeight() >= mode.getPhysicalWidth()) {
            return;
        }
        MotorolaSettingsSystem.setDisplayModeSetting(getContext().getContentResolver(), SettingsActivity.sConnectType, mode.getPhysicalHeight(), mode.getPhysicalWidth(), mode.getRefreshRate());
    }

    private void initUi(View view) {
        this.mTvResolution = (TextView) view.findViewById(R$id.tv_resolution);
        this.mTvOrientation = (TextView) view.findViewById(R$id.tv_orientation);
        RadioGroup radioGroup = (RadioGroup) view.findViewById(R$id.radio_group);
        this.mRBDesktop = (RadioButton) view.findViewById(R$id.rb_desktop);
        this.mRBMirror = (RadioButton) view.findViewById(R$id.rb_mirror);
        this.mRBDPhone = (RadioButton) view.findViewById(R$id.rb_phoneUi);
        this.mPhoneUiLayout = (LinearLayout) view.findViewById(R$id.ll_phoneUi);
        this.mRBDesktop.setOnClickListener(this);
        this.mRBMirror.setOnClickListener(this);
        this.mRBDPhone.setOnClickListener(this);
        this.mRBLand = (RadioButton) view.findViewById(R$id.rb_land);
        this.mRBPortrait = (RadioButton) view.findViewById(R$id.rb_portrait);
        this.mRBLand.setOnClickListener(this);
        this.mRBPortrait.setOnClickListener(this);
        if (MotoDesktopManager.isMobileUiMode(getContext().getDisplay())) {
            if (this.isTablet) {
                this.mRBDesktop.setVisibility(8);
            }
            this.mRBDesktop.setChecked(false);
            this.mRBDPhone.setChecked(true);
        }
        if (Utils.isDisplayVirtual(getContext()) && !MotoDesktopManager.isMobileUiMode(getActivity().getDisplay())) {
            this.mTvOrientation.setVisibility(0);
            radioGroup.setVisibility(0);
        }
        if (Utils.isDisplayVirtual(getContext())) {
            this.mPhoneUiLayout.setVisibility(0);
        }
        initDropAdapter();
        if (this.lists.size() < 2) {
            this.mTvResolution.setEnabled(false);
        }
        this.mTvResolution.setOnClickListener(new View.OnClickListener() { // from class: com.motorola.taskbar.settings.ModeFragment$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.lambda$initUi$1(view2);
            }
        });
        DisplayManager displayManager = (DisplayManager) getActivity().getSystemService("display");
        this.mDisplayManager = displayManager;
        displayManager.registerDisplayListener(this, new Handler());
    }

    private boolean isDisplayModeExist(Display.Mode mode) {
        for (Display.Mode mode2 : this.mSupportedModes) {
            if (mode2.getPhysicalWidth() == mode.getPhysicalWidth() && mode2.getPhysicalHeight() == mode.getPhysicalHeight()) {
                return true;
            }
        }
        return false;
    }

    private static boolean isDisplayModeMatch(Display.Mode mode, DisplayInfo displayInfo) {
        if (mode == null) {
            return false;
        }
        if (displayInfo.logicalWidth > displayInfo.logicalHeight) {
            if (Math.max(mode.getPhysicalWidth(), mode.getPhysicalHeight()) == displayInfo.logicalWidth && Math.min(mode.getPhysicalWidth(), mode.getPhysicalHeight()) == displayInfo.logicalHeight) {
                return true;
            }
        } else if (Math.min(mode.getPhysicalWidth(), mode.getPhysicalHeight()) == displayInfo.logicalWidth && Math.max(mode.getPhysicalWidth(), mode.getPhysicalHeight()) == displayInfo.logicalHeight) {
            return true;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initDropAdapter$2(int i, DialogInterface dialogInterface, int i2) {
        handleItemClick(i);
        dialogInterface.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initDropAdapter$3(AdapterView adapterView, View view, final int i, long j) {
        this.listPopupWindow.dismiss();
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(R$string.settings_mode_dialog_msg);
        builder.setPositiveButton(R$string.settings_mode_dialog_button, new DialogInterface.OnClickListener() { // from class: com.motorola.taskbar.settings.ModeFragment$$ExternalSyntheticLambda3
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                this.f$0.lambda$initDropAdapter$2(i, dialogInterface, i2);
            }
        });
        builder.setCancelable(true);
        builder.create().show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initUi$1(View view) {
        this.listPopupWindow.show();
    }

    private void setCurrentResolution() {
        DisplayInfo currentDisplayInfo = getCurrentDisplayInfo();
        this.activeMode = new Display.Mode(0, currentDisplayInfo.logicalWidth, currentDisplayInfo.logicalHeight, 0.0f);
        for (int i = 0; i < this.mSupportedModes.size(); i++) {
            if (isDisplayModeMatch((Display.Mode) this.mSupportedModes.get(i), currentDisplayInfo)) {
                this.mTvResolution.setText((CharSequence) this.lists.get(i));
            }
        }
    }

    private void setOrientation(boolean z) {
        DisplayInfo currentDisplayInfo = getCurrentDisplayInfo();
        int i = currentDisplayInfo.logicalWidth;
        int i2 = currentDisplayInfo.logicalHeight;
        if (z) {
            MotorolaSettingsSystem.setDisplayModeSetting(getContext().getContentResolver(), MotorolaSettingsSystem.ConnectType.RDP, Math.min(i, i2), Math.max(i, i2), getRefreshRate());
            this.mTvResolution.setText(Math.min(i, i2) + " x " + Math.max(i, i2));
        } else {
            MotorolaSettingsSystem.setDisplayModeSetting(getContext().getContentResolver(), MotorolaSettingsSystem.ConnectType.RDP, Math.max(i, i2), Math.min(i, i2), getRefreshRate());
            this.mTvResolution.setText(Math.max(i, i2) + " x " + Math.min(i, i2));
        }
        updateSupportResolutionList(getContext(), !z);
    }

    private void updateOrientation() {
        if (this.activeMode.getPhysicalWidth() > this.activeMode.getPhysicalHeight() && !this.mRBLand.isChecked()) {
            this.mRBLand.setChecked(true);
        } else {
            if (this.activeMode.getPhysicalWidth() >= this.activeMode.getPhysicalHeight() || this.mRBPortrait.isChecked()) {
                return;
            }
            this.mRBPortrait.setChecked(true);
        }
    }

    private void updateSupportResolutionList(Context context, boolean z) {
        Display.Mode[] desktopSupportedModes = ExtendedFunction.getDesktopSupportedModes(context.getDisplay());
        this.mSupportedModes.clear();
        for (Display.Mode mode : desktopSupportedModes) {
            if (!isDisplayModeExist(mode)) {
                this.mSupportedModes.add(mode);
            }
        }
        this.mSupportedModes.sort(new Comparator() { // from class: com.motorola.taskbar.settings.ModeFragment$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return ModeFragment.$r8$lambda$6LqMYqlg1J2iYDomBPTe8v3j_gs((Display.Mode) obj, (Display.Mode) obj2);
            }
        });
        Log.d(TAG, "mSupportedModes: " + this.mSupportedModes.toString());
        this.lists.clear();
        for (int i = 0; i < this.mSupportedModes.size(); i++) {
            this.lists.add(i, getDisplayModeText((Display.Mode) this.mSupportedModes.get(i), z));
        }
        Log.d(TAG, "lists: " + this.lists.toString());
    }

    public void initDropAdapter() {
        ListPopupWindow listPopupWindow = new ListPopupWindow(getContext());
        this.listPopupWindow = listPopupWindow;
        listPopupWindow.setAdapter(new ResolutionAdapter());
        this.listPopupWindow.setWidth(-2);
        this.listPopupWindow.setHeight(-2);
        this.listPopupWindow.setAnchorView(this.mTvResolution);
        this.listPopupWindow.setModal(true);
        this.listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.motorola.taskbar.settings.ModeFragment$$ExternalSyntheticLambda2
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(AdapterView adapterView, View view, int i, long j) {
                this.f$0.lambda$initDropAdapter$3(adapterView, view, i, j);
            }
        });
    }

    @Override // androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        DisplayInfo currentDisplayInfo = getCurrentDisplayInfo();
        Log.d(TAG, "displayInfo.name: " + currentDisplayInfo.name);
        this.isTablet = currentDisplayInfo.name.equals("R4Tablet");
        updateSupportResolutionList(context, currentDisplayInfo.logicalWidth > currentDisplayInfo.logicalHeight);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() == R$id.rb_desktop) {
            this.mRBMirror.setChecked(false);
            this.mRBDPhone.setChecked(false);
            MotorolaSettings.Global.putInt(getContext().getContentResolver(), "extra_display_mode", 1);
            return;
        }
        if (view.getId() == R$id.rb_mirror) {
            this.mRBDPhone.setChecked(false);
            this.mRBDesktop.setChecked(false);
            MotorolaSettings.Global.putInt(getContext().getContentResolver(), "extra_display_mode", 0);
        } else if (view.getId() == R$id.rb_phoneUi) {
            this.mRBMirror.setChecked(false);
            this.mRBDesktop.setChecked(false);
            MotorolaSettings.Global.putInt(getContext().getContentResolver(), "extra_display_mode", 2);
        } else if (view.getId() == R$id.rb_portrait) {
            setOrientation(true);
        } else if (view.getId() == R$id.rb_land) {
            setOrientation(false);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = Utils.isMobileUIAndPortrait(getContext()) ? layoutInflater.inflate(R$layout.mode_mobileui_layout, viewGroup, false) : layoutInflater.inflate(R$layout.mode_layout, viewGroup, false);
        initUi(viewInflate);
        return viewInflate;
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        DisplayManager displayManager = this.mDisplayManager;
        if (displayManager != null) {
            displayManager.unregisterDisplayListener(this);
        }
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public void onDisplayAdded(int i) {
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public void onDisplayChanged(int i) {
        if (getCurrentDisplayInfo() == null) {
            return;
        }
        DisplayInfo currentDisplayInfo = getCurrentDisplayInfo();
        updateSupportResolutionList(getActivity(), currentDisplayInfo.logicalWidth > currentDisplayInfo.logicalHeight);
        setCurrentResolution();
        updateOrientation();
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public void onDisplayRemoved(int i) {
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        setCurrentResolution();
        updateOrientation();
    }
}
