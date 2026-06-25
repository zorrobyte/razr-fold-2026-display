package com.android.systemui.statusbar.notification.row;

import android.R;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.util.Dumpable;
import android.util.Log;
import android.util.Size;
import com.android.internal.widget.NotificationDrawableConsumer;
import com.android.internal.widget.NotificationIconManager;
import com.android.systemui.graphics.ImageLoader;
import com.android.systemui.util.Assert;
import java.io.PrintWriter;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;

/* JADX INFO: compiled from: BigPictureIconManager.kt */
/* JADX INFO: loaded from: classes.dex */
public final class BigPictureIconManager implements NotificationIconManager, Dumpable {
    private final CoroutineDispatcher bgDispatcher;
    private final Context context;
    private DrawableState displayedState;
    private NotificationDrawableConsumer drawableConsumer;
    private final ImageLoader imageLoader;
    private Job lastLoadingJob;
    private final CoroutineDispatcher mainDispatcher;
    private int maxHeight;
    private int maxWidth;
    private final CoroutineScope scope;
    private final BigPictureStatsManager statsManager;
    private boolean viewShown;

    /* JADX INFO: compiled from: BigPictureIconManager.kt */
    abstract class DrawableState {
        private final Icon icon;

        /* JADX INFO: compiled from: BigPictureIconManager.kt */
        public final class Empty extends DrawableState {
            public static final Empty INSTANCE = new Empty();

            /* JADX WARN: Multi-variable type inference failed */
            private Empty() {
                super(null, 0 == true ? 1 : 0);
            }

            public boolean equals(Object obj) {
                return this == obj || (obj instanceof Empty);
            }

            public int hashCode() {
                return -356686193;
            }

            public String toString() {
                return "Empty";
            }
        }

        /* JADX INFO: compiled from: BigPictureIconManager.kt */
        public final class FullImage extends DrawableState {
            private final Size drawableSize;
            private final Icon icon;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public FullImage(Icon icon, Size size) {
                super(icon, null);
                icon.getClass();
                size.getClass();
                this.icon = icon;
                this.drawableSize = size;
            }

            public boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof FullImage)) {
                    return false;
                }
                FullImage fullImage = (FullImage) obj;
                return Intrinsics.areEqual(this.icon, fullImage.icon) && Intrinsics.areEqual(this.drawableSize, fullImage.drawableSize);
            }

            public final Size getDrawableSize() {
                return this.drawableSize;
            }

            @Override // com.android.systemui.statusbar.notification.row.BigPictureIconManager.DrawableState
            public Icon getIcon() {
                return this.icon;
            }

            public int hashCode() {
                return (this.icon.hashCode() * 31) + this.drawableSize.hashCode();
            }

            public String toString() {
                return "FullImage(icon=" + this.icon + ", drawableSize=" + this.drawableSize + ")";
            }
        }

        /* JADX INFO: compiled from: BigPictureIconManager.kt */
        public final class Initial extends DrawableState {
            public static final Initial INSTANCE = new Initial();

            /* JADX WARN: Multi-variable type inference failed */
            private Initial() {
                super(null, 0 == true ? 1 : 0);
            }

            public boolean equals(Object obj) {
                return this == obj || (obj instanceof Initial);
            }

            public int hashCode() {
                return 99151878;
            }

            public String toString() {
                return "Initial";
            }
        }

        /* JADX INFO: compiled from: BigPictureIconManager.kt */
        public final class PlaceHolder extends DrawableState {
            private final Size drawableSize;
            private final Icon icon;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public PlaceHolder(Icon icon, Size size) {
                super(icon, null);
                icon.getClass();
                size.getClass();
                this.icon = icon;
                this.drawableSize = size;
            }

            public boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof PlaceHolder)) {
                    return false;
                }
                PlaceHolder placeHolder = (PlaceHolder) obj;
                return Intrinsics.areEqual(this.icon, placeHolder.icon) && Intrinsics.areEqual(this.drawableSize, placeHolder.drawableSize);
            }

            public final Size getDrawableSize() {
                return this.drawableSize;
            }

            @Override // com.android.systemui.statusbar.notification.row.BigPictureIconManager.DrawableState
            public Icon getIcon() {
                return this.icon;
            }

            public int hashCode() {
                return (this.icon.hashCode() * 31) + this.drawableSize.hashCode();
            }

            public String toString() {
                return "PlaceHolder(icon=" + this.icon + ", drawableSize=" + this.drawableSize + ")";
            }
        }

        private DrawableState(Icon icon) {
            this.icon = icon;
        }

        public /* synthetic */ DrawableState(Icon icon, DefaultConstructorMarker defaultConstructorMarker) {
            this(icon);
        }

        public Icon getIcon() {
            return this.icon;
        }
    }

    /* JADX INFO: renamed from: com.android.systemui.statusbar.notification.row.BigPictureIconManager$loadImage$1, reason: invalid class name */
    /* JADX INFO: compiled from: BigPictureIconManager.kt */
    final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return BigPictureIconManager.this.loadImage(null, this);
        }
    }

    /* JADX INFO: renamed from: com.android.systemui.statusbar.notification.row.BigPictureIconManager$loadImage$2, reason: invalid class name */
    /* JADX INFO: compiled from: BigPictureIconManager.kt */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ Pair $drawableAndState;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass2(Pair pair, Continuation continuation) {
            super(2, continuation);
            this.$drawableAndState = pair;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return BigPictureIconManager.this.new AnonymousClass2(this.$drawableAndState, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            BigPictureIconManager.this.applyDrawableAndState(this.$drawableAndState);
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: com.android.systemui.statusbar.notification.row.BigPictureIconManager$startFreeImageJob$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: BigPictureIconManager.kt */
    final class C01191 extends SuspendLambda implements Function2 {
        final /* synthetic */ Size $drawableSize;
        final /* synthetic */ Icon $icon;
        int label;

        /* JADX INFO: renamed from: com.android.systemui.statusbar.notification.row.BigPictureIconManager$startFreeImageJob$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: BigPictureIconManager.kt */
        final class C00171 extends SuspendLambda implements Function2 {
            final /* synthetic */ Pair $drawableAndState;
            int label;
            final /* synthetic */ BigPictureIconManager this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C00171(BigPictureIconManager bigPictureIconManager, Pair pair, Continuation continuation) {
                super(2, continuation);
                this.this$0 = bigPictureIconManager;
                this.$drawableAndState = pair;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C00171(this.this$0, this.$drawableAndState, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
                return ((C00171) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                this.this$0.applyDrawableAndState(this.$drawableAndState);
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C01191(Icon icon, Size size, Continuation continuation) {
            super(2, continuation);
            this.$icon = icon;
            this.$drawableSize = size;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return BigPictureIconManager.this.new C01191(this.$icon, this.$drawableSize, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01191) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:14:0x004a, code lost:
        
            if (kotlinx.coroutines.BuildersKt.withContext(r1, r3, r6) == r0) goto L15;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r7) {
            /*
                r6 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r6.label
                r2 = 2
                r3 = 1
                if (r1 == 0) goto L1e
                if (r1 == r3) goto L1a
                if (r1 != r2) goto L12
                kotlin.ResultKt.throwOnFailure(r7)
                goto L4d
            L12:
                java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                r6.<init>(r7)
                throw r6
            L1a:
                kotlin.ResultKt.throwOnFailure(r7)
                goto L2c
            L1e:
                kotlin.ResultKt.throwOnFailure(r7)
                r6.label = r3
                r3 = 3000(0xbb8, double:1.482E-320)
                java.lang.Object r7 = kotlinx.coroutines.DelayKt.delay(r3, r6)
                if (r7 != r0) goto L2c
                goto L4c
            L2c:
                com.android.systemui.statusbar.notification.row.BigPictureIconManager r7 = com.android.systemui.statusbar.notification.row.BigPictureIconManager.this
                android.graphics.drawable.Icon r1 = r6.$icon
                android.util.Size r3 = r6.$drawableSize
                kotlin.Pair r7 = com.android.systemui.statusbar.notification.row.BigPictureIconManager.access$createPlaceHolder(r7, r1, r3)
                com.android.systemui.statusbar.notification.row.BigPictureIconManager r1 = com.android.systemui.statusbar.notification.row.BigPictureIconManager.this
                kotlinx.coroutines.CoroutineDispatcher r1 = com.android.systemui.statusbar.notification.row.BigPictureIconManager.access$getMainDispatcher$p(r1)
                com.android.systemui.statusbar.notification.row.BigPictureIconManager$startFreeImageJob$1$1 r3 = new com.android.systemui.statusbar.notification.row.BigPictureIconManager$startFreeImageJob$1$1
                com.android.systemui.statusbar.notification.row.BigPictureIconManager r4 = com.android.systemui.statusbar.notification.row.BigPictureIconManager.this
                r5 = 0
                r3.<init>(r4, r7, r5)
                r6.label = r2
                java.lang.Object r7 = kotlinx.coroutines.BuildersKt.withContext(r1, r3, r6)
                if (r7 != r0) goto L4d
            L4c:
                return r0
            L4d:
                com.android.systemui.statusbar.notification.row.BigPictureIconManager r6 = com.android.systemui.statusbar.notification.row.BigPictureIconManager.this
                java.lang.String r7 = "placeholder loaded"
                com.android.systemui.statusbar.notification.row.BigPictureIconManager.access$log(r6, r7)
                kotlin.Unit r6 = kotlin.Unit.INSTANCE
                return r6
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.row.BigPictureIconManager.C01191.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    /* JADX INFO: renamed from: com.android.systemui.statusbar.notification.row.BigPictureIconManager$startLoadingJob$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: BigPictureIconManager.kt */
    final class C01201 extends SuspendLambda implements Function2 {
        final /* synthetic */ Icon $icon;
        Object L$0;
        Object L$1;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C01201(Icon icon, Continuation continuation) {
            super(2, continuation);
            this.$icon = icon;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return BigPictureIconManager.this.new C01201(this.$icon, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01201) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:25:0x0076, code lost:
        
            if (r5.trackEvent(r1, r8) == r0) goto L38;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r9) throws java.lang.Throwable {
            /*
                r8 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r8.label
                r2 = 0
                r3 = 3
                r4 = 2
                r5 = 1
                if (r1 == 0) goto L39
                if (r1 == r5) goto L2b
                if (r1 == r4) goto L23
                if (r1 == r3) goto L1a
                java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
                java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
                r8.<init>(r9)
                throw r8
            L1a:
                java.lang.Object r8 = r8.L$0
                java.lang.Throwable r8 = (java.lang.Throwable) r8
                kotlin.ResultKt.throwOnFailure(r9)
                goto L9d
            L23:
                java.lang.Object r8 = r8.L$0
                kotlin.Unit r8 = (kotlin.Unit) r8
                kotlin.ResultKt.throwOnFailure(r9)
                goto L79
            L2b:
                java.lang.Object r1 = r8.L$1
                java.lang.String r1 = (java.lang.String) r1
                java.lang.Object r5 = r8.L$0
                com.android.systemui.statusbar.notification.row.BigPictureStatsManager r5 = (com.android.systemui.statusbar.notification.row.BigPictureStatsManager) r5
                kotlin.ResultKt.throwOnFailure(r9)     // Catch: java.lang.Throwable -> L37
                goto L60
            L37:
                r9 = move-exception
                goto L80
            L39:
                kotlin.ResultKt.throwOnFailure(r9)
                com.android.systemui.statusbar.notification.row.BigPictureIconManager r9 = com.android.systemui.statusbar.notification.row.BigPictureIconManager.this
                com.android.systemui.statusbar.notification.row.BigPictureStatsManager r9 = com.android.systemui.statusbar.notification.row.BigPictureIconManager.access$getStatsManager$p(r9)
                com.android.systemui.statusbar.notification.row.BigPictureIconManager r1 = com.android.systemui.statusbar.notification.row.BigPictureIconManager.this
                android.graphics.drawable.Icon r6 = r8.$icon
                java.util.UUID r7 = java.util.UUID.randomUUID()
                java.lang.String r7 = r7.toString()
                r9.onBegin(r7)
                r8.L$0 = r9     // Catch: java.lang.Throwable -> L7c
                r8.L$1 = r7     // Catch: java.lang.Throwable -> L7c
                r8.label = r5     // Catch: java.lang.Throwable -> L7c
                java.lang.Object r1 = com.android.systemui.statusbar.notification.row.BigPictureIconManager.access$loadImage(r1, r6, r8)     // Catch: java.lang.Throwable -> L7c
                if (r1 != r0) goto L5e
                goto L9b
            L5e:
                r5 = r9
                r1 = r7
            L60:
                kotlin.Unit r9 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L37
                java.lang.Integer r1 = r5.onEnd(r1)
                if (r1 == 0) goto L79
                int r1 = r1.intValue()
                r8.L$0 = r9
                r8.L$1 = r2
                r8.label = r4
                java.lang.Object r8 = r5.trackEvent(r1, r8)
                if (r8 != r0) goto L79
                goto L9b
            L79:
                kotlin.Unit r8 = kotlin.Unit.INSTANCE
                return r8
            L7c:
                r1 = move-exception
                r5 = r9
                r9 = r1
                r1 = r7
            L80:
                r5.onCancel(r1)     // Catch: java.lang.Throwable -> L84
                throw r9     // Catch: java.lang.Throwable -> L84
            L84:
                r9 = move-exception
                java.lang.Integer r1 = r5.onEnd(r1)
                if (r1 == 0) goto L9e
                int r1 = r1.intValue()
                r8.L$0 = r9
                r8.L$1 = r2
                r8.label = r3
                java.lang.Object r8 = r5.trackEvent(r1, r8)
                if (r8 != r0) goto L9c
            L9b:
                return r0
            L9c:
                r8 = r9
            L9d:
                r9 = r8
            L9e:
                throw r9
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.row.BigPictureIconManager.C01201.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    public BigPictureIconManager(Context context, ImageLoader imageLoader, BigPictureStatsManager bigPictureStatsManager, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, CoroutineDispatcher coroutineDispatcher2) {
        context.getClass();
        imageLoader.getClass();
        bigPictureStatsManager.getClass();
        coroutineScope.getClass();
        coroutineDispatcher.getClass();
        coroutineDispatcher2.getClass();
        this.context = context;
        this.imageLoader = imageLoader;
        this.statsManager = bigPictureStatsManager;
        this.scope = coroutineScope;
        this.mainDispatcher = coroutineDispatcher;
        this.bgDispatcher = coroutineDispatcher2;
        this.displayedState = DrawableState.Initial.INSTANCE;
        this.maxWidth = getMaxWidth();
        this.maxHeight = getMaxHeight();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void applyDrawableAndState(Pair pair) {
        DrawableState drawableState;
        Assert.isMainThread();
        NotificationDrawableConsumer notificationDrawableConsumer = this.drawableConsumer;
        if (notificationDrawableConsumer != null) {
            notificationDrawableConsumer.setImageDrawable(pair != null ? (Drawable) pair.getFirst() : null);
        }
        if (pair == null || (drawableState = (DrawableState) pair.getSecond()) == null) {
            drawableState = DrawableState.Empty.INSTANCE;
        }
        this.displayedState = drawableState;
    }

    private final void checkPlaceHolderSizeForDrawable(DrawableState drawableState, Drawable drawable) {
        if (drawableState instanceof DrawableState.PlaceHolder) {
            Size drawableSize = ((DrawableState.PlaceHolder) drawableState).getDrawableSize();
            int iComponent1 = BigPictureIconManagerKt.component1(drawableSize);
            int iComponent2 = BigPictureIconManagerKt.component2(drawableSize);
            Size intrinsicSize = BigPictureIconManagerKt.getIntrinsicSize(drawable);
            int iComponent12 = BigPictureIconManagerKt.component1(intrinsicSize);
            int iComponent22 = BigPictureIconManagerKt.component2(intrinsicSize);
            if (iComponent1 == iComponent12 && iComponent2 == iComponent22) {
                return;
            }
            Log.e("BigPicImageLoader", "Mismatch in dimensions, when replacing PlaceHolder " + iComponent1 + " X " + iComponent2 + " with Drawable " + iComponent12 + " X " + iComponent22 + ".");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Pair createPlaceHolder(Icon icon, Size size) {
        PlaceHolderDrawable placeHolderDrawable = new PlaceHolderDrawable(size.getWidth(), size.getHeight());
        return new Pair(placeHolderDrawable, new DrawableState.PlaceHolder(icon, BigPictureIconManagerKt.getIntrinsicSize(placeHolderDrawable)));
    }

    private final String getDebugString() {
        return "{ state:" + this.displayedState + ", hasConsumer:" + (this.drawableConsumer != null) + ", viewShown:" + this.viewShown + "}";
    }

    private final int getMaxHeight() {
        return this.context.getResources().getDimensionPixelSize(isLowRam() ? R.dimen.notification_header_height : R.dimen.notification_header_expand_icon_size);
    }

    private final int getMaxWidth() {
        return this.context.getResources().getDimensionPixelSize(isLowRam() ? R.dimen.notification_header_icon_size_ambient : R.dimen.notification_header_icon_size);
    }

    private final boolean isLowRam() {
        return ActivityManager.isLowRamDeviceStatic();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0067, code lost:
    
        if (kotlinx.coroutines.BuildersKt.withContext(r7, r2, r0) == r1) goto L21;
     */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object loadImage(android.graphics.drawable.Icon r7, kotlin.coroutines.Continuation r8) {
        /*
            r6 = this;
            boolean r0 = r8 instanceof com.android.systemui.statusbar.notification.row.BigPictureIconManager.AnonymousClass1
            if (r0 == 0) goto L13
            r0 = r8
            com.android.systemui.statusbar.notification.row.BigPictureIconManager$loadImage$1 r0 = (com.android.systemui.statusbar.notification.row.BigPictureIconManager.AnonymousClass1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.statusbar.notification.row.BigPictureIconManager$loadImage$1 r0 = new com.android.systemui.statusbar.notification.row.BigPictureIconManager$loadImage$1
            r0.<init>(r8)
        L18:
            java.lang.Object r8 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 0
            r4 = 2
            r5 = 1
            if (r2 == 0) goto L41
            if (r2 == r5) goto L39
            if (r2 != r4) goto L31
            java.lang.Object r6 = r0.L$0
            com.android.systemui.statusbar.notification.row.BigPictureIconManager r6 = (com.android.systemui.statusbar.notification.row.BigPictureIconManager) r6
            kotlin.ResultKt.throwOnFailure(r8)
            goto L6a
        L31:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L39:
            java.lang.Object r6 = r0.L$0
            com.android.systemui.statusbar.notification.row.BigPictureIconManager r6 = (com.android.systemui.statusbar.notification.row.BigPictureIconManager) r6
            kotlin.ResultKt.throwOnFailure(r8)
            goto L56
        L41:
            kotlin.ResultKt.throwOnFailure(r8)
            kotlinx.coroutines.CoroutineDispatcher r8 = r6.bgDispatcher
            com.android.systemui.statusbar.notification.row.BigPictureIconManager$loadImage$drawableAndState$1 r2 = new com.android.systemui.statusbar.notification.row.BigPictureIconManager$loadImage$drawableAndState$1
            r2.<init>(r6, r7, r3)
            r0.L$0 = r6
            r0.label = r5
            java.lang.Object r8 = kotlinx.coroutines.BuildersKt.withContext(r8, r2, r0)
            if (r8 != r1) goto L56
            goto L69
        L56:
            kotlin.Pair r8 = (kotlin.Pair) r8
            kotlinx.coroutines.CoroutineDispatcher r7 = r6.mainDispatcher
            com.android.systemui.statusbar.notification.row.BigPictureIconManager$loadImage$2 r2 = new com.android.systemui.statusbar.notification.row.BigPictureIconManager$loadImage$2
            r2.<init>(r8, r3)
            r0.L$0 = r6
            r0.label = r4
            java.lang.Object r7 = kotlinx.coroutines.BuildersKt.withContext(r7, r2, r0)
            if (r7 != r1) goto L6a
        L69:
            return r1
        L6a:
            java.lang.String r7 = "full image loaded"
            r6.log(r7)
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.row.BigPictureIconManager.loadImage(android.graphics.drawable.Icon, kotlin.coroutines.Continuation):java.lang.Object");
    }

    private final Pair loadImageOrPlaceHolderSync(Icon icon) {
        if (icon == null) {
            return null;
        }
        if (this.viewShown || skipLazyLoading(icon)) {
            return loadImageSync(icon);
        }
        Pair pairLoadPlaceHolderSync = loadPlaceHolderSync(icon);
        return pairLoadPlaceHolderSync == null ? loadImageSync(icon) : pairLoadPlaceHolderSync;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Pair loadImageSync(Icon icon) {
        Drawable drawableLoadDrawableSync$default = ImageLoader.loadDrawableSync$default(this.imageLoader, icon, this.context, this.maxWidth, this.maxHeight, 0, 16, null);
        if (drawableLoadDrawableSync$default == null) {
            return null;
        }
        checkPlaceHolderSizeForDrawable(this.displayedState, drawableLoadDrawableSync$default);
        return new Pair(drawableLoadDrawableSync$default, new DrawableState.FullImage(icon, BigPictureIconManagerKt.getIntrinsicSize(drawableLoadDrawableSync$default)));
    }

    private final Pair loadPlaceHolderSync(Icon icon) {
        Size sizeResizeToMax;
        Size sizeLoadSizeSync = this.imageLoader.loadSizeSync(icon, this.context);
        if (sizeLoadSizeSync == null || (sizeResizeToMax = BigPictureIconManagerKt.resizeToMax(sizeLoadSizeSync, this.maxWidth, this.maxHeight)) == null) {
            return null;
        }
        return createPlaceHolder(icon, sizeResizeToMax);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void log(String str) {
    }

    private final boolean skipLazyLoading(Icon icon) {
        Integer numValueOf = icon != null ? Integer.valueOf(icon.getType()) : null;
        return (numValueOf != null && numValueOf.intValue() == 1) || (numValueOf != null && numValueOf.intValue() == 5) || ((numValueOf != null && numValueOf.intValue() == 3) || numValueOf == null);
    }

    private final Job startFreeImageJob(Icon icon, Size size) {
        return BuildersKt__Builders_commonKt.launch$default(this.scope, null, null, new C01191(icon, size, null), 3, null);
    }

    private final Job startLoadingJob(Icon icon) {
        return BuildersKt__Builders_commonKt.launch$default(this.scope, null, null, new C01201(icon, null), 3, null);
    }

    public final void cancelJobs() {
        Job job = this.lastLoadingJob;
        if (job != null) {
            Job.DefaultImpls.cancel$default(job, null, 1, null);
        }
    }

    @Override // android.util.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.getClass();
        printWriter.println("BigPictureIconManager " + getDebugString());
    }

    public final void onViewShown(boolean z) {
        log("onViewShown:" + z);
        if (this.viewShown != z) {
            this.viewShown = z;
            DrawableState drawableState = this.displayedState;
            Job job = this.lastLoadingJob;
            Job jobStartFreeImageJob = null;
            if (job != null) {
                Job.DefaultImpls.cancel$default(job, null, 1, null);
            }
            if (!skipLazyLoading(drawableState.getIcon())) {
                if ((drawableState instanceof DrawableState.PlaceHolder) && z) {
                    jobStartFreeImageJob = startLoadingJob(((DrawableState.PlaceHolder) drawableState).getIcon());
                } else if ((drawableState instanceof DrawableState.FullImage) && !z) {
                    DrawableState.FullImage fullImage = (DrawableState.FullImage) drawableState;
                    jobStartFreeImageJob = startFreeImageJob(fullImage.getIcon(), fullImage.getDrawableSize());
                }
            }
            this.lastLoadingJob = jobStartFreeImageJob;
        }
    }

    public Runnable updateIcon(NotificationDrawableConsumer notificationDrawableConsumer, Icon icon) {
        notificationDrawableConsumer.getClass();
        this.drawableConsumer = notificationDrawableConsumer;
        Job job = this.lastLoadingJob;
        if (job != null) {
            Job.DefaultImpls.cancel$default(job, null, 1, null);
        }
        final Pair pairLoadImageOrPlaceHolderSync = loadImageOrPlaceHolderSync(icon);
        log("icon updated");
        return new Runnable() { // from class: com.android.systemui.statusbar.notification.row.BigPictureIconManager.updateIcon.1
            @Override // java.lang.Runnable
            public final void run() {
                BigPictureIconManager.this.applyDrawableAndState(pairLoadImageOrPlaceHolderSync);
            }
        };
    }

    public final void updateMaxImageSizes() {
        log("updateMaxImageSizes");
        this.maxWidth = getMaxWidth();
        this.maxHeight = getMaxHeight();
    }
}
