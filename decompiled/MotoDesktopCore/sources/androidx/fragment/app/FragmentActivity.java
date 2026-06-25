package androidx.fragment.app;

import X.w0;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.core.app.ComponentActivity;
import h.AbstractC0137d;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class FragmentActivity extends ComponentActivity implements androidx.lifecycle.k {

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public androidx.lifecycle.j f1528d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public boolean f1529e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public boolean f1530f;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public boolean f1532h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public boolean f1533i;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public boolean f1534j;

    /* JADX INFO: renamed from: k, reason: collision with root package name */
    public int f1535k;

    /* JADX INFO: renamed from: l, reason: collision with root package name */
    public h.l f1536l;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final Y.x f1526b = new Y.x(2, this);

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final C0101m f1527c = new C0101m(new C0099k(this));

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public boolean f1531g = true;

    public static void d(int i2) {
        if ((i2 & (-65536)) != 0) {
            throw new IllegalArgumentException("Can only use lower 16 bits for requestCode");
        }
    }

    public static boolean e(AbstractC0103o abstractC0103o) {
        List<AbstractComponentCallbacksC0098j> listEmptyList;
        androidx.lifecycle.b bVar = androidx.lifecycle.b.f1698c;
        z zVar = (z) abstractC0103o;
        if (zVar.f1674d.isEmpty()) {
            listEmptyList = Collections.emptyList();
        } else {
            synchronized (zVar.f1674d) {
                listEmptyList = (List) zVar.f1674d.clone();
            }
        }
        boolean zE = false;
        for (AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j : listEmptyList) {
            if (abstractComponentCallbacksC0098j != null) {
                if (((androidx.lifecycle.f) abstractComponentCallbacksC0098j.getLifecycle()).f1705b.compareTo(androidx.lifecycle.b.f1699d) >= 0) {
                    abstractComponentCallbacksC0098j.mLifecycleRegistry.b(bVar);
                    zE = true;
                }
                AbstractC0103o abstractC0103oPeekChildFragmentManager = abstractComponentCallbacksC0098j.peekChildFragmentManager();
                if (abstractC0103oPeekChildFragmentManager != null) {
                    zE |= e(abstractC0103oPeekChildFragmentManager);
                }
            }
        }
        return zE;
    }

    public final int c(AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j) {
        if (this.f1536l.f() >= 65534) {
            throw new IllegalStateException("Too many pending Fragment activity results.");
        }
        while (true) {
            h.l lVar = this.f1536l;
            int i2 = this.f1535k;
            if (lVar.f2613a) {
                lVar.c();
            }
            if (AbstractC0137d.a(lVar.f2614b, lVar.f2616d, i2) < 0) {
                int i3 = this.f1535k;
                this.f1536l.e(i3, abstractComponentCallbacksC0098j.mWho);
                this.f1535k = (this.f1535k + 1) % 65534;
                return i3;
            }
            this.f1535k = (this.f1535k + 1) % 65534;
        }
    }

    @Override // android.app.Activity
    public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        super.dump(str, fileDescriptor, printWriter, strArr);
        printWriter.print(str);
        printWriter.print("Local FragmentActivity ");
        printWriter.print(Integer.toHexString(System.identityHashCode(this)));
        printWriter.println(" State:");
        String str2 = str + "  ";
        printWriter.print(str2);
        printWriter.print("mCreated=");
        printWriter.print(this.f1529e);
        printWriter.print(" mResumed=");
        printWriter.print(this.f1530f);
        printWriter.print(" mStopped=");
        printWriter.print(this.f1531g);
        if (getApplication() != null) {
            androidx.lifecycle.j viewModelStore = getViewModelStore();
            String canonicalName = A.b.class.getCanonicalName();
            if (canonicalName == null) {
                throw new IllegalArgumentException("Local and anonymous classes can not be ViewModels");
            }
            String strConcat = "androidx.lifecycle.ViewModelProvider.DefaultKey:".concat(canonicalName);
            A.b bVar = (A.b) viewModelStore.f1716a.get(strConcat);
            if (!A.b.class.isInstance(bVar)) {
                bVar = new A.b();
                A.b bVar2 = (A.b) viewModelStore.f1716a.put(strConcat, bVar);
                if (bVar2 != null) {
                    bVar2.a();
                }
            }
            h.l lVar = bVar.f0a;
            if (lVar.f() > 0) {
                printWriter.print(str2);
                printWriter.println("Loaders:");
                if (lVar.f() > 0) {
                    w0.c(lVar.g(0));
                    printWriter.print(str2);
                    printWriter.print("  #");
                    if (lVar.f2613a) {
                        lVar.c();
                    }
                    printWriter.print(lVar.f2614b[0]);
                    printWriter.print(": ");
                    throw null;
                }
            }
        }
        this.f1527c.f1637a.f1641e.D(str, fileDescriptor, printWriter, strArr);
    }

    @Override // androidx.core.app.ComponentActivity, androidx.lifecycle.d
    public final androidx.lifecycle.c getLifecycle() {
        return this.f1391a;
    }

    @Override // androidx.lifecycle.k
    public final androidx.lifecycle.j getViewModelStore() {
        if (getApplication() == null) {
            throw new IllegalStateException("Your activity is not yet attached to the Application instance. You can't request ViewModel before onCreate call.");
        }
        if (this.f1528d == null) {
            C0100l c0100l = (C0100l) getLastNonConfigurationInstance();
            if (c0100l != null) {
                this.f1528d = c0100l.f1635a;
            }
            if (this.f1528d == null) {
                this.f1528d = new androidx.lifecycle.j();
            }
        }
        return this.f1528d;
    }

    @Override // android.app.Activity
    public final void onActivityResult(int i2, int i3, Intent intent) {
        C0101m c0101m = this.f1527c;
        c0101m.a();
        int i4 = i2 >> 16;
        if (i4 == 0) {
            super.onActivityResult(i2, i3, intent);
            return;
        }
        int i5 = i4 - 1;
        String str = (String) this.f1536l.d(i5, null);
        h.l lVar = this.f1536l;
        int iA = AbstractC0137d.a(lVar.f2614b, lVar.f2616d, i5);
        if (iA >= 0) {
            Object[] objArr = lVar.f2615c;
            Object obj = objArr[iA];
            Object obj2 = h.l.f2612e;
            if (obj != obj2) {
                objArr[iA] = obj2;
                lVar.f2613a = true;
            }
        }
        if (str == null) {
            Log.w("FragmentActivity", "Activity result delivered for unknown Fragment.");
            return;
        }
        AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098jK = c0101m.f1637a.f1641e.K(str);
        if (abstractComponentCallbacksC0098jK == null) {
            Log.w("FragmentActivity", "Activity result no fragment exists for who: ".concat(str));
        } else {
            abstractComponentCallbacksC0098jK.onActivityResult(65535 & i2, i3, intent);
        }
    }

    @Override // android.app.Activity
    public final void onBackPressed() {
        z zVar = this.f1527c.f1637a.f1641e;
        if (zVar.f1687q || zVar.r || !zVar.a()) {
            super.onBackPressed();
        }
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        C0101m c0101m = this.f1527c;
        c0101m.a();
        z zVar = c0101m.f1637a.f1641e;
        int i2 = 0;
        while (true) {
            ArrayList arrayList = zVar.f1674d;
            if (i2 >= arrayList.size()) {
                return;
            }
            AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j = (AbstractComponentCallbacksC0098j) arrayList.get(i2);
            if (abstractComponentCallbacksC0098j != null) {
                abstractComponentCallbacksC0098j.performConfigurationChanged(configuration);
            }
            i2++;
        }
    }

    @Override // androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        androidx.lifecycle.j jVar;
        C0101m c0101m = this.f1527c;
        AbstractC0102n abstractC0102n = c0101m.f1637a;
        z zVar = abstractC0102n.f1641e;
        if (zVar.f1682l != null) {
            throw new IllegalStateException("Already attached");
        }
        zVar.f1682l = abstractC0102n;
        zVar.f1683m = abstractC0102n;
        zVar.f1684n = null;
        super.onCreate(bundle);
        C0100l c0100l = (C0100l) getLastNonConfigurationInstance();
        if (c0100l != null && (jVar = c0100l.f1635a) != null && this.f1528d == null) {
            this.f1528d = jVar;
        }
        AbstractC0102n abstractC0102n2 = c0101m.f1637a;
        if (bundle != null) {
            abstractC0102n2.f1641e.X(bundle.getParcelable("android:support:fragments"), c0100l != null ? c0100l.f1636b : null);
            if (bundle.containsKey("android:support:next_request_index")) {
                this.f1535k = bundle.getInt("android:support:next_request_index");
                int[] intArray = bundle.getIntArray("android:support:request_indicies");
                String[] stringArray = bundle.getStringArray("android:support:request_fragment_who");
                if (intArray == null || stringArray == null || intArray.length != stringArray.length) {
                    Log.w("FragmentActivity", "Invalid requestCode mapping in savedInstanceState.");
                } else {
                    this.f1536l = new h.l(intArray.length);
                    for (int i2 = 0; i2 < intArray.length; i2++) {
                        this.f1536l.e(intArray[i2], stringArray[i2]);
                    }
                }
            }
        }
        if (this.f1536l == null) {
            this.f1536l = new h.l();
            this.f1535k = 0;
        }
        z zVar2 = abstractC0102n2.f1641e;
        zVar2.f1687q = false;
        zVar2.r = false;
        zVar2.C(1);
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public final boolean onCreatePanelMenu(int i2, Menu menu) {
        if (i2 != 0) {
            return super.onCreatePanelMenu(i2, menu);
        }
        return this.f1527c.f1637a.f1641e.j(menu, getMenuInflater()) | super.onCreatePanelMenu(i2, menu);
    }

    @Override // android.app.Activity, android.view.LayoutInflater.Factory2
    public final View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        View viewOnCreateView = this.f1527c.f1637a.f1641e.onCreateView(view, str, context, attributeSet);
        return viewOnCreateView == null ? super.onCreateView(view, str, context, attributeSet) : viewOnCreateView;
    }

    @Override // android.app.Activity, android.view.LayoutInflater.Factory
    public final View onCreateView(String str, Context context, AttributeSet attributeSet) {
        View viewOnCreateView = this.f1527c.f1637a.f1641e.onCreateView(null, str, context, attributeSet);
        return viewOnCreateView == null ? super.onCreateView(str, context, attributeSet) : viewOnCreateView;
    }

    @Override // android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        if (this.f1528d != null && !isChangingConfigurations()) {
            HashMap map = this.f1528d.f1716a;
            Iterator it = map.values().iterator();
            while (it.hasNext()) {
                ((A.b) it.next()).a();
            }
            map.clear();
        }
        this.f1527c.f1637a.f1641e.k();
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public final void onLowMemory() {
        super.onLowMemory();
        z zVar = this.f1527c.f1637a.f1641e;
        int i2 = 0;
        while (true) {
            ArrayList arrayList = zVar.f1674d;
            if (i2 >= arrayList.size()) {
                return;
            }
            AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j = (AbstractComponentCallbacksC0098j) arrayList.get(i2);
            if (abstractComponentCallbacksC0098j != null) {
                abstractComponentCallbacksC0098j.performLowMemory();
            }
            i2++;
        }
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public boolean onMenuItemSelected(int i2, MenuItem menuItem) {
        if (super.onMenuItemSelected(i2, menuItem)) {
            return true;
        }
        C0101m c0101m = this.f1527c;
        if (i2 == 0) {
            return c0101m.f1637a.f1641e.z(menuItem);
        }
        if (i2 != 6) {
            return false;
        }
        return c0101m.f1637a.f1641e.i(menuItem);
    }

    @Override // android.app.Activity
    public final void onMultiWindowModeChanged(boolean z2) {
        ArrayList arrayList = this.f1527c.f1637a.f1641e.f1674d;
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j = (AbstractComponentCallbacksC0098j) arrayList.get(size);
            if (abstractComponentCallbacksC0098j != null) {
                abstractComponentCallbacksC0098j.performMultiWindowModeChanged(z2);
            }
        }
    }

    @Override // android.app.Activity
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        this.f1527c.a();
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public void onPanelClosed(int i2, Menu menu) {
        if (i2 == 0) {
            this.f1527c.f1637a.f1641e.A(menu);
        }
        super.onPanelClosed(i2, menu);
    }

    @Override // android.app.Activity
    public final void onPause() {
        super.onPause();
        this.f1530f = false;
        Y.x xVar = this.f1526b;
        boolean zHasMessages = xVar.hasMessages(2);
        C0101m c0101m = this.f1527c;
        if (zHasMessages) {
            xVar.removeMessages(2);
            z zVar = c0101m.f1637a.f1641e;
            zVar.f1687q = false;
            zVar.r = false;
            zVar.C(4);
        }
        c0101m.f1637a.f1641e.C(3);
    }

    @Override // android.app.Activity
    public final void onPictureInPictureModeChanged(boolean z2) {
        ArrayList arrayList = this.f1527c.f1637a.f1641e.f1674d;
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j = (AbstractComponentCallbacksC0098j) arrayList.get(size);
            if (abstractComponentCallbacksC0098j != null) {
                abstractComponentCallbacksC0098j.performPictureInPictureModeChanged(z2);
            }
        }
    }

    @Override // android.app.Activity
    public void onPostResume() {
        super.onPostResume();
        this.f1526b.removeMessages(2);
        C0101m c0101m = this.f1527c;
        z zVar = c0101m.f1637a.f1641e;
        zVar.f1687q = false;
        zVar.r = false;
        zVar.C(4);
        c0101m.f1637a.f1641e.G();
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public final boolean onPreparePanel(int i2, View view, Menu menu) {
        if (i2 != 0 || menu == null) {
            return super.onPreparePanel(i2, view, menu);
        }
        return this.f1527c.f1637a.f1641e.B(menu) | super.onPreparePanel(0, view, menu);
    }

    @Override // android.app.Activity
    public void onRequestPermissionsResult(int i2, String[] strArr, int[] iArr) {
        C0101m c0101m = this.f1527c;
        c0101m.a();
        int i3 = (i2 >> 16) & 65535;
        if (i3 != 0) {
            int i4 = i3 - 1;
            String str = (String) this.f1536l.d(i4, null);
            h.l lVar = this.f1536l;
            int iA = AbstractC0137d.a(lVar.f2614b, lVar.f2616d, i4);
            if (iA >= 0) {
                Object[] objArr = lVar.f2615c;
                Object obj = objArr[iA];
                Object obj2 = h.l.f2612e;
                if (obj != obj2) {
                    objArr[iA] = obj2;
                    lVar.f2613a = true;
                }
            }
            if (str == null) {
                Log.w("FragmentActivity", "Activity result delivered for unknown Fragment.");
                return;
            }
            AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098jK = c0101m.f1637a.f1641e.K(str);
            if (abstractComponentCallbacksC0098jK == null) {
                Log.w("FragmentActivity", "Activity result no fragment exists for who: ".concat(str));
            } else {
                abstractComponentCallbacksC0098jK.onRequestPermissionsResult(i2 & 65535, strArr, iArr);
            }
        }
    }

    @Override // android.app.Activity
    public void onResume() {
        super.onResume();
        this.f1526b.sendEmptyMessage(2);
        this.f1530f = true;
        this.f1527c.f1637a.f1641e.G();
    }

    @Override // android.app.Activity
    public final Object onRetainNonConfigurationInstance() {
        z zVar = this.f1527c.f1637a.f1641e;
        z.e0(zVar.f1669A);
        A a2 = zVar.f1669A;
        if (a2 == null && this.f1528d == null) {
            return null;
        }
        C0100l c0100l = new C0100l();
        c0100l.f1635a = this.f1528d;
        c0100l.f1636b = a2;
        return c0100l;
    }

    @Override // androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        C0101m c0101m;
        super.onSaveInstanceState(bundle);
        do {
            c0101m = this.f1527c;
        } while (e(c0101m.f1637a.f1641e));
        Parcelable parcelableY = c0101m.f1637a.f1641e.Y();
        if (parcelableY != null) {
            bundle.putParcelable("android:support:fragments", parcelableY);
        }
        if (this.f1536l.f() > 0) {
            bundle.putInt("android:support:next_request_index", this.f1535k);
            int[] iArr = new int[this.f1536l.f()];
            String[] strArr = new String[this.f1536l.f()];
            for (int i2 = 0; i2 < this.f1536l.f(); i2++) {
                h.l lVar = this.f1536l;
                if (lVar.f2613a) {
                    lVar.c();
                }
                iArr[i2] = lVar.f2614b[i2];
                strArr[i2] = (String) this.f1536l.g(i2);
            }
            bundle.putIntArray("android:support:request_indicies", iArr);
            bundle.putStringArray("android:support:request_fragment_who", strArr);
        }
    }

    @Override // android.app.Activity
    public void onStart() {
        super.onStart();
        this.f1531g = false;
        boolean z2 = this.f1529e;
        C0101m c0101m = this.f1527c;
        if (!z2) {
            this.f1529e = true;
            z zVar = c0101m.f1637a.f1641e;
            zVar.f1687q = false;
            zVar.r = false;
            zVar.C(2);
        }
        c0101m.a();
        AbstractC0102n abstractC0102n = c0101m.f1637a;
        abstractC0102n.f1641e.G();
        z zVar2 = abstractC0102n.f1641e;
        zVar2.f1687q = false;
        zVar2.r = false;
        zVar2.C(3);
    }

    @Override // android.app.Activity
    public final void onStateNotSaved() {
        this.f1527c.a();
    }

    @Override // android.app.Activity
    public void onStop() {
        C0101m c0101m;
        super.onStop();
        this.f1531g = true;
        do {
            c0101m = this.f1527c;
        } while (e(c0101m.f1637a.f1641e));
        z zVar = c0101m.f1637a.f1641e;
        zVar.r = true;
        zVar.C(2);
    }

    @Override // android.app.Activity
    public final void startActivityForResult(Intent intent, int i2) {
        if (!this.f1534j && i2 != -1) {
            d(i2);
        }
        super.startActivityForResult(intent, i2);
    }

    @Override // android.app.Activity
    public final void startActivityForResult(Intent intent, int i2, Bundle bundle) {
        if (!this.f1534j && i2 != -1) {
            d(i2);
        }
        super.startActivityForResult(intent, i2, bundle);
    }

    @Override // android.app.Activity
    public final void startIntentSenderForResult(IntentSender intentSender, int i2, Intent intent, int i3, int i4, int i5) throws IntentSender.SendIntentException {
        if (!this.f1533i && i2 != -1) {
            d(i2);
        }
        super.startIntentSenderForResult(intentSender, i2, intent, i3, i4, i5);
    }

    @Override // android.app.Activity
    public final void startIntentSenderForResult(IntentSender intentSender, int i2, Intent intent, int i3, int i4, int i5, Bundle bundle) throws IntentSender.SendIntentException {
        if (!this.f1533i && i2 != -1) {
            d(i2);
        }
        super.startIntentSenderForResult(intentSender, i2, intent, i3, i4, i5, bundle);
    }
}
