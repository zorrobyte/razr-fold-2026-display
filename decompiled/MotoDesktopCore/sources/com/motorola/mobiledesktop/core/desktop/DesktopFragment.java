package com.motorola.mobiledesktop.core.desktop;

import X.B0;
import X.v0;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import androidx.fragment.app.AbstractComponentCallbacksC0098j;
import com.motorola.mobiledesktop.core.R;
import e0.c;
import e0.d;

/* JADX INFO: loaded from: classes.dex */
public class DesktopFragment extends AbstractComponentCallbacksC0098j {
    private static final String TAG = "DesktopFragment";
    private d mConnectStateManager;
    private ImageView mHdmiConnectStatus;
    private Button mHdmiSeeHowItWorks;
    c mStateListener = new B0(2, this);

    public static DesktopFragment newInstance() {
        return new DesktopFragment();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateWhileHdmiConnect(boolean z2) {
        if (z2) {
            this.mHdmiConnectStatus.setBackgroundResource(R.drawable.ic_connect_circle_bg);
            this.mHdmiConnectStatus.setImageResource(R.drawable.ic_rdp_hdmi_connect);
        } else {
            this.mHdmiConnectStatus.setBackgroundResource(R.drawable.ic_no_connect_circle_bg);
            this.mHdmiConnectStatus.setImageResource(R.drawable.ic_rdp_hdmi);
        }
    }

    @Override // androidx.fragment.app.AbstractComponentCallbacksC0098j
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        d dVarD = d.d(getContext().getApplicationContext());
        this.mConnectStateManager = dVarD;
        dVarD.a(this.mStateListener);
        this.mHdmiSeeHowItWorks.setOnClickListener(new K.c(7, this));
    }

    @Override // androidx.fragment.app.AbstractComponentCallbacksC0098j
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        v0.a(TAG, "onCreateView");
        View viewInflate = layoutInflater.inflate(R.layout.fragment_desktop, viewGroup, false);
        Button button = (Button) viewInflate.findViewById(R.id.btn_hdmi_see_how_it_works);
        this.mHdmiSeeHowItWorks = button;
        button.setSelected(true);
        this.mHdmiConnectStatus = (ImageView) viewInflate.findViewById(R.id.im_hdmi_status);
        return viewInflate;
    }

    @Override // androidx.fragment.app.AbstractComponentCallbacksC0098j
    public void onDestroyView() {
        super.onDestroyView();
        this.mConnectStateManager.g(this.mStateListener);
    }

    @Override // androidx.fragment.app.AbstractComponentCallbacksC0098j
    public void onResume() {
        super.onResume();
        this.mHdmiConnectStatus.setBackgroundResource(R.drawable.ic_no_connect_circle_bg);
        this.mHdmiConnectStatus.setImageResource(R.drawable.ic_rdp_hdmi);
    }
}
