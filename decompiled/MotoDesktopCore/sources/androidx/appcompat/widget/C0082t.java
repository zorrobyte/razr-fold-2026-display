package androidx.appcompat.widget;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.Log;
import android.util.TypedValue;
import androidx.appcompat.R$attr;
import androidx.appcompat.R$color;
import androidx.appcompat.R$drawable;
import b.AbstractC0122a;
import h.AbstractC0137d;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;
import o.AbstractC0152a;

/* JADX INFO: renamed from: androidx.appcompat.widget.t, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0082t {

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public static C0082t f1298f;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public WeakHashMap f1306a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final WeakHashMap f1307b = new WeakHashMap(0);

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public TypedValue f1308c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public boolean f1309d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public static final PorterDuff.Mode f1297e = PorterDuff.Mode.SRC_IN;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public static final C0081s f1299g = new C0081s(6);

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public static final int[] f1300h = {R$drawable.abc_textfield_search_default_mtrl_alpha, R$drawable.abc_textfield_default_mtrl_alpha, R$drawable.abc_ab_share_pack_mtrl_alpha};

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public static final int[] f1301i = {R$drawable.abc_ic_commit_search_api_mtrl_alpha, R$drawable.abc_seekbar_tick_mark_material, R$drawable.abc_ic_menu_share_mtrl_alpha, R$drawable.abc_ic_menu_copy_mtrl_am_alpha, R$drawable.abc_ic_menu_cut_mtrl_alpha, R$drawable.abc_ic_menu_selectall_mtrl_alpha, R$drawable.abc_ic_menu_paste_mtrl_am_alpha};

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public static final int[] f1302j = {R$drawable.abc_textfield_activated_mtrl_alpha, R$drawable.abc_textfield_search_activated_mtrl_alpha, R$drawable.abc_cab_background_top_mtrl_alpha, R$drawable.abc_text_cursor_material, R$drawable.abc_text_select_handle_left_mtrl_dark, R$drawable.abc_text_select_handle_middle_mtrl_dark, R$drawable.abc_text_select_handle_right_mtrl_dark, R$drawable.abc_text_select_handle_left_mtrl_light, R$drawable.abc_text_select_handle_middle_mtrl_light, R$drawable.abc_text_select_handle_right_mtrl_light};

    /* JADX INFO: renamed from: k, reason: collision with root package name */
    public static final int[] f1303k = {R$drawable.abc_popup_background_mtrl_mult, R$drawable.abc_cab_background_internal_bg, R$drawable.abc_menu_hardkey_panel_mtrl_mult};

    /* JADX INFO: renamed from: l, reason: collision with root package name */
    public static final int[] f1304l = {R$drawable.abc_tab_indicator_material, R$drawable.abc_textfield_search_material};

    /* JADX INFO: renamed from: m, reason: collision with root package name */
    public static final int[] f1305m = {R$drawable.abc_btn_check_material, R$drawable.abc_btn_radio_material};

    public static boolean b(int[] iArr, int i2) {
        for (int i3 : iArr) {
            if (i3 == i2) {
                return true;
            }
        }
        return false;
    }

    public static ColorStateList c(Context context, int i2) {
        int iB = l0.b(context, R$attr.colorControlHighlight);
        int iA = l0.a(context, R$attr.colorButtonNormal);
        int[] iArr = l0.f1263b;
        int[] iArr2 = l0.f1265d;
        int iA2 = AbstractC0152a.a(iB, i2);
        return new ColorStateList(new int[][]{iArr, iArr2, l0.f1264c, l0.f1267f}, new int[]{iA, iA2, AbstractC0152a.a(iB, i2), i2});
    }

    public static ColorStateList e(Context context) {
        int[][] iArr = new int[3][];
        int[] iArr2 = new int[3];
        ColorStateList colorStateListC = l0.c(context, R$attr.colorSwitchThumbNormal);
        if (colorStateListC == null || !colorStateListC.isStateful()) {
            iArr[0] = l0.f1263b;
            iArr2[0] = l0.a(context, R$attr.colorSwitchThumbNormal);
            iArr[1] = l0.f1266e;
            iArr2[1] = l0.b(context, R$attr.colorControlActivated);
            iArr[2] = l0.f1267f;
            iArr2[2] = l0.b(context, R$attr.colorSwitchThumbNormal);
        } else {
            int[] iArr3 = l0.f1263b;
            iArr[0] = iArr3;
            iArr2[0] = colorStateListC.getColorForState(iArr3, 0);
            iArr[1] = l0.f1266e;
            iArr2[1] = l0.b(context, R$attr.colorControlActivated);
            iArr[2] = l0.f1267f;
            iArr2[2] = colorStateListC.getDefaultColor();
        }
        return new ColorStateList(iArr, iArr2);
    }

    public static synchronized C0082t f() {
        try {
            if (f1298f == null) {
                f1298f = new C0082t();
            }
        } catch (Throwable th) {
            throw th;
        }
        return f1298f;
    }

    public static synchronized PorterDuffColorFilter j(int i2, PorterDuff.Mode mode) {
        PorterDuffColorFilter porterDuffColorFilter;
        C0081s c0081s = f1299g;
        c0081s.getClass();
        int i3 = (31 + i2) * 31;
        porterDuffColorFilter = (PorterDuffColorFilter) c0081s.a(Integer.valueOf(mode.hashCode() + i3));
        if (porterDuffColorFilter == null) {
            porterDuffColorFilter = new PorterDuffColorFilter(i2, mode);
        }
        return porterDuffColorFilter;
    }

    public static void l(Drawable drawable, int i2, PorterDuff.Mode mode) {
        if (G.a(drawable)) {
            drawable = drawable.mutate();
        }
        if (mode == null) {
            mode = f1297e;
        }
        drawable.setColorFilter(j(i2, mode));
    }

    public static void m(Drawable drawable, n0 n0Var, int[] iArr) {
        if (G.a(drawable) && drawable.mutate() != drawable) {
            Log.d("AppCompatDrawableManag", "Mutated drawable is not the same instance as the input.");
            return;
        }
        boolean z2 = n0Var.f1279d;
        if (!z2 && !n0Var.f1278c) {
            drawable.clearColorFilter();
            return;
        }
        PorterDuffColorFilter porterDuffColorFilterJ = null;
        ColorStateList colorStateList = z2 ? n0Var.f1276a : null;
        PorterDuff.Mode mode = n0Var.f1278c ? n0Var.f1277b : f1297e;
        if (colorStateList != null && mode != null) {
            porterDuffColorFilterJ = j(colorStateList.getColorForState(iArr, 0), mode);
        }
        drawable.setColorFilter(porterDuffColorFilterJ);
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x004b  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0066 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static boolean n(android.content.Context r7, int r8, android.graphics.drawable.Drawable r9) {
        /*
            int[] r0 = androidx.appcompat.widget.C0082t.f1300h
            boolean r0 = b(r0, r8)
            r1 = 1
            android.graphics.PorterDuff$Mode r2 = androidx.appcompat.widget.C0082t.f1297e
            r3 = 0
            r4 = -1
            if (r0 == 0) goto L12
            int r8 = androidx.appcompat.R$attr.colorControlNormal
        Lf:
            r5 = r1
        L10:
            r0 = r4
            goto L49
        L12:
            int[] r0 = androidx.appcompat.widget.C0082t.f1302j
            boolean r0 = b(r0, r8)
            if (r0 == 0) goto L1d
            int r8 = androidx.appcompat.R$attr.colorControlActivated
            goto Lf
        L1d:
            int[] r0 = androidx.appcompat.widget.C0082t.f1303k
            boolean r0 = b(r0, r8)
            r5 = 16842801(0x1010031, float:2.3693695E-38)
            if (r0 == 0) goto L2e
            android.graphics.PorterDuff$Mode r2 = android.graphics.PorterDuff.Mode.MULTIPLY
        L2a:
            r0 = r4
            r8 = r5
            r5 = r1
            goto L49
        L2e:
            int r0 = androidx.appcompat.R$drawable.abc_list_divider_mtrl_alpha
            if (r8 != r0) goto L41
            r8 = 1109603123(0x42233333, float:40.8)
            int r8 = java.lang.Math.round(r8)
            r0 = 16842800(0x1010030, float:2.3693693E-38)
            r5 = r1
            r6 = r0
            r0 = r8
            r8 = r6
            goto L49
        L41:
            int r0 = androidx.appcompat.R$drawable.abc_dialog_material_background
            if (r8 != r0) goto L46
            goto L2a
        L46:
            r8 = r3
            r5 = r8
            goto L10
        L49:
            if (r5 == 0) goto L66
            boolean r3 = androidx.appcompat.widget.G.a(r9)
            if (r3 == 0) goto L55
            android.graphics.drawable.Drawable r9 = r9.mutate()
        L55:
            int r7 = androidx.appcompat.widget.l0.b(r7, r8)
            android.graphics.PorterDuffColorFilter r7 = j(r7, r2)
            r9.setColorFilter(r7)
            if (r0 == r4) goto L65
            r9.setAlpha(r0)
        L65:
            return r1
        L66:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.C0082t.n(android.content.Context, int, android.graphics.drawable.Drawable):boolean");
    }

    public final synchronized void a(Context context, long j2, Drawable drawable) {
        try {
            Drawable.ConstantState constantState = drawable.getConstantState();
            if (constantState != null) {
                h.e eVar = (h.e) this.f1307b.get(context);
                if (eVar == null) {
                    eVar = new h.e();
                    this.f1307b.put(context, eVar);
                }
                eVar.e(j2, new WeakReference(constantState));
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public final Drawable d(Context context, int i2) {
        if (this.f1308c == null) {
            this.f1308c = new TypedValue();
        }
        TypedValue typedValue = this.f1308c;
        context.getResources().getValue(i2, typedValue, true);
        long j2 = (((long) typedValue.assetCookie) << 32) | ((long) typedValue.data);
        Drawable drawableG = g(context, j2);
        if (drawableG != null) {
            return drawableG;
        }
        if (i2 == R$drawable.abc_cab_background_top_material) {
            drawableG = new LayerDrawable(new Drawable[]{h(context, R$drawable.abc_cab_background_internal_bg), h(context, R$drawable.abc_cab_background_top_mtrl_alpha)});
        }
        if (drawableG != null) {
            drawableG.setChangingConfigurations(typedValue.changingConfigurations);
            a(context, j2, drawableG);
        }
        return drawableG;
    }

    public final synchronized Drawable g(Context context, long j2) {
        h.e eVar = (h.e) this.f1307b.get(context);
        if (eVar == null) {
            return null;
        }
        WeakReference weakReference = (WeakReference) eVar.d(j2, null);
        if (weakReference != null) {
            Drawable.ConstantState constantState = (Drawable.ConstantState) weakReference.get();
            if (constantState != null) {
                return constantState.newDrawable(context.getResources());
            }
            int iB = AbstractC0137d.b(eVar.f2585b, eVar.f2587d, j2);
            if (iB >= 0) {
                Object[] objArr = eVar.f2586c;
                Object obj = objArr[iB];
                Object obj2 = h.e.f2583e;
                if (obj != obj2) {
                    objArr[iB] = obj2;
                    eVar.f2584a = true;
                }
            }
        }
        return null;
    }

    public final synchronized Drawable h(Context context, int i2) {
        return i(context, i2, false);
    }

    public final synchronized Drawable i(Context context, int i2, boolean z2) {
        Drawable drawableD;
        if (!this.f1309d) {
            this.f1309d = true;
            Drawable drawableH = h(context, R$drawable.abc_vector_test);
            if (drawableH == null || (!(drawableH instanceof D.a) && !"android.graphics.drawable.VectorDrawable".equals(drawableH.getClass().getName()))) {
                this.f1309d = false;
                throw new IllegalStateException("This app has been built with an incorrect configuration. Please configure your build for VectorDrawableCompat.");
            }
        }
        drawableD = d(context, i2);
        if (drawableD == null) {
            drawableD = context.getDrawable(i2);
        }
        if (drawableD != null) {
            ColorStateList colorStateListK = k(context, i2);
            if (colorStateListK != null) {
                if (G.a(drawableD)) {
                    drawableD = drawableD.mutate();
                }
                drawableD.setTintList(colorStateListK);
                PorterDuff.Mode mode = i2 == R$drawable.abc_switch_thumb_material ? PorterDuff.Mode.MULTIPLY : null;
                if (mode != null) {
                    drawableD.setTintMode(mode);
                }
            } else {
                int i3 = R$drawable.abc_seekbar_track_material;
                PorterDuff.Mode mode2 = f1297e;
                if (i2 == i3) {
                    LayerDrawable layerDrawable = (LayerDrawable) drawableD;
                    l(layerDrawable.findDrawableByLayerId(R.id.background), l0.b(context, R$attr.colorControlNormal), mode2);
                    l(layerDrawable.findDrawableByLayerId(R.id.secondaryProgress), l0.b(context, R$attr.colorControlNormal), mode2);
                    l(layerDrawable.findDrawableByLayerId(R.id.progress), l0.b(context, R$attr.colorControlActivated), mode2);
                } else if (i2 == R$drawable.abc_ratingbar_material || i2 == R$drawable.abc_ratingbar_indicator_material || i2 == R$drawable.abc_ratingbar_small_material) {
                    LayerDrawable layerDrawable2 = (LayerDrawable) drawableD;
                    l(layerDrawable2.findDrawableByLayerId(R.id.background), l0.a(context, R$attr.colorControlNormal), mode2);
                    l(layerDrawable2.findDrawableByLayerId(R.id.secondaryProgress), l0.b(context, R$attr.colorControlActivated), mode2);
                    l(layerDrawable2.findDrawableByLayerId(R.id.progress), l0.b(context, R$attr.colorControlActivated), mode2);
                } else if (!n(context, i2, drawableD) && z2) {
                    drawableD = null;
                }
            }
        }
        if (drawableD != null) {
            Rect rect = G.f992a;
        }
        return drawableD;
    }

    public final synchronized ColorStateList k(Context context, int i2) {
        ColorStateList colorStateList;
        h.l lVar;
        try {
            WeakHashMap weakHashMap = this.f1306a;
            colorStateList = null;
            if (weakHashMap != null && (lVar = (h.l) weakHashMap.get(context)) != null) {
                colorStateList = (ColorStateList) lVar.d(i2, null);
            }
            if (colorStateList == null) {
                if (i2 == R$drawable.abc_edit_text_material) {
                    int i3 = R$color.abc_tint_edittext;
                    Object obj = AbstractC0122a.f2010a;
                    colorStateList = context.getColorStateList(i3);
                } else if (i2 == R$drawable.abc_switch_track_mtrl_alpha) {
                    int i4 = R$color.abc_tint_switch_track;
                    Object obj2 = AbstractC0122a.f2010a;
                    colorStateList = context.getColorStateList(i4);
                } else if (i2 == R$drawable.abc_switch_thumb_material) {
                    colorStateList = e(context);
                } else if (i2 == R$drawable.abc_btn_default_mtrl_shape) {
                    colorStateList = c(context, l0.b(context, R$attr.colorButtonNormal));
                } else if (i2 == R$drawable.abc_btn_borderless_material) {
                    colorStateList = c(context, 0);
                } else if (i2 == R$drawable.abc_btn_colored_material) {
                    colorStateList = c(context, l0.b(context, R$attr.colorAccent));
                } else if (i2 == R$drawable.abc_spinner_mtrl_am_alpha || i2 == R$drawable.abc_spinner_textfield_background_material) {
                    int i5 = R$color.abc_tint_spinner;
                    Object obj3 = AbstractC0122a.f2010a;
                    colorStateList = context.getColorStateList(i5);
                } else if (b(f1301i, i2)) {
                    colorStateList = l0.c(context, R$attr.colorControlNormal);
                } else if (b(f1304l, i2)) {
                    int i6 = R$color.abc_tint_default;
                    Object obj4 = AbstractC0122a.f2010a;
                    colorStateList = context.getColorStateList(i6);
                } else if (b(f1305m, i2)) {
                    int i7 = R$color.abc_tint_btn_checkable;
                    Object obj5 = AbstractC0122a.f2010a;
                    colorStateList = context.getColorStateList(i7);
                } else if (i2 == R$drawable.abc_seekbar_thumb_material) {
                    int i8 = R$color.abc_tint_seek_thumb;
                    Object obj6 = AbstractC0122a.f2010a;
                    colorStateList = context.getColorStateList(i8);
                }
                if (colorStateList != null) {
                    if (this.f1306a == null) {
                        this.f1306a = new WeakHashMap();
                    }
                    h.l lVar2 = (h.l) this.f1306a.get(context);
                    if (lVar2 == null) {
                        lVar2 = new h.l();
                        this.f1306a.put(context, lVar2);
                    }
                    lVar2.a(i2, colorStateList);
                }
            }
        } catch (Throwable th) {
            throw th;
        }
        return colorStateList;
    }
}
