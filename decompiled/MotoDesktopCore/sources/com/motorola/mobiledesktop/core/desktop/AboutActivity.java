package com.motorola.mobiledesktop.core.desktop;

import K.c;
import android.os.Bundle;
import android.webkit.WebView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.motorola.mobiledesktop.core.R;

/* JADX INFO: loaded from: classes.dex */
public class AboutActivity extends AppCompatActivity {

    /* JADX INFO: renamed from: p, reason: collision with root package name */
    public static final /* synthetic */ int f2318p = 0;

    /* JADX INFO: renamed from: o, reason: collision with root package name */
    public Toolbar f2319o;

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.f2319o = toolbar;
        g(toolbar);
        this.f2319o.setNavigationIcon(R.drawable.ic_arrow_back);
        this.f2319o.setNavigationOnClickListener(new c(5, this));
        ((WebView) findViewById(R.id.webview)).loadUrl("file:///android_asset/licenses.html");
    }
}
