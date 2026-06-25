package K;

import X.v0;
import android.R;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Message;
import android.view.View;
import androidx.appcompat.view.menu.q;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.p0;
import com.motorola.mobiledesktop.core.audio.MediaOutputRouteFragment;
import com.motorola.mobiledesktop.core.desktop.AboutActivity;
import com.motorola.mobiledesktop.core.desktop.DesktopActivity;
import com.motorola.mobiledesktop.core.desktop.DesktopFragment;
import com.motorola.mobiledesktop.core.desktop.tutorial.TutorialActivity;
import com.motorola.mobiledesktop.core.uinput.EventType;

/* JADX INFO: loaded from: classes.dex */
public final class c implements View.OnClickListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f194a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ Object f195b;

    public /* synthetic */ c(int i2, Object obj) {
        this.f194a = i2;
        this.f195b = obj;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        Message message;
        Message message2;
        Message message3;
        Message messageObtain = null;
        messageObtain = null;
        Object obj = this.f195b;
        switch (this.f194a) {
            case 0:
                e eVar = (e) obj;
                if (eVar.f197d && eVar.isShowing()) {
                    if (!eVar.f199f) {
                        TypedArray typedArrayObtainStyledAttributes = eVar.getContext().obtainStyledAttributes(new int[]{R.attr.windowCloseOnTouchOutside});
                        eVar.f198e = typedArrayObtainStyledAttributes.getBoolean(0, true);
                        typedArrayObtainStyledAttributes.recycle();
                        eVar.f199f = true;
                    }
                    if (eVar.f198e) {
                        eVar.cancel();
                    }
                    break;
                }
                break;
            case 1:
                ((MediaOutputRouteFragment) obj).getActivity().finish();
                break;
            case 2:
                androidx.appcompat.app.e eVar2 = (androidx.appcompat.app.e) obj;
                if (view == eVar2.f544h && (message3 = eVar2.f546j) != null) {
                    messageObtain = Message.obtain(message3);
                } else if (view == eVar2.f548l && (message2 = eVar2.f550n) != null) {
                    messageObtain = Message.obtain(message2);
                } else if (view == eVar2.f552p && (message = eVar2.r) != null) {
                    messageObtain = Message.obtain(message);
                }
                if (messageObtain != null) {
                    messageObtain.sendToTarget();
                }
                eVar2.f535G.obtainMessage(1, eVar2.f538b).sendToTarget();
                break;
            case 3:
                ((d.b) obj).a();
                break;
            case EventType.EVENT_MSC /* 4 */:
                p0 p0Var = ((Toolbar) obj).f1149K;
                q qVar = p0Var != null ? p0Var.f1281b : null;
                if (qVar != null) {
                    qVar.collapseActionView();
                }
                break;
            case EventType.EVENT_SW /* 5 */:
                int i2 = AboutActivity.f2318p;
                v0.a("AboutActivity", "finish");
                ((AboutActivity) obj).finish();
                break;
            case 6:
                int i3 = DesktopActivity.f2320q;
                v0.a("DesktopActivity", "finish");
                ((DesktopActivity) obj).finish();
                break;
            default:
                DesktopFragment desktopFragment = (DesktopFragment) obj;
                desktopFragment.startActivity(new Intent(desktopFragment.getContext(), (Class<?>) TutorialActivity.class));
                break;
        }
    }
}
