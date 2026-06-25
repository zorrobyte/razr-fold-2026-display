package com.motorola.taskbar.provider;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.util.SparseIntArray;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public abstract class TaskbarProviderManager {
    public static int addShortcut(Context context, ComponentName componentName, int i) {
        ContentResolver contentResolver = context.getContentResolver();
        ContentValues contentValues = new ContentValues();
        contentValues.put("PackageName", componentName.getPackageName());
        contentValues.put("ClassName", componentName.getClassName());
        contentValues.put("UserId", Integer.valueOf(i));
        Uri uriInsert = contentResolver.insert(TaskbarProvider.CONTENT_URI_SHORTCUT_INFO, contentValues);
        if (uriInsert == null) {
            return -1;
        }
        return Integer.parseInt(uriInsert.getPathSegments().get(1));
    }

    public static SparseIntArray getShortcutOrder(Context context) {
        String string;
        Cursor cursorQuery = context.getContentResolver().query(TaskbarProvider.CONTENT_URI_ORDER_INFO, null, null, null, null);
        string = "";
        if (cursorQuery != null) {
            string = cursorQuery.moveToFirst() ? cursorQuery.getString(0) : "";
            cursorQuery.close();
        }
        String[] strArrSplit = string.split(",");
        SparseIntArray sparseIntArray = new SparseIntArray();
        int i = 0;
        for (String str : strArrSplit) {
            try {
                sparseIntArray.put(Integer.parseInt(str), i);
                i++;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return sparseIntArray;
    }

    public static void removeIfInvalidShortcuts(Context context, List list) {
        PackageManager packageManager = context.getPackageManager();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ShortcutInfo shortcutInfo = (ShortcutInfo) it.next();
            Intent intent = new Intent();
            intent.setComponent(shortcutInfo.getComponentName());
            intent.setAction("android.intent.action.MAIN").addCategory("android.intent.category.LAUNCHER");
            List listQueryIntentActivitiesAsUser = packageManager.queryIntentActivitiesAsUser(intent, 8388608, shortcutInfo.getUserId());
            if (listQueryIntentActivitiesAsUser == null || listQueryIntentActivitiesAsUser.size() <= 0) {
                removeShortcutInfo(context, shortcutInfo);
            }
        }
    }

    public static void removeShortcutInfo(Context context, ShortcutInfo shortcutInfo) {
        context.getContentResolver().delete(TaskbarProvider.CONTENT_URI_SHORTCUT_INFO, "PackageName=? AND ClassName=? AND UserId=?", new String[]{shortcutInfo.getPackageName(), shortcutInfo.getClassName(), String.valueOf(shortcutInfo.getUserId())});
    }

    public static void removeShortcutInfo(Context context, String str, int i) {
        context.getContentResolver().delete(TaskbarProvider.CONTENT_URI_SHORTCUT_INFO, "PackageName=? AND UserId=?", new String[]{str, String.valueOf(i)});
    }

    private static void saveShortcutOrder(Context context, String str) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("Order", str);
        context.getContentResolver().update(TaskbarProvider.CONTENT_URI_ORDER_INFO, contentValues, null, null);
    }

    public static void saveShortcutOrderByIds(Context context, List list) {
        String str = "";
        for (int i = 0; i < list.size(); i++) {
            int iIntValue = ((Integer) list.get(i)).intValue();
            str = i == list.size() - 1 ? str + iIntValue : str + iIntValue + ",";
        }
        saveShortcutOrder(context, str);
    }
}
