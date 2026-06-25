package com.bumptech.glide;

import com.bumptech.glide.request.transition.NoTransition;
import com.bumptech.glide.request.transition.TransitionFactory;
import com.bumptech.glide.util.Preconditions;
import com.bumptech.glide.util.Util;

/* JADX INFO: loaded from: classes.dex */
public abstract class TransitionOptions implements Cloneable {
    private TransitionFactory transitionFactory = NoTransition.getFactory();

    private TransitionOptions self() {
        return this;
    }

    /* JADX INFO: renamed from: clone, reason: merged with bridge method [inline-methods] */
    public final TransitionOptions m2138clone() {
        try {
            return (TransitionOptions) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean equals(Object obj) {
        if (obj instanceof TransitionOptions) {
            return Util.bothNullOrEqual(this.transitionFactory, ((TransitionOptions) obj).transitionFactory);
        }
        return false;
    }

    final TransitionFactory getTransitionFactory() {
        return this.transitionFactory;
    }

    public int hashCode() {
        TransitionFactory transitionFactory = this.transitionFactory;
        if (transitionFactory != null) {
            return transitionFactory.hashCode();
        }
        return 0;
    }

    public final TransitionOptions transition(TransitionFactory transitionFactory) {
        this.transitionFactory = (TransitionFactory) Preconditions.checkNotNull(transitionFactory);
        return self();
    }
}
