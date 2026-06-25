package androidx.compose.runtime;

import java.util.ArrayList;

/* JADX INFO: compiled from: Applier.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractApplier implements Applier {
    private Object current;
    private final Object root;
    private final ArrayList stack = Stack.m607constructorimpl$default(null, 1, null);

    public AbstractApplier(Object obj) {
        this.root = obj;
        this.current = obj;
    }

    @Override // androidx.compose.runtime.Applier
    public final void clear() {
        Stack.m605clearimpl(this.stack);
        setCurrent(this.root);
        onClear();
    }

    @Override // androidx.compose.runtime.Applier
    public void down(Object obj) {
        Stack.m614pushimpl(this.stack, getCurrent());
        setCurrent(obj);
    }

    @Override // androidx.compose.runtime.Applier
    public Object getCurrent() {
        return this.current;
    }

    public final Object getRoot() {
        return this.root;
    }

    protected abstract void onClear();

    protected void setCurrent(Object obj) {
        this.current = obj;
    }

    @Override // androidx.compose.runtime.Applier
    public void up() {
        setCurrent(Stack.m613popimpl(this.stack));
    }
}
