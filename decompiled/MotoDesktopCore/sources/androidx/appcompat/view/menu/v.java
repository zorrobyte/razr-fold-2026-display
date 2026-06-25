package androidx.appcompat.view.menu;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.ActionProvider;
import android.view.CollapsibleActionView;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import java.lang.reflect.Method;
import q.InterfaceMenuItemC0157a;

/* JADX INFO: loaded from: classes.dex */
public final class v extends AbstractC0060d implements MenuItem {

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public Method f842d;

    @Override // android.view.MenuItem
    public final boolean collapseActionView() {
        return ((InterfaceMenuItemC0157a) this.f733a).collapseActionView();
    }

    public final u d(ActionProvider actionProvider) {
        return new u(this, actionProvider);
    }

    @Override // android.view.MenuItem
    public final boolean expandActionView() {
        return ((InterfaceMenuItemC0157a) this.f733a).expandActionView();
    }

    @Override // android.view.MenuItem
    public final ActionProvider getActionProvider() {
        v.c cVarB = ((InterfaceMenuItemC0157a) this.f733a).b();
        if (cVarB instanceof u) {
            return ((u) cVarB).f839a;
        }
        return null;
    }

    @Override // android.view.MenuItem
    public final View getActionView() {
        View actionView = ((InterfaceMenuItemC0157a) this.f733a).getActionView();
        return actionView instanceof r ? (View) ((r) actionView).f836a : actionView;
    }

    @Override // android.view.MenuItem
    public final int getAlphabeticModifiers() {
        return ((InterfaceMenuItemC0157a) this.f733a).getAlphabeticModifiers();
    }

    @Override // android.view.MenuItem
    public final char getAlphabeticShortcut() {
        return ((InterfaceMenuItemC0157a) this.f733a).getAlphabeticShortcut();
    }

    @Override // android.view.MenuItem
    public final CharSequence getContentDescription() {
        return ((InterfaceMenuItemC0157a) this.f733a).getContentDescription();
    }

    @Override // android.view.MenuItem
    public final int getGroupId() {
        return ((InterfaceMenuItemC0157a) this.f733a).getGroupId();
    }

    @Override // android.view.MenuItem
    public final Drawable getIcon() {
        return ((InterfaceMenuItemC0157a) this.f733a).getIcon();
    }

    @Override // android.view.MenuItem
    public final ColorStateList getIconTintList() {
        return ((InterfaceMenuItemC0157a) this.f733a).getIconTintList();
    }

    @Override // android.view.MenuItem
    public final PorterDuff.Mode getIconTintMode() {
        return ((InterfaceMenuItemC0157a) this.f733a).getIconTintMode();
    }

    @Override // android.view.MenuItem
    public final Intent getIntent() {
        return ((InterfaceMenuItemC0157a) this.f733a).getIntent();
    }

    @Override // android.view.MenuItem
    public final int getItemId() {
        return ((InterfaceMenuItemC0157a) this.f733a).getItemId();
    }

    @Override // android.view.MenuItem
    public final ContextMenu.ContextMenuInfo getMenuInfo() {
        return ((InterfaceMenuItemC0157a) this.f733a).getMenuInfo();
    }

    @Override // android.view.MenuItem
    public final int getNumericModifiers() {
        return ((InterfaceMenuItemC0157a) this.f733a).getNumericModifiers();
    }

    @Override // android.view.MenuItem
    public final char getNumericShortcut() {
        return ((InterfaceMenuItemC0157a) this.f733a).getNumericShortcut();
    }

    @Override // android.view.MenuItem
    public final int getOrder() {
        return ((InterfaceMenuItemC0157a) this.f733a).getOrder();
    }

    @Override // android.view.MenuItem
    public final SubMenu getSubMenu() {
        return ((InterfaceMenuItemC0157a) this.f733a).getSubMenu();
    }

    @Override // android.view.MenuItem
    public final CharSequence getTitle() {
        return ((InterfaceMenuItemC0157a) this.f733a).getTitle();
    }

    @Override // android.view.MenuItem
    public final CharSequence getTitleCondensed() {
        return ((InterfaceMenuItemC0157a) this.f733a).getTitleCondensed();
    }

    @Override // android.view.MenuItem
    public final CharSequence getTooltipText() {
        return ((InterfaceMenuItemC0157a) this.f733a).getTooltipText();
    }

    @Override // android.view.MenuItem
    public final boolean hasSubMenu() {
        return ((InterfaceMenuItemC0157a) this.f733a).hasSubMenu();
    }

    @Override // android.view.MenuItem
    public final boolean isActionViewExpanded() {
        return ((InterfaceMenuItemC0157a) this.f733a).isActionViewExpanded();
    }

    @Override // android.view.MenuItem
    public final boolean isCheckable() {
        return ((InterfaceMenuItemC0157a) this.f733a).isCheckable();
    }

    @Override // android.view.MenuItem
    public final boolean isChecked() {
        return ((InterfaceMenuItemC0157a) this.f733a).isChecked();
    }

    @Override // android.view.MenuItem
    public final boolean isEnabled() {
        return ((InterfaceMenuItemC0157a) this.f733a).isEnabled();
    }

    @Override // android.view.MenuItem
    public final boolean isVisible() {
        return ((InterfaceMenuItemC0157a) this.f733a).isVisible();
    }

    @Override // android.view.MenuItem
    public final MenuItem setActionProvider(ActionProvider actionProvider) {
        ((InterfaceMenuItemC0157a) this.f733a).a(actionProvider != null ? d(actionProvider) : null);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setActionView(int i2) {
        Object obj = this.f733a;
        ((InterfaceMenuItemC0157a) obj).setActionView(i2);
        View actionView = ((InterfaceMenuItemC0157a) obj).getActionView();
        if (actionView instanceof CollapsibleActionView) {
            ((InterfaceMenuItemC0157a) obj).setActionView(new r(actionView));
        }
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setActionView(View view) {
        if (view instanceof CollapsibleActionView) {
            view = new r(view);
        }
        ((InterfaceMenuItemC0157a) this.f733a).setActionView(view);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setAlphabeticShortcut(char c2) {
        ((InterfaceMenuItemC0157a) this.f733a).setAlphabeticShortcut(c2);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setAlphabeticShortcut(char c2, int i2) {
        ((InterfaceMenuItemC0157a) this.f733a).setAlphabeticShortcut(c2, i2);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setCheckable(boolean z2) {
        ((InterfaceMenuItemC0157a) this.f733a).setCheckable(z2);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setChecked(boolean z2) {
        ((InterfaceMenuItemC0157a) this.f733a).setChecked(z2);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setContentDescription(CharSequence charSequence) {
        ((InterfaceMenuItemC0157a) this.f733a).setContentDescription(charSequence);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setEnabled(boolean z2) {
        ((InterfaceMenuItemC0157a) this.f733a).setEnabled(z2);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setIcon(int i2) {
        ((InterfaceMenuItemC0157a) this.f733a).setIcon(i2);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setIcon(Drawable drawable) {
        ((InterfaceMenuItemC0157a) this.f733a).setIcon(drawable);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setIconTintList(ColorStateList colorStateList) {
        ((InterfaceMenuItemC0157a) this.f733a).setIconTintList(colorStateList);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setIconTintMode(PorterDuff.Mode mode) {
        ((InterfaceMenuItemC0157a) this.f733a).setIconTintMode(mode);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setIntent(Intent intent) {
        ((InterfaceMenuItemC0157a) this.f733a).setIntent(intent);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setNumericShortcut(char c2) {
        ((InterfaceMenuItemC0157a) this.f733a).setNumericShortcut(c2);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setNumericShortcut(char c2, int i2) {
        ((InterfaceMenuItemC0157a) this.f733a).setNumericShortcut(c2, i2);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setOnActionExpandListener(MenuItem.OnActionExpandListener onActionExpandListener) {
        ((InterfaceMenuItemC0157a) this.f733a).setOnActionExpandListener(onActionExpandListener != null ? new s(this, onActionExpandListener) : null);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setOnMenuItemClickListener(MenuItem.OnMenuItemClickListener onMenuItemClickListener) {
        ((InterfaceMenuItemC0157a) this.f733a).setOnMenuItemClickListener(onMenuItemClickListener != null ? new t(this, onMenuItemClickListener) : null);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setShortcut(char c2, char c3) {
        ((InterfaceMenuItemC0157a) this.f733a).setShortcut(c2, c3);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setShortcut(char c2, char c3, int i2, int i3) {
        ((InterfaceMenuItemC0157a) this.f733a).setShortcut(c2, c3, i2, i3);
        return this;
    }

    @Override // android.view.MenuItem
    public final void setShowAsAction(int i2) {
        ((InterfaceMenuItemC0157a) this.f733a).setShowAsAction(i2);
    }

    @Override // android.view.MenuItem
    public final MenuItem setShowAsActionFlags(int i2) {
        ((InterfaceMenuItemC0157a) this.f733a).setShowAsActionFlags(i2);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setTitle(int i2) {
        ((InterfaceMenuItemC0157a) this.f733a).setTitle(i2);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setTitle(CharSequence charSequence) {
        ((InterfaceMenuItemC0157a) this.f733a).setTitle(charSequence);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setTitleCondensed(CharSequence charSequence) {
        ((InterfaceMenuItemC0157a) this.f733a).setTitleCondensed(charSequence);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setTooltipText(CharSequence charSequence) {
        ((InterfaceMenuItemC0157a) this.f733a).setTooltipText(charSequence);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setVisible(boolean z2) {
        return ((InterfaceMenuItemC0157a) this.f733a).setVisible(z2);
    }
}
