package androidx.appcompat.app;

import X.K0;
import android.R;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.R$attr;

/* JADX INFO: loaded from: classes.dex */
public class s extends Dialog implements h {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public q f638a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final r f639b;

    public s(Context context, int i2) {
        if (i2 == 0) {
            TypedValue typedValue = new TypedValue();
            context.getTheme().resolveAttribute(R$attr.dialogTheme, typedValue, true);
            i2 = typedValue.resourceId;
        }
        super(context, i2);
        this.f639b = new r(this);
        a().p(null);
        a().a();
    }

    public final q a() {
        if (this.f638a == null) {
            this.f638a = new q(getContext(), getWindow(), this);
        }
        return this.f638a;
    }

    @Override // android.app.Dialog
    public final void addContentView(View view, ViewGroup.LayoutParams layoutParams) {
        q qVarA = a();
        qVarA.j();
        ((ViewGroup) qVarA.r.findViewById(R.id.content)).addView(view, layoutParams);
        qVarA.f614c.onContentChanged();
    }

    public final boolean b(KeyEvent keyEvent) {
        return super.dispatchKeyEvent(keyEvent);
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
        getWindow().getDecorView();
        r rVar = this.f639b;
        if (rVar == null) {
            return false;
        }
        return rVar.a(keyEvent);
    }

    @Override // android.app.Dialog
    public final View findViewById(int i2) {
        q qVarA = a();
        qVarA.j();
        return qVarA.f613b.findViewById(i2);
    }

    @Override // android.app.Dialog
    public final void invalidateOptionsMenu() {
        a().n();
    }

    @Override // android.app.Dialog
    public void onCreate(Bundle bundle) {
        a().m();
        super.onCreate(bundle);
        a().p(bundle);
    }

    @Override // android.app.Dialog
    public final void onStop() {
        K0 k0;
        super.onStop();
        q qVarA = a();
        qVarA.l();
        Y.r rVar = qVarA.f617f;
        if (rVar != null) {
            rVar.j0(false);
        }
        m mVar = qVarA.f604I;
        if (mVar == null || (k0 = mVar.f575c) == null) {
            return;
        }
        mVar.f577e.f612a.unregisterReceiver(k0);
        mVar.f575c = null;
    }

    @Override // android.app.Dialog
    public void setContentView(int i2) {
        a().u(i2);
    }

    @Override // android.app.Dialog
    public void setContentView(View view) {
        q qVarA = a();
        qVarA.j();
        ViewGroup viewGroup = (ViewGroup) qVarA.r.findViewById(R.id.content);
        viewGroup.removeAllViews();
        viewGroup.addView(view);
        qVarA.f614c.onContentChanged();
    }

    @Override // android.app.Dialog
    public void setContentView(View view, ViewGroup.LayoutParams layoutParams) {
        q qVarA = a();
        qVarA.j();
        ViewGroup viewGroup = (ViewGroup) qVarA.r.findViewById(R.id.content);
        viewGroup.removeAllViews();
        viewGroup.addView(view, layoutParams);
        qVarA.f614c.onContentChanged();
    }

    @Override // android.app.Dialog
    public final void setTitle(int i2) {
        super.setTitle(i2);
        a().v(getContext().getString(i2));
    }

    @Override // android.app.Dialog
    public void setTitle(CharSequence charSequence) {
        super.setTitle(charSequence);
        a().v(charSequence);
    }
}
