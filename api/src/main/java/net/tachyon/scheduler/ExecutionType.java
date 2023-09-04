package net.tachyon.scheduler;

/**
 * Specifies the execution type of task.
 * If {@link ExecutionType#SYNC} is specified, the task will be executed on the same thread as the scheduler.
 * If {@link ExecutionType#ASYNC} is specified, the task will be executed on a scheduled thread pool.
 */
public enum ExecutionType {

    /**
     * The task will be executed on the same thread as the scheduler.
     */
    SYNC,

    /**
     * The task will be executed on a scheduled thread pool.
     */
    ASYNC;

}
