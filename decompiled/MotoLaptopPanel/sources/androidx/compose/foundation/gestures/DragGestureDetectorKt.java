package androidx.compose.foundation.gestures;

import androidx.compose.ui.input.pointer.AwaitPointerEventScope;
import androidx.compose.ui.input.pointer.PointerEvent;
import androidx.compose.ui.input.pointer.PointerId;
import androidx.compose.ui.input.pointer.PointerInputChange;
import androidx.compose.ui.input.pointer.PointerInputScope;
import androidx.compose.ui.input.pointer.PointerType;
import androidx.compose.ui.platform.ViewConfiguration;
import androidx.compose.ui.unit.Dp;
import java.util.List;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Ref$LongRef;

/* JADX INFO: compiled from: DragGestureDetector.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class DragGestureDetectorKt {
    private static final float defaultTouchSlop;
    private static final float mouseSlop;
    private static final float mouseToTouchSlopRatio;

    /* JADX INFO: renamed from: androidx.compose.foundation.gestures.DragGestureDetectorKt$detectDragGestures$9, reason: invalid class name */
    /* JADX INFO: compiled from: DragGestureDetector.kt */
    final class AnonymousClass9 extends RestrictedSuspendLambda implements Function2 {
        final /* synthetic */ Function2 $onDrag;
        final /* synthetic */ Function0 $onDragCancel;
        final /* synthetic */ Function1 $onDragEnd;
        final /* synthetic */ Function3 $onDragStart;
        final /* synthetic */ Orientation $orientationLock;
        final /* synthetic */ Ref$LongRef $overSlop;
        final /* synthetic */ Function0 $shouldAwaitTouchSlop;
        float F$0;
        private /* synthetic */ Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        Object L$5;
        Object L$6;
        boolean Z$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass9(Function0 function0, Ref$LongRef ref$LongRef, Orientation orientation, Function3 function3, Function2 function2, Function0 function02, Function1 function1, Continuation continuation) {
            super(2, continuation);
            this.$shouldAwaitTouchSlop = function0;
            this.$overSlop = ref$LongRef;
            this.$orientationLock = orientation;
            this.$onDragStart = function3;
            this.$onDrag = function2;
            this.$onDragCancel = function02;
            this.$onDragEnd = function1;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass9 anonymousClass9 = new AnonymousClass9(this.$shouldAwaitTouchSlop, this.$overSlop, this.$orientationLock, this.$onDragStart, this.$onDrag, this.$onDragCancel, this.$onDragEnd, continuation);
            anonymousClass9.L$0 = obj;
            return anonymousClass9;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(AwaitPointerEventScope awaitPointerEventScope, Continuation continuation) {
            return ((AnonymousClass9) create(awaitPointerEventScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:164:0x04a0, code lost:
        
            if (androidx.compose.foundation.gestures.DragGestureDetectorKt.m123isPointerUpDmW0f2w(r2.getCurrentEvent(), r0) != false) goto L165;
         */
        /* JADX WARN: Code restructure failed: missing block: B:168:0x04c8, code lost:
        
            if (r9 != r6) goto L170;
         */
        /* JADX WARN: Code restructure failed: missing block: B:205:0x054f, code lost:
        
            if (r8 == false) goto L206;
         */
        /* JADX WARN: Code restructure failed: missing block: B:90:0x02f2, code lost:
        
            if (r4 == r6) goto L169;
         */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Path cross not found for [B:182:0x04ff, B:193:0x0528], limit reached: 221 */
        /* JADX WARN: Removed duplicated region for block: B:118:0x03ae A[PHI: r0 r1 r2 r4 r7 r9 r10 r11 r16 r18
          0x03ae: PHI (r0v21 float) = (r0v19 float), (r0v22 float) binds: [B:8:0x0065, B:116:0x03aa] A[DONT_GENERATE, DONT_INLINE]
          0x03ae: PHI (r1v36 androidx.compose.ui.input.pointer.PointerInputChange) = 
          (r1v34 androidx.compose.ui.input.pointer.PointerInputChange)
          (r1v38 androidx.compose.ui.input.pointer.PointerInputChange)
         binds: [B:8:0x0065, B:116:0x03aa] A[DONT_GENERATE, DONT_INLINE]
          0x03ae: PHI (r2v26 androidx.compose.ui.input.pointer.AwaitPointerEventScope) = 
          (r2v24 androidx.compose.ui.input.pointer.AwaitPointerEventScope)
          (r2v29 androidx.compose.ui.input.pointer.AwaitPointerEventScope)
         binds: [B:8:0x0065, B:116:0x03aa] A[DONT_GENERATE, DONT_INLINE]
          0x03ae: PHI (r4v19 androidx.compose.ui.input.pointer.AwaitPointerEventScope) = 
          (r4v17 androidx.compose.ui.input.pointer.AwaitPointerEventScope)
          (r4v20 androidx.compose.ui.input.pointer.AwaitPointerEventScope)
         binds: [B:8:0x0065, B:116:0x03aa] A[DONT_GENERATE, DONT_INLINE]
          0x03ae: PHI (r7v30 androidx.compose.foundation.gestures.TouchSlopDetector) = 
          (r7v28 androidx.compose.foundation.gestures.TouchSlopDetector)
          (r7v32 androidx.compose.foundation.gestures.TouchSlopDetector)
         binds: [B:8:0x0065, B:116:0x03aa] A[DONT_GENERATE, DONT_INLINE]
          0x03ae: PHI (r9v13 java.lang.Object) = (r9v12 java.lang.Object), (r9v21 java.lang.Object) binds: [B:8:0x0065, B:116:0x03aa] A[DONT_GENERATE, DONT_INLINE]
          0x03ae: PHI (r10v7 kotlin.jvm.internal.Ref$LongRef) = (r10v5 kotlin.jvm.internal.Ref$LongRef), (r10v8 kotlin.jvm.internal.Ref$LongRef) binds: [B:8:0x0065, B:116:0x03aa] A[DONT_GENERATE, DONT_INLINE]
          0x03ae: PHI (r11v20 kotlin.jvm.internal.Ref$LongRef) = (r11v18 kotlin.jvm.internal.Ref$LongRef), (r11v21 kotlin.jvm.internal.Ref$LongRef) binds: [B:8:0x0065, B:116:0x03aa] A[DONT_GENERATE, DONT_INLINE]
          0x03ae: PHI (r16v16 long) = (r16v14 long), (r16v17 long) binds: [B:8:0x0065, B:116:0x03aa] A[DONT_GENERATE, DONT_INLINE]
          0x03ae: PHI (r18v12 long) = (r18v10 long), (r18v13 long) binds: [B:8:0x0065, B:116:0x03aa] A[DONT_GENERATE, DONT_INLINE]] */
        /* JADX WARN: Removed duplicated region for block: B:120:0x03bb  */
        /* JADX WARN: Removed duplicated region for block: B:133:0x03f1  */
        /* JADX WARN: Removed duplicated region for block: B:144:0x041b  */
        /* JADX WARN: Removed duplicated region for block: B:157:0x0463  */
        /* JADX WARN: Removed duplicated region for block: B:158:0x0467  */
        /* JADX WARN: Removed duplicated region for block: B:163:0x0476  */
        /* JADX WARN: Removed duplicated region for block: B:19:0x0169  */
        /* JADX WARN: Removed duplicated region for block: B:233:0x03db A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:237:0x0212 A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:23:0x0181  */
        /* JADX WARN: Removed duplicated region for block: B:28:0x01b2  */
        /* JADX WARN: Removed duplicated region for block: B:29:0x01b5  */
        /* JADX WARN: Removed duplicated region for block: B:33:0x01e6 A[PHI: r0 r1 r2 r4 r5 r7 r8 r9 r11 r16 r18
          0x01e6: PHI (r0v10 float) = (r0v9 float), (r0v11 float) binds: [B:11:0x00e7, B:31:0x01e2] A[DONT_GENERATE, DONT_INLINE]
          0x01e6: PHI (r1v18 androidx.compose.ui.input.pointer.PointerInputChange) = 
          (r1v17 androidx.compose.ui.input.pointer.PointerInputChange)
          (r1v20 androidx.compose.ui.input.pointer.PointerInputChange)
         binds: [B:11:0x00e7, B:31:0x01e2] A[DONT_GENERATE, DONT_INLINE]
          0x01e6: PHI (r2v8 androidx.compose.ui.input.pointer.AwaitPointerEventScope) = 
          (r2v7 androidx.compose.ui.input.pointer.AwaitPointerEventScope)
          (r2v11 androidx.compose.ui.input.pointer.AwaitPointerEventScope)
         binds: [B:11:0x00e7, B:31:0x01e2] A[DONT_GENERATE, DONT_INLINE]
          0x01e6: PHI (r4v5 androidx.compose.ui.input.pointer.AwaitPointerEventScope) = 
          (r4v4 androidx.compose.ui.input.pointer.AwaitPointerEventScope)
          (r4v6 androidx.compose.ui.input.pointer.AwaitPointerEventScope)
         binds: [B:11:0x00e7, B:31:0x01e2] A[DONT_GENERATE, DONT_INLINE]
          0x01e6: PHI (r5v5 androidx.compose.foundation.gestures.TouchSlopDetector) = 
          (r5v4 androidx.compose.foundation.gestures.TouchSlopDetector)
          (r5v6 androidx.compose.foundation.gestures.TouchSlopDetector)
         binds: [B:11:0x00e7, B:31:0x01e2] A[DONT_GENERATE, DONT_INLINE]
          0x01e6: PHI (r7v7 java.lang.Object) = (r7v6 java.lang.Object), (r7v17 java.lang.Object) binds: [B:11:0x00e7, B:31:0x01e2] A[DONT_GENERATE, DONT_INLINE]
          0x01e6: PHI (r8v4 kotlin.jvm.internal.Ref$LongRef) = (r8v3 kotlin.jvm.internal.Ref$LongRef), (r8v6 kotlin.jvm.internal.Ref$LongRef) binds: [B:11:0x00e7, B:31:0x01e2] A[DONT_GENERATE, DONT_INLINE]
          0x01e6: PHI (r9v2 kotlin.jvm.internal.Ref$LongRef) = (r9v1 kotlin.jvm.internal.Ref$LongRef), (r9v3 kotlin.jvm.internal.Ref$LongRef) binds: [B:11:0x00e7, B:31:0x01e2] A[DONT_GENERATE, DONT_INLINE]
          0x01e6: PHI (r11v1 int) = (r11v0 int), (r11v14 int) binds: [B:11:0x00e7, B:31:0x01e2] A[DONT_GENERATE, DONT_INLINE]
          0x01e6: PHI (r16v6 long) = (r16v5 long), (r16v7 long) binds: [B:11:0x00e7, B:31:0x01e2] A[DONT_GENERATE, DONT_INLINE]
          0x01e6: PHI (r18v2 long) = (r18v1 long), (r18v3 long) binds: [B:11:0x00e7, B:31:0x01e2] A[DONT_GENERATE, DONT_INLINE]] */
        /* JADX WARN: Removed duplicated region for block: B:35:0x01f3  */
        /* JADX WARN: Removed duplicated region for block: B:49:0x022b  */
        /* JADX WARN: Removed duplicated region for block: B:60:0x0254  */
        /* JADX WARN: Removed duplicated region for block: B:73:0x029a  */
        /* JADX WARN: Removed duplicated region for block: B:75:0x02a0  */
        /* JADX WARN: Removed duplicated region for block: B:79:0x02ac  */
        /* JADX WARN: Removed duplicated region for block: B:80:0x02b3  */
        /* JADX WARN: Removed duplicated region for block: B:83:0x02bc A[ADDED_TO_REGION] */
        /* JADX WARN: Removed duplicated region for block: B:86:0x02cd  */
        /* JADX WARN: Type inference failed for: r0v23 */
        /* JADX WARN: Type inference failed for: r0v30, types: [java.lang.Object] */
        /* JADX WARN: Type inference failed for: r0v32, types: [kotlin.jvm.functions.Function3] */
        /* JADX WARN: Type inference failed for: r0v33, types: [kotlin.jvm.functions.Function2] */
        /* JADX WARN: Type inference failed for: r0v37 */
        /* JADX WARN: Type inference failed for: r0v55 */
        /* JADX WARN: Type inference failed for: r0v56 */
        /* JADX WARN: Type inference failed for: r13v0 */
        /* JADX WARN: Type inference failed for: r13v10, types: [kotlin.jvm.internal.DefaultConstructorMarker] */
        /* JADX WARN: Type inference failed for: r13v11 */
        /* JADX WARN: Type inference failed for: r13v12 */
        /* JADX WARN: Type inference failed for: r13v2 */
        /* JADX WARN: Type inference failed for: r13v3 */
        /* JADX WARN: Type inference failed for: r13v9, types: [androidx.compose.ui.input.pointer.PointerEventPass, java.lang.Object] */
        /* JADX WARN: Type inference failed for: r7v14 */
        /* JADX WARN: Type inference failed for: r7v19 */
        /* JADX WARN: Type inference failed for: r7v2 */
        /* JADX WARN: Type inference failed for: r7v20, types: [androidx.compose.ui.input.pointer.PointerInputChange] */
        /* JADX WARN: Type inference failed for: r7v21 */
        /* JADX WARN: Type inference failed for: r7v3 */
        /* JADX WARN: Type inference failed for: r7v31 */
        /* JADX WARN: Type inference failed for: r7v35 */
        /* JADX WARN: Type inference failed for: r7v40, types: [androidx.compose.ui.input.pointer.PointerInputChange, java.lang.Object] */
        /* JADX WARN: Type inference failed for: r7v43 */
        /* JADX WARN: Type inference failed for: r7v58 */
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
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:112:0x0377 -> B:113:0x0379). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:127:0x03e2 -> B:113:0x0379). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:148:0x0434 -> B:81:0x02b8). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:150:0x043b -> B:115:0x0392). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:154:0x045b -> B:155:0x045d). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:160:0x046d -> B:81:0x02b8). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:168:0x04c8 -> B:170:0x04cb). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:28:0x01b2 -> B:74:0x029e). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:42:0x021a -> B:43:0x021b). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:64:0x0269 -> B:74:0x029e). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:70:0x0291 -> B:71:0x0294). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:99:0x0324 -> B:89:0x02da). Please report as a decompilation issue!!! */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r24) {
            /*
                Method dump skipped, instruction units count: 1444
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.gestures.DragGestureDetectorKt.AnonymousClass9.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    static {
        float fM1877constructorimpl = Dp.m1877constructorimpl((float) 0.125d);
        mouseSlop = fM1877constructorimpl;
        float fM1877constructorimpl2 = Dp.m1877constructorimpl(18);
        defaultTouchSlop = fM1877constructorimpl2;
        mouseToTouchSlopRatio = fM1877constructorimpl / fM1877constructorimpl2;
    }

    public static final Object detectDragGestures(PointerInputScope pointerInputScope, Function3 function3, Function1 function1, Function0 function0, Function0 function02, Orientation orientation, Function2 function2, Continuation continuation) {
        Object objAwaitEachGesture = ForEachGestureKt.awaitEachGesture(pointerInputScope, new AnonymousClass9(function02, new Ref$LongRef(), orientation, function3, function2, function0, function1, null), continuation);
        return objAwaitEachGesture == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objAwaitEachGesture : Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: isPointerUp-DmW0f2w, reason: not valid java name */
    public static final boolean m123isPointerUpDmW0f2w(PointerEvent pointerEvent, long j) {
        Object obj;
        List changes = pointerEvent.getChanges();
        int size = changes.size();
        boolean z = false;
        int i = 0;
        while (true) {
            if (i >= size) {
                obj = null;
                break;
            }
            obj = changes.get(i);
            if (PointerId.m1225equalsimpl0(((PointerInputChange) obj).m1231getIdJ3iCeTQ(), j)) {
                break;
            }
            i++;
        }
        PointerInputChange pointerInputChange = (PointerInputChange) obj;
        if (pointerInputChange != null && pointerInputChange.getPressed()) {
            z = true;
        }
        return true ^ z;
    }

    /* JADX INFO: renamed from: pointerSlop-E8SPZFQ, reason: not valid java name */
    public static final float m124pointerSlopE8SPZFQ(ViewConfiguration viewConfiguration, int i) {
        return PointerType.m1255equalsimpl0(i, PointerType.Companion.m1259getMouseT8wyACA()) ? viewConfiguration.getTouchSlop() * mouseToTouchSlopRatio : viewConfiguration.getTouchSlop();
    }
}
