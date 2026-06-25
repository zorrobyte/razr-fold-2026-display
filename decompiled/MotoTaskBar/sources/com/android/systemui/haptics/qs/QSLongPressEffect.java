package com.android.systemui.haptics.qs;

import android.os.VibrationEffect;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlowKt;

/* JADX INFO: compiled from: QSLongPressEffect.kt */
/* JADX INFO: loaded from: classes.dex */
public final class QSLongPressEffect {
    private final MutableStateFlow _postedActionType;
    private final Flow actionType;
    private int effectDuration;
    private boolean hasInitialized;
    private final VibrationEffect snapEffect;
    private State state = State.IDLE;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* JADX INFO: compiled from: QSLongPressEffect.kt */
    public final class ActionType {
        private static final /* synthetic */ EnumEntries $ENTRIES;
        private static final /* synthetic */ ActionType[] $VALUES;
        public static final ActionType CLICK = new ActionType("CLICK", 0);
        public static final ActionType LONG_PRESS = new ActionType("LONG_PRESS", 1);
        public static final ActionType RESET_AND_LONG_PRESS = new ActionType("RESET_AND_LONG_PRESS", 2);
        public static final ActionType START_ANIMATOR = new ActionType("START_ANIMATOR", 3);
        public static final ActionType REVERSE_ANIMATOR = new ActionType("REVERSE_ANIMATOR", 4);
        public static final ActionType CANCEL_ANIMATOR = new ActionType("CANCEL_ANIMATOR", 5);
        public static final ActionType INITIALIZE_ANIMATOR = new ActionType("INITIALIZE_ANIMATOR", 6);

        private static final /* synthetic */ ActionType[] $values() {
            return new ActionType[]{CLICK, LONG_PRESS, RESET_AND_LONG_PRESS, START_ANIMATOR, REVERSE_ANIMATOR, CANCEL_ANIMATOR, INITIALIZE_ANIMATOR};
        }

        static {
            ActionType[] actionTypeArr$values = $values();
            $VALUES = actionTypeArr$values;
            $ENTRIES = EnumEntriesKt.enumEntries(actionTypeArr$values);
        }

        private ActionType(String str, int i) {
        }

        public static ActionType valueOf(String str) {
            return (ActionType) Enum.valueOf(ActionType.class, str);
        }

        public static ActionType[] values() {
            return (ActionType[]) $VALUES.clone();
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* JADX INFO: compiled from: QSLongPressEffect.kt */
    public final class State {
        private static final /* synthetic */ EnumEntries $ENTRIES;
        private static final /* synthetic */ State[] $VALUES;
        public static final State IDLE = new State("IDLE", 0);
        public static final State TIMEOUT_WAIT = new State("TIMEOUT_WAIT", 1);
        public static final State RUNNING_FORWARD = new State("RUNNING_FORWARD", 2);
        public static final State RUNNING_BACKWARDS = new State("RUNNING_BACKWARDS", 3);

        private static final /* synthetic */ State[] $values() {
            return new State[]{IDLE, TIMEOUT_WAIT, RUNNING_FORWARD, RUNNING_BACKWARDS};
        }

        static {
            State[] stateArr$values = $values();
            $VALUES = stateArr$values;
            $ENTRIES = EnumEntriesKt.enumEntries(stateArr$values);
        }

        private State(String str, int i) {
        }

        public static State valueOf(String str) {
            return (State) Enum.valueOf(State.class, str);
        }

        public static State[] values() {
            return (State[]) $VALUES.clone();
        }
    }

    /* JADX INFO: compiled from: QSLongPressEffect.kt */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[State.values().length];
            try {
                iArr[State.IDLE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[State.RUNNING_BACKWARDS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[State.TIMEOUT_WAIT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[State.RUNNING_FORWARD.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public QSLongPressEffect() {
        MutableStateFlow MutableStateFlow = StateFlowKt.MutableStateFlow(null);
        this._postedActionType = MutableStateFlow;
        this.actionType = MutableStateFlow;
        this.snapEffect = LongPressHapticBuilder.INSTANCE.createSnapEffect();
    }

    public final void clearActionType() {
        this._postedActionType.setValue(null);
    }

    public final Flow getActionType() {
        return this.actionType;
    }

    public final int getEffectDuration() {
        return this.effectDuration;
    }

    public final void handleActionCancel() {
        int i = WhenMappings.$EnumSwitchMapping$0[this.state.ordinal()];
        if (i == 3) {
            setState(State.IDLE);
        } else {
            if (i != 4) {
                return;
            }
            this._postedActionType.setValue(ActionType.REVERSE_ANIMATOR);
            setState(State.RUNNING_BACKWARDS);
        }
    }

    public final void handleActionDown() {
        int i = WhenMappings.$EnumSwitchMapping$0[this.state.ordinal()];
        if (i == 1) {
            setState(State.TIMEOUT_WAIT);
        } else {
            if (i != 2) {
                return;
            }
            this._postedActionType.setValue(ActionType.CANCEL_ANIMATOR);
        }
    }

    public final void handleActionUp() {
        int i = WhenMappings.$EnumSwitchMapping$0[this.state.ordinal()];
        if (i == 3) {
            this._postedActionType.setValue(ActionType.CLICK);
            setState(State.IDLE);
        } else {
            if (i != 4) {
                return;
            }
            this._postedActionType.setValue(ActionType.REVERSE_ANIMATOR);
            setState(State.RUNNING_BACKWARDS);
        }
    }

    public final void handleAnimationCancel() {
        setState(State.TIMEOUT_WAIT);
    }

    public final void handleAnimationComplete() {
        if (this.state == State.RUNNING_FORWARD) {
            this._postedActionType.setValue(ActionType.LONG_PRESS);
        }
        if (this.state != State.TIMEOUT_WAIT) {
            setState(State.IDLE);
        }
    }

    public final void handleAnimationStart() {
        setState(State.RUNNING_FORWARD);
    }

    public final void handleTimeoutComplete() {
        if (this.state == State.TIMEOUT_WAIT) {
            this._postedActionType.setValue(ActionType.START_ANIMATOR);
        }
    }

    public final boolean initializeEffect(int i) {
        if (i <= 0) {
            return false;
        }
        if (i == this.effectDuration) {
            return true;
        }
        this.effectDuration = i;
        this.hasInitialized = true;
        this._postedActionType.setValue(ActionType.INITIALIZE_ANIMATOR);
        setState(State.IDLE);
        return true;
    }

    public final void setState(State state) {
        state.getClass();
        this.state = state;
    }
}
