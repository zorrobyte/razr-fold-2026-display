package com.motorola.laptoppanel.service;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: PanelUiState.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class PanelUiState {

    /* JADX INFO: compiled from: PanelUiState.kt */
    public final class Hidden extends PanelUiState {
        public static final Hidden INSTANCE = new Hidden();

        private Hidden() {
            super(null);
        }

        public String toString() {
            return "Hidden";
        }
    }

    /* JADX INFO: compiled from: PanelUiState.kt */
    public final class IconVisible extends PanelUiState implements TopAppDataProvider {
        private final String pkg;
        private final int taskId;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public IconVisible(String str, int i) {
            super(null);
            str.getClass();
            this.pkg = str;
            this.taskId = i;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof IconVisible)) {
                return false;
            }
            IconVisible iconVisible = (IconVisible) obj;
            return Intrinsics.areEqual(this.pkg, iconVisible.pkg) && this.taskId == iconVisible.taskId;
        }

        @Override // com.motorola.laptoppanel.service.TopAppDataProvider
        public String getPkg() {
            return this.pkg;
        }

        @Override // com.motorola.laptoppanel.service.TopAppDataProvider
        public int getTaskId() {
            return this.taskId;
        }

        public int hashCode() {
            return (this.pkg.hashCode() * 31) + Integer.hashCode(this.taskId);
        }

        public String toString() {
            return "IconVisible(pkg=" + this.pkg + ", taskId=" + this.taskId + ")";
        }
    }

    /* JADX INFO: compiled from: PanelUiState.kt */
    public final class LaunchingDelayed extends PanelUiState implements TopAppDataProvider {
        private final String pkg;
        private final int taskId;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public LaunchingDelayed(String str, int i) {
            super(null);
            str.getClass();
            this.pkg = str;
            this.taskId = i;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof LaunchingDelayed)) {
                return false;
            }
            LaunchingDelayed launchingDelayed = (LaunchingDelayed) obj;
            return Intrinsics.areEqual(this.pkg, launchingDelayed.pkg) && this.taskId == launchingDelayed.taskId;
        }

        @Override // com.motorola.laptoppanel.service.TopAppDataProvider
        public String getPkg() {
            return this.pkg;
        }

        @Override // com.motorola.laptoppanel.service.TopAppDataProvider
        public int getTaskId() {
            return this.taskId;
        }

        public int hashCode() {
            return (this.pkg.hashCode() * 31) + Integer.hashCode(this.taskId);
        }

        public String toString() {
            return "LaunchingDelayed(pkg=" + this.pkg + ", taskId=" + this.taskId + ")";
        }
    }

    /* JADX INFO: compiled from: PanelUiState.kt */
    public interface PanelActive {
        String getPkg();

        int getTaskId();
    }

    /* JADX INFO: compiled from: PanelUiState.kt */
    public final class PanelPaused extends PanelUiState implements PanelActive, TopAppDataProvider {
        private final String pkg;
        private final int taskId;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public PanelPaused(String str, int i) {
            super(null);
            str.getClass();
            this.pkg = str;
            this.taskId = i;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof PanelPaused)) {
                return false;
            }
            PanelPaused panelPaused = (PanelPaused) obj;
            return Intrinsics.areEqual(this.pkg, panelPaused.pkg) && this.taskId == panelPaused.taskId;
        }

        @Override // com.motorola.laptoppanel.service.PanelUiState.PanelActive, com.motorola.laptoppanel.service.TopAppDataProvider
        public String getPkg() {
            return this.pkg;
        }

        @Override // com.motorola.laptoppanel.service.PanelUiState.PanelActive, com.motorola.laptoppanel.service.TopAppDataProvider
        public int getTaskId() {
            return this.taskId;
        }

        public int hashCode() {
            return (this.pkg.hashCode() * 31) + Integer.hashCode(this.taskId);
        }

        public String toString() {
            return "PanelPaused(pkg=" + this.pkg + ", taskId=" + this.taskId + ")";
        }
    }

    /* JADX INFO: compiled from: PanelUiState.kt */
    public final class PanelVisible extends PanelUiState implements PanelActive, TopAppDataProvider {
        private final String pkg;
        private final int taskId;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public PanelVisible(String str, int i) {
            super(null);
            str.getClass();
            this.pkg = str;
            this.taskId = i;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof PanelVisible)) {
                return false;
            }
            PanelVisible panelVisible = (PanelVisible) obj;
            return Intrinsics.areEqual(this.pkg, panelVisible.pkg) && this.taskId == panelVisible.taskId;
        }

        @Override // com.motorola.laptoppanel.service.PanelUiState.PanelActive, com.motorola.laptoppanel.service.TopAppDataProvider
        public String getPkg() {
            return this.pkg;
        }

        @Override // com.motorola.laptoppanel.service.PanelUiState.PanelActive, com.motorola.laptoppanel.service.TopAppDataProvider
        public int getTaskId() {
            return this.taskId;
        }

        public int hashCode() {
            return (this.pkg.hashCode() * 31) + Integer.hashCode(this.taskId);
        }

        public String toString() {
            return "PanelVisible(pkg=" + this.pkg + ", taskId=" + this.taskId + ")";
        }
    }

    private PanelUiState() {
    }

    public /* synthetic */ PanelUiState(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }
}
