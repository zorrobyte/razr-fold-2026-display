package com.motorola.mobiledesktop.core.desktop;

import K.c;
import X.v0;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.C0090b;
import androidx.fragment.app.z;
import com.motorola.mobiledesktop.core.R;

/* JADX INFO: loaded from: classes.dex */
public class DesktopActivity extends AppCompatActivity {

    /* JADX INFO: renamed from: q, reason: collision with root package name */
    public static final /* synthetic */ int f2320q = 0;

    /* JADX INFO: renamed from: o, reason: collision with root package name */
    public Toolbar f2321o;

    /* JADX INFO: renamed from: p, reason: collision with root package name */
    public DesktopFragment f2322p;

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_desktop);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.f2321o = toolbar;
        g(toolbar);
        this.f2321o.setNavigationIcon(R.drawable.ic_arrow_back);
        this.f2321o.setNavigationOnClickListener(new c(6, this));
        this.f2322p = DesktopFragment.newInstance();
        z zVar = this.f1527c.f1637a.f1641e;
        zVar.getClass();
        C0090b c0090b = new C0090b(zVar);
        int i2 = R.id.fl_content;
        DesktopFragment desktopFragment = this.f2322p;
        if (i2 == 0) {
            throw new IllegalArgumentException("Must use non-zero containerViewId");
        }
        c0090b.e(i2, desktopFragment, null, 2);
        c0090b.d(false);
    }

    @Override // android.app.Activity
    public final boolean onCreateOptionsMenu(Menu menu) {
        v0.a("DesktopActivity", "onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.toolbar_menu_desktop, menu);
        return true;
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onDestroy() {
        super.onDestroy();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override // android.app.Activity
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        v0.a("DesktopActivity", "onOptionsItemSelected:" + menuItem.getItemId());
        if (menuItem.getItemId() == R.id.menu_audio_routing) {
            startActivity(new Intent().setAction("com.motorola.mobiledesktop.action.MEDIA_OUTPUT").setFlags(268435456));
        } else if (menuItem.getItemId() == R.id.menu_about) {
            startActivity(new Intent(this, (Class<?>) AboutActivity.class));
        } else if (menuItem.getItemId() == R.id.menu_help) {
            Intent intent = new Intent("com.motorola.help.VIEW_HELP");
            intent.putExtra("topic", "help_url_mobile_desktop");
            startActivity(intent);
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override // android.app.Activity
    public final boolean onPrepareOptionsMenu(Menu menu) {
        v0.a("DesktopActivity", "onPrepareOptionsMenu");
        return super.onPrepareOptionsMenu(menu);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onResume() {
        super.onResume();
        v0.a("DesktopActivity", "onResume");
    }
}
