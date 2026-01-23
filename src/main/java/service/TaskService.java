package service;

import exceptions.NotFoundTaskException;
import exceptions.StorageException;
import model.Task;
import model.TaskStatus;
import storage.TaskStorage;

import java.time.LocalDateTime;

public class TaskService {
    private TaskStorage storage;

    public TaskService(TaskStorage storage) {
        this.storage = storage;
    }

    public void createTask(String name, String description, LocalDateTime dateTime) {
        storage.add(new Task(name, description, dateTime));
    }

    public void removeTask(String name) {
        storage.remove(findTask(name));
    }

    public Task[] showAllTasks() {
        return storage.showAllTasks();
    }
    public Task findTask(String name) {
        for (Task x : storage.showAllTasks()) {
            if (x == null) {
                break;
            }
            if (x.getName().equals(name)) {
                return x;
            }
        }
        throw new NotFoundTaskException("Task not found");
    }

    public void changeTaskName(Task task, String name) {
        task.changeName(name);
    }
    public void changeTaskDescription(Task task, String description) {
        task.changeDescription(description);
    }
    public void changeTaskDate(Task task, LocalDateTime dateTime) {
        task.changeDateTime(dateTime);
    }
    public void changeTaskStatus(Task task, TaskStatus status) {
        task.changeStatus(status);
    }

}
