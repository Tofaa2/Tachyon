package net.tachyon.scheduler;

import org.jctools.queues.MpmcUnboundedXaddArrayQueue;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class SchedulerManagerImpl implements SchedulerManager{

    private final Scheduler scheduler = new SchedulerImpl();
    private final MpmcUnboundedXaddArrayQueue<Runnable> shutdownTasks = new MpmcUnboundedXaddArrayQueue<>(1024);

    @Override
    public void process() {
        this.scheduler.process();
    }

    @Override
    public @NotNull Scheduler newScheduler() {
        return new SchedulerImpl();
    }

    @Override
    public void processTick() {
        this.scheduler.processTick();
    }

    @Override
    public @NotNull Task submitTask(@NotNull Supplier<TaskSchedule> task, @NotNull ExecutionType executionType) {
        return scheduler.submitTask(task, executionType);
    }

    @Override
    public void addShutdownTask(@NotNull Runnable runnable) {
        this.shutdownTasks.relaxedOffer(runnable);
    }

    @Override
    public void shutdown() {
        this.shutdownTasks.drain(Runnable::run);
    }
}
