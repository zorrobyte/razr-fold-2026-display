package Y;

import android.app.PendingIntent;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.fragment.app.FragmentActivity;
import com.motorola.mobiledesktop.core.R;
import com.motorola.mobiledesktop.core.audio.MediaOutputRouteFragment;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
public final class x extends Handler {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f455a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public Object f456b;

    public /* synthetic */ x() {
        this.f455a = 1;
    }

    public /* synthetic */ x(int i2, Object obj) {
        this.f455a = i2;
        this.f456b = obj;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public x(e0.A a2, Looper looper) {
        super(looper);
        this.f455a = 3;
        this.f456b = a2;
    }

    @Override // android.os.Handler
    public final void handleMessage(Message message) {
        Intent intentCreateAdminSupportIntent;
        int i2;
        switch (this.f455a) {
            case 0:
                if (message.what == 1000) {
                    MediaOutputRouteFragment mediaOutputRouteFragment = (MediaOutputRouteFragment) this.f456b;
                    if (!i.u(mediaOutputRouteFragment.getContext()).f412z) {
                        mediaOutputRouteFragment.getActivity().finish();
                        Context context = mediaOutputRouteFragment.getContext();
                        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) context.getSystemService(DevicePolicyManager.class);
                        if (devicePolicyManager != null && (intentCreateAdminSupportIntent = devicePolicyManager.createAdminSupportIntent("no_adjust_volume")) != null) {
                            context.startActivity(intentCreateAdminSupportIntent);
                        }
                    } else {
                        mediaOutputRouteFragment.updateList();
                    }
                    break;
                }
                break;
            case 1:
                int i3 = message.what;
                if (i3 == -3 || i3 == -2 || i3 == -1) {
                    ((DialogInterface.OnClickListener) message.obj).onClick((DialogInterface) ((WeakReference) this.f456b).get(), message.what);
                    break;
                } else if (i3 == 1) {
                    ((DialogInterface) message.obj).dismiss();
                    break;
                }
                break;
            case 2:
                if (message.what == 2) {
                    FragmentActivity fragmentActivity = (FragmentActivity) this.f456b;
                    androidx.fragment.app.z zVar = fragmentActivity.f1527c.f1637a.f1641e;
                    zVar.f1687q = false;
                    zVar.r = false;
                    zVar.C(4);
                    fragmentActivity.f1527c.f1637a.f1641e.G();
                } else {
                    super.handleMessage(message);
                }
                break;
            default:
                super.handleMessage(message);
                int i4 = message.what;
                e0.A a2 = (e0.A) this.f456b;
                if (i4 != 1) {
                    if (i4 == 2 && !a2.f2438q && (i2 = a2.f2421D) != -1) {
                        if (i2 != -1) {
                            int i5 = R.drawable.ic_wifi_quality_transparent;
                            String string = a2.f2424c.getString(R.string.wifi_quality_taskbar_content_poor_miracast);
                            Intent intent = new Intent("com.motorola.mobiledesktop.core.MIRACAST_QUALITY_POOR");
                            Context context2 = a2.f2424c;
                            intent.setPackage(context2.getPackageName());
                            a2.f2432k.addDesktopIcon("wifi_quality", i2, i5, 0, string, PendingIntent.getBroadcast(context2, 0, intent, 201326592));
                        }
                        a2.f2445y.sendEmptyMessageDelayed(1, 500L);
                    }
                    break;
                } else {
                    a2.b();
                    int i6 = a2.f2437p + 1;
                    a2.f2437p = i6;
                    long j2 = i6 % 3 == 0 ? 3000L : 500L;
                    if (!a2.f2438q && a2.f2421D != -1) {
                        a2.f2445y.sendEmptyMessageDelayed(2, j2);
                        break;
                    }
                }
                break;
        }
    }
}
