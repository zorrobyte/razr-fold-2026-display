package androidx.appcompat.app;

import X.K0;
import android.R;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import androidx.appcompat.widget.C0082t;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.w0;
import androidx.fragment.app.FragmentActivity;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class AppCompatActivity extends FragmentActivity implements h {

    /* JADX INFO: renamed from: m, reason: collision with root package name */
    public q f470m;

    /* JADX INFO: renamed from: n, reason: collision with root package name */
    public int f471n = 0;

    @Override // android.app.Activity
    public final void addContentView(View view, ViewGroup.LayoutParams layoutParams) {
        q qVarF = f();
        qVarF.j();
        ((ViewGroup) qVarF.r.findViewById(R.id.content)).addView(view, layoutParams);
        qVarF.f614c.onContentChanged();
    }

    @Override // androidx.core.app.ComponentActivity
    public final void b() {
        f().n();
    }

    @Override // android.app.Activity
    public final void closeOptionsMenu() {
        q qVarF = f();
        qVarF.l();
        Y.r rVar = qVarF.f617f;
        if (getWindow().hasFeature(0)) {
            if (rVar == null || !rVar.i()) {
                super.closeOptionsMenu();
            }
        }
    }

    @Override // androidx.core.app.ComponentActivity, android.app.Activity, android.view.Window.Callback
    public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        q qVarF = f();
        qVarF.l();
        Y.r rVar = qVarF.f617f;
        if (keyCode == 82 && rVar != null && rVar.Z(keyEvent)) {
            return true;
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    public final q f() {
        if (this.f470m == null) {
            this.f470m = new q(this, getWindow(), this);
        }
        return this.f470m;
    }

    @Override // android.app.Activity
    public final View findViewById(int i2) {
        q qVarF = f();
        qVarF.j();
        return qVarF.f613b.findViewById(i2);
    }

    public final void g(Toolbar toolbar) {
        q qVarF = f();
        Window.Callback callback = qVarF.f614c;
        if (callback instanceof Activity) {
            qVarF.l();
            Y.r rVar = qVarF.f617f;
            if (rVar instanceof C) {
                throw new IllegalStateException("This Activity already has an action bar supplied by the window decor. Do not request Window.FEATURE_SUPPORT_ACTION_BAR and set windowActionBar to false in your theme to use a Toolbar instead.");
            }
            qVarF.f618g = null;
            if (rVar != null) {
                rVar.X();
            }
            l lVar = qVarF.f615d;
            Window window = qVarF.f613b;
            if (toolbar != null) {
                x xVar = new x(toolbar, ((Activity) callback).getTitle(), lVar);
                qVarF.f617f = xVar;
                window.setCallback(xVar.f650j);
            } else {
                qVarF.f617f = null;
                window.setCallback(lVar);
            }
            qVarF.n();
        }
    }

    @Override // android.app.Activity
    public final MenuInflater getMenuInflater() {
        q qVarF = f();
        if (qVarF.f618g == null) {
            qVarF.l();
            Y.r rVar = qVarF.f617f;
            qVarF.f618g = new d.i(rVar != null ? rVar.E() : qVarF.f612a);
        }
        return qVarF.f618g;
    }

    @Override // android.view.ContextThemeWrapper, android.content.ContextWrapper, android.content.Context
    public final Resources getResources() {
        int i2 = w0.f1342a;
        return super.getResources();
    }

    @Override // android.app.Activity
    public final void invalidateOptionsMenu() {
        f().n();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity, android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        q qVarF = f();
        if (qVarF.f633w && qVarF.f628q) {
            qVarF.l();
            Y.r rVar = qVarF.f617f;
            if (rVar != null) {
                rVar.W();
            }
        }
        C0082t c0082tF = C0082t.f();
        Context context = qVarF.f612a;
        synchronized (c0082tF) {
            h.e eVar = (h.e) c0082tF.f1307b.get(context);
            if (eVar != null) {
                eVar.a();
            }
        }
        qVarF.a();
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public final void onContentChanged() {
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        q qVarF = f();
        qVarF.m();
        qVarF.p(bundle);
        if (qVarF.a() && this.f471n != 0) {
            onApplyThemeResource(getTheme(), this.f471n, false);
        }
        super.onCreate(bundle);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        K0 k0;
        super.onDestroy();
        q qVarF = f();
        if (qVarF.f605J) {
            qVarF.f613b.getDecorView().removeCallbacks(qVarF.f607L);
        }
        qVarF.f601F = true;
        Y.r rVar = qVarF.f617f;
        if (rVar != null) {
            rVar.X();
        }
        m mVar = qVarF.f604I;
        if (mVar == null || (k0 = mVar.f575c) == null) {
            return;
        }
        mVar.f577e.f612a.unregisterReceiver(k0);
        mVar.f575c = null;
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public final boolean onKeyDown(int i2, KeyEvent keyEvent) {
        return super.onKeyDown(i2, keyEvent);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity, android.view.Window.Callback
    public final boolean onMenuItemSelected(int i2, MenuItem menuItem) {
        Intent intentM;
        if (super.onMenuItemSelected(i2, menuItem)) {
            return true;
        }
        q qVarF = f();
        qVarF.l();
        Y.r rVar = qVarF.f617f;
        if (menuItem.getItemId() == 16908332 && rVar != null && (rVar.t() & 4) != 0 && (intentM = AbstractC0054a.m(this)) != null) {
            if (!shouldUpRecreateTask(intentM)) {
                navigateUpTo(intentM);
                return true;
            }
            ArrayList arrayList = new ArrayList();
            Intent intentM2 = AbstractC0054a.m(this);
            if (intentM2 == null) {
                intentM2 = AbstractC0054a.m(this);
            }
            if (intentM2 != null) {
                ComponentName component = intentM2.getComponent();
                if (component == null) {
                    component = intentM2.resolveActivity(getPackageManager());
                }
                int size = arrayList.size();
                try {
                    Intent intentN = AbstractC0054a.n(this, component);
                    while (intentN != null) {
                        arrayList.add(size, intentN);
                        intentN = AbstractC0054a.n(this, intentN.getComponent());
                    }
                    arrayList.add(intentM2);
                } catch (PackageManager.NameNotFoundException e2) {
                    Log.e("TaskStackBuilder", "Bad ComponentName while traversing activity parent metadata");
                    throw new IllegalArgumentException(e2);
                }
            }
            if (arrayList.isEmpty()) {
                throw new IllegalStateException("No intents added to TaskStackBuilder; cannot startActivities");
            }
            Intent[] intentArr = (Intent[]) arrayList.toArray(new Intent[arrayList.size()]);
            intentArr[0] = new Intent(intentArr[0]).addFlags(268484608);
            startActivities(intentArr, null);
            try {
                finishAffinity();
                return true;
            } catch (IllegalStateException unused) {
                finish();
                return true;
            }
        }
        return false;
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public final boolean onMenuOpened(int i2, Menu menu) {
        return super.onMenuOpened(i2, menu);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity, android.view.Window.Callback
    public final void onPanelClosed(int i2, Menu menu) {
        super.onPanelClosed(i2, menu);
    }

    @Override // android.app.Activity
    public final void onPostCreate(Bundle bundle) {
        super.onPostCreate(bundle);
        f().j();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onPostResume() {
        super.onPostResume();
        q qVarF = f();
        qVarF.l();
        Y.r rVar = qVarF.f617f;
        if (rVar != null) {
            rVar.j0(true);
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        int i2 = f().f602G;
        if (i2 != -100) {
            bundle.putInt("appcompat:local_night_mode", i2);
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onStart() {
        super.onStart();
        f().a();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onStop() {
        K0 k0;
        super.onStop();
        q qVarF = f();
        qVarF.l();
        Y.r rVar = qVarF.f617f;
        if (rVar != null) {
            rVar.j0(false);
        }
        m mVar = qVarF.f604I;
        if (mVar == null || (k0 = mVar.f575c) == null) {
            return;
        }
        mVar.f577e.f612a.unregisterReceiver(k0);
        mVar.f575c = null;
    }

    @Override // android.app.Activity
    public final void onTitleChanged(CharSequence charSequence, int i2) {
        super.onTitleChanged(charSequence, i2);
        f().v(charSequence);
    }

    @Override // android.app.Activity
    public final void openOptionsMenu() {
        q qVarF = f();
        qVarF.l();
        Y.r rVar = qVarF.f617f;
        if (getWindow().hasFeature(0)) {
            if (rVar == null || !rVar.e0()) {
                super.openOptionsMenu();
            }
        }
    }

    @Override // android.app.Activity
    public final void setContentView(int i2) {
        f().u(i2);
    }

    @Override // android.app.Activity
    public void setContentView(View view) {
        q qVarF = f();
        qVarF.j();
        ViewGroup viewGroup = (ViewGroup) qVarF.r.findViewById(R.id.content);
        viewGroup.removeAllViews();
        viewGroup.addView(view);
        qVarF.f614c.onContentChanged();
    }

    @Override // android.app.Activity
    public final void setContentView(View view, ViewGroup.LayoutParams layoutParams) {
        q qVarF = f();
        qVarF.j();
        ViewGroup viewGroup = (ViewGroup) qVarF.r.findViewById(R.id.content);
        viewGroup.removeAllViews();
        viewGroup.addView(view, layoutParams);
        qVarF.f614c.onContentChanged();
    }

    @Override // android.app.Activity, android.view.ContextThemeWrapper, android.content.ContextWrapper, android.content.Context
    public final void setTheme(int i2) {
        super.setTheme(i2);
        this.f471n = i2;
    }
}
