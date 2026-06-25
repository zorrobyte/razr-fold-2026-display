package androidx.appcompat.widget;

import android.R;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.R$id;

/* JADX INFO: loaded from: classes.dex */
public final class j0 {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final TextView f1220a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final TextView f1221b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final ImageView f1222c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public final ImageView f1223d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public final ImageView f1224e;

    public j0(View view) {
        this.f1220a = (TextView) view.findViewById(R.id.text1);
        this.f1221b = (TextView) view.findViewById(R.id.text2);
        this.f1222c = (ImageView) view.findViewById(R.id.icon1);
        this.f1223d = (ImageView) view.findViewById(R.id.icon2);
        this.f1224e = (ImageView) view.findViewById(R$id.edit_query);
    }
}
