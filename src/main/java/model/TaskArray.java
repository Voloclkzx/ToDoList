package model;

import Exceptions.NotFoundTaskException;

import java.util.Date;

public class TaskArray {
    static Task[] taskArray = new Task[100];
    private static int count = 0;

    public static void addTask(Task task) throws ArrayIndexOutOfBoundsException {
        taskArray[count] = task;
        count++;
    }
    public static void removeTask(Task task) {
        int index = 0;
        boolean flag = false;
        for (int i = 0; i < count - 1; i++) {
            if (taskArray[i].name.equals(task.name) || flag) {
                taskArray[i] = taskArray[i+1];
                flag = true;
            }
        }
        count--;

    }
    public static void toChangeTaskName(Task task, String name) {
        task.name = name;
    }
    public static void toChangeTaskDescription(Task task, String description) {
        task.description = description;
    }
    public static void toChangeTaskDate(Task task, Date date) {
        task.date = date;
    }
    public static void toChangeTaskState(Task task, States state) {
        task.state = state;
    }
    static public void showTasks() {
        for (int i = 0; i < count; i++) {
            System.out.println(taskArray[i] + ", ");
        }
    }

    static public Task findTask (String name) throws NotFoundTaskException {
        try {
            for (Task task : taskArray) {
                if (task.name.equals(name)) {
                    return task;
                }
            }
        } catch (NullPointerException e) {
            throw new NotFoundTaskException("Task is not found");

        }
        return null;
    }

    static public boolean taskInStorage(String name) {
        for (Task task : taskArray) {
            if (task.name.equals(name)) {
                return true;
            }
        }
        return false;
    }
}
