package androidx.appcompat.app;

import android.R;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import androidx.appcompat.R$attr;
import androidx.appcompat.R$id;
import androidx.core.widget.NestedScrollView;
import java.util.WeakHashMap;

/* JADX INFO: loaded from: classes.dex */
public final class g extends s {

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final e f565c;

    public g(Context context, int i2) {
        super(context, c(context, i2));
        this.f565c = new e(getContext(), this, getWindow());
    }

    public static int c(Context context, int i2) {
        if (((i2 >>> 24) & 255) >= 1) {
            return i2;
        }
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R$attr.alertDialogTheme, typedValue, true);
        return typedValue.resourceId;
    }

    @Override // androidx.appcompat.app.s, android.app.Dialog
    public final void onCreate(Bundle bundle) {
        int i2;
        ListAdapter listAdapter;
        View viewFindViewById;
        super.onCreate(bundle);
        e eVar = this.f565c;
        eVar.f538b.setContentView(eVar.f531C);
        int i3 = R$id.parentPanel;
        Window window = eVar.f539c;
        View viewFindViewById2 = window.findViewById(i3);
        View viewFindViewById3 = viewFindViewById2.findViewById(R$id.topPanel);
        View viewFindViewById4 = viewFindViewById2.findViewById(R$id.contentPanel);
        View viewFindViewById5 = viewFindViewById2.findViewById(R$id.buttonPanel);
        ViewGroup viewGroup = (ViewGroup) viewFindViewById2.findViewById(R$id.customPanel);
        window.setFlags(131072, 131072);
        viewGroup.setVisibility(8);
        View viewFindViewById6 = viewGroup.findViewById(R$id.topPanel);
        View viewFindViewById7 = viewGroup.findViewById(R$id.contentPanel);
        View viewFindViewById8 = viewGroup.findViewById(R$id.buttonPanel);
        ViewGroup viewGroupA = e.a(viewFindViewById6, viewFindViewById3);
        ViewGroup viewGroupA2 = e.a(viewFindViewById7, viewFindViewById4);
        ViewGroup viewGroupA3 = e.a(viewFindViewById8, viewFindViewById5);
        NestedScrollView nestedScrollView = (NestedScrollView) window.findViewById(R$id.scrollView);
        eVar.f555t = nestedScrollView;
        nestedScrollView.setFocusable(false);
        eVar.f555t.setNestedScrollingEnabled(false);
        TextView textView = (TextView) viewGroupA2.findViewById(R.id.message);
        eVar.f560y = textView;
        if (textView != null) {
            CharSequence charSequence = eVar.f542f;
            if (charSequence != null) {
                textView.setText(charSequence);
            } else {
                textView.setVisibility(8);
                eVar.f555t.removeView(eVar.f560y);
                if (eVar.f543g != null) {
                    ViewGroup viewGroup2 = (ViewGroup) eVar.f555t.getParent();
                    int iIndexOfChild = viewGroup2.indexOfChild(eVar.f555t);
                    viewGroup2.removeViewAt(iIndexOfChild);
                    viewGroup2.addView(eVar.f543g, iIndexOfChild, new ViewGroup.LayoutParams(-1, -1));
                } else {
                    viewGroupA2.setVisibility(8);
                }
            }
        }
        Button button = (Button) viewGroupA3.findViewById(R.id.button1);
        eVar.f544h = button;
        K.c cVar = eVar.f536H;
        button.setOnClickListener(cVar);
        boolean zIsEmpty = TextUtils.isEmpty(eVar.f545i);
        int i4 = eVar.f540d;
        if (zIsEmpty && eVar.f547k == null) {
            eVar.f544h.setVisibility(8);
            i2 = 0;
        } else {
            eVar.f544h.setText(eVar.f545i);
            Drawable drawable = eVar.f547k;
            if (drawable != null) {
                drawable.setBounds(0, 0, i4, i4);
                eVar.f544h.setCompoundDrawables(eVar.f547k, null, null, null);
            }
            eVar.f544h.setVisibility(0);
            i2 = 1;
        }
        Button button2 = (Button) viewGroupA3.findViewById(R.id.button2);
        eVar.f548l = button2;
        button2.setOnClickListener(cVar);
        if (TextUtils.isEmpty(eVar.f549m) && eVar.f551o == null) {
            eVar.f548l.setVisibility(8);
        } else {
            eVar.f548l.setText(eVar.f549m);
            Drawable drawable2 = eVar.f551o;
            if (drawable2 != null) {
                drawable2.setBounds(0, 0, i4, i4);
                eVar.f548l.setCompoundDrawables(eVar.f551o, null, null, null);
            }
            eVar.f548l.setVisibility(0);
            i2 |= 2;
        }
        Button button3 = (Button) viewGroupA3.findViewById(R.id.button3);
        eVar.f552p = button3;
        button3.setOnClickListener(cVar);
        if (TextUtils.isEmpty(eVar.f553q) && eVar.f554s == null) {
            eVar.f552p.setVisibility(8);
        } else {
            eVar.f552p.setText(eVar.f553q);
            Drawable drawable3 = eVar.f547k;
            if (drawable3 != null) {
                drawable3.setBounds(0, 0, i4, i4);
                eVar.f544h.setCompoundDrawables(eVar.f547k, null, null, null);
            }
            eVar.f552p.setVisibility(0);
            i2 |= 4;
        }
        TypedValue typedValue = new TypedValue();
        eVar.f537a.getTheme().resolveAttribute(R$attr.alertDialogCenterButtons, typedValue, true);
        if (typedValue.data != 0) {
            if (i2 == 1) {
                Button button4 = eVar.f544h;
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) button4.getLayoutParams();
                layoutParams.gravity = 1;
                layoutParams.weight = 0.5f;
                button4.setLayoutParams(layoutParams);
            } else if (i2 == 2) {
                Button button5 = eVar.f548l;
                LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) button5.getLayoutParams();
                layoutParams2.gravity = 1;
                layoutParams2.weight = 0.5f;
                button5.setLayoutParams(layoutParams2);
            } else if (i2 == 4) {
                Button button6 = eVar.f552p;
                LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) button6.getLayoutParams();
                layoutParams3.gravity = 1;
                layoutParams3.weight = 0.5f;
                button6.setLayoutParams(layoutParams3);
            }
        }
        if (i2 == 0) {
            viewGroupA3.setVisibility(8);
        }
        if (eVar.f561z != null) {
            viewGroupA.addView(eVar.f561z, 0, new ViewGroup.LayoutParams(-1, -2));
            window.findViewById(R$id.title_template).setVisibility(8);
        } else {
            eVar.f558w = (ImageView) window.findViewById(R.id.icon);
            if ((!TextUtils.isEmpty(eVar.f541e)) && eVar.f534F) {
                TextView textView2 = (TextView) window.findViewById(R$id.alertTitle);
                eVar.f559x = textView2;
                textView2.setText(eVar.f541e);
                int i5 = eVar.f556u;
                if (i5 != 0) {
                    eVar.f558w.setImageResource(i5);
                } else {
                    Drawable drawable4 = eVar.f557v;
                    if (drawable4 != null) {
                        eVar.f558w.setImageDrawable(drawable4);
                    } else {
                        eVar.f559x.setPadding(eVar.f558w.getPaddingLeft(), eVar.f558w.getPaddingTop(), eVar.f558w.getPaddingRight(), eVar.f558w.getPaddingBottom());
                        eVar.f558w.setVisibility(8);
                    }
                }
            } else {
                window.findViewById(R$id.title_template).setVisibility(8);
                eVar.f558w.setVisibility(8);
                viewGroupA.setVisibility(8);
            }
        }
        boolean z2 = viewGroup.getVisibility() != 8;
        int i6 = (viewGroupA == null || viewGroupA.getVisibility() == 8) ? 0 : 1;
        boolean z3 = viewGroupA3.getVisibility() != 8;
        if (!z3 && (viewFindViewById = viewGroupA2.findViewById(R$id.textSpacerNoButtons)) != null) {
            viewFindViewById.setVisibility(0);
        }
        if (i6 != 0) {
            NestedScrollView nestedScrollView2 = eVar.f555t;
            if (nestedScrollView2 != null) {
                nestedScrollView2.setClipToPadding(true);
            }
            View viewFindViewById9 = (eVar.f542f == null && eVar.f543g == null) ? null : viewGroupA.findViewById(R$id.titleDividerNoCustom);
            if (viewFindViewById9 != null) {
                viewFindViewById9.setVisibility(0);
            }
        } else {
            View viewFindViewById10 = viewGroupA2.findViewById(R$id.textSpacerNoTitle);
            if (viewFindViewById10 != null) {
                viewFindViewById10.setVisibility(0);
            }
        }
        AlertController$RecycleListView alertController$RecycleListView = eVar.f543g;
        if (alertController$RecycleListView instanceof AlertController$RecycleListView) {
            alertController$RecycleListView.getClass();
            if (!z3 || i6 == 0) {
                alertController$RecycleListView.setPadding(alertController$RecycleListView.getPaddingLeft(), i6 != 0 ? alertController$RecycleListView.getPaddingTop() : alertController$RecycleListView.f468a, alertController$RecycleListView.getPaddingRight(), z3 ? alertController$RecycleListView.getPaddingBottom() : alertController$RecycleListView.f469b);
            }
        }
        if (!z2) {
            View view = eVar.f543g;
            if (view == null) {
                view = eVar.f555t;
            }
            if (view != null) {
                int i7 = i6 | (z3 ? 2 : 0);
                View viewFindViewById11 = window.findViewById(R$id.scrollIndicatorUp);
                View viewFindViewById12 = window.findViewById(R$id.scrollIndicatorDown);
                WeakHashMap weakHashMap = v.l.f2836a;
                view.setScrollIndicators(i7, 3);
                if (viewFindViewById11 != null) {
                    viewGroupA2.removeView(viewFindViewById11);
                }
                if (viewFindViewById12 != null) {
                    viewGroupA2.removeView(viewFindViewById12);
                }
            }
        }
        AlertController$RecycleListView alertController$RecycleListView2 = eVar.f543g;
        if (alertController$RecycleListView2 == null || (listAdapter = eVar.f529A) == null) {
            return;
        }
        alertController$RecycleListView2.setAdapter(listAdapter);
        int i8 = eVar.f530B;
        if (i8 > -1) {
            alertController$RecycleListView2.setItemChecked(i8, true);
            alertController$RecycleListView2.setSelection(i8);
        }
    }

    @Override // android.app.Dialog, android.view.KeyEvent.Callback
    public final boolean onKeyDown(int i2, KeyEvent keyEvent) {
        NestedScrollView nestedScrollView = this.f565c.f555t;
        if (nestedScrollView == null || !nestedScrollView.k(keyEvent)) {
            return super.onKeyDown(i2, keyEvent);
        }
        return true;
    }

    @Override // android.app.Dialog, android.view.KeyEvent.Callback
    public final boolean onKeyUp(int i2, KeyEvent keyEvent) {
        NestedScrollView nestedScrollView = this.f565c.f555t;
        if (nestedScrollView == null || !nestedScrollView.k(keyEvent)) {
            return super.onKeyUp(i2, keyEvent);
        }
        return true;
    }

    @Override // androidx.appcompat.app.s, android.app.Dialog
    public final void setTitle(CharSequence charSequence) {
        super.setTitle(charSequence);
        e eVar = this.f565c;
        eVar.f541e = charSequence;
        TextView textView = eVar.f559x;
        if (textView != null) {
            textView.setText(charSequence);
        }
    }
}
