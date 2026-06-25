package com.motorola.taskbar.settings.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.motorola.taskbar.guide.R$id;
import com.motorola.taskbar.guide.R$layout;
import com.motorola.taskbar.guide.R$string;
import com.motorola.taskbar.settings.utils.SettingsUtils;

/* JADX INFO: loaded from: classes2.dex */
public class DongleFragment extends Fragment implements SettingsUtils.DongleCallback {
    private TextView infoView;
    private Button upgradeButton;
    private LinearLayout upgradeLayout;
    private TextView versionView;

    public static DongleFragment getInstance() {
        return new DongleFragment();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDongleRefresh$0(View view) {
        this.upgradeButton.setText(R$string.dongle_upgrading);
        this.upgradeButton.setEnabled(false);
        this.infoView.setText(R$string.dongle_upgrading_tip);
        SettingsUtils.startDongleUpgrade();
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(R$layout.dongle_layout, viewGroup, false);
        this.versionView = (TextView) viewInflate.findViewById(R$id.dongle_version);
        this.infoView = (TextView) viewInflate.findViewById(R$id.upgrade_info);
        this.upgradeButton = (Button) viewInflate.findViewById(R$id.dongle_upgrade);
        this.upgradeLayout = (LinearLayout) viewInflate.findViewById(R$id.upgrade_layout);
        onDongleRefresh();
        return viewInflate;
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        SettingsUtils.removeDongleCallback();
    }

    @Override // com.motorola.taskbar.settings.utils.SettingsUtils.DongleCallback
    public void onDongleRefresh() {
        try {
            String str = SettingsUtils.mDongleVersion;
            Log.d("DongleFragment", "onDongleRefresh: version = " + str);
            this.versionView.setText(str);
            boolean zIsDongleUpgradeAvailable = SettingsUtils.isDongleUpgradeAvailable();
            Log.d("DongleFragment", "onDongleRefresh: UpgradeAvailable = " + zIsDongleUpgradeAvailable);
            if (!zIsDongleUpgradeAvailable) {
                this.upgradeLayout.setVisibility(8);
                return;
            }
            this.upgradeLayout.setVisibility(0);
            if (SettingsUtils.mDongleUpgrading) {
                this.upgradeButton.setText(R$string.dongle_upgrading);
                this.upgradeButton.setEnabled(false);
                this.infoView.setText(R$string.dongle_upgrading_tip);
            } else {
                this.upgradeButton.setEnabled(true);
                this.upgradeButton.setText(R$string.dongle_upgrade);
                this.infoView.setText(getResources().getString(R$string.dongle_new_version, SettingsUtils.mDongleNewVersion));
            }
            this.upgradeButton.setOnClickListener(new View.OnClickListener() { // from class: com.motorola.taskbar.settings.fragment.DongleFragment$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$onDongleRefresh$0(view);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        SettingsUtils.setDongleCallback(this);
    }
}
