package com.google.android.material.internal;

import android.content.Context;
import android.util.AttributeSet;
import androidx.appcompat.view.menu.C;
import androidx.appcompat.view.menu.o;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/* JADX INFO: loaded from: classes.dex */
public class NavigationMenuView extends RecyclerView implements C {
    public NavigationMenuView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setLayoutManager(new LinearLayoutManager());
    }

    @Override // androidx.appcompat.view.menu.C
    public final void b(o oVar) {
    }

    public int getWindowAnimations() {
        return 0;
    }
}
