package storage;

import exceptions.StorageException;
import model.Task;

public interface TaskStorage {
    void add(Task task) throws StorageException;
    void remove(Task task);
    Task[] showAllTasks();
}
