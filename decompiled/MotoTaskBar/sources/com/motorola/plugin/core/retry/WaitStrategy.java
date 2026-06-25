package com.motorola.plugin.core.retry;

import java.util.concurrent.TimeUnit;
import kotlin.jvm.functions.Function1;
import kotlin.math.MathKt;
import kotlin.random.Random;

/* JADX INFO: compiled from: WaitStrategy.kt */
/* JADX INFO: loaded from: classes2.dex */
public interface WaitStrategy {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* JADX INFO: compiled from: WaitStrategy.kt */
    public final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }

        public static /* synthetic */ WaitStrategy exponentialWait$default(Companion companion, long j, TimeUnit timeUnit, long j2, int i, Object obj) {
            if ((i & 4) != 0) {
                j2 = 1;
            }
            return companion.exponentialWait(j, timeUnit, j2);
        }

        public static /* synthetic */ WaitStrategy fibonacciWait$default(Companion companion, long j, TimeUnit timeUnit, long j2, int i, Object obj) {
            if ((i & 4) != 0) {
                j2 = 1;
            }
            return companion.fibonacciWait(j, timeUnit, j2);
        }

        public static /* synthetic */ WaitStrategy randomWait$default(Companion companion, long j, TimeUnit timeUnit, long j2, TimeUnit timeUnit2, int i, Object obj) {
            if ((i & 4) != 0) {
                j2 = j;
            }
            if ((i & 8) != 0) {
                timeUnit2 = timeUnit;
            }
            return companion.randomWait(j, timeUnit, j2, timeUnit2);
        }

        public final WaitStrategy compositeJoin(WaitStrategy... waitStrategyArr) {
            waitStrategyArr.getClass();
            WaitStrategy waitStrategyNoWait = noWait();
            int length = waitStrategyArr.length;
            int i = 0;
            while (i < length) {
                WaitStrategy waitStrategy = waitStrategyArr[i];
                i++;
                waitStrategyNoWait = WaitStrategyKt.plus(waitStrategyNoWait, waitStrategy);
            }
            return waitStrategyNoWait;
        }

        public final WaitStrategy exceptionWait(final Class cls, final Function1 function1) {
            cls.getClass();
            function1.getClass();
            return new WaitStrategy() { // from class: com.motorola.plugin.core.retry.WaitStrategy$Companion$exceptionWait$1
                @Override // com.motorola.plugin.core.retry.WaitStrategy
                public long computeSleepTime(Attempt attempt) {
                    attempt.getClass();
                    if (!attempt.isException()) {
                        return 0L;
                    }
                    Throwable exceptionCause = attempt.getExceptionCause();
                    exceptionCause.getClass();
                    if (cls.isAssignableFrom(exceptionCause.getClass())) {
                        return ((Number) function1.invoke(exceptionCause)).longValue();
                    }
                    return 0L;
                }
            };
        }

        public final WaitStrategy exponentialWait(final long j, final TimeUnit timeUnit, final long j2) {
            timeUnit.getClass();
            return new WaitStrategy(timeUnit, j, j2) { // from class: com.motorola.plugin.core.retry.WaitStrategy$Companion$exponentialWait$1
                final /* synthetic */ long $maximumTime;
                final /* synthetic */ TimeUnit $maximumTimeTimeUnit;
                final /* synthetic */ long $multiplier;
                private final long maxTimeInMillis;

                {
                    this.$maximumTimeTimeUnit = timeUnit;
                    this.$maximumTime = j;
                    this.$multiplier = j2;
                    this.maxTimeInMillis = timeUnit.toMillis(j);
                }

                @Override // com.motorola.plugin.core.retry.WaitStrategy
                public long computeSleepTime(Attempt attempt) {
                    attempt.getClass();
                    long jRoundToLong = MathKt.roundToLong(this.$multiplier * Math.pow(2.0d, attempt.getAttemptNumber()));
                    long j3 = this.maxTimeInMillis;
                    if (jRoundToLong > j3) {
                        jRoundToLong = j3;
                    }
                    if (jRoundToLong >= 0) {
                        return jRoundToLong;
                    }
                    return 0L;
                }

                public final long getMaxTimeInMillis() {
                    return this.maxTimeInMillis;
                }
            };
        }

        public final WaitStrategy fibonacciWait(final long j, final TimeUnit timeUnit, final long j2) {
            timeUnit.getClass();
            return new WaitStrategy(timeUnit, j, j2) { // from class: com.motorola.plugin.core.retry.WaitStrategy$Companion$fibonacciWait$1
                final /* synthetic */ long $maximumTime;
                final /* synthetic */ TimeUnit $maximumTimeTimeUnit;
                final /* synthetic */ long $multiplier;
                private final long maxTimeInMillis;

                {
                    this.$maximumTimeTimeUnit = timeUnit;
                    this.$maximumTime = j;
                    this.$multiplier = j2;
                    this.maxTimeInMillis = timeUnit.toMillis(j);
                }

                private final long fib(long j3) {
                    if (j3 == 0) {
                        return 0L;
                    }
                    if (j3 == 1) {
                        return 1L;
                    }
                    if (2 > j3) {
                        return 0L;
                    }
                    long j4 = 2;
                    long j5 = 0;
                    long j6 = 1;
                    while (true) {
                        long j7 = j4 + 1;
                        long j8 = j5 + j6;
                        if (j4 == j3) {
                            return j8;
                        }
                        j5 = j6;
                        j6 = j8;
                        j4 = j7;
                    }
                }

                @Override // com.motorola.plugin.core.retry.WaitStrategy
                public long computeSleepTime(Attempt attempt) {
                    attempt.getClass();
                    long jFib = this.$multiplier * fib(attempt.getAttemptNumber());
                    long j3 = this.maxTimeInMillis;
                    if (jFib > j3 || jFib < 0) {
                        jFib = j3;
                    }
                    if (jFib >= 0) {
                        return jFib;
                    }
                    return 0L;
                }

                public final long getMaxTimeInMillis() {
                    return this.maxTimeInMillis;
                }
            };
        }

        public final WaitStrategy fixedWait(final long j, final TimeUnit timeUnit) {
            timeUnit.getClass();
            return new WaitStrategy(timeUnit, j) { // from class: com.motorola.plugin.core.retry.WaitStrategy$Companion$fixedWait$1
                final /* synthetic */ long $duration;
                final /* synthetic */ TimeUnit $timeUnit;
                private final long fixedTimeInMillis;

                {
                    this.$timeUnit = timeUnit;
                    this.$duration = j;
                    this.fixedTimeInMillis = timeUnit.toMillis(j);
                }

                @Override // com.motorola.plugin.core.retry.WaitStrategy
                public long computeSleepTime(Attempt attempt) {
                    attempt.getClass();
                    return this.fixedTimeInMillis;
                }

                public final long getFixedTimeInMillis() {
                    return this.fixedTimeInMillis;
                }
            };
        }

        public final WaitStrategy incrementingWait(final long j, final TimeUnit timeUnit, final long j2, final TimeUnit timeUnit2) {
            timeUnit.getClass();
            timeUnit2.getClass();
            return new WaitStrategy(timeUnit, j, timeUnit2, j2) { // from class: com.motorola.plugin.core.retry.WaitStrategy$Companion$incrementingWait$1
                final /* synthetic */ long $increment;
                final /* synthetic */ TimeUnit $incrementTimeUnit;
                final /* synthetic */ long $initialTime;
                final /* synthetic */ TimeUnit $initialTimeUnit;
                private final long incrementTimeInMillis;
                private final long initialTimeInMillis;

                {
                    this.$initialTimeUnit = timeUnit;
                    this.$initialTime = j;
                    this.$incrementTimeUnit = timeUnit2;
                    this.$increment = j2;
                    this.initialTimeInMillis = timeUnit.toMillis(j);
                    this.incrementTimeInMillis = timeUnit2.toMillis(j2);
                }

                @Override // com.motorola.plugin.core.retry.WaitStrategy
                public long computeSleepTime(Attempt attempt) {
                    attempt.getClass();
                    long attemptNumber = this.initialTimeInMillis + (this.incrementTimeInMillis * (attempt.getAttemptNumber() - 1));
                    if (attemptNumber >= 0) {
                        return attemptNumber;
                    }
                    return 0L;
                }

                public final long getIncrementTimeInMillis() {
                    return this.incrementTimeInMillis;
                }

                public final long getInitialTimeInMillis() {
                    return this.initialTimeInMillis;
                }
            };
        }

        public final WaitStrategy noWait() {
            return new WaitStrategy() { // from class: com.motorola.plugin.core.retry.WaitStrategy$Companion$noWait$1
                @Override // com.motorola.plugin.core.retry.WaitStrategy
                public long computeSleepTime(Attempt attempt) {
                    attempt.getClass();
                    return 0L;
                }
            };
        }

        public final WaitStrategy randomWait(final long j, final TimeUnit timeUnit, final long j2, final TimeUnit timeUnit2) {
            timeUnit.getClass();
            timeUnit2.getClass();
            return new WaitStrategy(timeUnit, j, timeUnit2, j2) { // from class: com.motorola.plugin.core.retry.WaitStrategy$Companion$randomWait$1
                final /* synthetic */ long $maxDuration;
                final /* synthetic */ TimeUnit $maxTimeUnit;
                final /* synthetic */ long $minDuration;
                final /* synthetic */ TimeUnit $minTimeUnit;
                private final long maxTimeInMillis;
                private final long minTimeInMillis;

                {
                    this.$minTimeUnit = timeUnit;
                    this.$minDuration = j;
                    this.$maxTimeUnit = timeUnit2;
                    this.$maxDuration = j2;
                    this.minTimeInMillis = timeUnit.toMillis(j);
                    this.maxTimeInMillis = timeUnit2.toMillis(j2);
                }

                @Override // com.motorola.plugin.core.retry.WaitStrategy
                public long computeSleepTime(Attempt attempt) {
                    attempt.getClass();
                    long jAbs = Math.abs(Random.Default.nextLong());
                    long j3 = this.maxTimeInMillis;
                    long j4 = this.minTimeInMillis;
                    return (jAbs % (j3 - j4)) + j4;
                }

                public final long getMaxTimeInMillis() {
                    return this.maxTimeInMillis;
                }

                public final long getMinTimeInMillis() {
                    return this.minTimeInMillis;
                }
            };
        }
    }

    long computeSleepTime(Attempt attempt);
}
