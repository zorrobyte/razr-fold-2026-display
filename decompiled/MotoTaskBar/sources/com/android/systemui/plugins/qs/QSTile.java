package com.android.systemui.plugins.qs;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.metrics.LogMaker;
import android.text.TextUtils;
import com.android.internal.logging.InstanceId;
import com.android.systemui.animation.Expandable;
import com.android.systemui.plugins.annotations.Dependencies;
import com.android.systemui.plugins.annotations.DependsOn;
import com.android.systemui.plugins.annotations.ProvidesInterface;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* JADX INFO: loaded from: classes.dex */
@Dependencies({@DependsOn(target = QSIconView.class), @DependsOn(target = Callback.class), @DependsOn(target = Icon.class), @DependsOn(target = State.class)})
@ProvidesInterface(version = 5)
public interface QSTile {
    public static final int VERSION = 5;

    @ProvidesInterface(version = 1)
    public class AdapterState extends State {
        public static final int VERSION = 1;
        public boolean forceExpandIcon;
        public boolean value;

        @Override // com.android.systemui.plugins.qs.QSTile.State
        public State copy() {
            AdapterState adapterState = new AdapterState();
            copyTo(adapterState);
            return adapterState;
        }

        @Override // com.android.systemui.plugins.qs.QSTile.State
        public boolean copyTo(State state) {
            AdapterState adapterState = (AdapterState) state;
            boolean z = (!super.copyTo(state) && adapterState.value == this.value && adapterState.forceExpandIcon == this.forceExpandIcon) ? false : true;
            adapterState.value = this.value;
            adapterState.forceExpandIcon = this.forceExpandIcon;
            return z;
        }

        @Override // com.android.systemui.plugins.qs.QSTile.State
        protected StringBuilder toStringBuilder() {
            StringBuilder stringBuilder = super.toStringBuilder();
            stringBuilder.insert(stringBuilder.length() - 1, ",value=" + this.value);
            stringBuilder.insert(stringBuilder.length() + (-1), ",forceExpandIcon=" + this.forceExpandIcon);
            return stringBuilder;
        }
    }

    @ProvidesInterface(version = 1)
    public class BooleanState extends AdapterState {
        public static final int VERSION = 1;

        @Override // com.android.systemui.plugins.qs.QSTile.AdapterState, com.android.systemui.plugins.qs.QSTile.State
        public State copy() {
            BooleanState booleanState = new BooleanState();
            copyTo(booleanState);
            return booleanState;
        }
    }

    @ProvidesInterface(version = 2)
    public interface Callback {
        public static final int VERSION = 2;

        void onStateChanged(State state);
    }

    @ProvidesInterface(version = 1)
    public abstract class Icon {
        public static final int VERSION = 1;

        public abstract Drawable getDrawable(Context context);

        public Drawable getInvisibleDrawable(Context context) {
            return getDrawable(context);
        }

        public int getPadding() {
            return 0;
        }

        public int hashCode() {
            return Icon.class.hashCode();
        }

        public String toString() {
            return "Icon";
        }
    }

    @ProvidesInterface(version = 1)
    public final class SignalState extends BooleanState {
        public static final int VERSION = 1;
        public boolean activityIn;
        public boolean activityOut;
        public boolean isOverlayIconWide;
        public boolean noDefaultNetwork;
        public int overlayIconId;

        @Override // com.android.systemui.plugins.qs.QSTile.BooleanState, com.android.systemui.plugins.qs.QSTile.AdapterState, com.android.systemui.plugins.qs.QSTile.State
        public State copy() {
            SignalState signalState = new SignalState();
            copyTo(signalState);
            return signalState;
        }

        @Override // com.android.systemui.plugins.qs.QSTile.AdapterState, com.android.systemui.plugins.qs.QSTile.State
        public boolean copyTo(State state) {
            SignalState signalState = (SignalState) state;
            boolean z = signalState.activityIn;
            boolean z2 = this.activityIn;
            boolean z3 = (z == z2 && signalState.activityOut == this.activityOut && signalState.isOverlayIconWide == this.isOverlayIconWide && signalState.overlayIconId == this.overlayIconId) ? false : true;
            signalState.activityIn = z2;
            signalState.activityOut = this.activityOut;
            signalState.isOverlayIconWide = this.isOverlayIconWide;
            signalState.overlayIconId = this.overlayIconId;
            signalState.noDefaultNetwork = this.noDefaultNetwork;
            return super.copyTo(state) || z3;
        }

        @Override // com.android.systemui.plugins.qs.QSTile.AdapterState, com.android.systemui.plugins.qs.QSTile.State
        protected StringBuilder toStringBuilder() {
            StringBuilder stringBuilder = super.toStringBuilder();
            stringBuilder.insert(stringBuilder.length() - 1, ",activityIn=" + this.activityIn);
            stringBuilder.insert(stringBuilder.length() + (-1), ",activityOut=" + this.activityOut);
            stringBuilder.insert(stringBuilder.length() + (-1), ",noDefaultNetwork=" + this.noDefaultNetwork);
            return stringBuilder;
        }
    }

    @ProvidesInterface(version = 1)
    public class State {
        public static final int DEFAULT_STATE = 2;
        public static final int VERSION = 1;
        public CharSequence contentDescription;
        public boolean disabledByPolicy;
        public CharSequence dualLabelContentDescription;
        public String expandedAccessibilityClassName;
        public Icon icon;
        public Supplier iconSupplier;
        public CharSequence label;
        public CharSequence secondaryLabel;
        public Drawable sideViewCustomDrawable;
        public String spec;
        public CharSequence stateDescription;
        public int state = 2;
        public boolean dualTarget = false;
        public boolean isTransient = false;
        public boolean handlesLongClick = true;
        public boolean handlesSecondaryClick = false;

        public State copy() {
            State state = new State();
            copyTo(state);
            return state;
        }

        public boolean copyTo(State state) {
            if (state == null) {
                throw new IllegalArgumentException();
            }
            if (!state.getClass().equals(getClass())) {
                throw new IllegalArgumentException();
            }
            boolean z = (Objects.equals(state.spec, this.spec) && Objects.equals(state.icon, this.icon) && Objects.equals(state.iconSupplier, this.iconSupplier) && Objects.equals(state.label, this.label) && Objects.equals(state.secondaryLabel, this.secondaryLabel) && Objects.equals(state.contentDescription, this.contentDescription) && Objects.equals(state.stateDescription, this.stateDescription) && Objects.equals(state.dualLabelContentDescription, this.dualLabelContentDescription) && Objects.equals(state.expandedAccessibilityClassName, this.expandedAccessibilityClassName) && Boolean.valueOf(state.disabledByPolicy).equals(Boolean.valueOf(this.disabledByPolicy)) && Integer.valueOf(state.state).equals(Integer.valueOf(this.state)) && Boolean.valueOf(state.isTransient).equals(Boolean.valueOf(this.isTransient)) && Boolean.valueOf(state.dualTarget).equals(Boolean.valueOf(this.dualTarget)) && Boolean.valueOf(state.handlesLongClick).equals(Boolean.valueOf(this.handlesLongClick)) && Boolean.valueOf(state.handlesSecondaryClick).equals(Boolean.valueOf(this.handlesSecondaryClick)) && Objects.equals(state.sideViewCustomDrawable, this.sideViewCustomDrawable)) ? false : true;
            state.spec = this.spec;
            state.icon = this.icon;
            state.iconSupplier = this.iconSupplier;
            state.label = this.label;
            state.secondaryLabel = this.secondaryLabel;
            state.contentDescription = this.contentDescription;
            state.stateDescription = this.stateDescription;
            state.dualLabelContentDescription = this.dualLabelContentDescription;
            state.expandedAccessibilityClassName = this.expandedAccessibilityClassName;
            state.disabledByPolicy = this.disabledByPolicy;
            state.state = this.state;
            state.dualTarget = this.dualTarget;
            state.isTransient = this.isTransient;
            state.handlesLongClick = this.handlesLongClick;
            state.handlesSecondaryClick = this.handlesSecondaryClick;
            state.sideViewCustomDrawable = this.sideViewCustomDrawable;
            return z;
        }

        public CharSequence getSecondaryLabel(CharSequence charSequence) {
            CharSequence charSequence2 = this.secondaryLabel;
            return (TextUtils.isEmpty(charSequence2) || TextUtils.getTrimmedLength(charSequence2) == 0) ? charSequence : charSequence2;
        }

        public CharSequence getStateText(int i, Resources resources) {
            return (this.state == 0 || (this instanceof BooleanState)) ? resources.getStringArray(i)[this.state] : "";
        }

        public String toString() {
            return toStringBuilder().toString();
        }

        protected StringBuilder toStringBuilder() {
            StringBuilder sb = new StringBuilder(getClass().getSimpleName());
            sb.append('[');
            sb.append("spec=");
            sb.append(this.spec);
            sb.append(",icon=");
            sb.append(this.icon);
            sb.append(",iconSupplier=");
            sb.append(this.iconSupplier);
            sb.append(",label=");
            sb.append(this.label);
            sb.append(",secondaryLabel=");
            sb.append(this.secondaryLabel);
            sb.append(",contentDescription=");
            sb.append(this.contentDescription);
            sb.append(",stateDescription=");
            sb.append(this.stateDescription);
            sb.append(",dualLabelContentDescription=");
            sb.append(this.dualLabelContentDescription);
            sb.append(",expandedAccessibilityClassName=");
            sb.append(this.expandedAccessibilityClassName);
            sb.append(",disabledByPolicy=");
            sb.append(this.disabledByPolicy);
            sb.append(",dualTarget=");
            sb.append(this.dualTarget);
            sb.append(",isTransient=");
            sb.append(this.isTransient);
            sb.append(",handlesSecondaryClick=");
            sb.append(this.handlesSecondaryClick);
            sb.append(",state=");
            sb.append(this.state);
            sb.append(",sideViewCustomDrawable=");
            sb.append(this.sideViewCustomDrawable);
            sb.append(']');
            return sb;
        }
    }

    void addCallback(Callback callback);

    @Deprecated
    default void clearState() {
    }

    void click(Expandable expandable);

    void destroy();

    void forceRefreshStateModern();

    int getCurrentTileUser();

    default TileDetailsViewModel getDetailsViewModel() {
        return null;
    }

    default boolean getDetailsViewModel(Consumer consumer) {
        TileDetailsViewModel detailsViewModel = getDetailsViewModel();
        consumer.accept(detailsViewModel);
        return detailsViewModel != null;
    }

    InstanceId getInstanceId();

    @Deprecated
    int getMetricsCategory();

    default String getMetricsSpec() {
        return getClass().getSimpleName();
    }

    State getState();

    CharSequence getTileLabel();

    String getTileSpec();

    boolean isAvailable();

    boolean isDestroyed();

    boolean isListening();

    default boolean isSignalTile() {
        return false;
    }

    default boolean isTileReady() {
        return false;
    }

    void longClick(Expandable expandable);

    default LogMaker populate(LogMaker logMaker) {
        return logMaker;
    }

    void refreshState();

    void removeCallback(Callback callback);

    void removeCallbacks();

    void secondaryClick(Expandable expandable);

    void setDetailListening(boolean z);

    void setListening(Object obj, boolean z);

    void setTileSpec(String str);

    void userSwitch(int i);
}
