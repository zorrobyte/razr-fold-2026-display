package Y;

import android.widget.SeekBar;
import com.motorola.mobiledesktop.core.audio.MediaDeviceListAdapter;

/* JADX INFO: loaded from: classes.dex */
public final class v implements SeekBar.OnSeekBarChangeListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ MediaDeviceListAdapter f448a;

    public v(MediaDeviceListAdapter mediaDeviceListAdapter) {
        this.f448a = mediaDeviceListAdapter;
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public final void onProgressChanged(SeekBar seekBar, int i2, boolean z2) {
        if (this.f448a.mTrackingTouch) {
            synchronized (MediaDeviceListAdapter.class) {
                this.f448a.mSelectMediaDevice.i(i2);
            }
        }
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public final void onStartTrackingTouch(SeekBar seekBar) {
        this.f448a.mTrackingTouch = true;
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public final void onStopTrackingTouch(SeekBar seekBar) {
        this.f448a.mTrackingTouch = false;
    }
}
