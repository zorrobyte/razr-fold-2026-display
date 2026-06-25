package com.motorola.mobiledesktop.core.desktop.tutorial;

import F.k;
import X.v0;
import X.w0;
import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import c0.a;
import c0.b;
import com.motorola.mobiledesktop.core.R;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class TutorialActivity extends Activity implements k {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public String f2331a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public ArrayList f2332b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public SlideView f2333c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public LinearLayout f2334d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public int f2335e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public ImageView f2336f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public ImageView f2337g;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public TextView f2338h;

    @Override // F.k
    public final void a(int i2, int i3, float f2) {
        w0.b(i2, "onPageScrolled:", "TutorialActivity");
    }

    @Override // F.k
    public final void b(int i2) {
        v0.a("TutorialActivity", "onPageScrollStateChanged");
    }

    @Override // F.k
    public final void c(int i2) {
        v0.a("TutorialActivity", "onPageSelected:" + i2 + ", cnt:" + this.f2333c.getChildCount());
        this.f2334d.getChildAt(this.f2335e).setBackgroundResource(R.drawable.indicator_no_select);
        this.f2335e = i2;
        this.f2334d.getChildAt(i2).setBackgroundResource(R.drawable.indicator_select);
        if (i2 == 0) {
            this.f2336f.setVisibility(8);
            this.f2337g.setVisibility(8);
            this.f2338h.setText(R.string.get_start);
            this.f2338h.setVisibility(0);
            return;
        }
        if (i2 == this.f2332b.size() - 1) {
            this.f2337g.setVisibility(8);
            this.f2338h.setText(R.string.got_it);
            this.f2338h.setVisibility(0);
        } else {
            this.f2336f.setVisibility(0);
            this.f2337g.setVisibility(0);
            this.f2338h.setVisibility(8);
        }
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_tutorial);
        this.f2331a = getIntent().getStringExtra("tutorial_type");
        Configuration configuration = getResources().getConfiguration();
        v0.a("TutorialActivity", "onCreate:" + this.f2331a + ", orientation:" + configuration.orientation);
        if (configuration.orientation == 2) {
            WindowManager.LayoutParams attributes = getWindow().getAttributes();
            attributes.width = getResources().getDimensionPixelOffset(R.dimen.helper_dialog_land_width);
            getWindow().setAttributes(attributes);
        }
        ((ImageView) findViewById(R.id.helper_close)).setOnClickListener(new b(this, 0));
        this.f2333c = (SlideView) findViewById(R.id.helper_content);
        this.f2332b = new ArrayList();
        TypedArray typedArrayObtainTypedArray = getResources().obtainTypedArray(R.array.helper_content_icon_id_hdmi);
        String[] stringArray = getResources().getStringArray(R.array.helper_content_title_hdmi);
        String[] stringArray2 = getResources().getStringArray(R.array.helper_content_desc_hdmi);
        for (int i2 = 0; i2 < stringArray.length; i2++) {
            View viewInflate = View.inflate(this, R.layout.view_dialog_helper, null);
            viewInflate.findViewById(R.id.iv_helper_content_icon).setBackgroundResource(typedArrayObtainTypedArray.getResourceId(i2, 0));
            ((TextView) viewInflate.findViewById(R.id.tv_helper_content_title)).setText(stringArray[i2]);
            ((TextView) viewInflate.findViewById(R.id.tv_helper_content_desc)).setText(stringArray2[i2]);
            this.f2332b.add(viewInflate);
        }
        SlideView slideView = this.f2333c;
        slideView.f2329g0 = this.f2332b;
        if (slideView.h0 == null) {
            a aVar = new a(slideView);
            slideView.h0 = aVar;
            slideView.setAdapter(aVar);
        }
        this.f2333c.b(this);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.helper_indicator);
        this.f2334d = linearLayout;
        linearLayout.removeAllViews();
        int dimension = (int) getResources().getDimension(R.dimen.helper_dialog_indicator_size);
        int dimension2 = (int) getResources().getDimension(R.dimen.helper_dialog_indicator_space);
        for (int i3 = 0; i3 < this.f2332b.size(); i3++) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(dimension, dimension);
            ImageView imageView = new ImageView(this);
            if (i3 != 0) {
                layoutParams.setMarginStart(dimension2);
            }
            imageView.setLayoutParams(layoutParams);
            imageView.setBackgroundResource(R.drawable.indicator_no_select);
            this.f2334d.addView(imageView);
        }
        this.f2335e = 0;
        this.f2334d.getChildAt(0).setBackgroundResource(R.drawable.indicator_select);
        ImageView imageView2 = (ImageView) findViewById(R.id.helper_previous);
        this.f2336f = imageView2;
        imageView2.setOnClickListener(new b(this, 1));
        this.f2336f.setVisibility(8);
        ImageView imageView3 = (ImageView) findViewById(R.id.iv_helper_next);
        this.f2337g = imageView3;
        imageView3.setOnClickListener(new b(this, 2));
        this.f2337g.setVisibility(8);
        TextView textView = (TextView) findViewById(R.id.tv_helper_next);
        this.f2338h = textView;
        textView.setText(R.string.get_start);
        this.f2338h.setVisibility(0);
        this.f2338h.setOnClickListener(new b(this, 3));
    }
}
