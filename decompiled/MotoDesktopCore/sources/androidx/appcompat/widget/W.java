package androidx.appcompat.widget;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

/* JADX INFO: loaded from: classes.dex */
public final class W implements TextWatcher {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ SearchView f1193a;

    public W(SearchView searchView) {
        this.f1193a = searchView;
    }

    @Override // android.text.TextWatcher
    public final void afterTextChanged(Editable editable) {
    }

    @Override // android.text.TextWatcher
    public final void beforeTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
    }

    @Override // android.text.TextWatcher
    public final void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
        SearchView searchView = this.f1193a;
        Editable text = searchView.f1100p.getText();
        searchView.V = text;
        boolean zIsEmpty = TextUtils.isEmpty(text);
        searchView.u(!zIsEmpty);
        int i5 = 8;
        if (searchView.f1092U && !searchView.f1085N && zIsEmpty) {
            searchView.f1104u.setVisibility(8);
            i5 = 0;
        }
        searchView.f1106w.setVisibility(i5);
        searchView.q();
        searchView.t();
        charSequence.toString();
        searchView.getClass();
    }
}
