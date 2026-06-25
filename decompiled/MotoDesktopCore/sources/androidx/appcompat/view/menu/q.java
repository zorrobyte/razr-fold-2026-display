package androidx.appcompat.view.menu;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.ActionProvider;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import b.AbstractC0122a;
import java.util.ArrayList;
import q.InterfaceMenuItemC0157a;

/* JADX INFO: loaded from: classes.dex */
public final class q implements InterfaceMenuItemC0157a {

    /* JADX INFO: renamed from: A, reason: collision with root package name */
    public v.c f808A;

    /* JADX INFO: renamed from: B, reason: collision with root package name */
    public MenuItem.OnActionExpandListener f809B;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final int f811a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final int f812b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final int f813c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public final int f814d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public CharSequence f815e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public CharSequence f816f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public Intent f817g;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public char f818h;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public char f820j;

    /* JADX INFO: renamed from: l, reason: collision with root package name */
    public Drawable f822l;

    /* JADX INFO: renamed from: n, reason: collision with root package name */
    public final o f824n;

    /* JADX INFO: renamed from: o, reason: collision with root package name */
    public G f825o;

    /* JADX INFO: renamed from: p, reason: collision with root package name */
    public MenuItem.OnMenuItemClickListener f826p;

    /* JADX INFO: renamed from: q, reason: collision with root package name */
    public CharSequence f827q;
    public CharSequence r;

    /* JADX INFO: renamed from: y, reason: collision with root package name */
    public int f834y;

    /* JADX INFO: renamed from: z, reason: collision with root package name */
    public View f835z;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public int f819i = 4096;

    /* JADX INFO: renamed from: k, reason: collision with root package name */
    public int f821k = 4096;

    /* JADX INFO: renamed from: m, reason: collision with root package name */
    public int f823m = 0;

    /* JADX INFO: renamed from: s, reason: collision with root package name */
    public ColorStateList f828s = null;

    /* JADX INFO: renamed from: t, reason: collision with root package name */
    public PorterDuff.Mode f829t = null;

    /* JADX INFO: renamed from: u, reason: collision with root package name */
    public boolean f830u = false;

    /* JADX INFO: renamed from: v, reason: collision with root package name */
    public boolean f831v = false;

    /* JADX INFO: renamed from: w, reason: collision with root package name */
    public boolean f832w = false;

    /* JADX INFO: renamed from: x, reason: collision with root package name */
    public int f833x = 16;

    /* JADX INFO: renamed from: C, reason: collision with root package name */
    public boolean f810C = false;

    public q(o oVar, int i2, int i3, int i4, int i5, CharSequence charSequence, int i6) {
        this.f824n = oVar;
        this.f811a = i3;
        this.f812b = i2;
        this.f813c = i4;
        this.f814d = i5;
        this.f815e = charSequence;
        this.f834y = i6;
    }

    public static void c(StringBuilder sb, int i2, int i3, String str) {
        if ((i2 & i3) == i3) {
            sb.append(str);
        }
    }

    @Override // q.InterfaceMenuItemC0157a
    public final InterfaceMenuItemC0157a a(v.c cVar) {
        v.c cVar2 = this.f808A;
        if (cVar2 != null) {
            cVar2.getClass();
        }
        this.f835z = null;
        this.f808A = cVar;
        this.f824n.p(true);
        v.c cVar3 = this.f808A;
        if (cVar3 != null) {
            cVar3.d(new e0.k(this));
        }
        return this;
    }

    @Override // q.InterfaceMenuItemC0157a
    public final v.c b() {
        return this.f808A;
    }

    @Override // android.view.MenuItem
    public final boolean collapseActionView() {
        if ((this.f834y & 8) == 0) {
            return false;
        }
        if (this.f835z == null) {
            return true;
        }
        MenuItem.OnActionExpandListener onActionExpandListener = this.f809B;
        if (onActionExpandListener == null || onActionExpandListener.onMenuItemActionCollapse(this)) {
            return this.f824n.d(this);
        }
        return false;
    }

    public final Drawable d(Drawable drawable) {
        if (drawable != null && this.f832w && (this.f830u || this.f831v)) {
            drawable = drawable.mutate();
            if (this.f830u) {
                drawable.setTintList(this.f828s);
            }
            if (this.f831v) {
                drawable.setTintMode(this.f829t);
            }
            this.f832w = false;
        }
        return drawable;
    }

    public final boolean e() {
        v.c cVar;
        if ((this.f834y & 8) == 0) {
            return false;
        }
        if (this.f835z == null && (cVar = this.f808A) != null) {
            this.f835z = cVar.b(this);
        }
        return this.f835z != null;
    }

    @Override // android.view.MenuItem
    public final boolean expandActionView() {
        if (!e()) {
            return false;
        }
        MenuItem.OnActionExpandListener onActionExpandListener = this.f809B;
        if (onActionExpandListener == null || onActionExpandListener.onMenuItemActionExpand(this)) {
            return this.f824n.f(this);
        }
        return false;
    }

    public final boolean f() {
        return (this.f833x & 32) == 32;
    }

    public final void g(boolean z2) {
        if (z2) {
            this.f833x |= 32;
        } else {
            this.f833x &= -33;
        }
    }

    @Override // android.view.MenuItem
    public final ActionProvider getActionProvider() {
        throw new UnsupportedOperationException("This is not supported, use MenuItemCompat.getActionProvider()");
    }

    @Override // android.view.MenuItem
    public final View getActionView() {
        View view = this.f835z;
        if (view != null) {
            return view;
        }
        v.c cVar = this.f808A;
        if (cVar == null) {
            return null;
        }
        View viewB = cVar.b(this);
        this.f835z = viewB;
        return viewB;
    }

    @Override // q.InterfaceMenuItemC0157a, android.view.MenuItem
    public final int getAlphabeticModifiers() {
        return this.f821k;
    }

    @Override // android.view.MenuItem
    public final char getAlphabeticShortcut() {
        return this.f820j;
    }

    @Override // q.InterfaceMenuItemC0157a, android.view.MenuItem
    public final CharSequence getContentDescription() {
        return this.f827q;
    }

    @Override // android.view.MenuItem
    public final int getGroupId() {
        return this.f812b;
    }

    @Override // android.view.MenuItem
    public final Drawable getIcon() {
        Drawable drawable = this.f822l;
        if (drawable != null) {
            return d(drawable);
        }
        int i2 = this.f823m;
        if (i2 == 0) {
            return null;
        }
        Drawable drawableA = AbstractC0122a.a(this.f824n.f781a, i2);
        this.f823m = 0;
        this.f822l = drawableA;
        return d(drawableA);
    }

    @Override // q.InterfaceMenuItemC0157a, android.view.MenuItem
    public final ColorStateList getIconTintList() {
        return this.f828s;
    }

    @Override // q.InterfaceMenuItemC0157a, android.view.MenuItem
    public final PorterDuff.Mode getIconTintMode() {
        return this.f829t;
    }

    @Override // android.view.MenuItem
    public final Intent getIntent() {
        return this.f817g;
    }

    @Override // android.view.MenuItem
    public final int getItemId() {
        return this.f811a;
    }

    @Override // android.view.MenuItem
    public final ContextMenu.ContextMenuInfo getMenuInfo() {
        return null;
    }

    @Override // q.InterfaceMenuItemC0157a, android.view.MenuItem
    public final int getNumericModifiers() {
        return this.f819i;
    }

    @Override // android.view.MenuItem
    public final char getNumericShortcut() {
        return this.f818h;
    }

    @Override // android.view.MenuItem
    public final int getOrder() {
        return this.f813c;
    }

    @Override // android.view.MenuItem
    public final SubMenu getSubMenu() {
        return this.f825o;
    }

    @Override // android.view.MenuItem
    public final CharSequence getTitle() {
        return this.f815e;
    }

    @Override // android.view.MenuItem
    public final CharSequence getTitleCondensed() {
        CharSequence charSequence = this.f816f;
        return charSequence != null ? charSequence : this.f815e;
    }

    @Override // q.InterfaceMenuItemC0157a, android.view.MenuItem
    public final CharSequence getTooltipText() {
        return this.r;
    }

    @Override // android.view.MenuItem
    public final boolean hasSubMenu() {
        return this.f825o != null;
    }

    @Override // android.view.MenuItem
    public final boolean isActionViewExpanded() {
        return this.f810C;
    }

    @Override // android.view.MenuItem
    public final boolean isCheckable() {
        return (this.f833x & 1) == 1;
    }

    @Override // android.view.MenuItem
    public final boolean isChecked() {
        return (this.f833x & 2) == 2;
    }

    @Override // android.view.MenuItem
    public final boolean isEnabled() {
        return (this.f833x & 16) != 0;
    }

    @Override // android.view.MenuItem
    public final boolean isVisible() {
        v.c cVar = this.f808A;
        return (cVar == null || !cVar.c()) ? (this.f833x & 8) == 0 : (this.f833x & 8) == 0 && this.f808A.a();
    }

    public final boolean requiresActionButton() {
        return (this.f834y & 2) == 2;
    }

    @Override // android.view.MenuItem
    public final MenuItem setActionProvider(ActionProvider actionProvider) {
        throw new UnsupportedOperationException("This is not supported, use MenuItemCompat.setActionProvider()");
    }

    @Override // android.view.MenuItem
    public final MenuItem setActionView(int i2) {
        int i3;
        Context context = this.f824n.f781a;
        View viewInflate = LayoutInflater.from(context).inflate(i2, (ViewGroup) new LinearLayout(context), false);
        this.f835z = viewInflate;
        this.f808A = null;
        if (viewInflate != null && viewInflate.getId() == -1 && (i3 = this.f811a) > 0) {
            viewInflate.setId(i3);
        }
        o oVar = this.f824n;
        oVar.f791k = true;
        oVar.p(true);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setActionView(View view) {
        int i2;
        this.f835z = view;
        this.f808A = null;
        if (view != null && view.getId() == -1 && (i2 = this.f811a) > 0) {
            view.setId(i2);
        }
        o oVar = this.f824n;
        oVar.f791k = true;
        oVar.p(true);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setAlphabeticShortcut(char c2) {
        if (this.f820j == c2) {
            return this;
        }
        this.f820j = Character.toLowerCase(c2);
        this.f824n.p(false);
        return this;
    }

    @Override // q.InterfaceMenuItemC0157a, android.view.MenuItem
    public final MenuItem setAlphabeticShortcut(char c2, int i2) {
        if (this.f820j == c2 && this.f821k == i2) {
            return this;
        }
        this.f820j = Character.toLowerCase(c2);
        this.f821k = KeyEvent.normalizeMetaState(i2);
        this.f824n.p(false);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setCheckable(boolean z2) {
        int i2 = this.f833x;
        int i3 = (z2 ? 1 : 0) | (i2 & (-2));
        this.f833x = i3;
        if (i2 != i3) {
            this.f824n.p(false);
        }
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setChecked(boolean z2) {
        int i2 = this.f833x;
        if ((i2 & 4) != 0) {
            o oVar = this.f824n;
            oVar.getClass();
            ArrayList arrayList = oVar.f786f;
            int size = arrayList.size();
            oVar.w();
            for (int i3 = 0; i3 < size; i3++) {
                q qVar = (q) arrayList.get(i3);
                if (qVar.f812b == this.f812b && (qVar.f833x & 4) != 0 && qVar.isCheckable()) {
                    boolean z3 = qVar == this;
                    int i4 = qVar.f833x;
                    int i5 = (z3 ? 2 : 0) | (i4 & (-3));
                    qVar.f833x = i5;
                    if (i4 != i5) {
                        qVar.f824n.p(false);
                    }
                }
            }
            oVar.v();
        } else {
            int i6 = (i2 & (-3)) | (z2 ? 2 : 0);
            this.f833x = i6;
            if (i2 != i6) {
                this.f824n.p(false);
            }
        }
        return this;
    }

    @Override // android.view.MenuItem
    public final /* bridge */ /* synthetic */ MenuItem setContentDescription(CharSequence charSequence) {
        setContentDescription(charSequence);
        return this;
    }

    @Override // q.InterfaceMenuItemC0157a, android.view.MenuItem
    public final InterfaceMenuItemC0157a setContentDescription(CharSequence charSequence) {
        this.f827q = charSequence;
        this.f824n.p(false);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setEnabled(boolean z2) {
        if (z2) {
            this.f833x |= 16;
        } else {
            this.f833x &= -17;
        }
        this.f824n.p(false);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setIcon(int i2) {
        this.f822l = null;
        this.f823m = i2;
        this.f832w = true;
        this.f824n.p(false);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setIcon(Drawable drawable) {
        this.f823m = 0;
        this.f822l = drawable;
        this.f832w = true;
        this.f824n.p(false);
        return this;
    }

    @Override // q.InterfaceMenuItemC0157a, android.view.MenuItem
    public final MenuItem setIconTintList(ColorStateList colorStateList) {
        this.f828s = colorStateList;
        this.f830u = true;
        this.f832w = true;
        this.f824n.p(false);
        return this;
    }

    @Override // q.InterfaceMenuItemC0157a, android.view.MenuItem
    public final MenuItem setIconTintMode(PorterDuff.Mode mode) {
        this.f829t = mode;
        this.f831v = true;
        this.f832w = true;
        this.f824n.p(false);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setIntent(Intent intent) {
        this.f817g = intent;
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setNumericShortcut(char c2) {
        if (this.f818h == c2) {
            return this;
        }
        this.f818h = c2;
        this.f824n.p(false);
        return this;
    }

    @Override // q.InterfaceMenuItemC0157a, android.view.MenuItem
    public final MenuItem setNumericShortcut(char c2, int i2) {
        if (this.f818h == c2 && this.f819i == i2) {
            return this;
        }
        this.f818h = c2;
        this.f819i = KeyEvent.normalizeMetaState(i2);
        this.f824n.p(false);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setOnActionExpandListener(MenuItem.OnActionExpandListener onActionExpandListener) {
        this.f809B = onActionExpandListener;
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setOnMenuItemClickListener(MenuItem.OnMenuItemClickListener onMenuItemClickListener) {
        this.f826p = onMenuItemClickListener;
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setShortcut(char c2, char c3) {
        this.f818h = c2;
        this.f820j = Character.toLowerCase(c3);
        this.f824n.p(false);
        return this;
    }

    @Override // q.InterfaceMenuItemC0157a, android.view.MenuItem
    public final MenuItem setShortcut(char c2, char c3, int i2, int i3) {
        this.f818h = c2;
        this.f819i = KeyEvent.normalizeMetaState(i2);
        this.f820j = Character.toLowerCase(c3);
        this.f821k = KeyEvent.normalizeMetaState(i3);
        this.f824n.p(false);
        return this;
    }

    @Override // android.view.MenuItem
    public final void setShowAsAction(int i2) {
        int i3 = i2 & 3;
        if (i3 != 0 && i3 != 1 && i3 != 2) {
            throw new IllegalArgumentException("SHOW_AS_ACTION_ALWAYS, SHOW_AS_ACTION_IF_ROOM, and SHOW_AS_ACTION_NEVER are mutually exclusive.");
        }
        this.f834y = i2;
        o oVar = this.f824n;
        oVar.f791k = true;
        oVar.p(true);
    }

    @Override // android.view.MenuItem
    public final MenuItem setShowAsActionFlags(int i2) {
        setShowAsAction(i2);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setTitle(int i2) {
        setTitle(this.f824n.f781a.getString(i2));
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setTitle(CharSequence charSequence) {
        this.f815e = charSequence;
        this.f824n.p(false);
        G g2 = this.f825o;
        if (g2 != null) {
            g2.setHeaderTitle(charSequence);
        }
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setTitleCondensed(CharSequence charSequence) {
        this.f816f = charSequence;
        this.f824n.p(false);
        return this;
    }

    @Override // android.view.MenuItem
    public final /* bridge */ /* synthetic */ MenuItem setTooltipText(CharSequence charSequence) {
        setTooltipText(charSequence);
        return this;
    }

    @Override // q.InterfaceMenuItemC0157a, android.view.MenuItem
    public final InterfaceMenuItemC0157a setTooltipText(CharSequence charSequence) {
        this.r = charSequence;
        this.f824n.p(false);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setVisible(boolean z2) {
        int i2 = this.f833x;
        int i3 = (z2 ? 0 : 8) | (i2 & (-9));
        this.f833x = i3;
        if (i2 != i3) {
            o oVar = this.f824n;
            oVar.f788h = true;
            oVar.p(true);
        }
        return this;
    }

    public final String toString() {
        CharSequence charSequence = this.f815e;
        if (charSequence != null) {
            return charSequence.toString();
        }
        return null;
    }
}
