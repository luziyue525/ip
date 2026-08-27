package zsiggy;

import zsiggy.task.Task;

public class TaskList {
    private final Task[] tasks;
    private int taskCount;

    public TaskList() {
        this.tasks = new Task[100];
        this.taskCount = 0;
    }

    public void add(Task task) {
        tasks[taskCount] = task;
        taskCount++;
    }

    public Task delete(int index) {
        Task deletedTask = tasks[index];

        for (int i = index; i < taskCount - 1; i++) {
            tasks[i] = tasks[i + 1];
        }

        taskCount--;
        tasks[taskCount] = null;

        return deletedTask;
    }

    public void mark(int index) {
        tasks[index].mark();
    }

    public void unmark(int index) {
        tasks[index].unmark();
    }

    public Task get(int index) {
        return tasks[index];
    }

    public Task[] getTasks() {
        return tasks;
    }

    public int getTaskCount() {
        return taskCount;
    }

    public boolean isValidIndex(int index) {
        return index >= 0 && index < taskCount;
    }
}