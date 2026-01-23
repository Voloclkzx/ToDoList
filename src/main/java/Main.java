import exceptions.NotFoundTaskException;
import exceptions.StorageException;
import model.TaskStatus;
import model.Task;
import service.TaskService;
import storage.InMemoryTaskStorage;
import storage.TaskStorage;

import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        TaskStorage storage = new InMemoryTaskStorage();
        TaskService service = new TaskService(storage);
        System.out.println("TO DO LIST started");
        while (true) {
            printMenu();
            String input = scanner.nextLine();
            switch (input) {
                case "1":
                    createTask(service); break;
                case "2":
                    deleteTask(service); break;
                case "3":
                    editTask(service); break;
                case "4":
                    printAllTasks(service.showAllTasks()); break;
                case "0": System.out.println("Exit"); return;
                default: System.out.println("Unknown command");
            }
        }
    }
    public static void printMenu() {
        System.out.println("""
                _____________________
                1) Create task
                2) Delete task
                3) Edit task
                4) Show tasks
                0) Exit
                _____________________
                
                """);
    }

    public static void createTask(TaskService service) {
        System.out.println("Task name: ");
        String inputName = scanner.nextLine();
        System.out.println("Description: ");
        String inputDescription = scanner.nextLine();
        System.out.println("Date (YYYY MM DD HH MM) or enter \"now\" or enter \"skip\":");
        String inputDate = scanner.nextLine();
        LocalDateTime parseInputDate;
        if (inputDate.equals("skip")) {
            parseInputDate = null;
        } else if (inputDate.equals("now")) {
            parseInputDate = LocalDateTime.now();
        } else {
            parseInputDate = LocalDateTime.of(parseDate(inputDate)[0], parseDate(inputDate)[1], parseDate(inputDate)[2], parseDate(inputDate)[3], parseDate(inputDate)[4]);
        }
        try {
            service.createTask(inputName, inputDescription, parseInputDate);
            System.out.println("Task created");
        } catch (StorageException e) {
            System.out.println(e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    public static void deleteTask(TaskService service) {
        System.out.println("Select a task to delete: ");
        printAllTasks(service.showAllTasks());
        while (true) {
            try {
                String input = scanner.next();
                if (input.equals("exit")) {
                    break;
                }
                service.removeTask(input);
                break;
            } catch (NotFoundTaskException e) {
                System.out.println("Task is not found");
            }
        }
    }
    public static void editTask(TaskService service) {
        System.out.println("Select a task to edit: ");
        printAllTasks(service.showAllTasks());
        Task inputTask = null;
        String input;
        while (true) {
            try {
                input = scanner.nextLine();
                if (input.equals("exit")) {
                    break;
                }
                inputTask = service.findTask(input);
                break;
            } catch (NotFoundTaskException e) {
                System.out.println("Task is not found, try again: ");
            }
        }
        System.out.println("""
            Select a task element to edit:
            1) name
            2) description
            3) date
            4) status
            """);
        String selectChange = scanner.nextLine();
        switch (selectChange.toLowerCase()) {
            case "1": editTaskName(service, inputTask); break;
            case "name": editTaskName(service, inputTask); break;
            case "2": editTaskDescription(service, inputTask); break;
            case "description": editTaskDescription(service, inputTask); break;
            case "3": editTaskDate(service, inputTask); break;
            case "date": editTaskDate(service, inputTask); break;
            case "4": editTaskStatus(service, inputTask); break;
            case "status": editTaskStatus(service, inputTask); break;
        }
    }
    public static void printAllTasks(Task[] taskArray) {
        for (int i = 0; i < taskArray.length - 1; i++) {
            System.out.println(taskArray[i]);
            if (taskArray[i+1] == null) {
                break;
            }
        }
    }
    public static int[] parseDate(String YYYY_MM_DD_HH_MM) {
        // Разбиваем по одному или нескольким пробелам
        String[] stringDate = YYYY_MM_DD_HH_MM.trim().split("\\s+");

        // Проверяем количество элементов
        if (stringDate.length != 5) {
            throw new IllegalArgumentException(
                    "Invalid date format. Expected: YYYY MM DD HH MM (5 numbers separated by spaces)"
            );
        }

        int[] intDate = new int[5];

        // Преобразуем строки в числа
        for (int i = 0; i < 5; i++) {
            try {
                intDate[i] = Integer.parseInt(stringDate[i]);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("All elements must be integers. Found: '" + stringDate[i] + "'");
            }
        }

        // Валидация значений
        if (intDate[0] < 1900 || intDate[0] > 2100) {  // Год
            throw new IllegalArgumentException("Year must be between 1900 and 2100");
        }
        if (intDate[1] < 1 || intDate[1] > 12) {       // Месяц
            throw new IllegalArgumentException("Month must be 1-12");
        }
        if (intDate[2] < 1 || intDate[2] > 31) {       // День
            throw new IllegalArgumentException("Day must be 1-31");
        }
        if (intDate[3] < 0 || intDate[3] > 23) {       // Часы
            throw new IllegalArgumentException("Hour must be 0-23");
        }
        if (intDate[4] < 0 || intDate[4] > 59) {       // Минуты
            throw new IllegalArgumentException("Minute must be 0-59");
        }
        // Уменьшаем месяц на 1 (январь = 0)

        return intDate;
    }

    public static void editTaskName(TaskService service, Task task) {
        System.out.println("Enter a new Task name");
        String newTaskName = scanner.nextLine();
        service.changeTaskName(task, newTaskName);
        System.out.println("Name is changed successfully");
    }
    public static void editTaskDescription(TaskService service, Task task) {
        System.out.println("Enter a new Task description");
        String newTaskDescription = scanner.nextLine();
        service.changeTaskDescription(task, newTaskDescription);
        System.out.println("Description is changed successfully");
    }
    public static void editTaskDate(TaskService service, Task task) {
        System.out.println("Enter a new Task date (YYYY MM DD HH MM) or enter \"now\": ");
        String newTaskDate = scanner.nextLine();

        LocalDateTime dateTime;

        if (newTaskDate.equalsIgnoreCase("now")) {
            dateTime = LocalDateTime.now();
        } else {
            try {
                int[] dateParts = parseDate(newTaskDate); // Парсим ОДИН раз!
                dateTime = LocalDateTime.of(
                        dateParts[0], // год
                        dateParts[1], // месяц
                        dateParts[2], // день
                        dateParts[3], // час
                        dateParts[4]  // минута
                );
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
                System.out.println("Date not changed. Please try again.");
                return;
            }
        }

        service.changeTaskDate(task, dateTime);
        System.out.println("Date is changed successfully");
    }
    public static void editTaskStatus(TaskService service, Task task) {
        System.out.println("Enter a new Task status: TODO, INPROGRESS, COMPLETE");
        String newTaskStatus;
        TaskStatus newStatus;
        while (true) {
            try {
                newTaskStatus = scanner.nextLine();
                newStatus = TaskStatus.valueOf(newTaskStatus.toUpperCase());
                break;
            } catch (Exception e) {
                System.out.println("Wrong status");
            }
        }
        service.changeTaskStatus(task, newStatus);
        System.out.println("Status is changed successfully");
    }
}
