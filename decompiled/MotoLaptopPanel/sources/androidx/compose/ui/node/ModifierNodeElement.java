package androidx.compose.ui.node;

import androidx.compose.ui.Modifier;

/* JADX INFO: compiled from: ModifierNodeElement.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class ModifierNodeElement implements Modifier.Element {
    public abstract Modifier.Node create();

    public abstract void update(Modifier.Node node);
}
