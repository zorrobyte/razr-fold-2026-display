package androidx.core.view.accessibility;

import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public interface AccessibilityViewCommand {

    public abstract class CommandArguments {
    }

    public abstract class MoveAtGranularityArguments extends CommandArguments {
    }

    public abstract class MoveHtmlArguments extends CommandArguments {
    }

    public abstract class MoveWindowArguments extends CommandArguments {
    }

    public abstract class ScrollToPositionArguments extends CommandArguments {
    }

    public abstract class SetProgressArguments extends CommandArguments {
    }

    public abstract class SetSelectionArguments extends CommandArguments {
    }

    public abstract class SetTextArguments extends CommandArguments {
    }

    boolean perform(View view, CommandArguments commandArguments);
}
