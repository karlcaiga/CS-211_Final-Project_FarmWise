import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class FarmWise {
    private Scanner scanner;
    private List<Equipment> equipmentList;
    private List<Tasks> taskList;
    private Receipt receiptGenerator;
    private int equipmentCounter;
    private int taskCounter;
    private SimpleDateFormat dateFormat;

    // ======= COLORS =======
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String CYAN = "\u001B[36m";
    private static final String YELLOW = "\u001B[33m";
    private static final String MAGENTA = "\u001B[35m";
    private static final String BRIGHT = "\u001B[1m";

    // ======= EQUIPMENT TYPES =======
    private static final String[] EQUIPMENT_TYPES = {
        "Tractor", "Harvester", "Plow", "Seeder", "Irrigation System",
        "Sprayer", "Cultivator", "Loader", "Mower", "Other"
    };

    // ======= PRIORITY LEVELS =======
    private static final String[] PRIORITY_LEVELS = {"HIGH", "MEDIUM", "LOW"};

    public FarmWise() {
        scanner = new Scanner(System.in);
        equipmentList = new ArrayList<>();
        taskList = new ArrayList<>();
        receiptGenerator = new Receipt();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        equipmentCounter = 1;
        taskCounter = 1;
    }

    public void run() {
        displayWelcome();

        while (true) {
            displayMainMenu();
            int choice = getIntInput("Choose an option: ");

            switch (choice) {
                case 1: manageEquipment(); break;
                case 2: manageTasks(); break;
                case 3: scheduleMaintenance(); break;
                case 4: viewDataReports(); break;
                case 5: showSessionSummary(); break;
                case 6: exitSystem(); return;
                default: printError("Invalid choice. Please try again.");
            }
        }
    }

    private void displayWelcome() {
        System.out.println(GREEN + BRIGHT +
                "\n" + "=".repeat(50));
        System.out.println("              FARMWISE SYSTEM                 ");
        System.out.println("        Smart Farm Management Platform       ");
        System.out.println("=".repeat(50) + RESET + "\n");
    }

    private void displayMainMenu() {
        System.out.println(MAGENTA + BRIGHT + "\n" + "=".repeat(50) + "\n" + RESET);
        System.out.println(CYAN + BRIGHT + "                  MAIN MENU                  " + RESET);
        System.out.println(MAGENTA + BRIGHT + "=".repeat(50) + RESET);
        System.out.println(GREEN + "  1. " + CYAN + "Manage Equipment" + RESET);
        System.out.println(GREEN + "  2. " + YELLOW + "Manage Tasks" + RESET);
        System.out.println(GREEN + "  3. " + GREEN + "Schedule Maintenance" + RESET);
        System.out.println(GREEN + "  4. " + MAGENTA + "View Data and Reports" + RESET);
        System.out.println(GREEN + "  5. " + CYAN + "Session Summary" + RESET);
        System.out.println(GREEN + "  6. " + RED + "Exit System" + RESET);
        System.out.println(MAGENTA + BRIGHT + "=".repeat(50) + RESET);
    }

    // ======= MANAGE EQUIPMENT =======
    private void manageEquipment() {
        while (true) {
            System.out.println(CYAN + BRIGHT + "\n" + "=".repeat(50) + RESET);
            System.out.println(CYAN + BRIGHT + "             MANAGE EQUIPMENT             " + RESET);
            System.out.println(CYAN + BRIGHT + "=".repeat(50) + RESET);
            System.out.println("1. View All Equipment");
            System.out.println("2. Add New Equipment");
            System.out.println("3. Update Equipment");
            System.out.println("4. Delete Equipment");
            System.out.println("5. Mark Equipment for Maintenance");
            System.out.println("6. Perform Maintenance");
            System.out.println("7. Back to Main Menu");

            int choice = getIntInput("\nChoose an option: ");
            switch (choice) {
                case 1: viewAllEquipment(); break;
                case 2: addNewEquipment(); break;
                case 3: updateEquipment(); break;
                case 4: deleteEquipment(); break;
                case 5: markEquipmentMaintenance(); break;
                case 6: performMaintenance(); break;
                case 7: return;
                default: printError("Invalid choice.");
            }
        }
    }

    private void viewAllEquipment() {
        System.out.println(YELLOW + BRIGHT + "\n" + "=".repeat(50) + RESET);
        System.out.println(YELLOW + BRIGHT + "             ALL EQUIPMENT             " + RESET);
        System.out.println(YELLOW + BRIGHT + "=".repeat(50) + RESET);
        
        if (equipmentList.isEmpty()) {
            printInfo("No equipment found.");
            return;
        }
        
        for (int i = 0; i < equipmentList.size(); i++) {
            System.out.printf("%d. %s\n", (i + 1), equipmentList.get(i));
        }
    }

    private void addNewEquipment() {
        System.out.println(GREEN + BRIGHT + "\n" + "=".repeat(50) + RESET);
        System.out.println(GREEN + BRIGHT + "           ADD NEW EQUIPMENT           " + RESET);
        System.out.println(GREEN + BRIGHT + "=".repeat(50) + RESET);
        
        String id = "E" + String.format("%03d", equipmentCounter++);
        System.out.print("Enter equipment name: ");
        String name = scanner.nextLine();
        
        // Equipment type selection menu
        System.out.println("\nSelect equipment type:");
        for (int i = 0; i < EQUIPMENT_TYPES.length; i++) {
            System.out.printf("%d. %s\n", (i + 1), EQUIPMENT_TYPES[i]);
        }
        
        int typeChoice = getIntInput("\nChoose type (1-" + EQUIPMENT_TYPES.length + "): ");
        String type;
        if (typeChoice >= 1 && typeChoice <= EQUIPMENT_TYPES.length) {
            type = EQUIPMENT_TYPES[typeChoice - 1];
        } else {
            printError("Invalid choice. Defaulting to 'Other'.");
            type = "Other";
        }

        equipmentList.add(new Equipment(id, name, type));
        printSuccess("Equipment added successfully! ID: " + id);
    }

    // NEW: Update equipment
    private void updateEquipment() {
        viewAllEquipment();
        if (equipmentList.isEmpty()) return;

        int index = getIntInput("\nSelect equipment to update (0 to cancel): ") - 1;
        if (index == -1) return;
        
        if (index >= 0 && index < equipmentList.size()) {
            Equipment eq = equipmentList.get(index);
            
            System.out.println("\nCurrent details:");
            System.out.println(eq);
            
            System.out.print("\nEnter new name (press Enter to keep current): ");
            String newName = scanner.nextLine();
            
            System.out.println("\nSelect new type (press 0 to keep current):");
            for (int i = 0; i < EQUIPMENT_TYPES.length; i++) {
                System.out.printf("%d. %s\n", (i + 1), EQUIPMENT_TYPES[i]);
            }
            
            int typeChoice = getIntInput("\nChoose type (0 to skip): ");
            String newType = null;
            if (typeChoice > 0 && typeChoice <= EQUIPMENT_TYPES.length) {
                newType = EQUIPMENT_TYPES[typeChoice - 1];
            }
            
            eq.updateEquipment(newName, newType);
            printSuccess("Equipment updated successfully!");
        } else {
            printError("Invalid selection.");
        }
    }

    // NEW: Delete equipment
    private void deleteEquipment() {
        viewAllEquipment();
        if (equipmentList.isEmpty()) return;

        int index = getIntInput("\nSelect equipment to delete (0 to cancel): ") - 1;
        if (index == -1) return;
        
        if (index >= 0 && index < equipmentList.size()) {
            Equipment eq = equipmentList.get(index);
            
            // Check if equipment is assigned to any task
            boolean isAssigned = false;
            for (Tasks task : taskList) {
                if (eq.getId().equals(task.getAssignedEq())) {
                    isAssigned = true;
                    break;
                }
            }
            
            if (isAssigned) {
                printError("Cannot delete equipment. It is assigned to a task.");
                return;
            }
            
            System.out.println("\nAre you sure you want to delete this equipment?");
            System.out.println(eq);
            System.out.print("Type 'YES' to confirm: ");
            String confirmation = scanner.nextLine();
            
            if (confirmation.equalsIgnoreCase("YES")) {
                equipmentList.remove(index);
                printSuccess("Equipment deleted successfully!");
            } else {
                printInfo("Deletion cancelled.");
            }
        } else {
            printError("Invalid selection.");
        }
    }

    private void markEquipmentMaintenance() {
        viewAllEquipment();
        if (equipmentList.isEmpty()) return;

        int index = getIntInput("\nSelect equipment to mark for maintenance (0 to cancel): ") - 1;
        if (index == -1) return;
        
        if (index >= 0 && index < equipmentList.size()) {
            equipmentList.get(index).markMaintenanceNeeded();
            printSuccess("Maintenance marked for: " + equipmentList.get(index).getName());
        } else {
            printError("Invalid selection.");
        }
    }

    private void performMaintenance() {
        List<Equipment> needsMaintenance = new ArrayList<>();
        for (Equipment eq : equipmentList) {
            if (eq.eqNeedsMaintenance()) needsMaintenance.add(eq);
        }

        if (needsMaintenance.isEmpty()) {
            printInfo("No equipment needs maintenance.");
            return;
        }

        System.out.println(YELLOW + BRIGHT + "\n" + "=".repeat(50) + RESET);
        System.out.println(YELLOW + BRIGHT + "   EQUIPMENT NEEDING MAINTENANCE   " + RESET);
        System.out.println(YELLOW + BRIGHT + "=".repeat(50) + RESET);
        
        for (int i = 0; i < needsMaintenance.size(); i++) {
            System.out.printf("%d. %s\n", (i + 1), needsMaintenance.get(i).getName());
        }

        int index = getIntInput("\nSelect equipment to maintain (0 to cancel): ") - 1;
        if (index == -1) return;
        
        if (index >= 0 && index < needsMaintenance.size()) {
            needsMaintenance.get(index).performEqMaintenance();
            printSuccess("Maintenance completed for: " + needsMaintenance.get(index).getName());
        } else {
            printError("Invalid selection.");
        }
    }

    // ======= MANAGE TASKS =======
    private void manageTasks() {
        while (true) {
            System.out.println(YELLOW + BRIGHT + "\n" + "=".repeat(50) + RESET);
            System.out.println(YELLOW + BRIGHT + "               MANAGE TASKS               " + RESET);
            System.out.println(YELLOW + BRIGHT + "=".repeat(50) + RESET);
            System.out.println("1. View All Tasks");
            System.out.println("2. Create New Task");
            System.out.println("3. Update Task");
            System.out.println("4. Delete Task");
            System.out.println("5. Assign Equipment to Task");
            System.out.println("6. Mark Task as Completed");
            System.out.println("7. Back to Main Menu");

            int choice = getIntInput("\nChoose an option: ");
            switch (choice) {
                case 1: viewAllTasks(); break;
                case 2: createNewTask(); break;
                case 3: updateTask(); break;
                case 4: deleteTask(); break;
                case 5: assignEquipmentToTask(); break;
                case 6: markTaskCompleted(); break;
                case 7: return;
                default: printError("Invalid choice.");
            }
        }
    }

    private void viewAllTasks() {
        System.out.println(YELLOW + BRIGHT + "\n" + "=".repeat(50) + RESET);
        System.out.println(YELLOW + BRIGHT + "               ALL TASKS               " + RESET);
        System.out.println(YELLOW + BRIGHT + "=".repeat(50) + RESET);
        
        if (taskList.isEmpty()) {
            printInfo("No tasks found.");
            return;
        }
        
        for (int i = 0; i < taskList.size(); i++) {
            System.out.printf("%d. %s\n", (i + 1), taskList.get(i));
        }
    }

    private void createNewTask() {
        System.out.println(GREEN + BRIGHT + "\n" + "=".repeat(50) + RESET);
        System.out.println(GREEN + BRIGHT + "            CREATE NEW TASK            " + RESET);
        System.out.println(GREEN + BRIGHT + "=".repeat(50) + RESET);
        
        String id = "T" + String.format("%03d", taskCounter++);
        System.out.print("Enter task name: ");
        String name = scanner.nextLine();
        
        // Priority selection menu
        System.out.println("\nSelect priority level:");
        for (int i = 0; i < PRIORITY_LEVELS.length; i++) {
            System.out.printf("%d. %s\n", (i + 1), PRIORITY_LEVELS[i]);
        }
        
        int priorityChoice = getIntInput("\nChoose priority (1-" + PRIORITY_LEVELS.length + "): ");
        String priority;
        if (priorityChoice >= 1 && priorityChoice <= PRIORITY_LEVELS.length) {
            priority = PRIORITY_LEVELS[priorityChoice - 1];
        } else {
            printError("Invalid choice. Defaulting to 'MEDIUM'.");
            priority = "MEDIUM";
        }
        
        // Date input with validation
        Date dueDate = null;
        while (dueDate == null) {
            System.out.print("\nEnter due date (YYYY-MM-DD, or press Enter for 7 days from now): ");
            String dateInput = scanner.nextLine();
            
            if (dateInput.trim().isEmpty()) {
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, 7);
                dueDate = cal.getTime();
            } else {
                try {
                    dueDate = dateFormat.parse(dateInput);
                    // Check if date is in the past
                    if (dueDate.before(new Date())) {
                        printError("Due date cannot be in the past.");
                        dueDate = null;
                    }
                } catch (ParseException e) {
                    printError("Invalid date format. Please use YYYY-MM-DD.");
                }
            }
        }

        Tasks task = new Tasks(id, name, dueDate, priority);
        taskList.add(task);
        printSuccess("Task created successfully! ID: " + id);
    }

    // NEW: Update task
    private void updateTask() {
        viewAllTasks();
        if (taskList.isEmpty()) return;

        int index = getIntInput("\nSelect task to update (0 to cancel): ") - 1;
        if (index == -1) return;
        
        if (index >= 0 && index < taskList.size()) {
            Tasks task = taskList.get(index);
            
            if (task.isCompleted()) {
                printError("Cannot update completed tasks.");
                return;
            }
            
            System.out.println("\nCurrent details:");
            System.out.println(task);
            
            System.out.print("\nEnter new name (press Enter to keep current): ");
            String newName = scanner.nextLine();
            
            System.out.println("\nSelect new priority (press 0 to keep current):");
            for (int i = 0; i < PRIORITY_LEVELS.length; i++) {
                System.out.printf("%d. %s\n", (i + 1), PRIORITY_LEVELS[i]);
            }
            
            int priorityChoice = getIntInput("\nChoose priority (0 to skip): ");
            String newPriority = null;
            if (priorityChoice > 0 && priorityChoice <= PRIORITY_LEVELS.length) {
                newPriority = PRIORITY_LEVELS[priorityChoice - 1];
            }
            
            // Date update
            Date newDueDate = null;
            System.out.print("\nEnter new due date (YYYY-MM-DD, press Enter to skip): ");
            String dateInput = scanner.nextLine();
            
            if (!dateInput.trim().isEmpty()) {
                try {
                    newDueDate = dateFormat.parse(dateInput);
                    if (newDueDate.before(new Date())) {
                        printError("Due date cannot be in the past.");
                        newDueDate = null;
                    }
                } catch (ParseException e) {
                    printError("Invalid date format. Keeping current due date.");
                }
            }
            
            task.updateTask(newName, newPriority, newDueDate);
            printSuccess("Task updated successfully!");
        } else {
            printError("Invalid selection.");
        }
    }

    // NEW: Delete task
    private void deleteTask() {
        viewAllTasks();
        if (taskList.isEmpty()) return;

        int index = getIntInput("\nSelect task to delete (0 to cancel): ") - 1;
        if (index == -1) return;
        
        if (index >= 0 && index < taskList.size()) {
            Tasks task = taskList.get(index);
            
            System.out.println("\nAre you sure you want to delete this task?");
            System.out.println(task);
            System.out.print("Type 'YES' to confirm: ");
            String confirmation = scanner.nextLine();
            
            if (confirmation.equalsIgnoreCase("YES")) {
                taskList.remove(index);
                printSuccess("Task deleted successfully!");
            } else {
                printInfo("Deletion cancelled.");
            }
        } else {
            printError("Invalid selection.");
        }
    }

    private void assignEquipmentToTask() {
        viewAllTasks();
        if (taskList.isEmpty()) return;

        int taskIndex = getIntInput("\nSelect task to assign equipment (0 to cancel): ") - 1;
        if (taskIndex == -1) return;
        
        if (taskIndex < 0 || taskIndex >= taskList.size()) {
            printError("Invalid task selection.");
            return;
        }

        Tasks task = taskList.get(taskIndex);
        if (task.isCompleted()) {
            printError("Cannot assign equipment to completed tasks.");
            return;
        }

        viewAllEquipment();
        if (equipmentList.isEmpty()) return;

        int eqIndex = getIntInput("\nSelect equipment to assign (0 to cancel): ") - 1;
        if (eqIndex == -1) return;
        
        if (eqIndex >= 0 && eqIndex < equipmentList.size()) {
            Equipment eq = equipmentList.get(eqIndex);
            
            if (eq.eqNeedsMaintenance()) {
                System.out.println("\nWarning: This equipment needs maintenance!");
                System.out.print("Assign anyway? (YES/NO): ");
                String choice = scanner.nextLine();
                if (!choice.equalsIgnoreCase("YES")) {
                    printInfo("Assignment cancelled.");
                    return;
                }
            }
            
            String equipmentId = eq.getId();
            task.assignEq(equipmentId);
            printSuccess("Equipment '" + eq.getName() + "' assigned to task '" + task.getName() + "'!");
        } else {
            printError("Invalid equipment selection.");
        }
    }

    private void markTaskCompleted() {
        List<Tasks> pendingTasks = new ArrayList<>();
        for (Tasks task : taskList) {
            if (!task.isCompleted()) pendingTasks.add(task);
        }

        if (pendingTasks.isEmpty()) {
            printInfo("No pending tasks.");
            return;
        }

        System.out.println(YELLOW + BRIGHT + "\n" + "=".repeat(50) + RESET);
        System.out.println(YELLOW + BRIGHT + "             PENDING TASKS             " + RESET);
        System.out.println(YELLOW + BRIGHT + "=".repeat(50) + RESET);
        
        for (int i = 0; i < pendingTasks.size(); i++) {
            System.out.printf("%d. %s\n", (i + 1), pendingTasks.get(i).getName());
        }

        int index = getIntInput("\nSelect task to mark as completed (0 to cancel): ") - 1;
        if (index == -1) return;
        
        if (index >= 0 && index < pendingTasks.size()) {
            pendingTasks.get(index).markComplete();
            receiptGenerator.generateReceipt(pendingTasks.get(index));
            printSuccess("Task marked as completed! Receipt generated.");
        } else {
            printError("Invalid selection.");
        }
    }

    // ======= SCHEDULE MAINTENANCE =======
    private void scheduleMaintenance() {
        System.out.println(GREEN + BRIGHT + "\n" + "=".repeat(50) + RESET);
        System.out.println(GREEN + BRIGHT + "        SCHEDULE MAINTENANCE        " + RESET);
        System.out.println(GREEN + BRIGHT + "=".repeat(50) + RESET);
        
        List<Equipment> needMaintenance = new ArrayList<>();
        for (Equipment eq : equipmentList) {
            if (eq.eqNeedsMaintenance()) needMaintenance.add(eq);
        }

        if (needMaintenance.isEmpty()) {
            printInfo("No equipment currently needs maintenance.");
            return;
        }

        System.out.println("Equipment needing maintenance:");
        for (int i = 0; i < needMaintenance.size(); i++) {
            Equipment eq = needMaintenance.get(i);
            System.out.printf("%d. %s (ID: %s)\n", (i + 1), eq.getName(), eq.getId());
        }

        System.out.println(CYAN + "\nNote: Use 'Perform Maintenance' option from Equipment menu to maintain these." + RESET);
    }

    // ======= VIEW DATA AND REPORTS =======
    private void viewDataReports() {
        while (true) {
            System.out.println(GREEN + BRIGHT + "\n" + "=".repeat(50) + RESET);
            System.out.println(GREEN + BRIGHT + "          DATA & REPORTS          " + RESET);
            System.out.println(GREEN + BRIGHT + "=".repeat(50) + RESET);
            System.out.println("1. Equipment Status Report");
            System.out.println("2. Task Completion Report");
            System.out.println("3. Maintenance Report");
            System.out.println("4. Detailed Equipment List");
            System.out.println("5. Detailed Task List");
            System.out.println("6. Back to Main Menu");

            int choice = getIntInput("\nChoose report: ");
            switch (choice) {
                case 1: generateEquipmentReport(); break;
                case 2: generateTaskReport(); break;
                case 3: generateMaintenanceReport(); break;
                case 4: viewAllEquipment(); break;
                case 5: viewAllTasks(); break;
                case 6: return;
                default: printError("Invalid choice.");
            }
        }
    }

    private void generateEquipmentReport() {
        System.out.println(YELLOW + BRIGHT + "\n" + "=".repeat(50) + RESET);
        System.out.println(YELLOW + BRIGHT + "     EQUIPMENT STATUS REPORT     " + RESET);
        System.out.println(YELLOW + BRIGHT + "=".repeat(50) + RESET);
        
        int operational = 0, maintenance = 0;

        for (Equipment eq : equipmentList) {
            if (eq.eqNeedsMaintenance()) maintenance++; 
            else operational++;
            System.out.println("• " + eq.getName() + " - " + eq.getStatus() + " (" + eq.getId() + ")");
        }

        System.out.println("\n" + "=".repeat(30));
        System.out.println("SUMMARY:");
        System.out.println("Total Equipment: " + equipmentList.size());
        System.out.println("Operational: " + operational);
        System.out.println("Needs Maintenance: " + maintenance);
        System.out.println("=".repeat(30));
    }

    private void generateTaskReport() {
        System.out.println(YELLOW + BRIGHT + "\n" + "=".repeat(50) + RESET);
        System.out.println(YELLOW + BRIGHT + "     TASK COMPLETION REPORT     " + RESET);
        System.out.println(YELLOW + BRIGHT + "=".repeat(50) + RESET);
        
        int completed = 0, pending = 0, overdue = 0;

        for (Tasks task : taskList) {
            String status = task.getStatus();
            if (status.equals("COMPLETED")) completed++;
            else if (status.equals("OVERDUE")) overdue++;
            else pending++;

            System.out.println("• " + task.getName() + " - " + status + " (" + task.getId() + ")");
        }

        System.out.println("\n" + "=".repeat(30));
        System.out.println("SUMMARY:");
        System.out.println("Total Tasks: " + taskList.size());
        System.out.println("Completed: " + completed);
        System.out.println("Pending: " + pending);
        System.out.println("Overdue: " + overdue);
        System.out.println("=".repeat(30));
    }

    private void generateMaintenanceReport() {
        System.out.println(YELLOW + BRIGHT + "\n" + "=".repeat(50) + RESET);
        System.out.println(YELLOW + BRIGHT + "       MAINTENANCE REPORT       " + RESET);
        System.out.println(YELLOW + BRIGHT + "=".repeat(50) + RESET);
        
        int needsMaintenance = 0;

        for (Equipment eq : equipmentList) {
            if (eq.eqNeedsMaintenance()) {
                needsMaintenance++;
                System.out.println("⚠ " + eq.getName() + " - REQUIRES MAINTENANCE (ID: " + eq.getId() + ")");
            } else {
                System.out.println("✓ " + eq.getName() + " - Operational (ID: " + eq.getId() + ")");
            }
        }

        System.out.println("\n" + "=".repeat(30));
        System.out.println("EQUIPMENT NEEDING MAINTENANCE: " + needsMaintenance);
        System.out.println("TOTAL EQUIPMENT: " + equipmentList.size());
        System.out.println("=".repeat(30));
    }

    // ======= SESSION SUMMARY =======
    private void showSessionSummary() {
        System.out.println(MAGENTA + BRIGHT + "\n" + "=".repeat(50) + RESET);
        System.out.println(MAGENTA + BRIGHT + "         SESSION SUMMARY         " + RESET);
        System.out.println(MAGENTA + BRIGHT + "=".repeat(50) + RESET);
        
        System.out.println("Total Equipment: " + equipmentList.size());

        int completedTasks = 0, pendingTasks = 0, overdueTasks = 0;
        for (Tasks task : taskList) {
            String status = task.getStatus();
            if (status.equals("COMPLETED")) completedTasks++;
            else if (status.equals("OVERDUE")) overdueTasks++;
            else pendingTasks++;
        }

        System.out.println("Total Tasks: " + taskList.size());
        System.out.println("  • Completed: " + completedTasks);
        System.out.println("  • Pending: " + pendingTasks);
        System.out.println("  • Overdue: " + overdueTasks);

        int maintenanceNeeded = 0;
        for (Equipment eq : equipmentList) {
            if (eq.eqNeedsMaintenance()) maintenanceNeeded++;
        }
        System.out.println("\nEquipment needing maintenance: " + maintenanceNeeded);

        System.out.println("\nRECENTLY COMPLETED TASKS:");
        boolean foundRecent = false;
        for (Tasks task : taskList) {
            if (task.isCompleted()) {
                System.out.println("✓ " + task.getName() + " (ID: " + task.getId() + ")");
                foundRecent = true;
            }
        }
        if (!foundRecent) printInfo("No tasks completed in this session.");
    }

    // ======= EXIT SYSTEM =======
    private void exitSystem() {
        System.out.println(RED + BRIGHT + "\n" + "=".repeat(50) + RESET);
        System.out.println(RED + BRIGHT + "         SYSTEM SHUTDOWN         " + RESET);
        System.out.println(RED + BRIGHT + "=".repeat(50) + RESET);
        
        receiptGenerator.saveSessionSummary(equipmentList, taskList);
        System.out.println(GREEN + "✓ Session summary saved to 'FarmWise_Summary.txt'" + RESET);
        System.out.println(YELLOW + BRIGHT + "\nTHANK YOU FARMERS! UNTIL NEXT TIME, SEE YA!\n" + RESET);
    }

    // ======= UTILITY METHODS =======
    private int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("Please enter a number: ");
            scanner.next();
        }
        int input = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        return input;
    }

    private void printSuccess(String message) {
        System.out.println(GREEN + "✓ " + message + RESET);
    }

    private void printError(String message) {
        System.out.println(RED + "✗ " + message + RESET);
    }

    private void printInfo(String message) {
        System.out.println(CYAN + "ℹ " + message + RESET);
    }

    // ======= MAIN METHOD =======
    public static void main(String[] args) {
        FarmWise system = new FarmWise();
        system.run();
    }
}