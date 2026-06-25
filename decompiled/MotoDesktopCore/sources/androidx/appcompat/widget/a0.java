package androidx.appcompat.widget;

import android.app.SearchableInfo;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.widget.SearchView;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes.dex */
public final class a0 implements View.OnClickListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ SearchView f1201a;

    public a0(SearchView searchView) {
        this.f1201a = searchView;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        SearchView searchView = this.f1201a;
        ImageView imageView = searchView.f1103t;
        SearchView.SearchAutoComplete searchAutoComplete = searchView.f1100p;
        if (view == imageView) {
            searchView.v(false);
            searchAutoComplete.requestFocus();
            searchAutoComplete.setImeVisibility(true);
            View.OnClickListener onClickListener = searchView.f1083L;
            if (onClickListener != null) {
                onClickListener.onClick(searchView);
                return;
            }
            return;
        }
        if (view == searchView.f1105v) {
            searchView.l();
            return;
        }
        if (view == searchView.f1104u) {
            searchView.p();
            return;
        }
        String strFlattenToShortString = null;
        if (view != searchView.f1106w) {
            if (view == searchAutoComplete) {
                f0.b bVar = SearchView.f1071g0;
                Method method = (Method) bVar.f2537b;
                if (method != null) {
                    try {
                        method.invoke(searchAutoComplete, null);
                    } catch (Exception unused) {
                    }
                }
                Method method2 = (Method) bVar.f2538c;
                if (method2 != null) {
                    try {
                        method2.invoke(searchAutoComplete, null);
                        return;
                    } catch (Exception unused2) {
                        return;
                    }
                }
                return;
            }
            return;
        }
        SearchableInfo searchableInfo = searchView.f1095b0;
        if (searchableInfo == null) {
            return;
        }
        try {
            if (!searchableInfo.getVoiceSearchLaunchWebSearch()) {
                if (searchableInfo.getVoiceSearchLaunchRecognizer()) {
                    searchView.getContext().startActivity(searchView.k(searchView.f1080I, searchableInfo));
                    return;
                }
                return;
            }
            Intent intent = new Intent(searchView.f1079H);
            ComponentName searchActivity = searchableInfo.getSearchActivity();
            if (searchActivity != null) {
                strFlattenToShortString = searchActivity.flattenToShortString();
            }
            intent.putExtra("calling_package", strFlattenToShortString);
            searchView.getContext().startActivity(intent);
        } catch (ActivityNotFoundException unused3) {
            Log.w("SearchView", "Could not find voice search activity");
        }
    }
}
