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

    // COLORS
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String CYAN = "\u001B[36m";
    private static final String YELLOW = "\u001B[33m";
    private static final String MAGENTA = "\u001B[35m";
    private static final String BRIGHT = "\u001B[1m";

    // EQUIPMENT TYPES 
    private static final String[] EQUIPMENT_TYPES = {
        "Tractor", "Harvester", "Plow", "Seeder", "Irrigation System",
        "Sprayer", "Cultivator", "Loader", "Mower", "Other"
    };

    // PRIORITY LEVELS
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
        
        // Main program loop
        boolean programRunning = true;
        while (programRunning) {
            programRunning = displayStartupMenu();
        }
    }

    private void displayWelcome() {
        // ASCII Art for FARMWISE in green
        System.out.println(GREEN + BRIGHT);
        System.out.println("███████╗ █████╗ ██████╗ ███╗   ███╗    ██╗    ██╗██╗███████╗███████╗");
        System.out.println("██╔════╝██╔══██╗██╔══██╗████╗ ████║    ██║    ██║██║██╔════╝██╔════╝");
        System.out.println("█████╗  ███████║██████╔╝██╔████╔██║    ██║ █╗ ██║██║███████╗█████╗  ");
        System.out.println("██╔══╝  ██╔══██║██╔══██╗██║╚██╔╝██║    ██║███╗██║██║╚════██║██╔══╝  ");
        System.out.println("██║     ██║  ██║██║  ██║██║ ╚═╝ ██║    ╚███╔███╔╝██║███████║███████╗");
        System.out.println("╚═╝     ╚═╝  ╚═╝╚═╝  ╚═╝╚═╝     ╚═╝     ╚══╝╚══╝ ╚═╝╚══════╝╚══════╝" + RESET);
        System.out.println("        W H E R E   S M A R T   D E C I S I O N S   G R O W            ");
        System.out.println(RESET);
    }

    private boolean displayStartupMenu() {
        System.out.println("[1]" + CYAN + BRIGHT + " Start" + RESET );
        System.out.println("[2]" + GREEN + BRIGHT + " About" + RESET);
        System.out.println("[3]" + RED + BRIGHT + " Exit" + RESET); 

        int choice = getIntInput("\nSelect an option: ");
        
        switch (choice) {
            case 1:
                startProgram();
                return true;
            case 2:
                showAbout();
                return true;
            case 3:
                exitProgram();
                return false;
            default:
                printError("Invalid choice. Please try again.");
                return true;
        }
    }

    private void startProgram() {

        // Start
        while (true) {
            displayMainMenu();
            int choice = getIntInput("Choose an option: ");

            switch (choice) {
                case 1: manageEquipment(); break;
                case 2: manageTasks(); break;
                case 3: scheduleMaintenance(); break;
                case 4: viewDataReports(); break;
                case 5: showSessionSummary(); break;
                case 6: 
                    exitToStartup(); 
                    return; // Return to startup menu
                default: printError(RED + BRIGHT + "Invalid choice. Please try again." + RESET);
            }
        }
    }

    private void showAbout() {
        System.out.println(YELLOW + BRIGHT + "\n══════════════════════════════════════════" + RESET);
        System.out.println(YELLOW + BRIGHT + "              ABOUT FARMWISE              " + RESET);
        System.out.println(YELLOW + BRIGHT + "══════════════════════════════════════════\n" + RESET);
        
        System.out.println(CYAN + "╔════════════════════════════════════════╗" + RESET);
        System.out.println(CYAN + "║ " + GREEN + "FarmWise Management System v1.0       " + CYAN + " ║" + RESET);
        System.out.println(CYAN + "║ " + YELLOW + "Developed for Modern Farm Operations  " + CYAN + " ║" + RESET);
        System.out.println(CYAN + "║                                        " + CYAN + "║" + RESET);
        System.out.println(CYAN + "║ " + GREEN + "FEATURES:                             " + CYAN + " ║" + RESET);
        System.out.println(CYAN + "║ " + CYAN + "-Equipment Management                " + CYAN + "  ║" + RESET);
        System.out.println(CYAN + "║ " + CYAN + "-Task Scheduling & Tracking          " + CYAN + "  ║" + RESET);
        System.out.println(CYAN + "║ " + CYAN + "-Maintenance Scheduling              " + CYAN + "  ║" + RESET);
        System.out.println(CYAN + "║ " + CYAN + "-Data Reports & Analytics            " + CYAN + "  ║" + RESET);
        System.out.println(CYAN + "║ " + CYAN + "-Session Summary & Receipts          " + CYAN + "  ║" + RESET);
        System.out.println(CYAN + "║                                        " + CYAN + "║" + RESET);
        System.out.println(CYAN + "║ " + GREEN + "2025 FarmWise. All Rights Reserved. " + CYAN + "   ║" + RESET);
        System.out.println(CYAN + "╚════════════════════════════════════════╝\n" + RESET);
        
        System.out.print("Press Enter to return to main menu...");
        scanner.nextLine();
    }

    private void exitToStartup() {
        System.out.println(YELLOW + BRIGHT + "\n══════════════════════════════════════════" + RESET);
        System.out.println(YELLOW + "       RETURNING TO STARTUP MENU...          " + RESET);
        System.out.println(YELLOW + BRIGHT + "══════════════════════════════════════════\n" + RESET);
    }

    private void exitProgram() {
        System.out.println(RED + BRIGHT + "\n══════════════════════════════════════════" + RESET);
        System.out.println(RED + BRIGHT + "         PROGRAM TERMINATION         " + RESET);
        System.out.println(RED + BRIGHT + "══════════════════════════════════════════\n" + RESET);
        
        // Save session summary before exiting
        if (!equipmentList.isEmpty() || !taskList.isEmpty()) {
            receiptGenerator.saveSessionSummary(equipmentList, taskList);
            System.out.println(GREEN + "Session summary saved to 'FarmWise_Summary.txt'" + RESET);
        }
        
        System.out.println(YELLOW + BRIGHT + "\nTHANK YOU FOR USING FARMWISE! GOODBYE!\n" + RESET);
    }

    private void displayMainMenu() {
        System.out.println(MAGENTA + "╔════════════════════════════════════════╗" + RESET);
        System.out.println(MAGENTA + "║ " + GREEN + "1. " + CYAN + BRIGHT + "Manage Equipment             " + MAGENTA + "       ║" + RESET);
        System.out.println(MAGENTA + "║ " + GREEN + "2. " + YELLOW + BRIGHT + "Manage Tasks                 " + MAGENTA + "       ║" + RESET);
        System.out.println(MAGENTA + "║ " + GREEN + "3. " + GREEN + BRIGHT + "Schedule Maintenance         " + MAGENTA + "       ║" + RESET);
        System.out.println(MAGENTA + "║ " + GREEN + "4. " + MAGENTA + BRIGHT + "View Data and Reports        " + MAGENTA + "       ║" + RESET);
        System.out.println(MAGENTA + "║ " + GREEN + "5. " + GREEN + BRIGHT + "Session Summary              " + MAGENTA + "       ║" + RESET);
        System.out.println(MAGENTA + "║ " + GREEN + "6. " + YELLOW + BRIGHT + "Return to Startup Menu       " + MAGENTA + "       ║" + RESET);
        System.out.println(MAGENTA + "╚════════════════════════════════════════╝\n" + RESET);
    }

    // MANAGE EQUIPMENT
    private void manageEquipment() {
        while (true) {
            System.out.println(CYAN + BRIGHT + "\n" + "=".repeat(40) + RESET);
            System.out.println(CYAN + BRIGHT + "             MANAGE EQUIPMENT              " + RESET);
            System.out.println(CYAN + BRIGHT + "=".repeat(40) + RESET);
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
                default: printError(RED + BRIGHT + "Invalid choice.");
            }
        }
    }

    private void viewAllEquipment() {
        System.out.println(YELLOW + BRIGHT + "\n" + "=".repeat(40) + RESET);
        System.out.println(YELLOW + BRIGHT + "              ALL EQUIPMENT              " + RESET);
        System.out.println(YELLOW + BRIGHT + "=".repeat(40) + RESET);
        
        if (equipmentList.isEmpty()) {
            printInfo(RED + BRIGHT + "No equipment found.");
            return;
        }
        
        for (int i = 0; i < equipmentList.size(); i++) {
            System.out.printf("%d. %s\n", (i + 1), equipmentList.get(i));
        }
    }

    private void addNewEquipment() {
        System.out.println(GREEN + BRIGHT + "\n" + "=".repeat(40) + RESET);
        System.out.println(GREEN + BRIGHT + "            ADD NEW EQUIPMENT              " + RESET);
        System.out.println(GREEN + BRIGHT + "=".repeat(40) + RESET);
        
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
            printError(RED + BRIGHT + "Invalid choice. Defaulting to 'Other'.");
            type = "Other";
        }

        equipmentList.add(new Equipment(id, name, type));
        printSuccess(GREEN + BRIGHT + "Equipment added successfully! ID: " + id);
    }

    // UPDATE EQ
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
            printSuccess(GREEN + BRIGHT + "Equipment updated successfully!");
        } else {
            printError(RED + BRIGHT + "Invalid selection.");
        }
    }

    // DEL EQ
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
                printSuccess(GREEN + BRIGHT +"Equipment deleted successfully!");
            } else {
                printInfo("Deletion cancelled.");
            }
        } else {
            printError(RED + BRIGHT + "Invalid selection.");
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
            printError(RED + BRIGHT + "Invalid selection.");
        }
    }

    private void performMaintenance() {
        List<Equipment> needsMaintenance = new ArrayList<>();
        for (Equipment eq : equipmentList) {
            if (eq.eqNeedsMaintenance()) needsMaintenance.add(eq);
        }

        if (needsMaintenance.isEmpty()) {
            printInfo(RED + BRIGHT + "No equipment needs maintenance.");
            return;
        }

        System.out.println(YELLOW + BRIGHT + "\n" + "=".repeat(40) + RESET);
        System.out.println(YELLOW + BRIGHT + "     EQUIPMENT NEEDING MAINTENANCE   " + RESET);
        System.out.println(YELLOW + BRIGHT + "=".repeat(40) + RESET);
        
        for (int i = 0; i < needsMaintenance.size(); i++) {
            System.out.printf("%d. %s\n", (i + 1), needsMaintenance.get(i).getName());
        }

        int index = getIntInput("\nSelect equipment to maintain (0 to cancel): ") - 1;
        if (index == -1) return;
        
        if (index >= 0 && index < needsMaintenance.size()) {
            needsMaintenance.get(index).performEqMaintenance();
            printSuccess("Maintenance completed for: " + needsMaintenance.get(index).getName());
        } else {
            printError(RED + BRIGHT + "Invalid selection.");
        }
    }

    // MANAGE TASKS
    private void manageTasks() {
        while (true) {
            System.out.println(YELLOW + BRIGHT + "\n" + "=".repeat(40) + RESET);
            System.out.println(YELLOW + BRIGHT + "              MANAGE TASKS              " + RESET);
            System.out.println(YELLOW + BRIGHT + "=".repeat(40) + RESET);
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
                default: printError(RED + BRIGHT + "Invalid choice.");
            }
        }
    }

    private void viewAllTasks() {
        System.out.println(YELLOW + BRIGHT + "\n" + "=".repeat(40) + RESET);
        System.out.println(YELLOW + BRIGHT + "              ALL TASKS              " + RESET);
        System.out.println(YELLOW + BRIGHT + "=".repeat(40) + RESET);
        
        if (taskList.isEmpty()) {
            printInfo(RED + BRIGHT + "No tasks found.");
            return;
        }
        
        for (int i = 0; i < taskList.size(); i++) {
            System.out.printf("%d. %s\n", (i + 1), taskList.get(i));
        }
    }

    private void createNewTask() {
        System.out.println(GREEN + BRIGHT + "\n" + "=".repeat(40) + RESET);
        System.out.println(GREEN + BRIGHT + "              CREATE NEW TASK              " + RESET);
        System.out.println(GREEN + BRIGHT + "=".repeat(40) + RESET);
        
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
            printError(RED + BRIGHT + "Invalid choice. Defaulting to 'MEDIUM'.");
            priority = "MEDIUM";
        }
        
        // DATE INPUT ++ WITH VALIDATION
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
                    printError(RED + BRIGHT + "Invalid date format. Please use YYYY-MM-DD.");
                }
            }
        }

        Tasks task = new Tasks(id, name, dueDate, priority);
        taskList.add(task);
        printSuccess(GREEN + BRIGHT + "Task created successfully! ID: " + id);
    }

    // UPD TASK
    private void updateTask() {
        viewAllTasks();
        if (taskList.isEmpty()) return;

        int index = getIntInput("\nSelect task to update (0 to cancel): ") - 1;
        if (index == -1) return;
        
        if (index >= 0 && index < taskList.size()) {
            Tasks task = taskList.get(index);
            
            if (task.isCompleted()) {
                printError(RED + BRIGHT + "Cannot update completed tasks.");
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
            
            // DATE UPDAATE
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
                    printError(RED + BRIGHT + "Invalid date format. Keeping current due date.");
                }
            }
            
            task.updateTask(newName, newPriority, newDueDate);
            printSuccess(GREEN + BRIGHT +"Task updated successfully!");
        } else {
            printError(RED + BRIGHT + "Invalid selection.");
        }
    }

    // DELETE TASK
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
                printSuccess(GREEN + BRIGHT +"Task deleted successfully!");
            } else {
                printInfo("Deletion cancelled.");
            }
        } else {
            printError(RED + BRIGHT + "Invalid selection.");
        }
    }

    private void assignEquipmentToTask() {
        viewAllTasks();
        if (taskList.isEmpty()) return;

        int taskIndex = getIntInput("\nSelect task to assign equipment (0 to cancel): ") - 1;
        if (taskIndex == -1) return;
        
        if (taskIndex < 0 || taskIndex >= taskList.size()) {
            printError(RED + BRIGHT + "Invalid task selection.");
            return;
        }

        Tasks task = taskList.get(taskIndex);
        if (task.isCompleted()) {
            printError(RED + BRIGHT + "Cannot assign equipment to completed tasks.");
            return;
        }

        viewAllEquipment();
        if (equipmentList.isEmpty()) return;

        int eqIndex = getIntInput("\nSelect equipment to assign (0 to cancel): ") - 1;
        if (eqIndex == -1) return;
        
        if (eqIndex >= 0 && eqIndex < equipmentList.size()) {
            Equipment eq = equipmentList.get(eqIndex);
            
            if (eq.eqNeedsMaintenance()) {
                System.out.println(YELLOW + BRIGHT + "\nWarning: This equipment needs maintenance!");
                System.out.print("Assign anyway? (YES/NO): ");
                String choice = scanner.nextLine();
                if (!choice.equalsIgnoreCase("YES")) {
                    printInfo(RED + BRIGHT + "Assignment cancelled.");
                    return;
                }
            }
            
            String equipmentId = eq.getId();
            task.assignEq(equipmentId);
            printSuccess("Equipment '" + eq.getName() + "' assigned to task '" + task.getName() + "'!");
        } else {
            printError(RED + BRIGHT + "Invalid equipment selection.");
        }
    }

    private void markTaskCompleted() {
        List<Tasks> pendingTasks = new ArrayList<>();
        for (Tasks task : taskList) {
            if (!task.isCompleted()) pendingTasks.add(task);
        }

        if (pendingTasks.isEmpty()) {
            printInfo(YELLOW + BRIGHT + "No pending tasks.");
            return;
        }

        System.out.println(YELLOW + BRIGHT + "\n" + "=".repeat(40) + RESET);
        System.out.println(YELLOW + BRIGHT + "              PENDING TASKS              " + RESET);
        System.out.println(YELLOW + BRIGHT + "=".repeat(40) + RESET);
        
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
            printError(RED + BRIGHT + "Invalid selection.");
        }
    }

    // SCHEDULE MAINTENANCE
    private void scheduleMaintenance() {
        System.out.println(GREEN + BRIGHT + "\n" + "=".repeat(40) + RESET);
        System.out.println(GREEN + BRIGHT + "         SCHEDULE MAINTENANCE              " + RESET);
        System.out.println(GREEN + BRIGHT + "=".repeat(40) + RESET);
        
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

    // VIEW DATA AND REPORTS
    private void viewDataReports() {
        while (true) {
            System.out.println(GREEN + BRIGHT + "\n" + "=".repeat(40) + RESET);
            System.out.println(GREEN + BRIGHT + "           DATA & REPORTS             " + RESET);
            System.out.println(GREEN + BRIGHT + "=".repeat(40) + RESET);
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
                default: printError(RED + BRIGHT + "Invalid choice.");
            }
        }
    }

    private void generateEquipmentReport() {
        System.out.println(YELLOW + BRIGHT + "\n" + "=".repeat(40) + RESET);
        System.out.println(YELLOW + BRIGHT + "     EQUIPMENT STATUS REPORT     " + RESET);
        System.out.println(YELLOW + BRIGHT + "=".repeat(40) + RESET);
        
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
        System.out.println(YELLOW + BRIGHT + "\n" + "=".repeat(40) + RESET);
        System.out.println(YELLOW + BRIGHT + "     TASK COMPLETION REPORT     " + RESET);
        System.out.println(YELLOW + BRIGHT + "=".repeat(40) + RESET);
        
        int completed = 0, pending = 0, overdue = 0;

        for (Tasks task : taskList) {
            if (task.isCompleted()) {
                completed++;
            } else if (task.isOverdue()) {
                overdue++;
            } else {
                pending++;
            }

            String status = task.getStatus();
            String coloredStatus;
            if (status.equals("COMPLETED")) {
                coloredStatus = GREEN + BRIGHT + status + RESET;
            } else if (status.equals("OVERDUE")) {
                coloredStatus = RED + BRIGHT + status + RESET;
            } else {
                coloredStatus = YELLOW + BRIGHT + status + RESET;
            }
            
            System.out.println("• " + task.getName() + " - " + coloredStatus + " (" + task.getId() + ")");
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
        System.out.println(YELLOW + BRIGHT + "\n" + "=".repeat(40) + RESET);
        System.out.println(YELLOW + BRIGHT + "       MAINTENANCE REPORT       " + RESET);
        System.out.println(YELLOW + BRIGHT + "=".repeat(40) + RESET);
        
        int needsMaintenance = 0;

        for (Equipment eq : equipmentList) {
            if (eq.eqNeedsMaintenance()) {
                needsMaintenance++;
                System.out.println(eq.getName() + " - REQUIRES MAINTENANCE (ID: " + eq.getId() + ")");
            } else {
                System.out.println(eq.getName() + " - Operational (ID: " + eq.getId() + ")");
            }
        }

        System.out.println("\n" + "=".repeat(30));
        System.out.println("EQUIPMENT NEEDING MAINTENANCE: " + needsMaintenance);
        System.out.println("TOTAL EQUIPMENT: " + equipmentList.size());
        System.out.println("=".repeat(30));
    }

    // SESSION SUMMARY
    private void showSessionSummary() {
        System.out.println(MAGENTA + BRIGHT + "\n" + "=".repeat(40) + RESET);
        System.out.println(MAGENTA + BRIGHT + "             SESSION SUMMARY         " + RESET);
        System.out.println(MAGENTA + BRIGHT + "=".repeat(40) + RESET);
        
        System.out.println("Total Equipment: " + equipmentList.size());
        
        int completedTasks = 0, pendingTasks = 0, overdueTasks = 0;
        for (Tasks task : taskList) {
            if (task.isCompleted()) {
                completedTasks++;
            } else if (task.isOverdue()) {
                overdueTasks++;
            } else {
                pendingTasks++;
            }
        }

        System.out.println("Total Tasks: " + taskList.size());
        System.out.println(GREEN + BRIGHT + "Completed: " + completedTasks + RESET);
        System.out.println(YELLOW + BRIGHT + "Pending: " + pendingTasks + RESET);
        System.out.println(RED + BRIGHT + "Overdue: " + overdueTasks + RESET);

        int maintenanceNeeded = 0;
        for (Equipment eq : equipmentList) {
            if (eq.eqNeedsMaintenance()) maintenanceNeeded++;
        }
        System.out.println("\nEquipment needing maintenance: " + maintenanceNeeded);

        System.out.println("\nRECENTLY COMPLETED TASKS:");
        boolean foundRecent = false;
        for (Tasks task : taskList) {
            if (task.isCompleted()) {  // ✅ FIXED: Use boolean method directly
                System.out.println("• " + task.getName() + " (ID: " + task.getId() + ")");
                foundRecent = true;
            }
        }
        if (!foundRecent) printInfo("No tasks completed in this session.");
    }

    // UTILITY METHODS
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
        System.out.println(GREEN + message + RESET);
    }

    private void printError(String message) {
        System.out.println(RED + message + RESET);
    }

    private void printInfo(String message) {
        System.out.println(CYAN + message + RESET);
    }

    // ======= MAIN METHOD =======
    public static void main(String[] args) {
        FarmWise system = new FarmWise();
        system.run();
    }
}