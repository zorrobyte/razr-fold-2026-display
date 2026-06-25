package androidx.core.animation;

import android.os.Looper;
import android.util.AndroidRuntimeException;
import android.util.Log;
import androidx.collection.SimpleArrayMap;
import androidx.core.animation.AnimationHandler;
import androidx.core.animation.Animator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public final class AnimatorSet extends Animator implements AnimationHandler.AnimationFrameCallback {
    private static final Comparator EVENT_COMPARATOR = new Comparator() { // from class: androidx.core.animation.AnimatorSet.3
        @Override // java.util.Comparator
        public int compare(AnimationEvent animationEvent, AnimationEvent animationEvent2) {
            long time = animationEvent.getTime();
            long time2 = animationEvent2.getTime();
            if (time == time2) {
                int i = animationEvent2.mEvent;
                int i2 = animationEvent.mEvent;
                return i + i2 == 1 ? i2 - i : i - i2;
            }
            if (time2 == -1) {
                return -1;
            }
            return (time != -1 && time - time2 <= 0) ? -1 : 1;
        }
    };
    private boolean mChildrenInitialized;
    private ValueAnimator mDelayAnim;
    private long mDuration;
    private long mFirstFrame;
    private Interpolator mInterpolator;
    private int mLastEventId;
    private long mLastFrameTime;
    private AnimatorListenerAdapter mNoOpListener;
    private long mPauseTime;
    boolean mReversing;
    private Node mRootNode;
    private SeekState mSeekState;
    private boolean mSelfPulse;
    private long mTotalDuration;
    private ArrayList mPlayingSet = new ArrayList();
    SimpleArrayMap mNodeMap = new SimpleArrayMap();
    private ArrayList mEvents = new ArrayList();
    private ArrayList mNodes = new ArrayList();
    boolean mDependencyDirty = false;
    private boolean mStarted = false;
    long mStartDelay = 0;

    class AnimationEvent {
        final int mEvent;
        final Node mNode;

        AnimationEvent(Node node, int i) {
            this.mNode = node;
            this.mEvent = i;
        }

        long getTime() {
            int i = this.mEvent;
            if (i == 0) {
                return this.mNode.mStartTime;
            }
            if (i != 1) {
                return this.mNode.mEndTime;
            }
            Node node = this.mNode;
            long j = node.mStartTime;
            if (j == -1) {
                return -1L;
            }
            return j + node.mAnimation.getStartDelay();
        }

        public String toString() {
            int i = this.mEvent;
            return (i == 0 ? "start" : i == 1 ? "delay ended" : "end") + " " + this.mNode.mAnimation.toString();
        }
    }

    public class Builder {
        private Node mCurrentNode;

        Builder(Animator animator) {
            AnimatorSet.this.mDependencyDirty = true;
            this.mCurrentNode = AnimatorSet.this.getNodeForAnimation(animator);
        }

        public Builder with(Animator animator) {
            this.mCurrentNode.addSibling(AnimatorSet.this.getNodeForAnimation(animator));
            return this;
        }
    }

    class Node implements Cloneable {
        Animator mAnimation;
        ArrayList mParents;
        ArrayList mSiblings;
        ArrayList mChildNodes = null;
        boolean mEnded = false;
        Node mLatestParent = null;
        boolean mParentsAdded = false;
        long mStartTime = 0;
        long mEndTime = 0;
        long mTotalDuration = 0;

        Node(Animator animator) {
            this.mAnimation = animator;
        }

        void addChild(Node node) {
            if (this.mChildNodes == null) {
                this.mChildNodes = new ArrayList();
            }
            if (this.mChildNodes.contains(node)) {
                return;
            }
            this.mChildNodes.add(node);
            node.addParent(this);
        }

        public void addParent(Node node) {
            if (this.mParents == null) {
                this.mParents = new ArrayList();
            }
            if (this.mParents.contains(node)) {
                return;
            }
            this.mParents.add(node);
            node.addChild(this);
        }

        public void addParents(ArrayList arrayList) {
            if (arrayList == null) {
                return;
            }
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                addParent((Node) arrayList.get(i));
            }
        }

        public void addSibling(Node node) {
            if (this.mSiblings == null) {
                this.mSiblings = new ArrayList();
            }
            if (this.mSiblings.contains(node)) {
                return;
            }
            this.mSiblings.add(node);
            node.addSibling(this);
        }

        /* JADX INFO: renamed from: clone, reason: merged with bridge method [inline-methods] */
        public Node m1047clone() {
            try {
                Node node = (Node) super.clone();
                node.mAnimation = this.mAnimation.m1046clone();
                if (this.mChildNodes != null) {
                    node.mChildNodes = new ArrayList(this.mChildNodes);
                }
                if (this.mSiblings != null) {
                    node.mSiblings = new ArrayList(this.mSiblings);
                }
                if (this.mParents != null) {
                    node.mParents = new ArrayList(this.mParents);
                }
                node.mEnded = false;
                return node;
            } catch (CloneNotSupportedException unused) {
                throw new AssertionError();
            }
        }
    }

    class SeekState {
        private long mPlayTime = -1;
        private boolean mSeekingInReverse = false;

        SeekState() {
        }

        long getPlayTime() {
            return this.mPlayTime;
        }

        long getPlayTimeNormalized() {
            AnimatorSet animatorSet = AnimatorSet.this;
            return animatorSet.mReversing ? (animatorSet.getTotalDuration() - AnimatorSet.this.mStartDelay) - this.mPlayTime : this.mPlayTime;
        }

        boolean isActive() {
            return this.mPlayTime != -1;
        }

        void reset() {
            this.mPlayTime = -1L;
            this.mSeekingInReverse = false;
        }

        void updateSeekDirection(boolean z) {
            if (z && AnimatorSet.this.getTotalDuration() == -1) {
                throw new UnsupportedOperationException("Error: Cannot reverse infinite animator set");
            }
            if (this.mPlayTime < 0 || z == this.mSeekingInReverse) {
                return;
            }
            this.mPlayTime = (AnimatorSet.this.getTotalDuration() - AnimatorSet.this.mStartDelay) - this.mPlayTime;
            this.mSeekingInReverse = z;
        }
    }

    public AnimatorSet() {
        ValueAnimator duration = ValueAnimator.ofFloat(0.0f, 1.0f).setDuration(0L);
        this.mDelayAnim = duration;
        this.mRootNode = new Node(duration);
        this.mDuration = -1L;
        this.mInterpolator = null;
        this.mTotalDuration = 0L;
        this.mLastFrameTime = -1L;
        this.mFirstFrame = -1L;
        this.mLastEventId = -1;
        this.mReversing = false;
        this.mSelfPulse = true;
        this.mSeekState = new SeekState();
        this.mChildrenInitialized = false;
        this.mPauseTime = -1L;
        this.mNoOpListener = new AnimatorListenerAdapter() { // from class: androidx.core.animation.AnimatorSet.1
            @Override // androidx.core.animation.AnimatorListenerAdapter, androidx.core.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                if (AnimatorSet.this.mNodeMap.get(animator) == null) {
                    throw new AndroidRuntimeException("Error: animation ended is not in the node map");
                }
                ((Node) AnimatorSet.this.mNodeMap.get(animator)).mEnded = true;
            }
        };
        this.mNodeMap.put(this.mDelayAnim, this.mRootNode);
        this.mNodes.add(this.mRootNode);
    }

    private void addNoOpListener() {
        for (int i = 1; i < this.mNodes.size(); i++) {
            ((Node) this.mNodes.get(i)).mAnimation.addListener(this.mNoOpListener);
        }
    }

    private void createDependencyGraph() {
        if (!this.mDependencyDirty) {
            for (int i = 0; i < this.mNodes.size(); i++) {
                if (((Node) this.mNodes.get(i)).mTotalDuration == ((Node) this.mNodes.get(i)).mAnimation.getTotalDuration()) {
                }
            }
            return;
        }
        this.mDependencyDirty = false;
        int size = this.mNodes.size();
        for (int i2 = 0; i2 < size; i2++) {
            ((Node) this.mNodes.get(i2)).mParentsAdded = false;
        }
        for (int i3 = 0; i3 < size; i3++) {
            Node node = (Node) this.mNodes.get(i3);
            if (!node.mParentsAdded) {
                node.mParentsAdded = true;
                ArrayList arrayList = node.mSiblings;
                if (arrayList != null) {
                    findSiblings(node, arrayList);
                    node.mSiblings.remove(node);
                    int size2 = node.mSiblings.size();
                    for (int i4 = 0; i4 < size2; i4++) {
                        node.addParents(((Node) node.mSiblings.get(i4)).mParents);
                    }
                    for (int i5 = 0; i5 < size2; i5++) {
                        Node node2 = (Node) node.mSiblings.get(i5);
                        node2.addParents(node.mParents);
                        node2.mParentsAdded = true;
                    }
                }
            }
        }
        for (int i6 = 0; i6 < size; i6++) {
            Node node3 = (Node) this.mNodes.get(i6);
            Node node4 = this.mRootNode;
            if (node3 != node4 && node3.mParents == null) {
                node3.addParent(node4);
            }
        }
        ArrayList arrayList2 = new ArrayList(this.mNodes.size());
        Node node5 = this.mRootNode;
        node5.mStartTime = 0L;
        node5.mEndTime = this.mDelayAnim.getDuration();
        updatePlayTime(this.mRootNode, arrayList2);
        sortAnimationEvents();
        ArrayList arrayList3 = this.mEvents;
        this.mTotalDuration = ((AnimationEvent) arrayList3.get(arrayList3.size() - 1)).getTime();
    }

    private void endAnimation() {
        this.mStarted = false;
        this.mLastFrameTime = -1L;
        this.mFirstFrame = -1L;
        this.mLastEventId = -1;
        this.mPaused = false;
        this.mPauseTime = -1L;
        this.mSeekState.reset();
        this.mPlayingSet.clear();
        removeAnimationCallback();
        ArrayList arrayList = this.mListeners;
        if (arrayList != null) {
            ArrayList arrayList2 = (ArrayList) arrayList.clone();
            int size = arrayList2.size();
            for (int i = 0; i < size; i++) {
                ((Animator.AnimatorListener) arrayList2.get(i)).onAnimationEnd(this, this.mReversing);
            }
        }
        removeNoOpListener();
        this.mSelfPulse = true;
        this.mReversing = false;
    }

    private int findLatestEventIdForTime(long j) {
        int size = this.mEvents.size();
        int i = this.mLastEventId;
        if (!this.mReversing) {
            for (int i2 = i + 1; i2 < size; i2++) {
                AnimationEvent animationEvent = (AnimationEvent) this.mEvents.get(i2);
                if (animationEvent.getTime() != -1 && animationEvent.getTime() <= j) {
                    i = i2;
                }
            }
            return i;
        }
        long totalDuration = getTotalDuration() - j;
        int i3 = this.mLastEventId;
        if (i3 != -1) {
            size = i3;
        }
        this.mLastEventId = size;
        for (int i4 = size - 1; i4 >= 0; i4--) {
            if (((AnimationEvent) this.mEvents.get(i4)).getTime() >= totalDuration) {
                i = i4;
            }
        }
        return i;
    }

    private void findSiblings(Node node, ArrayList arrayList) {
        if (arrayList.contains(node)) {
            return;
        }
        arrayList.add(node);
        if (node.mSiblings == null) {
            return;
        }
        for (int i = 0; i < node.mSiblings.size(); i++) {
            findSiblings((Node) node.mSiblings.get(i), arrayList);
        }
    }

    private long getPlayTimeForNode(long j, Node node) {
        return getPlayTimeForNode(j, node, this.mReversing);
    }

    private long getPlayTimeForNode(long j, Node node, boolean z) {
        if (!z) {
            return j - node.mStartTime;
        }
        return node.mEndTime - (getTotalDuration() - j);
    }

    private void handleAnimationEvents(int i, int i2, long j) {
        if (!this.mReversing) {
            for (int i3 = i + 1; i3 <= i2; i3++) {
                AnimationEvent animationEvent = (AnimationEvent) this.mEvents.get(i3);
                Node node = animationEvent.mNode;
                int i4 = animationEvent.mEvent;
                if (i4 == 0) {
                    this.mPlayingSet.add(node);
                    if (node.mAnimation.isStarted()) {
                        node.mAnimation.cancel();
                    }
                    node.mEnded = false;
                    node.mAnimation.startWithoutPulsing(false);
                    pulseFrame(node, 0L);
                } else if (i4 == 2 && !node.mEnded) {
                    pulseFrame(node, getPlayTimeForNode(j, node));
                }
            }
            return;
        }
        if (i == -1) {
            i = this.mEvents.size();
        }
        for (int i5 = i - 1; i5 >= i2; i5--) {
            AnimationEvent animationEvent2 = (AnimationEvent) this.mEvents.get(i5);
            Node node2 = animationEvent2.mNode;
            int i6 = animationEvent2.mEvent;
            if (i6 == 2) {
                if (node2.mAnimation.isStarted()) {
                    node2.mAnimation.cancel();
                }
                node2.mEnded = false;
                this.mPlayingSet.add(animationEvent2.mNode);
                node2.mAnimation.startWithoutPulsing(true);
                pulseFrame(node2, 0L);
            } else if (i6 == 1 && !node2.mEnded) {
                pulseFrame(node2, getPlayTimeForNode(j, node2));
            }
        }
    }

    private void initAnimation() {
        if (this.mInterpolator != null) {
            for (int i = 0; i < this.mNodes.size(); i++) {
                ((Node) this.mNodes.get(i)).mAnimation.setInterpolator(this.mInterpolator);
            }
        }
        updateAnimatorsDuration();
        createDependencyGraph();
    }

    private void initChildren() {
        if (isInitialized()) {
            return;
        }
        this.mChildrenInitialized = true;
        skipToEndValue(false);
    }

    private static boolean isEmptySet(AnimatorSet animatorSet) {
        if (animatorSet.getStartDelay() > 0) {
            return false;
        }
        for (int i = 0; i < animatorSet.getChildAnimations().size(); i++) {
            Animator animator = (Animator) animatorSet.getChildAnimations().get(i);
            if (!(animator instanceof AnimatorSet) || !isEmptySet((AnimatorSet) animator)) {
                return false;
            }
        }
        return true;
    }

    private void notifyUpdateListeners() {
        if (this.mUpdateListeners != null) {
            for (int i = 0; i < this.mUpdateListeners.size(); i++) {
                ((Animator.AnimatorUpdateListener) this.mUpdateListeners.get(i)).onAnimationUpdate(this);
            }
        }
    }

    private void pulseFrame(Node node, long j) {
        if (node.mEnded) {
            return;
        }
        float durationScale = ValueAnimator.getDurationScale();
        if (durationScale == 0.0f) {
            durationScale = 1.0f;
        }
        node.mEnded = node.mAnimation.pulseAnimationFrame((long) (j * durationScale));
    }

    private void removeAnimationCallback() {
        if (this.mSelfPulse) {
            AnimationHandler.getInstance().removeCallback(this);
        }
    }

    private void removeNoOpListener() {
        for (int i = 1; i < this.mNodes.size(); i++) {
            ((Node) this.mNodes.get(i)).mAnimation.removeListener(this.mNoOpListener);
        }
    }

    private void sortAnimationEvents() {
        boolean z;
        this.mEvents.clear();
        for (int i = 1; i < this.mNodes.size(); i++) {
            Node node = (Node) this.mNodes.get(i);
            this.mEvents.add(new AnimationEvent(node, 0));
            this.mEvents.add(new AnimationEvent(node, 1));
            this.mEvents.add(new AnimationEvent(node, 2));
        }
        Collections.sort(this.mEvents, EVENT_COMPARATOR);
        int size = this.mEvents.size();
        int i2 = 0;
        while (i2 < size) {
            AnimationEvent animationEvent = (AnimationEvent) this.mEvents.get(i2);
            if (animationEvent.mEvent == 2) {
                Node node2 = animationEvent.mNode;
                long j = node2.mStartTime;
                long j2 = node2.mEndTime;
                if (j == j2) {
                    z = true;
                } else if (j2 == j + node2.mAnimation.getStartDelay()) {
                    z = false;
                }
                int i3 = i2 + 1;
                int i4 = size;
                int i5 = i4;
                for (int i6 = i3; i6 < size && (i4 >= size || i5 >= size); i6++) {
                    if (((AnimationEvent) this.mEvents.get(i6)).mNode == animationEvent.mNode) {
                        if (((AnimationEvent) this.mEvents.get(i6)).mEvent == 0) {
                            i4 = i6;
                        } else if (((AnimationEvent) this.mEvents.get(i6)).mEvent == 1) {
                            i5 = i6;
                        }
                    }
                }
                if (z && i4 == this.mEvents.size()) {
                    throw new UnsupportedOperationException("Something went wrong, no start isfound after stop for an animation that has the same start and endtime.");
                }
                if (i5 == this.mEvents.size()) {
                    throw new UnsupportedOperationException("Something went wrong, no startdelay end is found after stop for an animation");
                }
                if (z) {
                    this.mEvents.add(i2, (AnimationEvent) this.mEvents.remove(i4));
                    i2 = i3;
                }
                this.mEvents.add(i2, (AnimationEvent) this.mEvents.remove(i5));
                i2 += 2;
            }
            i2++;
        }
        if (!this.mEvents.isEmpty() && ((AnimationEvent) this.mEvents.get(0)).mEvent != 0) {
            throw new UnsupportedOperationException("Sorting went bad, the start event should always be at index 0");
        }
        this.mEvents.add(0, new AnimationEvent(this.mRootNode, 0));
        this.mEvents.add(1, new AnimationEvent(this.mRootNode, 1));
        this.mEvents.add(2, new AnimationEvent(this.mRootNode, 2));
        ArrayList arrayList = this.mEvents;
        if (((AnimationEvent) arrayList.get(arrayList.size() - 1)).mEvent != 0) {
            ArrayList arrayList2 = this.mEvents;
            if (((AnimationEvent) arrayList2.get(arrayList2.size() - 1)).mEvent != 1) {
                return;
            }
        }
        throw new UnsupportedOperationException("Something went wrong, the last event is not an end event");
    }

    private void start(boolean z, boolean z2) {
        if (Looper.myLooper() == null) {
            throw new AndroidRuntimeException("Animators may only be run on Looper threads");
        }
        this.mStarted = true;
        this.mSelfPulse = z2;
        this.mPaused = false;
        this.mPauseTime = -1L;
        int size = this.mNodes.size();
        for (int i = 0; i < size; i++) {
            ((Node) this.mNodes.get(i)).mEnded = false;
        }
        initAnimation();
        if (z && !canReverse()) {
            throw new UnsupportedOperationException("Cannot reverse infinite AnimatorSet");
        }
        this.mReversing = z;
        boolean zIsEmptySet = isEmptySet(this);
        if (!zIsEmptySet) {
            startAnimation();
        }
        ArrayList arrayList = this.mListeners;
        if (arrayList != null) {
            ArrayList arrayList2 = (ArrayList) arrayList.clone();
            int size2 = arrayList2.size();
            for (int i2 = 0; i2 < size2; i2++) {
                ((Animator.AnimatorListener) arrayList2.get(i2)).onAnimationStart(this, z);
            }
        }
        if (zIsEmptySet) {
            end();
        }
    }

    private void startAnimation() {
        addNoOpListener();
        long playTime = 0;
        if (this.mSeekState.getPlayTimeNormalized() == 0 && this.mReversing) {
            this.mSeekState.reset();
        }
        if (isInitialized()) {
            skipToEndValue(!this.mReversing);
        } else if (this.mReversing) {
            initChildren();
            skipToEndValue(!this.mReversing);
        } else {
            for (int size = this.mEvents.size() - 1; size >= 0; size--) {
                if (((AnimationEvent) this.mEvents.get(size)).mEvent == 1) {
                    Animator animator = ((AnimationEvent) this.mEvents.get(size)).mNode.mAnimation;
                    if (animator.isInitialized()) {
                        animator.skipToEndValue(true);
                    }
                }
            }
        }
        if (this.mReversing || this.mStartDelay == 0 || this.mSeekState.isActive()) {
            if (this.mSeekState.isActive()) {
                this.mSeekState.updateSeekDirection(this.mReversing);
                playTime = this.mSeekState.getPlayTime();
            }
            int iFindLatestEventIdForTime = findLatestEventIdForTime(playTime);
            handleAnimationEvents(-1, iFindLatestEventIdForTime, playTime);
            for (int size2 = this.mPlayingSet.size() - 1; size2 >= 0; size2--) {
                if (((Node) this.mPlayingSet.get(size2)).mEnded) {
                    this.mPlayingSet.remove(size2);
                }
            }
            this.mLastEventId = iFindLatestEventIdForTime;
        }
        if (this.mSelfPulse) {
            Animator.addAnimationCallback(this);
        }
    }

    private void updateAnimatorsDuration() {
        if (this.mDuration >= 0) {
            int size = this.mNodes.size();
            for (int i = 0; i < size; i++) {
                ((Node) this.mNodes.get(i)).mAnimation.setDuration(this.mDuration);
            }
        }
        this.mDelayAnim.setDuration(this.mStartDelay);
    }

    private void updatePlayTime(Node node, ArrayList arrayList) {
        int i = 0;
        if (node.mChildNodes == null) {
            if (node == this.mRootNode) {
                while (i < this.mNodes.size()) {
                    Node node2 = (Node) this.mNodes.get(i);
                    if (node2 != this.mRootNode) {
                        node2.mStartTime = -1L;
                        node2.mEndTime = -1L;
                    }
                    i++;
                }
                return;
            }
            return;
        }
        arrayList.add(node);
        int size = node.mChildNodes.size();
        while (i < size) {
            Node node3 = (Node) node.mChildNodes.get(i);
            node3.mTotalDuration = node3.mAnimation.getTotalDuration();
            int iIndexOf = arrayList.indexOf(node3);
            if (iIndexOf >= 0) {
                while (iIndexOf < arrayList.size()) {
                    ((Node) arrayList.get(iIndexOf)).mLatestParent = null;
                    ((Node) arrayList.get(iIndexOf)).mStartTime = -1L;
                    ((Node) arrayList.get(iIndexOf)).mEndTime = -1L;
                    iIndexOf++;
                }
                node3.mStartTime = -1L;
                node3.mEndTime = -1L;
                node3.mLatestParent = null;
                Log.w("AnimatorSet", "Cycle found in AnimatorSet: " + this);
            } else {
                long j = node3.mStartTime;
                if (j != -1) {
                    long j2 = node.mEndTime;
                    if (j2 == -1) {
                        node3.mLatestParent = node;
                        node3.mStartTime = -1L;
                        node3.mEndTime = -1L;
                    } else {
                        if (j2 >= j) {
                            node3.mLatestParent = node;
                            node3.mStartTime = j2;
                        }
                        long j3 = node3.mTotalDuration;
                        node3.mEndTime = j3 == -1 ? -1L : node3.mStartTime + j3;
                    }
                }
                updatePlayTime(node3, arrayList);
            }
            i++;
        }
        arrayList.remove(node);
    }

    public boolean canReverse() {
        return getTotalDuration() != -1;
    }

    @Override // androidx.core.animation.Animator
    public void cancel() {
        if (Looper.myLooper() == null) {
            throw new AndroidRuntimeException("Animators may only be run on Looper threads");
        }
        if (isStarted()) {
            ArrayList arrayList = this.mListeners;
            if (arrayList != null) {
                ArrayList arrayList2 = (ArrayList) arrayList.clone();
                int size = arrayList2.size();
                for (int i = 0; i < size; i++) {
                    ((Animator.AnimatorListener) arrayList2.get(i)).onAnimationCancel(this);
                }
            }
            ArrayList arrayList3 = new ArrayList(this.mPlayingSet);
            int size2 = arrayList3.size();
            for (int i2 = 0; i2 < size2; i2++) {
                ((Node) arrayList3.get(i2)).mAnimation.cancel();
            }
            this.mPlayingSet.clear();
            endAnimation();
        }
    }

    @Override // androidx.core.animation.Animator
    /* JADX INFO: renamed from: clone, reason: merged with bridge method [inline-methods] */
    public AnimatorSet m1046clone() {
        final AnimatorSet animatorSet = (AnimatorSet) super.m1046clone();
        int size = this.mNodes.size();
        animatorSet.mStarted = false;
        animatorSet.mLastFrameTime = -1L;
        animatorSet.mFirstFrame = -1L;
        animatorSet.mLastEventId = -1;
        animatorSet.mPaused = false;
        animatorSet.mPauseTime = -1L;
        animatorSet.mSeekState = new SeekState();
        animatorSet.mSelfPulse = true;
        animatorSet.mPlayingSet = new ArrayList();
        animatorSet.mNodeMap = new SimpleArrayMap();
        animatorSet.mNodes = new ArrayList(size);
        animatorSet.mEvents = new ArrayList();
        animatorSet.mNoOpListener = new AnimatorListenerAdapter() { // from class: androidx.core.animation.AnimatorSet.2
            @Override // androidx.core.animation.AnimatorListenerAdapter, androidx.core.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                if (animatorSet.mNodeMap.get(animator) == null) {
                    throw new AndroidRuntimeException("Error: animation ended is not in the node map");
                }
                ((Node) animatorSet.mNodeMap.get(animator)).mEnded = true;
            }
        };
        animatorSet.mReversing = false;
        animatorSet.mDependencyDirty = true;
        HashMap map = new HashMap(size);
        for (int i = 0; i < size; i++) {
            Node node = (Node) this.mNodes.get(i);
            Node nodeM1047clone = node.m1047clone();
            nodeM1047clone.mAnimation.removeListener(this.mNoOpListener);
            map.put(node, nodeM1047clone);
            animatorSet.mNodes.add(nodeM1047clone);
            animatorSet.mNodeMap.put(nodeM1047clone.mAnimation, nodeM1047clone);
        }
        Node node2 = (Node) map.get(this.mRootNode);
        animatorSet.mRootNode = node2;
        animatorSet.mDelayAnim = (ValueAnimator) node2.mAnimation;
        for (int i2 = 0; i2 < size; i2++) {
            Node node3 = (Node) this.mNodes.get(i2);
            Node node4 = (Node) map.get(node3);
            Node node5 = node3.mLatestParent;
            node4.mLatestParent = node5 == null ? null : (Node) map.get(node5);
            ArrayList arrayList = node3.mChildNodes;
            int size2 = arrayList == null ? 0 : arrayList.size();
            for (int i3 = 0; i3 < size2; i3++) {
                node4.mChildNodes.set(i3, (Node) map.get(node3.mChildNodes.get(i3)));
            }
            ArrayList arrayList2 = node3.mSiblings;
            int size3 = arrayList2 == null ? 0 : arrayList2.size();
            for (int i4 = 0; i4 < size3; i4++) {
                node4.mSiblings.set(i4, (Node) map.get(node3.mSiblings.get(i4)));
            }
            ArrayList arrayList3 = node3.mParents;
            int size4 = arrayList3 == null ? 0 : arrayList3.size();
            for (int i5 = 0; i5 < size4; i5++) {
                node4.mParents.set(i5, (Node) map.get(node3.mParents.get(i5)));
            }
        }
        return animatorSet;
    }

    @Override // androidx.core.animation.AnimationHandler.AnimationFrameCallback
    public boolean doAnimationFrame(long j) {
        float durationScale = ValueAnimator.getDurationScale();
        if (durationScale == 0.0f) {
            end();
            return true;
        }
        if (this.mFirstFrame < 0) {
            this.mFirstFrame = j;
        }
        if (this.mPaused) {
            if (this.mPauseTime == -1) {
                this.mPauseTime = j;
            }
            removeAnimationCallback();
            return false;
        }
        long j2 = this.mPauseTime;
        if (j2 > 0) {
            this.mFirstFrame += j - j2;
            this.mPauseTime = -1L;
        }
        if (this.mSeekState.isActive()) {
            this.mSeekState.updateSeekDirection(this.mReversing);
            if (this.mReversing) {
                this.mFirstFrame = j - ((long) (this.mSeekState.getPlayTime() * durationScale));
            } else {
                this.mFirstFrame = j - ((long) ((this.mSeekState.getPlayTime() + this.mStartDelay) * durationScale));
            }
            skipToEndValue(!this.mReversing);
            this.mPlayingSet.clear();
            for (int size = this.mNodes.size() - 1; size >= 0; size--) {
                ((Node) this.mNodes.get(size)).mEnded = false;
            }
            this.mLastEventId = -1;
            this.mSeekState.reset();
        }
        if (!this.mReversing && j < this.mFirstFrame + ((long) (this.mStartDelay * durationScale))) {
            return false;
        }
        long j3 = (long) ((j - this.mFirstFrame) / durationScale);
        this.mLastFrameTime = j;
        int iFindLatestEventIdForTime = findLatestEventIdForTime(j3);
        handleAnimationEvents(this.mLastEventId, iFindLatestEventIdForTime, j3);
        this.mLastEventId = iFindLatestEventIdForTime;
        for (int i = 0; i < this.mPlayingSet.size(); i++) {
            Node node = (Node) this.mPlayingSet.get(i);
            if (!node.mEnded) {
                pulseFrame(node, getPlayTimeForNode(j3, node));
            }
        }
        for (int size2 = this.mPlayingSet.size() - 1; size2 >= 0; size2--) {
            if (((Node) this.mPlayingSet.get(size2)).mEnded) {
                this.mPlayingSet.remove(size2);
            }
        }
        boolean z = !this.mReversing ? !(this.mPlayingSet.isEmpty() && this.mLastEventId == this.mEvents.size() - 1) : !(this.mPlayingSet.size() == 1 && this.mPlayingSet.get(0) == this.mRootNode) && (!this.mPlayingSet.isEmpty() || this.mLastEventId >= 3);
        notifyUpdateListeners();
        if (!z) {
            return false;
        }
        endAnimation();
        return true;
    }

    @Override // androidx.core.animation.Animator
    public void end() {
        if (Looper.myLooper() == null) {
            throw new AndroidRuntimeException("Animators may only be run on Looper threads");
        }
        if (isStarted()) {
            if (this.mReversing) {
                int size = this.mLastEventId;
                if (size == -1) {
                    size = this.mEvents.size();
                }
                this.mLastEventId = size;
                while (true) {
                    int i = this.mLastEventId;
                    if (i <= 0) {
                        break;
                    }
                    int i2 = i - 1;
                    this.mLastEventId = i2;
                    AnimationEvent animationEvent = (AnimationEvent) this.mEvents.get(i2);
                    Animator animator = animationEvent.mNode.mAnimation;
                    if (!((Node) this.mNodeMap.get(animator)).mEnded) {
                        int i3 = animationEvent.mEvent;
                        if (i3 == 2) {
                            animator.reverse();
                        } else if (i3 == 1 && animator.isStarted()) {
                            animator.end();
                        }
                    }
                }
            } else {
                while (this.mLastEventId < this.mEvents.size() - 1) {
                    int i4 = this.mLastEventId + 1;
                    this.mLastEventId = i4;
                    AnimationEvent animationEvent2 = (AnimationEvent) this.mEvents.get(i4);
                    Animator animator2 = animationEvent2.mNode.mAnimation;
                    if (!((Node) this.mNodeMap.get(animator2)).mEnded) {
                        int i5 = animationEvent2.mEvent;
                        if (i5 == 0) {
                            animator2.start();
                        } else if (i5 == 2 && animator2.isStarted()) {
                            animator2.end();
                        }
                    }
                }
            }
            this.mPlayingSet.clear();
        }
        endAnimation();
    }

    public ArrayList getChildAnimations() {
        ArrayList arrayList = new ArrayList();
        int size = this.mNodes.size();
        for (int i = 0; i < size; i++) {
            Node node = (Node) this.mNodes.get(i);
            if (node != this.mRootNode) {
                arrayList.add(node.mAnimation);
            }
        }
        return arrayList;
    }

    Node getNodeForAnimation(Animator animator) {
        Node node = (Node) this.mNodeMap.get(animator);
        if (node == null) {
            node = new Node(animator);
            this.mNodeMap.put(animator, node);
            this.mNodes.add(node);
            if (animator instanceof AnimatorSet) {
                ((AnimatorSet) animator).mSelfPulse = false;
            }
        }
        return node;
    }

    @Override // androidx.core.animation.Animator
    public long getStartDelay() {
        return this.mStartDelay;
    }

    @Override // androidx.core.animation.Animator
    public long getTotalDuration() {
        updateAnimatorsDuration();
        createDependencyGraph();
        return this.mTotalDuration;
    }

    @Override // androidx.core.animation.Animator
    boolean isInitialized() {
        boolean z = true;
        if (this.mChildrenInitialized) {
            return true;
        }
        int i = 0;
        while (true) {
            if (i >= this.mNodes.size()) {
                break;
            }
            if (!((Node) this.mNodes.get(i)).mAnimation.isInitialized()) {
                z = false;
                break;
            }
            i++;
        }
        this.mChildrenInitialized = z;
        return z;
    }

    @Override // androidx.core.animation.Animator
    public boolean isStarted() {
        return this.mStarted;
    }

    public Builder play(Animator animator) {
        return new Builder(animator);
    }

    public void playTogether(Animator... animatorArr) {
        if (animatorArr != null) {
            Builder builderPlay = play(animatorArr[0]);
            for (int i = 1; i < animatorArr.length; i++) {
                builderPlay.with(animatorArr[i]);
            }
        }
    }

    @Override // androidx.core.animation.Animator
    boolean pulseAnimationFrame(long j) {
        return doAnimationFrame(j);
    }

    @Override // androidx.core.animation.Animator
    public void reverse() {
        start(true, true);
    }

    @Override // androidx.core.animation.Animator
    public AnimatorSet setDuration(long j) {
        if (j < 0) {
            throw new IllegalArgumentException("duration must be a value of zero or greater");
        }
        this.mDependencyDirty = true;
        this.mDuration = j;
        return this;
    }

    @Override // androidx.core.animation.Animator
    public void setInterpolator(Interpolator interpolator) {
        this.mInterpolator = interpolator;
    }

    @Override // androidx.core.animation.Animator
    public void setStartDelay(long j) {
        if (j < 0) {
            Log.w("AnimatorSet", "Start delay should always be non-negative");
            j = 0;
        }
        long j2 = j - this.mStartDelay;
        if (j2 == 0) {
            return;
        }
        this.mStartDelay = j;
        if (this.mDependencyDirty) {
            return;
        }
        int size = this.mNodes.size();
        int i = 0;
        while (true) {
            if (i >= size) {
                break;
            }
            Node node = (Node) this.mNodes.get(i);
            if (node == this.mRootNode) {
                node.mEndTime = this.mStartDelay;
            } else {
                long j3 = node.mStartTime;
                node.mStartTime = j3 == -1 ? -1L : j3 + j2;
                long j4 = node.mEndTime;
                node.mEndTime = j4 != -1 ? j4 + j2 : -1L;
            }
            i++;
        }
        long j5 = this.mTotalDuration;
        if (j5 != -1) {
            this.mTotalDuration = j5 + j2;
        }
    }

    @Override // androidx.core.animation.Animator
    void skipToEndValue(boolean z) {
        if (this.mSelfPulse && !isInitialized()) {
            throw new UnsupportedOperationException("Children must be initialized.");
        }
        initAnimation();
        if (z) {
            for (int size = this.mEvents.size() - 1; size >= 0; size--) {
                if (((AnimationEvent) this.mEvents.get(size)).mEvent == 1) {
                    ((AnimationEvent) this.mEvents.get(size)).mNode.mAnimation.skipToEndValue(true);
                }
            }
            return;
        }
        for (int i = 0; i < this.mEvents.size(); i++) {
            if (((AnimationEvent) this.mEvents.get(i)).mEvent == 2) {
                ((AnimationEvent) this.mEvents.get(i)).mNode.mAnimation.skipToEndValue(false);
            }
        }
    }

    @Override // androidx.core.animation.Animator
    public void start() {
        start(false, true);
    }

    @Override // androidx.core.animation.Animator
    void startWithoutPulsing(boolean z) {
        start(z, false);
    }

    public String toString() {
        String str = "AnimatorSet@" + Integer.toHexString(hashCode()) + "{";
        int size = this.mNodes.size();
        for (int i = 0; i < size; i++) {
            str = str + "\n    " + ((Node) this.mNodes.get(i)).mAnimation.toString();
        }
        return str + "\n}";
    }
}
