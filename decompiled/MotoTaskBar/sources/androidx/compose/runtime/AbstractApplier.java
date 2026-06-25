package androidx.compose.runtime;

import java.util.ArrayList;

/* JADX INFO: compiled from: Applier.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractApplier implements Applier {
    private Object current;
    private final Object root;
    private final ArrayList stack = Stack.m50constructorimpl$default(null, 1, null);

    public AbstractApplier(Object obj) {
        this.root = obj;
        this.current = obj;
    }

    @Override // androidx.compose.runtime.Applier
    public final void clear() {
        Stack.m48clearimpl(this.stack);
        setCurrent(this.root);
        onClear();
    }

    @Override // androidx.compose.runtime.Applier
    public void down(Object obj) {
        Stack.m57pushimpl(this.stack, getCurrent());
        setCurrent(obj);
    }

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
        setCurrent(Stack.m56popimpl(this.stack));
    }
}
