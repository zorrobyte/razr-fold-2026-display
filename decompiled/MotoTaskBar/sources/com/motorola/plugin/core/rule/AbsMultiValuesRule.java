package com.motorola.plugin.core.rule;

import kotlin.sequences.Sequence;

/* JADX INFO: compiled from: AbsMultiValuesRule.kt */
/* JADX INFO: loaded from: classes2.dex */
public abstract class AbsMultiValuesRule implements IRule {
    private final Sequence multiValues;

    public AbsMultiValuesRule(Sequence sequence) {
        sequence.getClass();
        this.multiValues = sequence;
    }

    protected final Sequence getMultiValues() {
        return this.multiValues;
    }
}
