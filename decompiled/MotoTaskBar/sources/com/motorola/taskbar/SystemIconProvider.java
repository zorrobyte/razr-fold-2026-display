package com.motorola.taskbar;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Binder;
import android.util.SparseArray;
import com.android.systemui.Dependency;
import com.motorola.taskbar.bar.TaskBarController;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public class SystemIconProvider extends ContentProvider {
    private static final Uri CONTENT_URI_ICON_POSITION = Uri.parse("content://com.motorola.systemui.desk.SystemIconProvider/IconPosition/");

    private static String getKey(int i, String str, int i2) {
        return i + "_" + i2 + "_" + str;
    }

    @Override // android.content.ContentProvider
    public int delete(Uri uri, String str, String[] strArr) {
        return 0;
    }

    @Override // android.content.ContentProvider
    public String getType(Uri uri) {
        return null;
    }

    @Override // android.content.ContentProvider
    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        return true;
    }

    @Override // android.content.ContentProvider
    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        int i;
        if (!uri.toString().startsWith(CONTENT_URI_ICON_POSITION.toString())) {
            throw new IllegalArgumentException("Unknown URI " + uri);
        }
        List<String> pathSegments = uri.getPathSegments();
        if (pathSegments == null || pathSegments.size() < 2 || pathSegments.size() > 3) {
            throw new IllegalArgumentException("Invalid segments: " + pathSegments);
        }
        String str3 = pathSegments.get(1);
        if (pathSegments.size() > 2) {
            String str4 = pathSegments.get(2);
            try {
                i = Integer.parseInt(str4);
            } catch (NumberFormatException unused) {
                throw new IllegalArgumentException("Invalid displayId: " + str4);
            }
        } else {
            i = -1;
        }
        MatrixCursor matrixCursor = new MatrixCursor(new String[]{"displayId", "left", "top", "right", "bottom"});
        SparseArray sparseArrayQueryDesktopIconRect = ((TaskBarController) Dependency.get(TaskBarController.class)).queryDesktopIconRect(getKey(Binder.getCallingUid(), str3, i), i);
        for (int i2 = 0; i2 < sparseArrayQueryDesktopIconRect.size(); i2++) {
            Rect rect = (Rect) sparseArrayQueryDesktopIconRect.valueAt(i2);
            matrixCursor.addRow(new Object[]{Integer.valueOf(sparseArrayQueryDesktopIconRect.keyAt(i2)), Integer.valueOf(rect.left), Integer.valueOf(rect.top), Integer.valueOf(rect.right), Integer.valueOf(rect.bottom)});
        }
        return matrixCursor;
    }

    @Override // android.content.ContentProvider
    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        return 0;
    }
}
