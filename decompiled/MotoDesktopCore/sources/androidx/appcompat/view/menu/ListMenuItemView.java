package androidx.appcompat.view.menu;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.appcompat.R$attr;
import androidx.appcompat.R$id;
import androidx.appcompat.R$layout;
import androidx.appcompat.R$styleable;
import java.util.WeakHashMap;

/* JADX INFO: loaded from: classes.dex */
public class ListMenuItemView extends LinearLayout implements B, AbsListView.SelectionBoundsAdjuster {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public q f696a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public ImageView f697b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public RadioButton f698c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public TextView f699d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public CheckBox f700e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public TextView f701f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public ImageView f702g;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public ImageView f703h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public LinearLayout f704i;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public final Drawable f705j;

    /* JADX INFO: renamed from: k, reason: collision with root package name */
    public final int f706k;

    /* JADX INFO: renamed from: l, reason: collision with root package name */
    public final Context f707l;

    /* JADX INFO: renamed from: m, reason: collision with root package name */
    public boolean f708m;

    /* JADX INFO: renamed from: n, reason: collision with root package name */
    public final Drawable f709n;

    /* JADX INFO: renamed from: o, reason: collision with root package name */
    public final boolean f710o;

    /* JADX INFO: renamed from: p, reason: collision with root package name */
    public LayoutInflater f711p;

    /* JADX INFO: renamed from: q, reason: collision with root package name */
    public boolean f712q;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ListMenuItemView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        int i2 = R$attr.listMenuViewStyle;
        f0.b bVarM = f0.b.m(getContext(), attributeSet, R$styleable.MenuView, i2, 0);
        this.f705j = bVarM.f(R$styleable.MenuView_android_itemBackground);
        int i3 = R$styleable.MenuView_android_itemTextAppearance;
        TypedArray typedArray = (TypedArray) bVarM.f2538c;
        this.f706k = typedArray.getResourceId(i3, -1);
        this.f708m = typedArray.getBoolean(R$styleable.MenuView_preserveIconSpacing, false);
        this.f707l = context;
        this.f709n = bVarM.f(R$styleable.MenuView_subMenuArrow);
        TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(null, new int[]{R.attr.divider}, R$attr.dropDownListViewStyle, 0);
        this.f710o = typedArrayObtainStyledAttributes.hasValue(0);
        bVarM.n();
        typedArrayObtainStyledAttributes.recycle();
    }

    private LayoutInflater getInflater() {
        if (this.f711p == null) {
            this.f711p = LayoutInflater.from(getContext());
        }
        return this.f711p;
    }

    private void setSubMenuArrowVisible(boolean z2) {
        ImageView imageView = this.f702g;
        if (imageView != null) {
            imageView.setVisibility(z2 ? 0 : 8);
        }
    }

    @Override // android.widget.AbsListView.SelectionBoundsAdjuster
    public final void adjustListItemSelectionBounds(Rect rect) {
        ImageView imageView = this.f703h;
        if (imageView == null || imageView.getVisibility() != 0) {
            return;
        }
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.f703h.getLayoutParams();
        rect.top = this.f703h.getHeight() + layoutParams.topMargin + layoutParams.bottomMargin + rect.top;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0037  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x005a  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x005e  */
    @Override // androidx.appcompat.view.menu.B
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void c(androidx.appcompat.view.menu.q r11) {
        /*
            Method dump skipped, instruction units count: 315
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.view.menu.ListMenuItemView.c(androidx.appcompat.view.menu.q):void");
    }

    @Override // androidx.appcompat.view.menu.B
    public q getItemData() {
        return this.f696a;
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        WeakHashMap weakHashMap = v.l.f2836a;
        setBackground(this.f705j);
        TextView textView = (TextView) findViewById(R$id.title);
        this.f699d = textView;
        int i2 = this.f706k;
        if (i2 != -1) {
            textView.setTextAppearance(this.f707l, i2);
        }
        this.f701f = (TextView) findViewById(R$id.shortcut);
        ImageView imageView = (ImageView) findViewById(R$id.submenuarrow);
        this.f702g = imageView;
        if (imageView != null) {
            imageView.setImageDrawable(this.f709n);
        }
        this.f703h = (ImageView) findViewById(R$id.group_divider);
        this.f704i = (LinearLayout) findViewById(R$id.content);
    }

    @Override // android.widget.LinearLayout, android.view.View
    public final void onMeasure(int i2, int i3) {
        if (this.f697b != null && this.f708m) {
            ViewGroup.LayoutParams layoutParams = getLayoutParams();
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.f697b.getLayoutParams();
            int i4 = layoutParams.height;
            if (i4 > 0 && layoutParams2.width <= 0) {
                layoutParams2.width = i4;
            }
        }
        super.onMeasure(i2, i3);
    }

    public void setCheckable(boolean z2) {
        CompoundButton compoundButton;
        CompoundButton compoundButton2;
        if (!z2 && this.f698c == null && this.f700e == null) {
            return;
        }
        if ((this.f696a.f833x & 4) != 0) {
            if (this.f698c == null) {
                RadioButton radioButton = (RadioButton) getInflater().inflate(R$layout.abc_list_menu_item_radio, (ViewGroup) this, false);
                this.f698c = radioButton;
                LinearLayout linearLayout = this.f704i;
                if (linearLayout != null) {
                    linearLayout.addView(radioButton, -1);
                } else {
                    addView(radioButton, -1);
                }
            }
            compoundButton = this.f698c;
            compoundButton2 = this.f700e;
        } else {
            if (this.f700e == null) {
                CheckBox checkBox = (CheckBox) getInflater().inflate(R$layout.abc_list_menu_item_checkbox, (ViewGroup) this, false);
                this.f700e = checkBox;
                LinearLayout linearLayout2 = this.f704i;
                if (linearLayout2 != null) {
                    linearLayout2.addView(checkBox, -1);
                } else {
                    addView(checkBox, -1);
                }
            }
            compoundButton = this.f700e;
            compoundButton2 = this.f698c;
        }
        if (z2) {
            compoundButton.setChecked(this.f696a.isChecked());
            if (compoundButton.getVisibility() != 0) {
                compoundButton.setVisibility(0);
            }
            if (compoundButton2 == null || compoundButton2.getVisibility() == 8) {
                return;
            }
            compoundButton2.setVisibility(8);
            return;
        }
        CheckBox checkBox2 = this.f700e;
        if (checkBox2 != null) {
            checkBox2.setVisibility(8);
        }
        RadioButton radioButton2 = this.f698c;
        if (radioButton2 != null) {
            radioButton2.setVisibility(8);
        }
    }

    public void setChecked(boolean z2) {
        CompoundButton compoundButton;
        if ((this.f696a.f833x & 4) != 0) {
            if (this.f698c == null) {
                RadioButton radioButton = (RadioButton) getInflater().inflate(R$layout.abc_list_menu_item_radio, (ViewGroup) this, false);
                this.f698c = radioButton;
                LinearLayout linearLayout = this.f704i;
                if (linearLayout != null) {
                    linearLayout.addView(radioButton, -1);
                } else {
                    addView(radioButton, -1);
                }
            }
            compoundButton = this.f698c;
        } else {
            if (this.f700e == null) {
                CheckBox checkBox = (CheckBox) getInflater().inflate(R$layout.abc_list_menu_item_checkbox, (ViewGroup) this, false);
                this.f700e = checkBox;
                LinearLayout linearLayout2 = this.f704i;
                if (linearLayout2 != null) {
                    linearLayout2.addView(checkBox, -1);
                } else {
                    addView(checkBox, -1);
                }
            }
            compoundButton = this.f700e;
        }
        compoundButton.setChecked(z2);
    }

    public void setForceShowIcon(boolean z2) {
        this.f712q = z2;
        this.f708m = z2;
    }

    public void setGroupDividerEnabled(boolean z2) {
        ImageView imageView = this.f703h;
        if (imageView != null) {
            imageView.setVisibility((this.f710o || !z2) ? 8 : 0);
        }
    }

    public void setIcon(Drawable drawable) {
        boolean z2 = this.f696a.f824n.f798s || this.f712q;
        if (z2 || this.f708m) {
            ImageView imageView = this.f697b;
            if (imageView == null && drawable == null && !this.f708m) {
                return;
            }
            if (imageView == null) {
                ImageView imageView2 = (ImageView) getInflater().inflate(R$layout.abc_list_menu_item_icon, (ViewGroup) this, false);
                this.f697b = imageView2;
                LinearLayout linearLayout = this.f704i;
                if (linearLayout != null) {
                    linearLayout.addView(imageView2, 0);
                } else {
                    addView(imageView2, 0);
                }
            }
            if (drawable == null && !this.f708m) {
                this.f697b.setVisibility(8);
                return;
            }
            ImageView imageView3 = this.f697b;
            if (!z2) {
                drawable = null;
            }
            imageView3.setImageDrawable(drawable);
            if (this.f697b.getVisibility() != 0) {
                this.f697b.setVisibility(0);
            }
        }
    }

    public void setTitle(CharSequence charSequence) {
        if (charSequence == null) {
            if (this.f699d.getVisibility() != 8) {
                this.f699d.setVisibility(8);
            }
        } else {
            this.f699d.setText(charSequence);
            if (this.f699d.getVisibility() != 0) {
                this.f699d.setVisibility(0);
            }
        }
    }
}
