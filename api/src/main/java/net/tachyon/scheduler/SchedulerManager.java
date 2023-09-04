package net.tachyon.scheduler;

import org.jetbrains.annotations.NotNull;

public non-sealed interface SchedulerManager extends Scheduler {

    @NotNull Scheduler newScheduler();

    void addShutdownTask(@NotNull Runnable runnable);

    void shutdown();

}
