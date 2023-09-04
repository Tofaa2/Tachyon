package net.tachyon.scheduler;

import org.jetbrains.annotations.NotNull;

public interface Schedulable {

    @NotNull Scheduler getScheduler();

}