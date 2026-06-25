package Y;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public final class A implements View.OnClickListener {
    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        try {
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.setComponent(new ComponentName("com.android.settings", "com.android.settings.SubSettings"));
            intent.putExtra(":settings:show_fragment", "com.android.settings.bluetooth.BluetoothPairingDetail");
            intent.putExtra(":android:no_headers", false);
        } catch (ActivityNotFoundException e2) {
            e2.printStackTrace();
        }
    }
}
