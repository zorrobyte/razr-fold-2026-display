package com.motorola.taskbar.provider;

import android.content.ComponentName;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

/* JADX INFO: loaded from: classes2.dex */
public class ShortcutInfo implements BaseColumns {
    private final String mClassName;
    private final ComponentName mComponentName;
    private final int mId;
    private final String mPackageName;
    private final int mUserId;

    public ShortcutInfo(Cursor cursor) {
        this.mId = cursor.getInt(cursor.getColumnIndex("_id"));
        String string = cursor.getString(cursor.getColumnIndex("PackageName"));
        this.mPackageName = string;
        String string2 = cursor.getString(cursor.getColumnIndex("ClassName"));
        this.mClassName = string2;
        this.mUserId = cursor.getInt(cursor.getColumnIndex("UserId"));
        this.mComponentName = new ComponentName(string, string2);
    }

    public static void createTable(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("CREATE TABLE ShortcutInfo (_id INTEGER PRIMARY KEY,PackageName TEXT,ClassName TEXT,UserId INTEGER);");
    }

    public static String getKey(String str, int i) {
        return str + "#" + i;
    }

    public static void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        Log.d("ShortcutInfo", "onUpgrade oldVersion=" + i);
        Log.d("ShortcutInfo", "onUpgrade newVersion=" + i2);
    }

    public String getClassName() {
        return this.mClassName;
    }

    public ComponentName getComponentName() {
        return this.mComponentName;
    }

    public int getId() {
        return this.mId;
    }

    public String getKey() {
        return getKey(getPackageName(), getUserId());
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public int getUserId() {
        return this.mUserId;
    }
}
