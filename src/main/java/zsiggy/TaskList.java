package zsiggy;

import zsiggy.task.Task;

/**
 * Stores and manages the tasks in Zsiggy.
 */
public class TaskList {
    private final Task[] tasks;
    private int taskCount;

    /**
     * Creates an empty task list with space for up to 100 tasks.
     */
    public TaskList() {
        this.tasks = new Task[100];
        this.taskCount = 0;
    }

    /**
     * Adds a task to the task list.
     *
     * @param task the task to add
     */
    public void add(Task task) {
        tasks[taskCount] = task;
        taskCount++;
    }

    /**
     * Deletes the task at the specified index.
     *
     * @param index the zero-based index of the task to delete
     * @return the task that was deleted
     */
    public Task delete(int index) {
        Task deletedTask = tasks[index];

        for (int i = index; i < taskCount - 1; i++) {
            tasks[i] = tasks[i + 1];
        }

        taskCount--;
        tasks[taskCount] = null;

        return deletedTask;
    }

    /**
     * Marks the task at the specified index as done.
     *
     * @param index the zero-based index of the task
     */
    public void mark(int index) {
        tasks[index].mark();
    }

    /**
     * Marks the task at the specified index as not done.
     *
     * @param index the zero-based index of the task
     */
    public void unmark(int index) {
        tasks[index].unmark();
    }

    /**
     * Returns the task at the specified index.
     *
     * @param index the zero-based index of the task
     * @return the task at the given index
     */
    public Task get(int index) {
        return tasks[index];
    }

    /**
     * Returns the array containing the tasks.
     *
     * @return the task array
     */
    public Task[] getTasks() {
        return tasks;
    }

    /**
     * Returns the number of tasks currently stored.
     *
     * @return the number of tasks
     */
    public int getTaskCount() {
        return taskCount;
    }

    /**
     * Checks whether the given index refers to an existing task.
     *
     * @param index the zero-based index to check
     * @return true if the index is valid, false otherwise
     */
    public boolean isValidIndex(int index) {
        return index >= 0 && index < taskCount;
    }
}