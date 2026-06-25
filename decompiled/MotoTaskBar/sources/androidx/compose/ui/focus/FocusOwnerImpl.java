package androidx.compose.ui.focus;

import android.view.KeyEvent;
import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0;
import androidx.collection.MutableLongSet;
import androidx.collection.MutableObjectList;
import androidx.compose.ui.ComposeUiFlags;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.focus.FocusRequester;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.input.key.KeyEventType;
import androidx.compose.ui.input.key.KeyEvent_androidKt;
import androidx.compose.ui.input.rotary.RotaryInputModifierNode;
import androidx.compose.ui.input.rotary.RotaryScrollEvent;
import androidx.compose.ui.internal.InlineClassHelperKt;
import androidx.compose.ui.node.DelegatableNode;
import androidx.compose.ui.node.DelegatableNodeKt;
import androidx.compose.ui.node.LayoutNode;
import androidx.compose.ui.node.ModifierNodeElement;
import androidx.compose.ui.node.NodeChain;
import androidx.compose.ui.node.NodeKind;
import androidx.compose.ui.unit.LayoutDirection;
import java.util.ArrayList;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference0Impl;
import kotlin.jvm.internal.PropertyReference0Impl;

/* JADX INFO: compiled from: FocusOwnerImpl.kt */
/* JADX INFO: loaded from: classes.dex */
public final class FocusOwnerImpl implements FocusOwner {
    private FocusTargetNode activeFocusTargetNode;
    private final FocusInvalidationManager focusInvalidationManager;
    private boolean isFocusCaptured;
    private MutableLongSet keysCurrentlyDown;
    private final Function0 onClearFocusForOwner;
    private final Function0 onFocusRectInterop;
    private final Function0 onLayoutDirection;
    private final Function1 onMoveFocusInterop;
    private final Function2 onRequestFocusForOwner;
    private FocusTargetNode rootFocusNode = new FocusTargetNode(Focusability.Companion.m162getNeverLCbbffg(), null, null, 6, null);
    private final FocusTransactionManager focusTransactionManager = new FocusTransactionManager();
    private final Modifier modifier = new ModifierNodeElement() { // from class: androidx.compose.ui.focus.FocusOwnerImpl$modifier$1
        @Override // androidx.compose.ui.node.ModifierNodeElement
        public FocusTargetNode create() {
            return this.this$0.getRootFocusNode$ui_release();
        }

        public boolean equals(Object obj) {
            return obj == this;
        }

        public int hashCode() {
            return this.this$0.getRootFocusNode$ui_release().hashCode();
        }

        @Override // androidx.compose.ui.node.ModifierNodeElement
        public void update(FocusTargetNode focusTargetNode) {
        }
    };
    private final MutableObjectList listeners = new MutableObjectList(1);

    /* JADX INFO: compiled from: FocusOwnerImpl.kt */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[CustomDestinationResult.values().length];
            try {
                iArr[CustomDestinationResult.Redirected.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[CustomDestinationResult.Cancelled.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[CustomDestinationResult.RedirectCancelled.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[CustomDestinationResult.None.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public FocusOwnerImpl(Function1 function1, Function2 function2, Function1 function12, Function0 function0, Function0 function02, Function0 function03) {
        this.onRequestFocusForOwner = function2;
        this.onMoveFocusInterop = function12;
        this.onClearFocusForOwner = function0;
        this.onFocusRectInterop = function02;
        this.onLayoutDirection = function03;
        this.focusInvalidationManager = new FocusInvalidationManager(function1, new FocusOwnerImpl$focusInvalidationManager$1(this), new PropertyReference0Impl(this) { // from class: androidx.compose.ui.focus.FocusOwnerImpl$focusInvalidationManager$2
            @Override // kotlin.reflect.KProperty0
            public Object get() {
                return ((FocusOwnerImpl) this.receiver).getRootState();
            }
        }, new MutablePropertyReference0Impl(this) { // from class: androidx.compose.ui.focus.FocusOwnerImpl$focusInvalidationManager$3
            @Override // kotlin.reflect.KProperty0
            public Object get() {
                return ((FocusOwnerImpl) this.receiver).getActiveFocusTargetNode();
            }
        });
    }

    private final boolean clearFocus(boolean z, boolean z2) {
        NodeChain nodes$ui_release;
        if (getActiveFocusTargetNode() == null) {
            return true;
        }
        if (isFocusCaptured() && !z) {
            return false;
        }
        FocusTargetNode activeFocusTargetNode = getActiveFocusTargetNode();
        setActiveFocusTargetNode(null);
        if (z2 && activeFocusTargetNode != null) {
            activeFocusTargetNode.dispatchFocusCallbacks$ui_release(isFocusCaptured() ? FocusStateImpl.Captured : FocusStateImpl.Active, FocusStateImpl.Inactive);
            int iM658constructorimpl = NodeKind.m658constructorimpl(1024);
            if (!activeFocusTargetNode.getNode().isAttached()) {
                InlineClassHelperKt.throwIllegalStateException("visitAncestors called on an unattached node");
            }
            Modifier.Node parent$ui_release = activeFocusTargetNode.getNode().getParent$ui_release();
            LayoutNode layoutNodeRequireLayoutNode = DelegatableNodeKt.requireLayoutNode(activeFocusTargetNode);
            while (layoutNodeRequireLayoutNode != null) {
                if ((layoutNodeRequireLayoutNode.getNodes$ui_release().getHead$ui_release().getAggregateChildKindSet$ui_release() & iM658constructorimpl) != 0) {
                    while (parent$ui_release != null) {
                        if ((parent$ui_release.getKindSet$ui_release() & iM658constructorimpl) != 0) {
                            for (Modifier.Node nodePop = parent$ui_release; nodePop != null; nodePop = DelegatableNodeKt.pop(null)) {
                                if (nodePop instanceof FocusTargetNode) {
                                    ((FocusTargetNode) nodePop).dispatchFocusCallbacks$ui_release(FocusStateImpl.ActiveParent, FocusStateImpl.Inactive);
                                } else {
                                    nodePop.getKindSet$ui_release();
                                }
                            }
                        }
                        parent$ui_release = parent$ui_release.getParent$ui_release();
                    }
                }
                layoutNodeRequireLayoutNode = layoutNodeRequireLayoutNode.getParent$ui_release();
                parent$ui_release = (layoutNodeRequireLayoutNode == null || (nodes$ui_release = layoutNodeRequireLayoutNode.getNodes$ui_release()) == null) ? null : nodes$ui_release.getTail$ui_release();
            }
        }
        return true;
    }

    private final FocusTargetNode findFocusTargetNode() {
        return FocusTraversalKt.findActiveFocusNode(this.rootFocusNode);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void invalidateOwnerFocusState() {
        if ((ComposeUiFlags.isTrackFocusEnabled && getActiveFocusTargetNode() == null) || this.rootFocusNode.getFocusState() == FocusStateImpl.Inactive) {
            this.onClearFocusForOwner.mo2224invoke();
        }
    }

    private final Modifier.Node lastLocalKeyInputNode(DelegatableNode delegatableNode) {
        int iM658constructorimpl = NodeKind.m658constructorimpl(1024) | NodeKind.m658constructorimpl(8192);
        if (!delegatableNode.getNode().isAttached()) {
            InlineClassHelperKt.throwIllegalStateException("visitLocalDescendants called on an unattached node");
        }
        Modifier.Node node = delegatableNode.getNode();
        Modifier.Node node2 = null;
        if ((node.getAggregateChildKindSet$ui_release() & iM658constructorimpl) != 0) {
            for (Modifier.Node child$ui_release = node.getChild$ui_release(); child$ui_release != null; child$ui_release = child$ui_release.getChild$ui_release()) {
                if ((child$ui_release.getKindSet$ui_release() & iM658constructorimpl) != 0) {
                    if ((NodeKind.m658constructorimpl(1024) & child$ui_release.getKindSet$ui_release()) != 0) {
                        return node2;
                    }
                    node2 = child$ui_release;
                }
            }
        }
        return node2;
    }

    /* JADX INFO: renamed from: validateKeyEvent-ZmokQxo, reason: not valid java name */
    private final boolean m141validateKeyEventZmokQxo(KeyEvent keyEvent) {
        long jM462getKeyZmokQxo = KeyEvent_androidKt.m462getKeyZmokQxo(keyEvent);
        int iM463getTypeZmokQxo = KeyEvent_androidKt.m463getTypeZmokQxo(keyEvent);
        KeyEventType.Companion companion = KeyEventType.Companion;
        if (KeyEventType.m458equalsimpl0(iM463getTypeZmokQxo, companion.m459getKeyDownCS__XNY())) {
            MutableLongSet mutableLongSet = this.keysCurrentlyDown;
            if (mutableLongSet == null) {
                mutableLongSet = new MutableLongSet(3);
                this.keysCurrentlyDown = mutableLongSet;
            }
            mutableLongSet.plusAssign(jM462getKeyZmokQxo);
        } else if (KeyEventType.m458equalsimpl0(iM463getTypeZmokQxo, companion.m460getKeyUpCS__XNY())) {
            MutableLongSet mutableLongSet2 = this.keysCurrentlyDown;
            if (mutableLongSet2 == null || !mutableLongSet2.contains(jM462getKeyZmokQxo)) {
                return false;
            }
            MutableLongSet mutableLongSet3 = this.keysCurrentlyDown;
            if (mutableLongSet3 != null) {
                mutableLongSet3.remove(jM462getKeyZmokQxo);
            }
        }
        return true;
    }

    @Override // androidx.compose.ui.focus.FocusManager
    public void clearFocus(boolean z) {
        mo136clearFocusI7lrPNg(z, true, true, FocusDirection.Companion.m126getExitdhqQ8s());
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x0065 A[Catch: all -> 0x0041, TRY_LEAVE, TryCatch #0 {all -> 0x0041, blocks: (B:16:0x0037, B:18:0x003d, B:21:0x0043, B:23:0x0048, B:25:0x0051, B:29:0x0065), top: B:37:0x0037 }] */
    @Override // androidx.compose.ui.focus.FocusOwner
    /* JADX INFO: renamed from: clearFocus-I7lrPNg */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean mo136clearFocusI7lrPNg(boolean r8, boolean r9, boolean r10, int r11) {
        /*
            r7 = this;
            boolean r0 = androidx.compose.ui.ComposeUiFlags.isTrackFocusEnabled
            r1 = 0
            r2 = 3
            r3 = 2
            r4 = 1
            if (r0 == 0) goto L31
            if (r8 != 0) goto L2c
            androidx.compose.ui.focus.FocusTargetNode r0 = r7.rootFocusNode
            androidx.compose.ui.focus.CustomDestinationResult r11 = androidx.compose.ui.focus.FocusTransactionsKt.m151performCustomClearFocusMxy_nc0(r0, r11)
            int[] r0 = androidx.compose.ui.focus.FocusOwnerImpl.WhenMappings.$EnumSwitchMapping$0
            int r11 = r11.ordinal()
            r11 = r0[r11]
            if (r11 == r4) goto L6e
            if (r11 == r3) goto L6e
            if (r11 == r2) goto L6e
            r0 = 4
            if (r11 != r0) goto L26
            boolean r1 = r7.clearFocus(r8, r9)
            goto L6e
        L26:
            kotlin.NoWhenBranchMatchedException r7 = new kotlin.NoWhenBranchMatchedException
            r7.<init>()
            throw r7
        L2c:
            boolean r1 = r7.clearFocus(r8, r9)
            goto L6e
        L31:
            androidx.compose.ui.focus.FocusTransactionManager r0 = r7.getFocusTransactionManager()
            androidx.compose.ui.focus.FocusOwnerImpl$clearFocus$clearedFocusSuccessfully$1 r5 = new kotlin.jvm.functions.Function0() { // from class: androidx.compose.ui.focus.FocusOwnerImpl$clearFocus$clearedFocusSuccessfully$1
                static {
                    /*
                        androidx.compose.ui.focus.FocusOwnerImpl$clearFocus$clearedFocusSuccessfully$1 r0 = new androidx.compose.ui.focus.FocusOwnerImpl$clearFocus$clearedFocusSuccessfully$1
                        r0.<init>()
                        
                        // error: 0x0005: SPUT (r0 I:androidx.compose.ui.focus.FocusOwnerImpl$clearFocus$clearedFocusSuccessfully$1) androidx.compose.ui.focus.FocusOwnerImpl$clearFocus$clearedFocusSuccessfully$1.INSTANCE androidx.compose.ui.focus.FocusOwnerImpl$clearFocus$clearedFocusSuccessfully$1
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.focus.FocusOwnerImpl$clearFocus$clearedFocusSuccessfully$1.<clinit>():void");
                }

                {
                    /*
                        r1 = this;
                        r0 = 0
                        r1.<init>(r0)
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.focus.FocusOwnerImpl$clearFocus$clearedFocusSuccessfully$1.<init>():void");
                }

                @Override // kotlin.jvm.functions.Function0
                /* JADX INFO: renamed from: invoke */
                public /* bridge */ /* synthetic */ java.lang.Object mo2224invoke() {
                    /*
                        r0 = this;
                        r0.m142invoke()
                        kotlin.Unit r0 = kotlin.Unit.INSTANCE
                        return r0
                    */
                    throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.focus.FocusOwnerImpl$clearFocus$clearedFocusSuccessfully$1.mo2224invoke():java.lang.Object");
                }

                /* JADX INFO: renamed from: invoke, reason: collision with other method in class */
                public final void m142invoke() {
                    /*
                        r0 = this;
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.focus.FocusOwnerImpl$clearFocus$clearedFocusSuccessfully$1.m142invoke():void");
                }
            }
            boolean r6 = r0.getOngoingTransaction()     // Catch: java.lang.Throwable -> L41
            if (r6 == 0) goto L43
            androidx.compose.ui.focus.FocusTransactionManager.access$cancelTransaction(r0)     // Catch: java.lang.Throwable -> L41
            goto L43
        L41:
            r7 = move-exception
            goto L78
        L43:
            androidx.compose.ui.focus.FocusTransactionManager.access$beginTransaction(r0)     // Catch: java.lang.Throwable -> L41
            if (r5 == 0) goto L4f
            androidx.compose.runtime.collection.MutableVector r6 = androidx.compose.ui.focus.FocusTransactionManager.access$getCancellationListener$p(r0)     // Catch: java.lang.Throwable -> L41
            r6.add(r5)     // Catch: java.lang.Throwable -> L41
        L4f:
            if (r8 != 0) goto L65
            androidx.compose.ui.focus.FocusTargetNode r5 = r7.rootFocusNode     // Catch: java.lang.Throwable -> L41
            androidx.compose.ui.focus.CustomDestinationResult r11 = androidx.compose.ui.focus.FocusTransactionsKt.m151performCustomClearFocusMxy_nc0(r5, r11)     // Catch: java.lang.Throwable -> L41
            int[] r5 = androidx.compose.ui.focus.FocusOwnerImpl.WhenMappings.$EnumSwitchMapping$0     // Catch: java.lang.Throwable -> L41
            int r11 = r11.ordinal()     // Catch: java.lang.Throwable -> L41
            r11 = r5[r11]     // Catch: java.lang.Throwable -> L41
            if (r11 == r4) goto L6b
            if (r11 == r3) goto L6b
            if (r11 == r2) goto L6b
        L65:
            androidx.compose.ui.focus.FocusTargetNode r11 = r7.rootFocusNode     // Catch: java.lang.Throwable -> L41
            boolean r1 = androidx.compose.ui.focus.FocusTransactionsKt.clearFocus(r11, r8, r9)     // Catch: java.lang.Throwable -> L41
        L6b:
            androidx.compose.ui.focus.FocusTransactionManager.access$commitTransaction(r0)
        L6e:
            if (r1 == 0) goto L77
            if (r10 == 0) goto L77
            kotlin.jvm.functions.Function0 r7 = r7.onClearFocusForOwner
            r7.mo2224invoke()
        L77:
            return r1
        L78:
            androidx.compose.ui.focus.FocusTransactionManager.access$commitTransaction(r0)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.focus.FocusOwnerImpl.mo136clearFocusI7lrPNg(boolean, boolean, boolean, int):boolean");
    }

    @Override // androidx.compose.ui.focus.FocusOwner
    /* JADX INFO: renamed from: dispatchInterceptedSoftKeyboardEvent-ZmokQxo */
    public boolean mo137dispatchInterceptedSoftKeyboardEventZmokQxo(KeyEvent keyEvent) {
        NodeChain nodes$ui_release;
        if (this.focusInvalidationManager.hasPendingInvalidation()) {
            System.out.println((Object) "FocusRelatedWarning: Dispatching intercepted soft keyboard event while the focus system is invalidated.");
            return false;
        }
        FocusTargetNode focusTargetNodeFindActiveFocusNode = FocusTraversalKt.findActiveFocusNode(this.rootFocusNode);
        if (focusTargetNodeFindActiveFocusNode != null) {
            int iM658constructorimpl = NodeKind.m658constructorimpl(131072);
            if (!focusTargetNodeFindActiveFocusNode.getNode().isAttached()) {
                InlineClassHelperKt.throwIllegalStateException("visitAncestors called on an unattached node");
            }
            Modifier.Node node = focusTargetNodeFindActiveFocusNode.getNode();
            LayoutNode layoutNodeRequireLayoutNode = DelegatableNodeKt.requireLayoutNode(focusTargetNodeFindActiveFocusNode);
            while (layoutNodeRequireLayoutNode != null) {
                if ((layoutNodeRequireLayoutNode.getNodes$ui_release().getHead$ui_release().getAggregateChildKindSet$ui_release() & iM658constructorimpl) != 0) {
                    while (node != null) {
                        if ((node.getKindSet$ui_release() & iM658constructorimpl) != 0) {
                            for (Modifier.Node nodePop = node; nodePop != null; nodePop = DelegatableNodeKt.pop(null)) {
                                nodePop.getKindSet$ui_release();
                            }
                        }
                        node = node.getParent$ui_release();
                    }
                }
                layoutNodeRequireLayoutNode = layoutNodeRequireLayoutNode.getParent$ui_release();
                node = (layoutNodeRequireLayoutNode == null || (nodes$ui_release = layoutNodeRequireLayoutNode.getNodes$ui_release()) == null) ? null : nodes$ui_release.getTail$ui_release();
            }
            ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(null);
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:45:0x009d A[Catch: all -> 0x0233, TryCatch #0 {all -> 0x0233, blocks: (B:3:0x0006, B:5:0x000f, B:8:0x001a, B:12:0x0024, B:15:0x002f, B:74:0x010b, B:76:0x0119, B:77:0x011c, B:79:0x012b, B:82:0x013c, B:86:0x0146, B:89:0x014c, B:90:0x0151, B:92:0x0158, B:91:0x0155, B:93:0x015d, B:94:0x0162, B:96:0x0168, B:98:0x016e, B:101:0x0177, B:103:0x017f, B:110:0x0196, B:111:0x0198, B:112:0x01a8, B:114:0x01ac, B:116:0x01b0, B:121:0x01c1, B:120:0x01bc, B:122:0x01cc, B:126:0x01dc, B:127:0x01ec, B:129:0x01f0, B:131:0x01f4, B:136:0x0205, B:135:0x0200, B:138:0x0212, B:140:0x0219, B:145:0x022b, B:146:0x022d, B:18:0x0037, B:20:0x0045, B:21:0x0048, B:23:0x0052, B:26:0x0063, B:30:0x006d, B:42:0x0093, B:44:0x0097, B:33:0x0072, B:34:0x007a, B:35:0x007f, B:37:0x0085, B:39:0x008b, B:45:0x009d, B:47:0x00ad, B:48:0x00b0, B:50:0x00be, B:53:0x00cf, B:57:0x00d9, B:69:0x00ff, B:71:0x0103, B:60:0x00de, B:61:0x00e6, B:62:0x00eb, B:64:0x00f1, B:66:0x00f7), top: B:152:0x0006 }] */
    @Override // androidx.compose.ui.focus.FocusOwner
    /* JADX INFO: renamed from: dispatchKeyEvent-YhN2O0w */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean mo138dispatchKeyEventYhN2O0w(android.view.KeyEvent r11, kotlin.jvm.functions.Function0 r12) {
        /*
            Method dump skipped, instruction units count: 568
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.focus.FocusOwnerImpl.mo138dispatchKeyEventYhN2O0w(android.view.KeyEvent, kotlin.jvm.functions.Function0):boolean");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v28 */
    /* JADX WARN: Type inference failed for: r0v29 */
    /* JADX WARN: Type inference failed for: r0v7, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r10v10 */
    /* JADX WARN: Type inference failed for: r10v11 */
    /* JADX WARN: Type inference failed for: r10v5, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r6v11 */
    /* JADX WARN: Type inference failed for: r6v12, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r6v14 */
    /* JADX WARN: Type inference failed for: r6v2 */
    /* JADX WARN: Type inference failed for: r6v3 */
    @Override // androidx.compose.ui.focus.FocusOwner
    public boolean dispatchRotaryEvent(RotaryScrollEvent rotaryScrollEvent, Function0 function0) {
        RotaryInputModifierNode rotaryInputModifierNode;
        int size;
        NodeChain nodes$ui_release;
        ?? Pop;
        NodeChain nodes$ui_release2;
        if (this.focusInvalidationManager.hasPendingInvalidation()) {
            System.out.println((Object) "FocusRelatedWarning: Dispatching rotary event while the focus system is invalidated.");
            return false;
        }
        FocusTargetNode focusTargetNodeFindFocusTargetNode = findFocusTargetNode();
        if (focusTargetNodeFindFocusTargetNode != null) {
            int iM658constructorimpl = NodeKind.m658constructorimpl(16384);
            if (!focusTargetNodeFindFocusTargetNode.getNode().isAttached()) {
                InlineClassHelperKt.throwIllegalStateException("visitAncestors called on an unattached node");
            }
            Modifier.Node node = focusTargetNodeFindFocusTargetNode.getNode();
            LayoutNode layoutNodeRequireLayoutNode = DelegatableNodeKt.requireLayoutNode(focusTargetNodeFindFocusTargetNode);
            loop0: while (true) {
                if (layoutNodeRequireLayoutNode == null) {
                    Pop = 0;
                    break;
                }
                if ((layoutNodeRequireLayoutNode.getNodes$ui_release().getHead$ui_release().getAggregateChildKindSet$ui_release() & iM658constructorimpl) != 0) {
                    while (node != null) {
                        if ((node.getKindSet$ui_release() & iM658constructorimpl) != 0) {
                            Pop = node;
                            while (Pop != 0) {
                                if (Pop instanceof RotaryInputModifierNode) {
                                    break loop0;
                                }
                                Pop.getKindSet$ui_release();
                                Pop = DelegatableNodeKt.pop(null);
                            }
                        }
                        node = node.getParent$ui_release();
                    }
                }
                layoutNodeRequireLayoutNode = layoutNodeRequireLayoutNode.getParent$ui_release();
                node = (layoutNodeRequireLayoutNode == null || (nodes$ui_release2 = layoutNodeRequireLayoutNode.getNodes$ui_release()) == null) ? null : nodes$ui_release2.getTail$ui_release();
            }
            rotaryInputModifierNode = (RotaryInputModifierNode) Pop;
        } else {
            rotaryInputModifierNode = null;
        }
        if (rotaryInputModifierNode != null) {
            int iM658constructorimpl2 = NodeKind.m658constructorimpl(16384);
            if (!rotaryInputModifierNode.getNode().isAttached()) {
                InlineClassHelperKt.throwIllegalStateException("visitAncestors called on an unattached node");
            }
            Modifier.Node parent$ui_release = rotaryInputModifierNode.getNode().getParent$ui_release();
            LayoutNode layoutNodeRequireLayoutNode2 = DelegatableNodeKt.requireLayoutNode(rotaryInputModifierNode);
            ArrayList arrayList = null;
            while (layoutNodeRequireLayoutNode2 != null) {
                if ((layoutNodeRequireLayoutNode2.getNodes$ui_release().getHead$ui_release().getAggregateChildKindSet$ui_release() & iM658constructorimpl2) != 0) {
                    while (parent$ui_release != null) {
                        if ((parent$ui_release.getKindSet$ui_release() & iM658constructorimpl2) != 0) {
                            for (Modifier.Node nodePop = parent$ui_release; nodePop != null; nodePop = DelegatableNodeKt.pop(null)) {
                                if (nodePop instanceof RotaryInputModifierNode) {
                                    if (arrayList == null) {
                                        arrayList = new ArrayList();
                                    }
                                    arrayList.add(nodePop);
                                } else {
                                    nodePop.getKindSet$ui_release();
                                }
                            }
                        }
                        parent$ui_release = parent$ui_release.getParent$ui_release();
                    }
                }
                layoutNodeRequireLayoutNode2 = layoutNodeRequireLayoutNode2.getParent$ui_release();
                parent$ui_release = (layoutNodeRequireLayoutNode2 == null || (nodes$ui_release = layoutNodeRequireLayoutNode2.getNodes$ui_release()) == null) ? null : nodes$ui_release.getTail$ui_release();
            }
            if (arrayList != null && arrayList.size() - 1 >= 0) {
                while (true) {
                    int i = size - 1;
                    if (((RotaryInputModifierNode) arrayList.get(size)).onPreRotaryScrollEvent(rotaryScrollEvent)) {
                        return true;
                    }
                    if (i < 0) {
                        break;
                    }
                    size = i;
                }
            }
            for (?? node2 = rotaryInputModifierNode.getNode(); node2 != 0; node2 = DelegatableNodeKt.pop(null)) {
                if (!(node2 instanceof RotaryInputModifierNode)) {
                    node2.getKindSet$ui_release();
                } else if (((RotaryInputModifierNode) node2).onPreRotaryScrollEvent(rotaryScrollEvent)) {
                    return true;
                }
            }
            if (((Boolean) function0.mo2224invoke()).booleanValue()) {
                return true;
            }
            for (?? node3 = rotaryInputModifierNode.getNode(); node3 != 0; node3 = DelegatableNodeKt.pop(null)) {
                if (!(node3 instanceof RotaryInputModifierNode)) {
                    node3.getKindSet$ui_release();
                } else if (((RotaryInputModifierNode) node3).onRotaryScrollEvent(rotaryScrollEvent)) {
                    return true;
                }
            }
            if (arrayList != null) {
                int size2 = arrayList.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    if (((RotaryInputModifierNode) arrayList.get(i2)).onRotaryScrollEvent(rotaryScrollEvent)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override // androidx.compose.ui.focus.FocusOwner
    /* JADX INFO: renamed from: focusSearch-ULY8qGw */
    public Boolean mo139focusSearchULY8qGw(int i, Rect rect, final Function1 function1) {
        final FocusTargetNode focusTargetNodeFindFocusTargetNode = findFocusTargetNode();
        if (focusTargetNodeFindFocusTargetNode != null) {
            FocusRequester focusRequesterM156customFocusSearchOMvw8 = FocusTraversalKt.m156customFocusSearchOMvw8(focusTargetNodeFindFocusTargetNode, i, (LayoutDirection) this.onLayoutDirection.mo2224invoke());
            FocusRequester.Companion companion = FocusRequester.Companion;
            if (Intrinsics.areEqual(focusRequesterM156customFocusSearchOMvw8, companion.getCancel())) {
                return null;
            }
            if (Intrinsics.areEqual(focusRequesterM156customFocusSearchOMvw8, companion.getRedirect$ui_release())) {
                FocusTargetNode focusTargetNodeFindFocusTargetNode2 = findFocusTargetNode();
                if (focusTargetNodeFindFocusTargetNode2 != null) {
                    return (Boolean) function1.invoke(focusTargetNodeFindFocusTargetNode2);
                }
                return null;
            }
            if (!Intrinsics.areEqual(focusRequesterM156customFocusSearchOMvw8, companion.getDefault())) {
                return Boolean.valueOf(focusRequesterM156customFocusSearchOMvw8.findFocusTargetNode$ui_release(function1));
            }
        } else {
            focusTargetNodeFindFocusTargetNode = null;
        }
        return FocusTraversalKt.m157focusSearch0X8WOeE(this.rootFocusNode, i, (LayoutDirection) this.onLayoutDirection.mo2224invoke(), rect, new Function1() { // from class: androidx.compose.ui.focus.FocusOwnerImpl$focusSearch$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(FocusTargetNode focusTargetNode) {
                boolean zBooleanValue;
                if (Intrinsics.areEqual(focusTargetNode, focusTargetNodeFindFocusTargetNode)) {
                    zBooleanValue = false;
                } else {
                    if (Intrinsics.areEqual(focusTargetNode, this.getRootFocusNode$ui_release())) {
                        throw new IllegalStateException("Focus search landed at the root.");
                    }
                    zBooleanValue = ((Boolean) function1.invoke(focusTargetNode)).booleanValue();
                }
                return Boolean.valueOf(zBooleanValue);
            }
        });
    }

    @Override // androidx.compose.ui.focus.FocusOwner
    public FocusTargetNode getActiveFocusTargetNode() {
        return this.activeFocusTargetNode;
    }

    @Override // androidx.compose.ui.focus.FocusOwner
    public Rect getFocusRect() {
        FocusTargetNode focusTargetNodeFindFocusTargetNode = findFocusTargetNode();
        if (focusTargetNodeFindFocusTargetNode != null) {
            return FocusTraversalKt.focusRect(focusTargetNodeFindFocusTargetNode);
        }
        return null;
    }

    @Override // androidx.compose.ui.focus.FocusOwner
    public FocusTransactionManager getFocusTransactionManager() {
        return this.focusTransactionManager;
    }

    @Override // androidx.compose.ui.focus.FocusOwner
    public MutableObjectList getListeners() {
        return this.listeners;
    }

    @Override // androidx.compose.ui.focus.FocusOwner
    public Modifier getModifier() {
        return this.modifier;
    }

    public final FocusTargetNode getRootFocusNode$ui_release() {
        return this.rootFocusNode;
    }

    @Override // androidx.compose.ui.focus.FocusOwner
    public FocusState getRootState() {
        return this.rootFocusNode.getFocusState();
    }

    @Override // androidx.compose.ui.focus.FocusOwner
    public boolean isFocusCaptured() {
        return this.isFocusCaptured;
    }

    @Override // androidx.compose.ui.focus.FocusOwner
    public void releaseFocus() {
        if (ComposeUiFlags.isTrackFocusEnabled) {
            FocusTransactionsKt.clearFocus(this.rootFocusNode, true, true);
            return;
        }
        FocusTransactionManager focusTransactionManager = getFocusTransactionManager();
        if (focusTransactionManager.getOngoingTransaction()) {
            FocusTransactionsKt.clearFocus(this.rootFocusNode, true, true);
            return;
        }
        try {
            focusTransactionManager.beginTransaction();
            FocusTransactionsKt.clearFocus(this.rootFocusNode, true, true);
        } finally {
            focusTransactionManager.commitTransaction();
        }
    }

    @Override // androidx.compose.ui.focus.FocusOwner
    /* JADX INFO: renamed from: requestFocusForOwner-7o62pno */
    public boolean mo140requestFocusForOwner7o62pno(FocusDirection focusDirection, Rect rect) {
        return ((Boolean) this.onRequestFocusForOwner.invoke(focusDirection, rect)).booleanValue();
    }

    @Override // androidx.compose.ui.focus.FocusOwner
    public void scheduleInvalidation(FocusEventModifierNode focusEventModifierNode) {
        this.focusInvalidationManager.scheduleInvalidation(focusEventModifierNode);
    }

    @Override // androidx.compose.ui.focus.FocusOwner
    public void scheduleInvalidation(FocusPropertiesModifierNode focusPropertiesModifierNode) {
        this.focusInvalidationManager.scheduleInvalidation(focusPropertiesModifierNode);
    }

    @Override // androidx.compose.ui.focus.FocusOwner
    public void scheduleInvalidation(FocusTargetNode focusTargetNode) {
        this.focusInvalidationManager.scheduleInvalidation(focusTargetNode);
    }

    @Override // androidx.compose.ui.focus.FocusOwner
    public void scheduleInvalidationForOwner() {
        this.focusInvalidationManager.scheduleInvalidationForOwner();
    }

    @Override // androidx.compose.ui.focus.FocusOwner
    public void setActiveFocusTargetNode(FocusTargetNode focusTargetNode) {
        FocusTargetNode focusTargetNode2 = this.activeFocusTargetNode;
        this.activeFocusTargetNode = focusTargetNode;
        if (focusTargetNode == null || focusTargetNode2 != focusTargetNode) {
            setFocusCaptured(false);
        }
        if (ComposeUiFlags.isSemanticAutofillEnabled) {
            MutableObjectList listeners = getListeners();
            Object[] objArr = listeners.content;
            int i = listeners._size;
            for (int i2 = 0; i2 < i; i2++) {
                ((FocusListener) objArr[i2]).onFocusChanged(focusTargetNode2, focusTargetNode);
            }
        }
    }

    public void setFocusCaptured(boolean z) {
        if (!((z && getActiveFocusTargetNode() == null) ? false : true)) {
            InlineClassHelperKt.throwIllegalArgumentException("Cannot capture focus when the active focus target node is unset");
        }
        this.isFocusCaptured = z;
    }
}
