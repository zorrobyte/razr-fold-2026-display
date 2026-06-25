package androidx.appcompat.view.menu;

import android.content.Context;
import android.view.MenuItem;
import android.view.SubMenu;
import androidx.collection.SimpleArrayMap;
import androidx.core.internal.view.SupportMenuItem;

/* JADX INFO: loaded from: classes.dex */
abstract class BaseMenuWrapper {
    final Context mContext;
    private SimpleArrayMap mMenuItems;
    private SimpleArrayMap mSubMenus;

    BaseMenuWrapper(Context context) {
        this.mContext = context;
    }

    final MenuItem getMenuItemWrapper(MenuItem menuItem) {
        if (!(menuItem instanceof SupportMenuItem)) {
            return menuItem;
        }
        SupportMenuItem supportMenuItem = (SupportMenuItem) menuItem;
        if (this.mMenuItems == null) {
            this.mMenuItems = new SimpleArrayMap();
        }
        MenuItem menuItem2 = (MenuItem) this.mMenuItems.get(supportMenuItem);
        if (menuItem2 != null) {
            return menuItem2;
        }
        MenuItemWrapperICS menuItemWrapperICS = new MenuItemWrapperICS(this.mContext, supportMenuItem);
        this.mMenuItems.put(supportMenuItem, menuItemWrapperICS);
        return menuItemWrapperICS;
    }

    final SubMenu getSubMenuWrapper(SubMenu subMenu) {
        return subMenu;
    }

    final void internalClear() {
        SimpleArrayMap simpleArrayMap = this.mMenuItems;
        if (simpleArrayMap != null) {
            simpleArrayMap.clear();
        }
        SimpleArrayMap simpleArrayMap2 = this.mSubMenus;
        if (simpleArrayMap2 != null) {
            simpleArrayMap2.clear();
        }
    }

    final void internalRemoveGroup(int i) {
        if (this.mMenuItems == null) {
            return;
        }
        int i2 = 0;
        while (i2 < this.mMenuItems.size()) {
            if (((SupportMenuItem) this.mMenuItems.keyAt(i2)).getGroupId() == i) {
                this.mMenuItems.removeAt(i2);
                i2--;
            }
            i2++;
        }
    }

    final void internalRemoveItem(int i) {
        if (this.mMenuItems == null) {
            return;
        }
        for (int i2 = 0; i2 < this.mMenuItems.size(); i2++) {
            if (((SupportMenuItem) this.mMenuItems.keyAt(i2)).getItemId() == i) {
                this.mMenuItems.removeAt(i2);
                return;
            }
        }
    }
}
