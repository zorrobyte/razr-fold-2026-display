package androidx.appcompat.widget;

import android.app.PendingIntent;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import androidx.appcompat.R$attr;
import androidx.appcompat.R$dimen;
import androidx.appcompat.R$id;
import androidx.appcompat.R$layout;
import androidx.appcompat.R$string;
import androidx.appcompat.R$styleable;
import androidx.cursoradapter.widget.CursorAdapter;
import androidx.customview.view.AbsSavedState;
import java.lang.reflect.Method;
import java.util.WeakHashMap;

/* JADX INFO: loaded from: classes.dex */
public class SearchView extends LinearLayoutCompat implements d.c {

    /* JADX INFO: renamed from: g0, reason: collision with root package name */
    public static final f0.b f1071g0;

    /* JADX INFO: renamed from: A, reason: collision with root package name */
    public final Rect f1072A;

    /* JADX INFO: renamed from: B, reason: collision with root package name */
    public final int[] f1073B;

    /* JADX INFO: renamed from: C, reason: collision with root package name */
    public final int[] f1074C;

    /* JADX INFO: renamed from: D, reason: collision with root package name */
    public final ImageView f1075D;

    /* JADX INFO: renamed from: E, reason: collision with root package name */
    public final Drawable f1076E;

    /* JADX INFO: renamed from: F, reason: collision with root package name */
    public final int f1077F;

    /* JADX INFO: renamed from: G, reason: collision with root package name */
    public final int f1078G;

    /* JADX INFO: renamed from: H, reason: collision with root package name */
    public final Intent f1079H;

    /* JADX INFO: renamed from: I, reason: collision with root package name */
    public final Intent f1080I;

    /* JADX INFO: renamed from: J, reason: collision with root package name */
    public final CharSequence f1081J;

    /* JADX INFO: renamed from: K, reason: collision with root package name */
    public View.OnFocusChangeListener f1082K;

    /* JADX INFO: renamed from: L, reason: collision with root package name */
    public View.OnClickListener f1083L;

    /* JADX INFO: renamed from: M, reason: collision with root package name */
    public boolean f1084M;

    /* JADX INFO: renamed from: N, reason: collision with root package name */
    public boolean f1085N;

    /* JADX INFO: renamed from: O, reason: collision with root package name */
    public CursorAdapter f1086O;

    /* JADX INFO: renamed from: P, reason: collision with root package name */
    public boolean f1087P;

    /* JADX INFO: renamed from: Q, reason: collision with root package name */
    public CharSequence f1088Q;

    /* JADX INFO: renamed from: R, reason: collision with root package name */
    public boolean f1089R;

    /* JADX INFO: renamed from: S, reason: collision with root package name */
    public boolean f1090S;

    /* JADX INFO: renamed from: T, reason: collision with root package name */
    public int f1091T;

    /* JADX INFO: renamed from: U, reason: collision with root package name */
    public boolean f1092U;
    public CharSequence V;

    /* JADX INFO: renamed from: W, reason: collision with root package name */
    public boolean f1093W;

    /* JADX INFO: renamed from: a0, reason: collision with root package name */
    public int f1094a0;

    /* JADX INFO: renamed from: b0, reason: collision with root package name */
    public SearchableInfo f1095b0;

    /* JADX INFO: renamed from: c0, reason: collision with root package name */
    public Bundle f1096c0;

    /* JADX INFO: renamed from: d0, reason: collision with root package name */
    public final X f1097d0;

    /* JADX INFO: renamed from: e0, reason: collision with root package name */
    public final X f1098e0;

    /* JADX INFO: renamed from: f0, reason: collision with root package name */
    public final WeakHashMap f1099f0;

    /* JADX INFO: renamed from: p, reason: collision with root package name */
    public final SearchAutoComplete f1100p;

    /* JADX INFO: renamed from: q, reason: collision with root package name */
    public final View f1101q;
    public final View r;

    /* JADX INFO: renamed from: s, reason: collision with root package name */
    public final View f1102s;

    /* JADX INFO: renamed from: t, reason: collision with root package name */
    public final ImageView f1103t;

    /* JADX INFO: renamed from: u, reason: collision with root package name */
    public final ImageView f1104u;

    /* JADX INFO: renamed from: v, reason: collision with root package name */
    public final ImageView f1105v;

    /* JADX INFO: renamed from: w, reason: collision with root package name */
    public final ImageView f1106w;

    /* JADX INFO: renamed from: x, reason: collision with root package name */
    public final View f1107x;

    /* JADX INFO: renamed from: y, reason: collision with root package name */
    public i0 f1108y;

    /* JADX INFO: renamed from: z, reason: collision with root package name */
    public final Rect f1109z;

    public static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new g0();

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        public boolean f1110c;

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.f1110c = ((Boolean) parcel.readValue(null)).booleanValue();
        }

        public final String toString() {
            return "SearchView.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " isIconified=" + this.f1110c + "}";
        }

        @Override // androidx.customview.view.AbsSavedState, android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i2) {
            super.writeToParcel(parcel, i2);
            parcel.writeValue(Boolean.valueOf(this.f1110c));
        }
    }

    public static class SearchAutoComplete extends AppCompatAutoCompleteTextView {

        /* JADX INFO: renamed from: d, reason: collision with root package name */
        public int f1111d;

        /* JADX INFO: renamed from: e, reason: collision with root package name */
        public SearchView f1112e;

        /* JADX INFO: renamed from: f, reason: collision with root package name */
        public boolean f1113f;

        /* JADX INFO: renamed from: g, reason: collision with root package name */
        public final h0 f1114g;

        public SearchAutoComplete(Context context, AttributeSet attributeSet) {
            super(context, attributeSet, R$attr.autoCompleteTextViewStyle);
            this.f1114g = new h0(this);
            this.f1111d = getThreshold();
        }

        private int getSearchViewTextMinWidthDp() {
            Configuration configuration = getResources().getConfiguration();
            int i2 = configuration.screenWidthDp;
            int i3 = configuration.screenHeightDp;
            if (i2 >= 960 && i3 >= 720 && configuration.orientation == 2) {
                return 256;
            }
            if (i2 < 600) {
                return (i2 < 640 || i3 < 480) ? 160 : 192;
            }
            return 192;
        }

        @Override // android.widget.AutoCompleteTextView
        public final boolean enoughToFilter() {
            return this.f1111d <= 0 || super.enoughToFilter();
        }

        @Override // androidx.appcompat.widget.AppCompatAutoCompleteTextView, android.widget.TextView, android.view.View
        public final InputConnection onCreateInputConnection(EditorInfo editorInfo) {
            InputConnection inputConnectionOnCreateInputConnection = super.onCreateInputConnection(editorInfo);
            if (this.f1113f) {
                h0 h0Var = this.f1114g;
                removeCallbacks(h0Var);
                post(h0Var);
            }
            return inputConnectionOnCreateInputConnection;
        }

        @Override // android.view.View
        public final void onFinishInflate() {
            super.onFinishInflate();
            setMinWidth((int) TypedValue.applyDimension(1, getSearchViewTextMinWidthDp(), getResources().getDisplayMetrics()));
        }

        @Override // android.widget.AutoCompleteTextView, android.widget.TextView, android.view.View
        public final void onFocusChanged(boolean z2, int i2, Rect rect) {
            super.onFocusChanged(z2, i2, rect);
            SearchView searchView = this.f1112e;
            searchView.v(searchView.f1085N);
            searchView.post(searchView.f1097d0);
            SearchAutoComplete searchAutoComplete = searchView.f1100p;
            if (searchAutoComplete.hasFocus()) {
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
                    } catch (Exception unused2) {
                    }
                }
            }
        }

        @Override // android.widget.AutoCompleteTextView, android.widget.TextView, android.view.View
        public final boolean onKeyPreIme(int i2, KeyEvent keyEvent) {
            if (i2 == 4) {
                if (keyEvent.getAction() == 0 && keyEvent.getRepeatCount() == 0) {
                    KeyEvent.DispatcherState keyDispatcherState = getKeyDispatcherState();
                    if (keyDispatcherState != null) {
                        keyDispatcherState.startTracking(keyEvent, this);
                    }
                    return true;
                }
                if (keyEvent.getAction() == 1) {
                    KeyEvent.DispatcherState keyDispatcherState2 = getKeyDispatcherState();
                    if (keyDispatcherState2 != null) {
                        keyDispatcherState2.handleUpEvent(keyEvent);
                    }
                    if (keyEvent.isTracking() && !keyEvent.isCanceled()) {
                        this.f1112e.clearFocus();
                        setImeVisibility(false);
                        return true;
                    }
                }
            }
            return super.onKeyPreIme(i2, keyEvent);
        }

        @Override // android.widget.AutoCompleteTextView, android.widget.TextView, android.view.View
        public final void onWindowFocusChanged(boolean z2) {
            Method method;
            super.onWindowFocusChanged(z2);
            if (z2 && this.f1112e.hasFocus() && getVisibility() == 0) {
                this.f1113f = true;
                Context context = getContext();
                f0.b bVar = SearchView.f1071g0;
                if (context.getResources().getConfiguration().orientation != 2 || (method = (Method) SearchView.f1071g0.f2539d) == null) {
                    return;
                }
                try {
                    method.invoke(this, Boolean.TRUE);
                } catch (Exception unused) {
                }
            }
        }

        @Override // android.widget.AutoCompleteTextView
        public final void performCompletion() {
        }

        @Override // android.widget.AutoCompleteTextView
        public final void replaceText(CharSequence charSequence) {
        }

        public void setImeVisibility(boolean z2) {
            InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService("input_method");
            h0 h0Var = this.f1114g;
            if (!z2) {
                this.f1113f = false;
                removeCallbacks(h0Var);
                inputMethodManager.hideSoftInputFromWindow(getWindowToken(), 0);
            } else {
                if (!inputMethodManager.isActive(this)) {
                    this.f1113f = true;
                    return;
                }
                this.f1113f = false;
                removeCallbacks(h0Var);
                inputMethodManager.showSoftInput(this, 0);
            }
        }

        public void setSearchView(SearchView searchView) {
            this.f1112e = searchView;
        }

        @Override // android.widget.AutoCompleteTextView
        public void setThreshold(int i2) {
            super.setThreshold(i2);
            this.f1111d = i2;
        }
    }

    static {
        f0.b bVar = new f0.b(3);
        try {
            Method declaredMethod = AutoCompleteTextView.class.getDeclaredMethod("doBeforeTextChanged", null);
            bVar.f2537b = declaredMethod;
            declaredMethod.setAccessible(true);
        } catch (NoSuchMethodException unused) {
        }
        try {
            Method declaredMethod2 = AutoCompleteTextView.class.getDeclaredMethod("doAfterTextChanged", null);
            bVar.f2538c = declaredMethod2;
            declaredMethod2.setAccessible(true);
        } catch (NoSuchMethodException unused2) {
        }
        try {
            Method method = AutoCompleteTextView.class.getMethod("ensureImeVisible", Boolean.TYPE);
            bVar.f2539d = method;
            method.setAccessible(true);
        } catch (NoSuchMethodException unused3) {
        }
        f1071g0 = bVar;
    }

    public SearchView(Context context) {
        this(context, null);
    }

    public SearchView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R$attr.searchViewStyle);
    }

    public SearchView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.f1109z = new Rect();
        this.f1072A = new Rect();
        int i3 = 2;
        this.f1073B = new int[2];
        this.f1074C = new int[2];
        this.f1097d0 = new X(this, 0);
        this.f1098e0 = new X(this, 1);
        this.f1099f0 = new WeakHashMap();
        a0 a0Var = new a0(this);
        b0 b0Var = new b0(this);
        c0 c0Var = new c0(this);
        Y.y yVar = new Y.y(i3, this);
        Y.z zVar = new Y.z(i3, this);
        W w2 = new W(this);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.SearchView, i2, 0);
        f0.b bVar = new f0.b(context, typedArrayObtainStyledAttributes);
        LayoutInflater.from(context).inflate(typedArrayObtainStyledAttributes.getResourceId(R$styleable.SearchView_layout, R$layout.abc_search_view), (ViewGroup) this, true);
        SearchAutoComplete searchAutoComplete = (SearchAutoComplete) findViewById(R$id.search_src_text);
        this.f1100p = searchAutoComplete;
        searchAutoComplete.setSearchView(this);
        this.f1101q = findViewById(R$id.search_edit_frame);
        View viewFindViewById = findViewById(R$id.search_plate);
        this.r = viewFindViewById;
        View viewFindViewById2 = findViewById(R$id.submit_area);
        this.f1102s = viewFindViewById2;
        ImageView imageView = (ImageView) findViewById(R$id.search_button);
        this.f1103t = imageView;
        ImageView imageView2 = (ImageView) findViewById(R$id.search_go_btn);
        this.f1104u = imageView2;
        ImageView imageView3 = (ImageView) findViewById(R$id.search_close_btn);
        this.f1105v = imageView3;
        ImageView imageView4 = (ImageView) findViewById(R$id.search_voice_btn);
        this.f1106w = imageView4;
        ImageView imageView5 = (ImageView) findViewById(R$id.search_mag_icon);
        this.f1075D = imageView5;
        Drawable drawableF = bVar.f(R$styleable.SearchView_queryBackground);
        WeakHashMap weakHashMap = v.l.f2836a;
        viewFindViewById.setBackground(drawableF);
        viewFindViewById2.setBackground(bVar.f(R$styleable.SearchView_submitBackground));
        imageView.setImageDrawable(bVar.f(R$styleable.SearchView_searchIcon));
        imageView2.setImageDrawable(bVar.f(R$styleable.SearchView_goIcon));
        imageView3.setImageDrawable(bVar.f(R$styleable.SearchView_closeIcon));
        imageView4.setImageDrawable(bVar.f(R$styleable.SearchView_voiceIcon));
        imageView5.setImageDrawable(bVar.f(R$styleable.SearchView_searchIcon));
        this.f1076E = bVar.f(R$styleable.SearchView_searchHintIcon);
        imageView.setTooltipText(getResources().getString(R$string.abc_searchview_description_search));
        this.f1077F = typedArrayObtainStyledAttributes.getResourceId(R$styleable.SearchView_suggestionRowLayout, R$layout.abc_search_dropdown_item_icons_2line);
        this.f1078G = typedArrayObtainStyledAttributes.getResourceId(R$styleable.SearchView_commitIcon, 0);
        imageView.setOnClickListener(a0Var);
        imageView3.setOnClickListener(a0Var);
        imageView2.setOnClickListener(a0Var);
        imageView4.setOnClickListener(a0Var);
        searchAutoComplete.setOnClickListener(a0Var);
        searchAutoComplete.addTextChangedListener(w2);
        searchAutoComplete.setOnEditorActionListener(c0Var);
        searchAutoComplete.setOnItemClickListener(yVar);
        searchAutoComplete.setOnItemSelectedListener(zVar);
        searchAutoComplete.setOnKeyListener(b0Var);
        searchAutoComplete.setOnFocusChangeListener(new Y(this));
        setIconifiedByDefault(typedArrayObtainStyledAttributes.getBoolean(R$styleable.SearchView_iconifiedByDefault, true));
        int dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(R$styleable.SearchView_android_maxWidth, -1);
        if (dimensionPixelSize != -1) {
            setMaxWidth(dimensionPixelSize);
        }
        this.f1081J = typedArrayObtainStyledAttributes.getText(R$styleable.SearchView_defaultQueryHint);
        this.f1088Q = typedArrayObtainStyledAttributes.getText(R$styleable.SearchView_queryHint);
        int i4 = typedArrayObtainStyledAttributes.getInt(R$styleable.SearchView_android_imeOptions, -1);
        if (i4 != -1) {
            setImeOptions(i4);
        }
        int i5 = typedArrayObtainStyledAttributes.getInt(R$styleable.SearchView_android_inputType, -1);
        if (i5 != -1) {
            setInputType(i5);
        }
        setFocusable(typedArrayObtainStyledAttributes.getBoolean(R$styleable.SearchView_android_focusable, true));
        bVar.n();
        Intent intent = new Intent("android.speech.action.WEB_SEARCH");
        this.f1079H = intent;
        intent.addFlags(268435456);
        intent.putExtra("android.speech.extra.LANGUAGE_MODEL", "web_search");
        Intent intent2 = new Intent("android.speech.action.RECOGNIZE_SPEECH");
        this.f1080I = intent2;
        intent2.addFlags(268435456);
        View viewFindViewById3 = findViewById(searchAutoComplete.getDropDownAnchor());
        this.f1107x = viewFindViewById3;
        if (viewFindViewById3 != null) {
            viewFindViewById3.addOnLayoutChangeListener(new Z(this));
        }
        v(this.f1084M);
        s();
    }

    private int getPreferredHeight() {
        return getContext().getResources().getDimensionPixelSize(R$dimen.abc_search_view_preferred_height);
    }

    private int getPreferredWidth() {
        return getContext().getResources().getDimensionPixelSize(R$dimen.abc_search_view_preferred_width);
    }

    private void setQuery(CharSequence charSequence) {
        SearchAutoComplete searchAutoComplete = this.f1100p;
        searchAutoComplete.setText(charSequence);
        searchAutoComplete.setSelection(TextUtils.isEmpty(charSequence) ? 0 : charSequence.length());
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void clearFocus() {
        this.f1090S = true;
        super.clearFocus();
        SearchAutoComplete searchAutoComplete = this.f1100p;
        searchAutoComplete.clearFocus();
        searchAutoComplete.setImeVisibility(false);
        this.f1090S = false;
    }

    public int getImeOptions() {
        return this.f1100p.getImeOptions();
    }

    public int getInputType() {
        return this.f1100p.getInputType();
    }

    public int getMaxWidth() {
        return this.f1091T;
    }

    public CharSequence getQuery() {
        return this.f1100p.getText();
    }

    public CharSequence getQueryHint() {
        CharSequence charSequence = this.f1088Q;
        if (charSequence != null) {
            return charSequence;
        }
        SearchableInfo searchableInfo = this.f1095b0;
        return (searchableInfo == null || searchableInfo.getHintId() == 0) ? this.f1081J : getContext().getText(this.f1095b0.getHintId());
    }

    public int getSuggestionCommitIconResId() {
        return this.f1078G;
    }

    public int getSuggestionRowLayout() {
        return this.f1077F;
    }

    public CursorAdapter getSuggestionsAdapter() {
        return this.f1086O;
    }

    public final Intent j(String str, Uri uri, String str2, String str3) {
        Intent intent = new Intent(str);
        intent.addFlags(268435456);
        if (uri != null) {
            intent.setData(uri);
        }
        intent.putExtra("user_query", this.V);
        if (str3 != null) {
            intent.putExtra("query", str3);
        }
        if (str2 != null) {
            intent.putExtra("intent_extra_data_key", str2);
        }
        Bundle bundle = this.f1096c0;
        if (bundle != null) {
            intent.putExtra("app_data", bundle);
        }
        intent.setComponent(this.f1095b0.getSearchActivity());
        return intent;
    }

    public final Intent k(Intent intent, SearchableInfo searchableInfo) {
        ComponentName searchActivity = searchableInfo.getSearchActivity();
        Intent intent2 = new Intent("android.intent.action.SEARCH");
        intent2.setComponent(searchActivity);
        PendingIntent activity = PendingIntent.getActivity(getContext(), 0, intent2, 1073741824);
        Bundle bundle = new Bundle();
        Bundle bundle2 = this.f1096c0;
        if (bundle2 != null) {
            bundle.putParcelable("app_data", bundle2);
        }
        Intent intent3 = new Intent(intent);
        Resources resources = getResources();
        String string = searchableInfo.getVoiceLanguageModeId() != 0 ? resources.getString(searchableInfo.getVoiceLanguageModeId()) : "free_form";
        String string2 = searchableInfo.getVoicePromptTextId() != 0 ? resources.getString(searchableInfo.getVoicePromptTextId()) : null;
        String string3 = searchableInfo.getVoiceLanguageId() != 0 ? resources.getString(searchableInfo.getVoiceLanguageId()) : null;
        int voiceMaxResults = searchableInfo.getVoiceMaxResults() != 0 ? searchableInfo.getVoiceMaxResults() : 1;
        intent3.putExtra("android.speech.extra.LANGUAGE_MODEL", string);
        intent3.putExtra("android.speech.extra.PROMPT", string2);
        intent3.putExtra("android.speech.extra.LANGUAGE", string3);
        intent3.putExtra("android.speech.extra.MAX_RESULTS", voiceMaxResults);
        intent3.putExtra("calling_package", searchActivity != null ? searchActivity.flattenToShortString() : null);
        intent3.putExtra("android.speech.extra.RESULTS_PENDINGINTENT", activity);
        intent3.putExtra("android.speech.extra.RESULTS_PENDINGINTENT_BUNDLE", bundle);
        return intent3;
    }

    public final void l() {
        SearchAutoComplete searchAutoComplete = this.f1100p;
        if (!TextUtils.isEmpty(searchAutoComplete.getText())) {
            searchAutoComplete.setText("");
            searchAutoComplete.requestFocus();
            searchAutoComplete.setImeVisibility(true);
        } else if (this.f1084M) {
            clearFocus();
            v(true);
        }
    }

    public final void m(int i2) {
        int position;
        String strD;
        Cursor cursor = this.f1086O.getCursor();
        if (cursor != null && cursor.moveToPosition(i2)) {
            Intent intentJ = null;
            try {
                int i3 = k0.f1248n;
                String strD2 = k0.d(cursor, cursor.getColumnIndex("suggest_intent_action"));
                if (strD2 == null) {
                    strD2 = this.f1095b0.getSuggestIntentAction();
                }
                if (strD2 == null) {
                    strD2 = "android.intent.action.SEARCH";
                }
                String strD3 = k0.d(cursor, cursor.getColumnIndex("suggest_intent_data"));
                if (strD3 == null) {
                    strD3 = this.f1095b0.getSuggestIntentData();
                }
                if (strD3 != null && (strD = k0.d(cursor, cursor.getColumnIndex("suggest_intent_data_id"))) != null) {
                    strD3 = strD3 + "/" + Uri.encode(strD);
                }
                intentJ = j(strD2, strD3 == null ? null : Uri.parse(strD3), k0.d(cursor, cursor.getColumnIndex("suggest_intent_extra_data")), k0.d(cursor, cursor.getColumnIndex("suggest_intent_query")));
            } catch (RuntimeException e2) {
                try {
                    position = cursor.getPosition();
                } catch (RuntimeException unused) {
                    position = -1;
                }
                Log.w("SearchView", "Search suggestions cursor at row " + position + " returned exception.", e2);
            }
            if (intentJ != null) {
                try {
                    getContext().startActivity(intentJ);
                } catch (RuntimeException e3) {
                    Log.e("SearchView", "Failed launch activity: " + intentJ, e3);
                }
            }
        }
        SearchAutoComplete searchAutoComplete = this.f1100p;
        searchAutoComplete.setImeVisibility(false);
        searchAutoComplete.dismissDropDown();
    }

    public final void n(int i2) {
        Editable text = this.f1100p.getText();
        Cursor cursor = this.f1086O.getCursor();
        if (cursor == null) {
            return;
        }
        if (!cursor.moveToPosition(i2)) {
            setQuery(text);
            return;
        }
        CharSequence charSequenceConvertToString = this.f1086O.convertToString(cursor);
        if (charSequenceConvertToString != null) {
            setQuery(charSequenceConvertToString);
        } else {
            setQuery(text);
        }
    }

    public final void o(CharSequence charSequence) {
        setQuery(charSequence);
    }

    @Override // d.c
    public final void onActionViewCollapsed() {
        SearchAutoComplete searchAutoComplete = this.f1100p;
        searchAutoComplete.setText("");
        searchAutoComplete.setSelection(searchAutoComplete.length());
        this.V = "";
        clearFocus();
        v(true);
        searchAutoComplete.setImeOptions(this.f1094a0);
        this.f1093W = false;
    }

    @Override // d.c
    public final void onActionViewExpanded() {
        if (this.f1093W) {
            return;
        }
        this.f1093W = true;
        SearchAutoComplete searchAutoComplete = this.f1100p;
        int imeOptions = searchAutoComplete.getImeOptions();
        this.f1094a0 = imeOptions;
        searchAutoComplete.setImeOptions(imeOptions | 33554432);
        searchAutoComplete.setText("");
        setIconified(false);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        removeCallbacks(this.f1097d0);
        post(this.f1098e0);
        super.onDetachedFromWindow();
    }

    @Override // androidx.appcompat.widget.LinearLayoutCompat, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        super.onLayout(z2, i2, i3, i4, i5);
        if (z2) {
            int[] iArr = this.f1073B;
            SearchAutoComplete searchAutoComplete = this.f1100p;
            searchAutoComplete.getLocationInWindow(iArr);
            int[] iArr2 = this.f1074C;
            getLocationInWindow(iArr2);
            int i6 = iArr[1] - iArr2[1];
            int i7 = iArr[0] - iArr2[0];
            int width = searchAutoComplete.getWidth() + i7;
            int height = searchAutoComplete.getHeight() + i6;
            Rect rect = this.f1109z;
            rect.set(i7, i6, width, height);
            int i8 = rect.left;
            int i9 = rect.right;
            int i10 = i5 - i3;
            Rect rect2 = this.f1072A;
            rect2.set(i8, 0, i9, i10);
            i0 i0Var = this.f1108y;
            if (i0Var == null) {
                i0 i0Var2 = new i0(rect2, rect, searchAutoComplete);
                this.f1108y = i0Var2;
                setTouchDelegate(i0Var2);
            } else {
                i0Var.f1215b.set(rect2);
                Rect rect3 = i0Var.f1217d;
                rect3.set(rect2);
                int i11 = -i0Var.f1218e;
                rect3.inset(i11, i11);
                i0Var.f1216c.set(rect);
            }
        }
    }

    @Override // androidx.appcompat.widget.LinearLayoutCompat, android.view.View
    public final void onMeasure(int i2, int i3) {
        int i4;
        if (this.f1085N) {
            super.onMeasure(i2, i3);
            return;
        }
        int mode = View.MeasureSpec.getMode(i2);
        int size = View.MeasureSpec.getSize(i2);
        if (mode == Integer.MIN_VALUE) {
            int i5 = this.f1091T;
            size = i5 > 0 ? Math.min(i5, size) : Math.min(getPreferredWidth(), size);
        } else if (mode == 0) {
            size = this.f1091T;
            if (size <= 0) {
                size = getPreferredWidth();
            }
        } else if (mode == 1073741824 && (i4 = this.f1091T) > 0) {
            size = Math.min(i4, size);
        }
        int mode2 = View.MeasureSpec.getMode(i3);
        int size2 = View.MeasureSpec.getSize(i3);
        if (mode2 == Integer.MIN_VALUE) {
            size2 = Math.min(getPreferredHeight(), size2);
        } else if (mode2 == 0) {
            size2 = getPreferredHeight();
        }
        super.onMeasure(View.MeasureSpec.makeMeasureSpec(size, 1073741824), View.MeasureSpec.makeMeasureSpec(size2, 1073741824));
    }

    @Override // android.view.View
    public final void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.f1465a);
        v(savedState.f1110c);
        requestLayout();
    }

    @Override // android.view.View
    public final Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.f1110c = this.f1085N;
        return savedState;
    }

    @Override // android.view.View
    public final void onWindowFocusChanged(boolean z2) {
        super.onWindowFocusChanged(z2);
        post(this.f1097d0);
    }

    public final void p() {
        SearchAutoComplete searchAutoComplete = this.f1100p;
        Editable text = searchAutoComplete.getText();
        if (text == null || TextUtils.getTrimmedLength(text) <= 0) {
            return;
        }
        if (this.f1095b0 != null) {
            getContext().startActivity(j("android.intent.action.SEARCH", null, null, text.toString()));
        }
        searchAutoComplete.setImeVisibility(false);
        searchAutoComplete.dismissDropDown();
    }

    public final void q() {
        boolean z2 = true;
        boolean z3 = !TextUtils.isEmpty(this.f1100p.getText());
        if (!z3 && (!this.f1084M || this.f1093W)) {
            z2 = false;
        }
        int i2 = z2 ? 0 : 8;
        ImageView imageView = this.f1105v;
        imageView.setVisibility(i2);
        Drawable drawable = imageView.getDrawable();
        if (drawable != null) {
            drawable.setState(z3 ? ViewGroup.ENABLED_STATE_SET : ViewGroup.EMPTY_STATE_SET);
        }
    }

    public final void r() {
        int[] iArr = this.f1100p.hasFocus() ? ViewGroup.FOCUSED_STATE_SET : ViewGroup.EMPTY_STATE_SET;
        Drawable background = this.r.getBackground();
        if (background != null) {
            background.setState(iArr);
        }
        Drawable background2 = this.f1102s.getBackground();
        if (background2 != null) {
            background2.setState(iArr);
        }
        invalidate();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean requestFocus(int i2, Rect rect) {
        if (this.f1090S || !isFocusable()) {
            return false;
        }
        if (this.f1085N) {
            return super.requestFocus(i2, rect);
        }
        boolean zRequestFocus = this.f1100p.requestFocus(i2, rect);
        if (zRequestFocus) {
            v(false);
        }
        return zRequestFocus;
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:593)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    public final void s() {
        CharSequence queryHint = getQueryHint();
        CharSequence charSequence = queryHint;
        if (queryHint == null) {
            charSequence = "";
        }
        boolean z2 = this.f1084M;
        SearchAutoComplete searchAutoComplete = this.f1100p;
        CharSequence charSequence2 = charSequence;
        if (z2) {
            Drawable drawable = this.f1076E;
            charSequence2 = charSequence;
            if (drawable != null) {
                int textSize = (int) (((double) searchAutoComplete.getTextSize()) * 1.25d);
                drawable.setBounds(0, 0, textSize, textSize);
                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("   ");
                spannableStringBuilder.setSpan(new ImageSpan(drawable), 1, 2, 33);
                spannableStringBuilder.append(charSequence);
                charSequence2 = spannableStringBuilder;
            }
        }
        searchAutoComplete.setHint(charSequence2);
    }

    public void setAppSearchData(Bundle bundle) {
        this.f1096c0 = bundle;
    }

    public void setIconified(boolean z2) {
        if (z2) {
            l();
            return;
        }
        v(false);
        SearchAutoComplete searchAutoComplete = this.f1100p;
        searchAutoComplete.requestFocus();
        searchAutoComplete.setImeVisibility(true);
        View.OnClickListener onClickListener = this.f1083L;
        if (onClickListener != null) {
            onClickListener.onClick(this);
        }
    }

    public void setIconifiedByDefault(boolean z2) {
        if (this.f1084M == z2) {
            return;
        }
        this.f1084M = z2;
        v(z2);
        s();
    }

    public void setImeOptions(int i2) {
        this.f1100p.setImeOptions(i2);
    }

    public void setInputType(int i2) {
        this.f1100p.setInputType(i2);
    }

    public void setMaxWidth(int i2) {
        this.f1091T = i2;
        requestLayout();
    }

    public void setOnCloseListener(d0 d0Var) {
    }

    public void setOnQueryTextFocusChangeListener(View.OnFocusChangeListener onFocusChangeListener) {
        this.f1082K = onFocusChangeListener;
    }

    public void setOnQueryTextListener(e0 e0Var) {
    }

    public void setOnSearchClickListener(View.OnClickListener onClickListener) {
        this.f1083L = onClickListener;
    }

    public void setOnSuggestionListener(f0 f0Var) {
    }

    public void setQueryHint(CharSequence charSequence) {
        this.f1088Q = charSequence;
        s();
    }

    public void setQueryRefinementEnabled(boolean z2) {
        this.f1089R = z2;
        CursorAdapter cursorAdapter = this.f1086O;
        if (cursorAdapter instanceof k0) {
            ((k0) cursorAdapter).f1254f = z2 ? 2 : 1;
        }
    }

    public void setSearchableInfo(SearchableInfo searchableInfo) {
        this.f1095b0 = searchableInfo;
        Intent intent = null;
        SearchAutoComplete searchAutoComplete = this.f1100p;
        if (searchableInfo != null) {
            searchAutoComplete.setThreshold(searchableInfo.getSuggestThreshold());
            searchAutoComplete.setImeOptions(this.f1095b0.getImeOptions());
            int inputType = this.f1095b0.getInputType();
            if ((inputType & 15) == 1) {
                inputType &= -65537;
                if (this.f1095b0.getSuggestAuthority() != null) {
                    inputType |= 589824;
                }
            }
            searchAutoComplete.setInputType(inputType);
            CursorAdapter cursorAdapter = this.f1086O;
            if (cursorAdapter != null) {
                cursorAdapter.changeCursor(null);
            }
            if (this.f1095b0.getSuggestAuthority() != null) {
                k0 k0Var = new k0(getContext(), this, this.f1095b0, this.f1099f0);
                this.f1086O = k0Var;
                searchAutoComplete.setAdapter(k0Var);
                ((k0) this.f1086O).f1254f = this.f1089R ? 2 : 1;
            }
            s();
        }
        SearchableInfo searchableInfo2 = this.f1095b0;
        boolean z2 = false;
        if (searchableInfo2 != null && searchableInfo2.getVoiceSearchEnabled()) {
            if (this.f1095b0.getVoiceSearchLaunchWebSearch()) {
                intent = this.f1079H;
            } else if (this.f1095b0.getVoiceSearchLaunchRecognizer()) {
                intent = this.f1080I;
            }
            if (intent != null) {
                z2 = getContext().getPackageManager().resolveActivity(intent, 65536) != null;
            }
        }
        this.f1092U = z2;
        if (z2) {
            searchAutoComplete.setPrivateImeOptions("nm");
        }
        v(this.f1085N);
    }

    public void setSubmitButtonEnabled(boolean z2) {
        this.f1087P = z2;
        v(this.f1085N);
    }

    public void setSuggestionsAdapter(CursorAdapter cursorAdapter) {
        this.f1086O = cursorAdapter;
        this.f1100p.setAdapter(cursorAdapter);
    }

    public final void t() {
        this.f1102s.setVisibility(((this.f1087P || this.f1092U) && !this.f1085N && (this.f1104u.getVisibility() == 0 || this.f1106w.getVisibility() == 0)) ? 0 : 8);
    }

    public final void u(boolean z2) {
        boolean z3 = this.f1087P;
        this.f1104u.setVisibility((!z3 || !(z3 || this.f1092U) || this.f1085N || !hasFocus() || (!z2 && this.f1092U)) ? 8 : 0);
    }

    public final void v(boolean z2) {
        this.f1085N = z2;
        int i2 = 8;
        int i3 = z2 ? 0 : 8;
        boolean zIsEmpty = TextUtils.isEmpty(this.f1100p.getText());
        this.f1103t.setVisibility(i3);
        u(!zIsEmpty);
        this.f1101q.setVisibility(z2 ? 8 : 0);
        ImageView imageView = this.f1075D;
        imageView.setVisibility((imageView.getDrawable() == null || this.f1084M) ? 8 : 0);
        q();
        if (this.f1092U && !this.f1085N && zIsEmpty) {
            this.f1104u.setVisibility(8);
            i2 = 0;
        }
        this.f1106w.setVisibility(i2);
        t();
    }
}
