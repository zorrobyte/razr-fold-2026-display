package androidx.compose.ui.scrollcapture;

import android.os.CancellationSignal;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;

/* JADX INFO: compiled from: ComposeScrollCaptureCallback.android.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class ComposeScrollCaptureCallback_androidKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final Job launchWithCancellationSignal(CoroutineScope coroutineScope, final CancellationSignal cancellationSignal, Function2 function2) {
        final Job jobLaunch$default = BuildersKt__Builders_commonKt.launch$default(coroutineScope, null, null, function2, 3, null);
        jobLaunch$default.invokeOnCompletion(new Function1() { // from class: androidx.compose.ui.scrollcapture.ComposeScrollCaptureCallback_androidKt.launchWithCancellationSignal.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                invoke((Throwable) obj);
                return Unit.INSTANCE;
            }

            public final void invoke(Throwable th) {
                if (th != null) {
                    cancellationSignal.cancel();
                }
            }
        });
        cancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener() { // from class: androidx.compose.ui.scrollcapture.ComposeScrollCaptureCallback_androidKt$$ExternalSyntheticLambda0
            @Override // android.os.CancellationSignal.OnCancelListener
            public final void onCancel() {
                Job.DefaultImpls.cancel$default(jobLaunch$default, null, 1, null);
            }
        });
        return jobLaunch$default;
    }
}
