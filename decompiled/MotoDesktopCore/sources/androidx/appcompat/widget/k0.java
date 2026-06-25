package androidx.appcompat.widget;

import android.app.SearchableInfo;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.appcompat.R$id;
import androidx.cursoradapter.widget.ResourceCursorAdapter;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.WeakHashMap;

/* JADX INFO: loaded from: classes.dex */
public final class k0 extends ResourceCursorAdapter implements View.OnClickListener {

    /* JADX INFO: renamed from: n, reason: collision with root package name */
    public static final /* synthetic */ int f1248n = 0;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final SearchView f1249a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final SearchableInfo f1250b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final Context f1251c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public final WeakHashMap f1252d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public final int f1253e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public int f1254f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public ColorStateList f1255g;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public int f1256h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public int f1257i;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public int f1258j;

    /* JADX INFO: renamed from: k, reason: collision with root package name */
    public int f1259k;

    /* JADX INFO: renamed from: l, reason: collision with root package name */
    public int f1260l;

    /* JADX INFO: renamed from: m, reason: collision with root package name */
    public int f1261m;

    public k0(Context context, SearchView searchView, SearchableInfo searchableInfo, WeakHashMap weakHashMap) {
        super(context, searchView.getSuggestionRowLayout(), (Cursor) null, true);
        this.f1254f = 1;
        this.f1256h = -1;
        this.f1257i = -1;
        this.f1258j = -1;
        this.f1259k = -1;
        this.f1260l = -1;
        this.f1261m = -1;
        this.f1249a = searchView;
        this.f1250b = searchableInfo;
        this.f1253e = searchView.getSuggestionCommitIconResId();
        this.f1251c = context;
        this.f1252d = weakHashMap;
    }

    public static String d(Cursor cursor, int i2) {
        if (i2 == -1) {
            return null;
        }
        try {
            return cursor.getString(i2);
        } catch (Exception e2) {
            Log.e("SuggestionsAdapter", "unexpected error retrieving valid column from cursor, did the remote process die?", e2);
            return null;
        }
    }

    public final Drawable a(Uri uri) throws FileNotFoundException {
        int identifier;
        String authority = uri.getAuthority();
        if (TextUtils.isEmpty(authority)) {
            throw new FileNotFoundException("No authority: " + uri);
        }
        try {
            Resources resourcesForApplication = this.mContext.getPackageManager().getResourcesForApplication(authority);
            List<String> pathSegments = uri.getPathSegments();
            if (pathSegments == null) {
                throw new FileNotFoundException("No path: " + uri);
            }
            int size = pathSegments.size();
            if (size == 1) {
                try {
                    identifier = Integer.parseInt(pathSegments.get(0));
                } catch (NumberFormatException unused) {
                    throw new FileNotFoundException("Single path segment is not a resource ID: " + uri);
                }
            } else {
                if (size != 2) {
                    throw new FileNotFoundException("More than two path segments: " + uri);
                }
                identifier = resourcesForApplication.getIdentifier(pathSegments.get(1), pathSegments.get(0), authority);
            }
            if (identifier != 0) {
                return resourcesForApplication.getDrawable(identifier);
            }
            throw new FileNotFoundException("No resource found for: " + uri);
        } catch (PackageManager.NameNotFoundException unused2) {
            throw new FileNotFoundException("No package found for authority: " + uri);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:54:0x010c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final android.graphics.drawable.Drawable b(java.lang.String r11) {
        /*
            Method dump skipped, instruction units count: 276
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.k0.b(java.lang.String):android.graphics.drawable.Drawable");
    }

    /* JADX WARN: Removed duplicated region for block: B:57:0x0148  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x014a  */
    @Override // androidx.cursoradapter.widget.CursorAdapter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void bindView(android.view.View r22, android.content.Context r23, android.database.Cursor r24) {
        /*
            Method dump skipped, instruction units count: 442
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.k0.bindView(android.view.View, android.content.Context, android.database.Cursor):void");
    }

    public final Cursor c(SearchableInfo searchableInfo, String str) {
        String suggestAuthority;
        String[] strArr = null;
        if (searchableInfo == null || (suggestAuthority = searchableInfo.getSuggestAuthority()) == null) {
            return null;
        }
        Uri.Builder builderFragment = new Uri.Builder().scheme("content").authority(suggestAuthority).query("").fragment("");
        String suggestPath = searchableInfo.getSuggestPath();
        if (suggestPath != null) {
            builderFragment.appendEncodedPath(suggestPath);
        }
        builderFragment.appendPath("search_suggest_query");
        String suggestSelection = searchableInfo.getSuggestSelection();
        if (suggestSelection != null) {
            strArr = new String[]{str};
        } else {
            builderFragment.appendPath(str);
        }
        String[] strArr2 = strArr;
        builderFragment.appendQueryParameter("limit", String.valueOf(50));
        return this.mContext.getContentResolver().query(builderFragment.build(), null, suggestSelection, strArr2, null);
    }

    @Override // androidx.cursoradapter.widget.CursorAdapter, androidx.cursoradapter.widget.c
    public final void changeCursor(Cursor cursor) {
        try {
            super.changeCursor(cursor);
            if (cursor != null) {
                this.f1256h = cursor.getColumnIndex("suggest_text_1");
                this.f1257i = cursor.getColumnIndex("suggest_text_2");
                this.f1258j = cursor.getColumnIndex("suggest_text_2_url");
                this.f1259k = cursor.getColumnIndex("suggest_icon_1");
                this.f1260l = cursor.getColumnIndex("suggest_icon_2");
                this.f1261m = cursor.getColumnIndex("suggest_flags");
            }
        } catch (Exception e2) {
            Log.e("SuggestionsAdapter", "error changing cursor and caching columns", e2);
        }
    }

    @Override // androidx.cursoradapter.widget.CursorAdapter, androidx.cursoradapter.widget.c
    public final CharSequence convertToString(Cursor cursor) {
        String strD;
        String strD2;
        if (cursor == null) {
            return null;
        }
        String strD3 = d(cursor, cursor.getColumnIndex("suggest_intent_query"));
        if (strD3 != null) {
            return strD3;
        }
        SearchableInfo searchableInfo = this.f1250b;
        if (searchableInfo.shouldRewriteQueryFromData() && (strD2 = d(cursor, cursor.getColumnIndex("suggest_intent_data"))) != null) {
            return strD2;
        }
        if (!searchableInfo.shouldRewriteQueryFromText() || (strD = d(cursor, cursor.getColumnIndex("suggest_text_1"))) == null) {
            return null;
        }
        return strD;
    }

    @Override // androidx.cursoradapter.widget.CursorAdapter, android.widget.BaseAdapter, android.widget.SpinnerAdapter
    public final View getDropDownView(int i2, View view, ViewGroup viewGroup) {
        try {
            return super.getDropDownView(i2, view, viewGroup);
        } catch (RuntimeException e2) {
            Log.w("SuggestionsAdapter", "Search suggestions cursor threw exception.", e2);
            View viewNewDropDownView = this.newDropDownView(this.mContext, this.mCursor, viewGroup);
            if (viewNewDropDownView != null) {
                ((j0) viewNewDropDownView.getTag()).f1220a.setText(e2.toString());
            }
            return viewNewDropDownView;
        }
    }

    @Override // androidx.cursoradapter.widget.CursorAdapter, android.widget.Adapter
    public final View getView(int i2, View view, ViewGroup viewGroup) {
        try {
            return super.getView(i2, view, viewGroup);
        } catch (RuntimeException e2) {
            Log.w("SuggestionsAdapter", "Search suggestions cursor threw exception.", e2);
            View viewNewView = this.newView(this.mContext, this.mCursor, viewGroup);
            ((j0) viewNewView.getTag()).f1220a.setText(e2.toString());
            return viewNewView;
        }
    }

    @Override // androidx.cursoradapter.widget.CursorAdapter, android.widget.BaseAdapter, android.widget.Adapter
    public final boolean hasStableIds() {
        return false;
    }

    @Override // androidx.cursoradapter.widget.ResourceCursorAdapter, androidx.cursoradapter.widget.CursorAdapter
    public final View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View viewNewView = super.newView(context, cursor, viewGroup);
        viewNewView.setTag(new j0(viewNewView));
        ((ImageView) viewNewView.findViewById(R$id.edit_query)).setImageResource(this.f1253e);
        return viewNewView;
    }

    @Override // android.widget.BaseAdapter
    public final void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        Cursor cursor = getCursor();
        Bundle extras = cursor != null ? cursor.getExtras() : null;
        if (extras != null) {
            extras.getBoolean("in_progress");
        }
    }

    @Override // android.widget.BaseAdapter
    public final void notifyDataSetInvalidated() {
        super.notifyDataSetInvalidated();
        Cursor cursor = getCursor();
        Bundle extras = cursor != null ? cursor.getExtras() : null;
        if (extras != null) {
            extras.getBoolean("in_progress");
        }
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        Object tag = view.getTag();
        if (tag instanceof CharSequence) {
            this.f1249a.o((CharSequence) tag);
        }
    }

    @Override // androidx.cursoradapter.widget.CursorAdapter, androidx.cursoradapter.widget.c
    public final Cursor runQueryOnBackgroundThread(CharSequence charSequence) {
        String string = charSequence == null ? "" : charSequence.toString();
        SearchView searchView = this.f1249a;
        if (searchView.getVisibility() == 0 && searchView.getWindowVisibility() == 0) {
            try {
                Cursor cursorC = c(this.f1250b, string);
                if (cursorC != null) {
                    cursorC.getCount();
                    return cursorC;
                }
            } catch (RuntimeException e2) {
                Log.w("SuggestionsAdapter", "Search suggestions query threw an exception.", e2);
            }
        }
        return null;
    }
}
