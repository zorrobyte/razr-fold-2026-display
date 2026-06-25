package androidx.appcompat.widget;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import androidx.appcompat.widget.SearchView;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes.dex */
public final class b0 implements View.OnKeyListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ SearchView f1203a;

    public b0(SearchView searchView) {
        this.f1203a = searchView;
    }

    @Override // android.view.View.OnKeyListener
    public final boolean onKey(View view, int i2, KeyEvent keyEvent) {
        SearchView searchView = this.f1203a;
        if (searchView.f1095b0 == null) {
            return false;
        }
        SearchView.SearchAutoComplete searchAutoComplete = searchView.f1100p;
        if (!searchAutoComplete.isPopupShowing() || searchAutoComplete.getListSelection() == -1) {
            if (TextUtils.getTrimmedLength(searchAutoComplete.getText()) == 0 || !keyEvent.hasNoModifiers() || keyEvent.getAction() != 1 || i2 != 66) {
                return false;
            }
            view.cancelLongPress();
            searchView.getContext().startActivity(searchView.j("android.intent.action.SEARCH", null, null, searchAutoComplete.getText().toString()));
            return true;
        }
        if (searchView.f1095b0 == null || searchView.f1086O == null || keyEvent.getAction() != 0 || !keyEvent.hasNoModifiers()) {
            return false;
        }
        if (i2 == 66 || i2 == 84 || i2 == 61) {
            searchView.m(searchAutoComplete.getListSelection());
        } else {
            if (i2 != 21 && i2 != 22) {
                if (i2 != 19) {
                    return false;
                }
                searchAutoComplete.getListSelection();
                return false;
            }
            searchAutoComplete.setSelection(i2 == 21 ? 0 : searchAutoComplete.length());
            searchAutoComplete.setListSelection(0);
            searchAutoComplete.clearListSelection();
            Method method = (Method) SearchView.f1071g0.f2539d;
            if (method != null) {
                try {
                    method.invoke(searchAutoComplete, Boolean.TRUE);
                } catch (Exception unused) {
                }
            }
        }
        return true;
    }
}
