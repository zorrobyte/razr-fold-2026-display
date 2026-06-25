package androidx.graphics.shapes;

import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: Features.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class Feature {
    public static final Factory Factory = new Factory(null);
    private final List cubics;

    /* JADX INFO: compiled from: Features.kt */
    public final class Corner extends Feature {
        private final boolean convex;
        private final boolean isConcaveCorner;
        private final boolean isConvexCorner;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Corner(List list, boolean z) {
            super(list);
            list.getClass();
            this.convex = z;
            this.isConvexCorner = z;
            this.isConcaveCorner = !z;
        }

        public final boolean getConvex() {
            return this.convex;
        }

        public String toString() {
            return "Corner: cubics=" + CollectionsKt.joinToString$default(getCubics(), ", ", null, null, 0, null, new Function1() { // from class: androidx.graphics.shapes.Feature$Corner$toString$1
                @Override // kotlin.jvm.functions.Function1
                public final CharSequence invoke(Cubic cubic) {
                    cubic.getClass();
                    StringBuilder sb = new StringBuilder();
                    sb.append('[');
                    sb.append(cubic);
                    sb.append(']');
                    return sb.toString();
                }
            }, 30, null) + " convex=" + this.convex;
        }

        @Override // androidx.graphics.shapes.Feature
        public Feature transformed(PointTransformer pointTransformer) {
            pointTransformer.getClass();
            List listCreateListBuilder = CollectionsKt.createListBuilder();
            int size = getCubics().size();
            for (int i = 0; i < size; i++) {
                listCreateListBuilder.add(((Cubic) getCubics().get(i)).transformed(pointTransformer));
            }
            return new Corner(CollectionsKt.build(listCreateListBuilder), this.convex);
        }
    }

    /* JADX INFO: compiled from: Features.kt */
    public final class Edge extends Feature {
        private final boolean isEdge;
        private final boolean isIgnorableFeature;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Edge(List list) {
            super(list);
            list.getClass();
            this.isIgnorableFeature = true;
            this.isEdge = true;
        }

        public String toString() {
            return "Edge";
        }

        @Override // androidx.graphics.shapes.Feature
        public Edge transformed(PointTransformer pointTransformer) {
            pointTransformer.getClass();
            List listCreateListBuilder = CollectionsKt.createListBuilder();
            int size = getCubics().size();
            for (int i = 0; i < size; i++) {
                listCreateListBuilder.add(((Cubic) getCubics().get(i)).transformed(pointTransformer));
            }
            return new Edge(CollectionsKt.build(listCreateListBuilder));
        }
    }

    /* JADX INFO: compiled from: Features.kt */
    public final class Factory {
        private Factory() {
        }

        public /* synthetic */ Factory(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public Feature(List list) {
        list.getClass();
        this.cubics = list;
    }

    public final List getCubics() {
        return this.cubics;
    }

    public abstract Feature transformed(PointTransformer pointTransformer);
}
