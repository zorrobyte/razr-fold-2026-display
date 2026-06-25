package com.motorola.taskbar.provider;

import android.app.ActivityTaskManager;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.hardware.display.DisplayManager;
import android.net.Uri;
import android.os.Handler;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.widget.Toast;
import com.android.systemui.Dependency;
import com.android.systemui.Prefs;
import com.motorola.taskbar.R$string;
import com.motorola.taskbar.bar.TaskBarController;
import com.motorola.trackpad.ReadyForProxy;

/* JADX INFO: loaded from: classes2.dex */
public class TaskbarProvider extends ContentProvider {
    public static final Uri CONTENT_URI_CHOOSER_MODE_INFO;
    public static final Uri CONTENT_URI_ORDER_INFO;
    public static final Uri CONTENT_URI_SHORTCUT_INFO;
    private static final String[] mTables;
    public static final UriMatcher mUriMatcher;
    private static final Uri[] mUris;
    private DatabaseHelper mOpenHelper;
    private Handler mUiHandler;

    class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, "TaskbarProvider.db", (SQLiteDatabase.CursorFactory) null, 1);
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            ShortcutInfo.createTable(sQLiteDatabase);
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
            ShortcutInfo.onUpgrade(sQLiteDatabase, i, i2);
        }
    }

    static {
        Uri uri = Uri.parse("content://com.motorola.systemui.desk.TaskbarProvider/ShortcutInfo");
        CONTENT_URI_SHORTCUT_INFO = uri;
        Uri uri2 = Uri.parse("content://com.motorola.systemui.desk.TaskbarProvider/Order");
        CONTENT_URI_ORDER_INFO = uri2;
        Uri uri3 = Uri.parse("content://com.motorola.systemui.desk.TaskbarProvider/ChooserMode");
        CONTENT_URI_CHOOSER_MODE_INFO = uri3;
        UriMatcher uriMatcher = new UriMatcher(-1);
        mUriMatcher = uriMatcher;
        uriMatcher.addURI("com.motorola.systemui.desk.TaskbarProvider", "ShortcutInfo", 1);
        uriMatcher.addURI("com.motorola.systemui.desk.TaskbarProvider", "ShortcutInfo/#", 2);
        uriMatcher.addURI("com.motorola.systemui.desk.TaskbarProvider", "Order", 3);
        uriMatcher.addURI("com.motorola.systemui.desk.TaskbarProvider", "Order/#", 4);
        uriMatcher.addURI("com.motorola.systemui.desk.TaskbarProvider", "ChooserMode", 5);
        uriMatcher.addURI("com.motorola.systemui.desk.TaskbarProvider", "ChooserMode/#", 6);
        mTables = new String[]{"null", "ShortcutInfo", "ShortcutInfo", "Order", "Order", "ChooserMode", "ChooserMode"};
        mUris = new Uri[]{null, uri, uri, uri2, uri2, uri3, uri3};
    }

    private boolean canInsertNewShortcut() {
        if (DatabaseUtils.queryNumEntries(this.mOpenHelper.getReadableDatabase(), "ShortcutInfo") < 10) {
            return true;
        }
        getUiHandler().post(new Runnable() { // from class: com.motorola.taskbar.provider.TaskbarProvider$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$canInsertNewShortcut$0();
            }
        });
        return false;
    }

    private Handler getUiHandler() {
        if (this.mUiHandler == null) {
            this.mUiHandler = (Handler) Dependency.get(Dependency.MAIN_HANDLER);
        }
        return this.mUiHandler;
    }

    private Cursor handleCustomQuery(Uri uri, int i, String[] strArr, String str, String[] strArr2, String str2) {
        if (i == 3 || i == 4) {
            MatrixCursor matrixCursor = new MatrixCursor(new String[]{"Order"});
            matrixCursor.addRow(new Object[]{Prefs.getString(getContext(), "ShortcutOrder", "")});
            return matrixCursor;
        }
        if (i == 5) {
            return new MatrixCursor(new String[]{"mode"});
        }
        if (i != 6) {
            return null;
        }
        ReadyForProxy readyForProxy = (ReadyForProxy) Dependency.get(ReadyForProxy.class);
        TaskBarController taskBarController = (TaskBarController) Dependency.get(TaskBarController.class);
        MatrixCursor matrixCursor2 = new MatrixCursor(new String[]{"mode"});
        try {
            int i2 = Integer.parseInt(uri.getPathSegments().get(1));
            matrixCursor2.addRow(new Object[]{readyForProxy.isExternalModeChooserShowing(i2) ? "chooser" : taskBarController.getDisplayChooserMode(i2)});
            return matrixCursor2;
        } catch (NumberFormatException e) {
            Log.e("TaskbarProvider", "handleCustomQuery exception: " + uri);
            e.printStackTrace();
            return matrixCursor2;
        }
    }

    private int handleCustomUpdate(int i, ContentValues contentValues, String str, String[] strArr) {
        if (i != 3 && i != 4) {
            return -1;
        }
        String asString = contentValues.getAsString("Order");
        if (asString == null) {
            asString = "";
        }
        Prefs.putString(getContext(), "ShortcutOrder", asString);
        return 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$canInsertNewShortcut$0() {
        Display display;
        Context context = getContext();
        try {
            ActivityTaskManager.RootTaskInfo focusedRootTaskInfo = ActivityTaskManager.getService().getFocusedRootTaskInfo();
            if (focusedRootTaskInfo != null && focusedRootTaskInfo.displayId != 0 && (display = ((DisplayManager) context.getSystemService("display")).getDisplay(focusedRootTaskInfo.displayId)) != null) {
                context = context.createDisplayContext(display);
            }
        } catch (RemoteException unused) {
        }
        Toast.makeText(context, R$string.pin_apps_exceeded, 0).show();
    }

    @Override // android.content.ContentProvider
    public int bulkInsert(Uri uri, ContentValues[] contentValuesArr) {
        SQLiteDatabase writableDatabase = this.mOpenHelper.getWritableDatabase();
        int iMatch = mUriMatcher.match(uri);
        if (iMatch < 0) {
            throw new IllegalArgumentException("Unknown URI " + uri);
        }
        if (Math.abs(iMatch) % 2 != 1) {
            throw new IllegalArgumentException("Unknown URI " + uri);
        }
        String str = mTables[iMatch];
        writableDatabase.beginTransaction();
        try {
            for (ContentValues contentValues : contentValuesArr) {
                long jInsertOrThrow = writableDatabase.insertOrThrow(str, null, contentValues);
                if (jInsertOrThrow <= 0) {
                    Log.e("TaskbarProvider", "Bulk Insert error, value:" + contentValues.toString());
                    throw new SQLException("Failed to insert row into " + uri);
                }
                getContext().getContentResolver().notifyChange(ContentUris.withAppendedId(mUris[iMatch], jInsertOrThrow), (ContentObserver) null, 4);
            }
            writableDatabase.setTransactionSuccessful();
            int length = contentValuesArr.length;
            writableDatabase.endTransaction();
            return length;
        } catch (Throwable th) {
            writableDatabase.endTransaction();
            throw th;
        }
    }

    @Override // android.content.ContentProvider
    public int delete(Uri uri, String str, String[] strArr) {
        String str2;
        int iDelete;
        SQLiteDatabase writableDatabase = this.mOpenHelper.getWritableDatabase();
        int iMatch = mUriMatcher.match(uri);
        if (iMatch < 0) {
            throw new IllegalArgumentException("Unknown URI " + uri);
        }
        if (Math.abs(iMatch) % 2 == 1) {
            iDelete = writableDatabase.delete(mTables[iMatch], str, strArr);
        } else {
            String str3 = uri.getPathSegments().get(1);
            String str4 = mTables[iMatch];
            StringBuilder sb = new StringBuilder();
            sb.append("_id=");
            sb.append(str3);
            if (TextUtils.isEmpty(str)) {
                str2 = "";
            } else {
                str2 = " AND (" + str + ')';
            }
            sb.append(str2);
            iDelete = writableDatabase.delete(str4, sb.toString(), strArr);
        }
        getContext().getContentResolver().notifyChange(uri, (ContentObserver) null, 16);
        return iDelete;
    }

    @Override // android.content.ContentProvider
    public String getType(Uri uri) {
        return null;
    }

    @Override // android.content.ContentProvider
    public Uri insert(Uri uri, ContentValues contentValues) {
        SQLiteDatabase writableDatabase = this.mOpenHelper.getWritableDatabase();
        int iMatch = mUriMatcher.match(uri);
        if (iMatch < 0) {
            throw new IllegalArgumentException("Unknown URI " + uri);
        }
        if (Math.abs(iMatch) % 2 != 1) {
            throw new IllegalArgumentException("Unknown URI " + uri);
        }
        if (iMatch == 1 && !canInsertNewShortcut()) {
            return null;
        }
        long jInsert = writableDatabase.insert(mTables[iMatch], null, contentValues);
        if (jInsert <= 0) {
            return null;
        }
        Uri uriWithAppendedId = ContentUris.withAppendedId(mUris[iMatch], jInsert);
        getContext().getContentResolver().notifyChange(uriWithAppendedId, (ContentObserver) null, 4);
        return uriWithAppendedId;
    }

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        this.mOpenHelper = new DatabaseHelper(getContext());
        return true;
    }

    @Override // android.content.ContentProvider
    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        SQLiteQueryBuilder sQLiteQueryBuilder = new SQLiteQueryBuilder();
        int iMatch = mUriMatcher.match(uri);
        if (iMatch < 0) {
            throw new IllegalArgumentException("Unknown URI " + uri);
        }
        Cursor cursorHandleCustomQuery = handleCustomQuery(uri, iMatch, strArr, str, strArr2, str2);
        if (cursorHandleCustomQuery != null) {
            return cursorHandleCustomQuery;
        }
        if (Math.abs(iMatch) % 2 == 1) {
            sQLiteQueryBuilder.setTables(mTables[iMatch]);
        } else {
            sQLiteQueryBuilder.setTables(mTables[iMatch]);
            sQLiteQueryBuilder.appendWhere("_id=" + uri.getPathSegments().get(1));
        }
        Cursor cursorQuery = sQLiteQueryBuilder.query(this.mOpenHelper.getReadableDatabase(), strArr, str, strArr2, null, null, !TextUtils.isEmpty(str2) ? str2 : "_id ASC");
        cursorQuery.setNotificationUri(getContext().getContentResolver(), uri);
        return cursorQuery;
    }

    @Override // android.content.ContentProvider
    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        String str2;
        SQLiteDatabase writableDatabase = this.mOpenHelper.getWritableDatabase();
        int iMatch = mUriMatcher.match(uri);
        if (iMatch < 0) {
            throw new IllegalArgumentException("Unknown URI " + uri);
        }
        int iHandleCustomUpdate = handleCustomUpdate(iMatch, contentValues, str, strArr);
        if (iHandleCustomUpdate < 0) {
            if (Math.abs(iMatch) % 2 == 1) {
                iHandleCustomUpdate = writableDatabase.update(mTables[iMatch], contentValues, str, strArr);
            } else {
                String str3 = uri.getPathSegments().get(1);
                String str4 = mTables[iMatch];
                StringBuilder sb = new StringBuilder();
                sb.append("_id=");
                sb.append(str3);
                if (TextUtils.isEmpty(str)) {
                    str2 = "";
                } else {
                    str2 = " AND (" + str + ')';
                }
                sb.append(str2);
                iHandleCustomUpdate = writableDatabase.update(str4, contentValues, sb.toString(), strArr);
            }
        }
        getContext().getContentResolver().notifyChange(uri, (ContentObserver) null, 8);
        return iHandleCustomUpdate;
    }
}
