package com.android.systemui.statusbar.notification.collection.listbuilder;

/* JADX INFO: loaded from: classes.dex */
public class PipelineState {
    private int mState = 0;

    public static String getStateName(int i) {
        switch (i) {
            case 0:
                return "STATE_IDLE";
            case 1:
                return "STATE_BUILD_STARTED";
            case 2:
                return "STATE_RESETTING";
            case 3:
                return "STATE_PRE_GROUP_FILTERING";
            case 4:
                return "STATE_GROUPING";
            case 5:
                return "STATE_TRANSFORMING";
            case 6:
                return "STATE_GROUP_STABILIZING";
            case 7:
                return "STATE_SORTING";
            case 8:
                return "STATE_FINALIZE_FILTERING";
            case 9:
                return "STATE_FINALIZING";
            default:
                return "STATE:" + i;
        }
    }

    public int getState() {
        return this.mState;
    }

    public String getStateName() {
        return getStateName(this.mState);
    }

    public void incrementTo(int i) {
        if (this.mState == i - 1) {
            this.mState = i;
            return;
        }
        throw new IllegalStateException("Cannot increment from state " + this.mState + " to state " + i);
    }

    public void requireIsBefore(int i) {
        if (this.mState < i) {
            return;
        }
        throw new IllegalStateException("Required state is <" + i + " but actual state is " + this.mState);
    }

    public void requireState(int i) {
        if (i == this.mState) {
            return;
        }
        throw new IllegalStateException("Required state is <" + i + " but actual state is " + this.mState);
    }

    public void setState(int i) {
        this.mState = i;
    }
}
