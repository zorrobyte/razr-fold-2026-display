package Y;

import android.widget.CompoundButton;
import com.motorola.mobiledesktop.core.audio.MediaDeviceListAdapter;

/* JADX INFO: loaded from: classes.dex */
public final class u implements CompoundButton.OnCheckedChangeListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ MediaDeviceListAdapter f447a;

    public u(MediaDeviceListAdapter mediaDeviceListAdapter) {
        this.f447a = mediaDeviceListAdapter;
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
        synchronized (MediaDeviceListAdapter.class) {
            i.u(this.f447a.getContext()).y(z2);
        }
    }
}
