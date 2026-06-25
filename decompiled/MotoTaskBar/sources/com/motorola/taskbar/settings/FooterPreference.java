package com.motorola.taskbar.settings;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import com.android.settingslib.RestrictedLockUtils;
import com.motorola.taskbar.guide.R$id;
import com.motorola.taskbar.guide.R$layout;

/* JADX INFO: loaded from: classes2.dex */
public class FooterPreference extends Preference {
    private Context mContext;

    public FooterPreference(Context context) {
        super(context);
        init(context);
    }

    public FooterPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        setLayoutResource(R$layout.preference_footer);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$0(View view) {
        RestrictedLockUtils.sendShowAdminSupportDetailsIntent(this.mContext, null);
    }

    @Override // androidx.preference.Preference
    public void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        preferenceViewHolder.setDividerAllowedAbove(false);
        preferenceViewHolder.setDividerAllowedBelow(false);
        preferenceViewHolder.itemView.setClickable(false);
        preferenceViewHolder.findViewById(R$id.more_detail).setOnClickListener(new View.OnClickListener() { // from class: com.motorola.taskbar.settings.FooterPreference$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$onBindViewHolder$0(view);
            }
        });
    }

    public void setAdmin(RestrictedLockUtils.EnforcedAdmin enforcedAdmin) {
    }
}
