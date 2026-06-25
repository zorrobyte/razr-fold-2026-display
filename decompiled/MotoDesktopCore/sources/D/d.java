package d;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.view.LayoutInflater;
import androidx.appcompat.R$style;

/* JADX INFO: loaded from: classes.dex */
public final class d extends ContextWrapper {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public int f2353a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public Resources.Theme f2354b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public LayoutInflater f2355c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public Resources f2356d;

    public d(Context context, int i2) {
        super(context);
        this.f2353a = i2;
    }

    public final void a() {
        if (this.f2354b == null) {
            this.f2354b = getResources().newTheme();
            Resources.Theme theme = getBaseContext().getTheme();
            if (theme != null) {
                this.f2354b.setTo(theme);
            }
        }
        this.f2354b.applyStyle(this.f2353a, true);
    }

    @Override // android.content.ContextWrapper
    public final void attachBaseContext(Context context) {
        super.attachBaseContext(context);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public final AssetManager getAssets() {
        return getResources().getAssets();
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public final Resources getResources() {
        if (this.f2356d == null) {
            this.f2356d = super.getResources();
        }
        return this.f2356d;
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public final Object getSystemService(String str) {
        if (!"layout_inflater".equals(str)) {
            return getBaseContext().getSystemService(str);
        }
        if (this.f2355c == null) {
            this.f2355c = LayoutInflater.from(getBaseContext()).cloneInContext(this);
        }
        return this.f2355c;
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public final Resources.Theme getTheme() {
        Resources.Theme theme = this.f2354b;
        if (theme != null) {
            return theme;
        }
        if (this.f2353a == 0) {
            this.f2353a = R$style.Theme_AppCompat_Light;
        }
        a();
        return this.f2354b;
    }

    public final int getThemeResId() {
        return this.f2353a;
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public final void setTheme(int i2) {
        if (this.f2353a != i2) {
            this.f2353a = i2;
            a();
        }
    }
}
