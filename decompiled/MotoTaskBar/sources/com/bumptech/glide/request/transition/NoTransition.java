package com.bumptech.glide.request.transition;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.request.transition.Transition;

/* JADX INFO: loaded from: classes.dex */
public class NoTransition implements Transition {
    static final NoTransition NO_ANIMATION = new NoTransition();
    private static final TransitionFactory NO_ANIMATION_FACTORY = new NoAnimationFactory();

    public class NoAnimationFactory implements TransitionFactory {
        @Override // com.bumptech.glide.request.transition.TransitionFactory
        public Transition build(DataSource dataSource, boolean z) {
            return NoTransition.NO_ANIMATION;
        }
    }

    public static Transition get() {
        return NO_ANIMATION;
    }

    public static TransitionFactory getFactory() {
        return NO_ANIMATION_FACTORY;
    }

    @Override // com.bumptech.glide.request.transition.Transition
    public boolean transition(Object obj, Transition.ViewAdapter viewAdapter) {
        return false;
    }
}
