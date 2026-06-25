package androidx.appcompat.view.menu;

import android.R;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.ActionProvider;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import q.InterfaceMenuItemC0157a;

/* JADX INFO: renamed from: androidx.appcompat.view.menu.a, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0057a implements InterfaceMenuItemC0157a {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public CharSequence f713a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public CharSequence f714b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public Intent f715c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public char f716d;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public char f718f;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public Drawable f720h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public final Context f721i;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public CharSequence f722j;

    /* JADX INFO: renamed from: k, reason: collision with root package name */
    public CharSequence f723k;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public int f717e = 4096;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public int f719g = 4096;

    /* JADX INFO: renamed from: l, reason: collision with root package name */
    public ColorStateList f724l = null;

    /* JADX INFO: renamed from: m, reason: collision with root package name */
    public PorterDuff.Mode f725m = null;

    /* JADX INFO: renamed from: n, reason: collision with root package name */
    public boolean f726n = false;

    /* JADX INFO: renamed from: o, reason: collision with root package name */
    public boolean f727o = false;

    /* JADX INFO: renamed from: p, reason: collision with root package name */
    public int f728p = 16;

    public C0057a(Context context, CharSequence charSequence) {
        this.f721i = context;
        this.f713a = charSequence;
    }

    @Override // q.InterfaceMenuItemC0157a
    public final InterfaceMenuItemC0157a a(v.c cVar) {
        throw new UnsupportedOperationException();
    }

    @Override // q.InterfaceMenuItemC0157a
    public final v.c b() {
        return null;
    }

    public final void c() {
        Drawable drawable = this.f720h;
        if (drawable != null) {
            if (this.f726n || this.f727o) {
                this.f720h = drawable;
                Drawable drawableMutate = drawable.mutate();
                this.f720h = drawableMutate;
                if (this.f726n) {
                    drawableMutate.setTintList(this.f724l);
                }
                if (this.f727o) {
                    this.f720h.setTintMode(this.f725m);
                }
            }
        }
    }

    @Override // android.view.MenuItem
    public final boolean collapseActionView() {
        return false;
    }

    @Override // android.view.MenuItem
    public final boolean expandActionView() {
        return false;
    }

    @Override // android.view.MenuItem
    public final ActionProvider getActionProvider() {
        throw new UnsupportedOperationException();
    }

    @Override // android.view.MenuItem
    public final View getActionView() {
        return null;
    }

    @Override // q.InterfaceMenuItemC0157a, android.view.MenuItem
    public final int getAlphabeticModifiers() {
        return this.f719g;
    }

    @Override // android.view.MenuItem
    public final char getAlphabeticShortcut() {
        return this.f718f;
    }

    @Override // q.InterfaceMenuItemC0157a, android.view.MenuItem
    public final CharSequence getContentDescription() {
        return this.f722j;
    }

    @Override // android.view.MenuItem
    public final int getGroupId() {
        return 0;
    }

    @Override // android.view.MenuItem
    public final Drawable getIcon() {
        return this.f720h;
    }

    @Override // q.InterfaceMenuItemC0157a, android.view.MenuItem
    public final ColorStateList getIconTintList() {
        return this.f724l;
    }

    @Override // q.InterfaceMenuItemC0157a, android.view.MenuItem
    public final PorterDuff.Mode getIconTintMode() {
        return this.f725m;
    }

    @Override // android.view.MenuItem
    public final Intent getIntent() {
        return this.f715c;
    }

    @Override // android.view.MenuItem
    public final int getItemId() {
        return R.id.home;
    }

    @Override // android.view.MenuItem
    public final ContextMenu.ContextMenuInfo getMenuInfo() {
        return null;
    }

    @Override // q.InterfaceMenuItemC0157a, android.view.MenuItem
    public final int getNumericModifiers() {
        return this.f717e;
    }

    @Override // android.view.MenuItem
    public final char getNumericShortcut() {
        return this.f716d;
    }

    @Override // android.view.MenuItem
    public final int getOrder() {
        return 0;
    }

    @Override // android.view.MenuItem
    public final SubMenu getSubMenu() {
        return null;
    }

    @Override // android.view.MenuItem
    public final CharSequence getTitle() {
        return this.f713a;
    }

    @Override // android.view.MenuItem
    public final CharSequence getTitleCondensed() {
        CharSequence charSequence = this.f714b;
        return charSequence != null ? charSequence : this.f713a;
    }

    @Override // q.InterfaceMenuItemC0157a, android.view.MenuItem
    public final CharSequence getTooltipText() {
        return this.f723k;
    }

    @Override // android.view.MenuItem
    public final boolean hasSubMenu() {
        return false;
    }

    @Override // android.view.MenuItem
    public final boolean isActionViewExpanded() {
        return false;
    }

    @Override // android.view.MenuItem
    public final boolean isCheckable() {
        return (this.f728p & 1) != 0;
    }

    @Override // android.view.MenuItem
    public final boolean isChecked() {
        return (this.f728p & 2) != 0;
    }

    @Override // android.view.MenuItem
    public final boolean isEnabled() {
        return (this.f728p & 16) != 0;
    }

    @Override // android.view.MenuItem
    public final boolean isVisible() {
        return (this.f728p & 8) == 0;
    }

    @Override // android.view.MenuItem
    public final MenuItem setActionProvider(ActionProvider actionProvider) {
        throw new UnsupportedOperationException();
    }

    @Override // android.view.MenuItem
    public final MenuItem setActionView(int i2) {
        throw new UnsupportedOperationException();
    }

    @Override // android.view.MenuItem
    public final MenuItem setActionView(View view) {
        throw new UnsupportedOperationException();
    }

    @Override // android.view.MenuItem
    public final MenuItem setAlphabeticShortcut(char c2) {
        this.f718f = Character.toLowerCase(c2);
        return this;
    }

    @Override // q.InterfaceMenuItemC0157a, android.view.MenuItem
    public final MenuItem setAlphabeticShortcut(char c2, int i2) {
        this.f718f = Character.toLowerCase(c2);
        this.f719g = KeyEvent.normalizeMetaState(i2);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setCheckable(boolean z2) {
        this.f728p = (z2 ? 1 : 0) | (this.f728p & (-2));
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setChecked(boolean z2) {
        this.f728p = (z2 ? 2 : 0) | (this.f728p & (-3));
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setContentDescription(CharSequence charSequence) {
        this.f722j = charSequence;
        return this;
    }

    @Override // q.InterfaceMenuItemC0157a, android.view.MenuItem
    public final InterfaceMenuItemC0157a setContentDescription(CharSequence charSequence) {
        this.f722j = charSequence;
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setEnabled(boolean z2) {
        this.f728p = (z2 ? 16 : 0) | (this.f728p & (-17));
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setIcon(int i2) {
        this.f720h = this.f721i.getDrawable(i2);
        c();
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setIcon(Drawable drawable) {
        this.f720h = drawable;
        c();
        return this;
    }

    @Override // q.InterfaceMenuItemC0157a, android.view.MenuItem
    public final MenuItem setIconTintList(ColorStateList colorStateList) {
        this.f724l = colorStateList;
        this.f726n = true;
        c();
        return this;
    }

    @Override // q.InterfaceMenuItemC0157a, android.view.MenuItem
    public final MenuItem setIconTintMode(PorterDuff.Mode mode) {
        this.f725m = mode;
        this.f727o = true;
        c();
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setIntent(Intent intent) {
        this.f715c = intent;
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setNumericShortcut(char c2) {
        this.f716d = c2;
        return this;
    }

    @Override // q.InterfaceMenuItemC0157a, android.view.MenuItem
    public final MenuItem setNumericShortcut(char c2, int i2) {
        this.f716d = c2;
        this.f717e = KeyEvent.normalizeMetaState(i2);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setOnActionExpandListener(MenuItem.OnActionExpandListener onActionExpandListener) {
        throw new UnsupportedOperationException();
    }

    @Override // android.view.MenuItem
    public final MenuItem setOnMenuItemClickListener(MenuItem.OnMenuItemClickListener onMenuItemClickListener) {
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setShortcut(char c2, char c3) {
        this.f716d = c2;
        this.f718f = Character.toLowerCase(c3);
        return this;
    }

    @Override // q.InterfaceMenuItemC0157a, android.view.MenuItem
    public final MenuItem setShortcut(char c2, char c3, int i2, int i3) {
        this.f716d = c2;
        this.f717e = KeyEvent.normalizeMetaState(i2);
        this.f718f = Character.toLowerCase(c3);
        this.f719g = KeyEvent.normalizeMetaState(i3);
        return this;
    }

    @Override // android.view.MenuItem
    public final void setShowAsAction(int i2) {
    }

    @Override // android.view.MenuItem
    public final MenuItem setShowAsActionFlags(int i2) {
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setTitle(int i2) {
        this.f713a = this.f721i.getResources().getString(i2);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setTitle(CharSequence charSequence) {
        this.f713a = charSequence;
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setTitleCondensed(CharSequence charSequence) {
        this.f714b = charSequence;
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setTooltipText(CharSequence charSequence) {
        this.f723k = charSequence;
        return this;
    }

    @Override // q.InterfaceMenuItemC0157a, android.view.MenuItem
    public final InterfaceMenuItemC0157a setTooltipText(CharSequence charSequence) {
        this.f723k = charSequence;
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setVisible(boolean z2) {
        this.f728p = (this.f728p & 8) | (z2 ? 0 : 8);
        return this;
    }
}
