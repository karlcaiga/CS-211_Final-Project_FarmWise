import java.util.*;

// 5. MAIN SYSTEM CLASS
public class FarmWise {
    private Scanner scanner;
    private List<Equipment> equipmentList;
    private List<Tasks> taskList;
    private Receipt receiptGenerator;
    private int equipmentCounter;
    private int taskCounter;

    // ======= COLORS (NON-INTRUSIVE) =======

    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";    // Red text
    private static final String GREEN = "\u001B[32m";  // Green text
    private static final String CYAN = "\u001B[36m";   // Cyan text
    private static final String YELLOW = "\u001B[33m"; // Yellow text
    private static final String MAGENTA = "\u001B[35m"; // Magenta
    private static final String BRIGHT = "\u001B[1m";   // Bold

    

    public FarmWise() {
        scanner = new Scanner(System.in);
        equipmentList = new ArrayList<>();
        taskList = new ArrayList<>();
        receiptGenerator = new Receipt();
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
                default: System.out.println(RED + BRIGHT + "Invalid choice. Please try again.");
            }
        }
    }

    private void displayWelcome() {
        System.out.println(GREEN + BRIGHT +
                "\n==============================================");
        System.out.println("              FARMWISE SYSTEM                 ");
        System.out.println("        Smart Farm Management Platform       ");
        System.out.println("==============================================" + RESET + "\n");
    }

    private void displayMainMenu() {
        System.out.println(MAGENTA + BRIGHT + "\n================= MAIN MENU =================\n" + RESET);

        System.out.println(MAGENTA + "╔══════════════════════════════════╗" + RESET);
        System.out.println(MAGENTA + "║ " + GREEN + "1. " + CYAN + "Manage Equipment             " + MAGENTA + " ║" + RESET);
        System.out.println(MAGENTA + "║ " + GREEN + "2. " + YELLOW +  "Manage Tasks                 " + MAGENTA + " ║" + RESET);
        System.out.println(MAGENTA + "║ " + GREEN + "3. " + GREEN + "Schedule Maintenance         " + MAGENTA + " ║" + RESET);
        System.out.println(MAGENTA + "║ " + GREEN + "4. " + MAGENTA + "View Data and Reports        " + MAGENTA + " ║" + RESET);
        System.out.println(MAGENTA + "║ " + GREEN + "5. " + GREEN + "Session Summary              " + MAGENTA + " ║" + RESET);
        System.out.println(MAGENTA + "║ " + GREEN + "6. " + RED + "Exit System                  " + MAGENTA + " ║" + RESET);
        System.out.println(MAGENTA + "╚══════════════════════════════════╝\n" + RESET);
    }

    // 1. MANAGE EQUIPMENT
    private void manageEquipment() {
        while (true) {
            System.out.println(CYAN + BRIGHT + "\n========== MANAGE EQUIPMENT ==========" + RESET);
            System.out.println("1. View All Equipment");
            System.out.println("2. Add New Equipment");
            System.out.println("3. Mark Equipment for Maintenance");
            System.out.println("4. Perform Maintenance");
            System.out.println("5. Back to Main Menu");

            int choice = getIntInput("Choose an option: ");
            switch (choice) {
                case 1: viewAllEquipment(); break;
                case 2: addNewEquipment(); break;
                case 3: markEquipmentMaintenance(); break;
                case 4: performMaintenance(); break;
                case 5: return;
                default: System.out.println(RED + "Invalid choice.");
            }
        }
    }

    private void viewAllEquipment() {
        System.out.println(YELLOW + BRIGHT + "\n========== ALL EQUIPMENT ==========" + RESET);
        if (equipmentList.isEmpty()) {
            System.out.println("No equipment found.");
            return;
        }
        for (int i = 0; i < equipmentList.size(); i++) {
            System.out.println((i + 1) + ". " + equipmentList.get(i));
        }
    }

    private void addNewEquipment() {
        System.out.println(GREEN + BRIGHT + "\n========== ADD NEW EQUIPMENT ==========" + RESET);
        String id = "E" + String.format("%03d", equipmentCounter++);
        System.out.print("Enter equipment name: ");
        String name = scanner.nextLine();
        System.out.print("Enter equipment type: ");
        String type = scanner.nextLine();

        equipmentList.add(new Equipment(id, name, type));
        System.out.println(GREEN + "✓ Equipment added successfully! ID: " + id + RESET);
    }

    private void markEquipmentMaintenance() {
        viewAllEquipment();
        if (equipmentList.isEmpty()) return;

        int index = getIntInput("Select equipment to mark for maintenance: ") - 1;
        if (index >= 0 && index < equipmentList.size()) {
            equipmentList.get(index).markMaintenanceNeeded();
            System.out.println(GREEN + "✓ Maintenance marked for: " + equipmentList.get(index).getName() + RESET);
        } else {
            System.out.println(RED +"Invalid selection.");
        }
    }

    private void performMaintenance() {
        List<Equipment> needsMaintenance = new ArrayList<>();
        for (Equipment eq : equipmentList) {
            if (eq.eqNeedsMaintenance()) needsMaintenance.add(eq);
        }

        if (needsMaintenance.isEmpty()) {
            System.out.println(RED +"No equipment needs maintenance.");
            return;
        }

        System.out.println(YELLOW + BRIGHT + "\n========== EQUIPMENT NEEDING MAINTENANCE ==========" + RESET);
        for (int i = 0; i < needsMaintenance.size(); i++) {
            System.out.println((i + 1) + ". " + needsMaintenance.get(i).getName());
        }

        int index = getIntInput("Select equipment to maintain: ") - 1;
        if (index >= 0 && index < needsMaintenance.size()) {
            needsMaintenance.get(index).performEqMaintenance();
            System.out.println(GREEN + "✓ Maintenance completed for: " + needsMaintenance.get(index).getName() + RESET);
        } else {
            System.out.println(RED +"Invalid selection.");
        }
    }

    // 2. MANAGE TASKS
    private void manageTasks() {
        while (true) {
            System.out.println(YELLOW + BRIGHT + "\n========== MANAGE TASKS ==========" + RESET);
            System.out.println("1. View All Tasks");
            System.out.println("2. Create New Task");
            System.out.println("3. Assign Equipment to Task");
            System.out.println("4. Mark Task as Completed");
            System.out.println("5. Back to Main Menu");

            int choice = getIntInput("Choose an option: ");
            switch (choice) {
                case 1: viewAllTasks(); break;
                case 2: createNewTask(); break;
                case 3: assignEquipmentToTask(); break;
                case 4: markTaskCompleted(); break;
                case 5: return;
                default: System.out.println(RED +"Invalid choice.");
            }
        }
    }

    private void viewAllTasks() {
        System.out.println(YELLOW + BRIGHT + "\n=========== ALL TASKS ==========" + RESET);
        if (taskList.isEmpty()) {
            System.out.println(RED +"No tasks found.");
            return;
        }
        for (int i = 0; i < taskList.size(); i++) {
            System.out.println((i + 1) + ". " + taskList.get(i));
        }
    }

    private void createNewTask() {
        System.out.println(GREEN + BRIGHT + "\n========== CREATE NEW TASKS ==========" + RESET);
        String id = "T" + String.format("%03d", taskCounter++);
        System.out.print("Enter task name: ");
        String name = scanner.nextLine();
        System.out.print("Enter priority (HIGH/MEDIUM/LOW): ");
        String priority = scanner.nextLine().toUpperCase();

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 7);

        Tasks task = new Tasks(id, name, cal.getTime(), priority);
        taskList.add(task);
        System.out.println(GREEN + "✓ Task created successfully! ID: " + id + RESET);
    }

    private void assignEquipmentToTask() {
        viewAllTasks();
        if (taskList.isEmpty()) return;

        int taskIndex = getIntInput("Select task to assign equipment: ") - 1;
        if (taskIndex < 0 || taskIndex >= taskList.size()) {
            System.out.println(RED +"Invalid task selection.");
            return;
        }

        viewAllEquipment();
        if (equipmentList.isEmpty()) return;

        int eqIndex = getIntInput("Select equipment to assign: ") - 1;
        if (eqIndex >= 0 && eqIndex < equipmentList.size()) {
            String equipmentId = equipmentList.get(eqIndex).getId();
            taskList.get(taskIndex).assignEq(equipmentId);
            System.out.println(GREEN + "✓ Equipment assigned successfully!" + RESET);
        } else {
            System.out.println(RED +"Invalid equipment selection.");
        }
    }

    private void markTaskCompleted() {
        List<Tasks> pendingTasks = new ArrayList<>();
        for (Tasks task : taskList) {
            if (!task.isCompleted()) pendingTasks.add(task);
        }

        if (pendingTasks.isEmpty()) {
            System.out.println(RED +"No pending tasks.");
            return;
        }

        System.out.println(YELLOW + BRIGHT + "\n========== PENDING TASKS ==========" + RESET);
        for (int i = 0; i < pendingTasks.size(); i++) {
            System.out.println((i + 1) + ". " + pendingTasks.get(i).getName());
        }

        int index = getIntInput("Select task to mark as completed: ") - 1;
        if (index >= 0 && index < pendingTasks.size()) {
            pendingTasks.get(index).markComplete();
            receiptGenerator.generateReceipt(pendingTasks.get(index));
            System.out.println(GREEN + "✓ Task marked as completed! Receipt generated." + RESET);
        } else {
            System.out.println(RED +"Invalid selection.");
        }
    }

    // 3. SCHEDULE MAINTENANCE
    private void scheduleMaintenance() {
        System.out.println(GREEN + BRIGHT + "\n========== SCHEDULE MAINTENANCE ==========" + RESET);
        List<Equipment> needMaintenance = new ArrayList<>();

        for (Equipment eq : equipmentList) {
            if (eq.eqNeedsMaintenance()) needMaintenance.add(eq);
        }

        if (needMaintenance.isEmpty()) {
            System.out.println(RED +"No equipment currently needs maintenance.");
            return;
        }

        System.out.println("Equipment needing maintenance:");
        for (int i = 0; i < needMaintenance.size(); i++) {
            System.out.println((i + 1) + ". " + needMaintenance.get(i).getName());
        }

        System.out.println(CYAN + "Use 'Perform Maintenance' option from Equipment menu to maintain these.");
    }

    // 4. VIEW DATA AND REPORTS
    private void viewDataReports() {
        System.out.println(GREEN + BRIGHT + "\n========== DATA & REPORTS ==========" + RESET);
        System.out.println("1. Equipment Status Report");
        System.out.println("2. Task Completion Report");
        System.out.println("3. Maintenance Report");

        int choice = getIntInput("Choose report: ");
        switch (choice) {
            case 1: generateEquipmentReport(); break;
            case 2: generateTaskReport(); break;
            case 3: generateMaintenanceReport(); break;
            default: System.out.println(RED +"Invalid choice.");
        }
    }

    private void generateEquipmentReport() {
        System.out.println(YELLOW + BRIGHT + "\n========== EQUIPMENT STATUS REPORT ==========" + RESET);
        int operational = 0, maintenance = 0;

        for (Equipment eq : equipmentList) {
            if (eq.eqNeedsMaintenance()) maintenance++; else operational++;
            System.out.println("• " + eq.getName() + " - " + eq.getStatus());
        }

        System.out.println("\nSUMMARY: " + operational + " operational, " + maintenance + " need maintenance");
    }

    private void generateTaskReport() {
        System.out.println(YELLOW + BRIGHT + "\n========== TASK COMPLETION REPORT ==========" + RESET);
        int completed = 0, pending = 0, overdue = 0;

        for (Tasks task : taskList) {
            String status = task.getStatus();
            if (status.equals("COMPLETED")) completed++;
            else if (status.equals("OVERDUE")) overdue++;
            else pending++;

            System.out.println("• " + task.getName() + " - " + status);
        }

        System.out.println("\nSUMMARY: " + completed + " completed, " + pending + " pending, " + overdue + " overdue");
    }

    private void generateMaintenanceReport() {
        System.out.println(YELLOW + BRIGHT + "\n========== MAINTENANCE REPORT ==========" + RESET);
        int needsMaintenance = 0;

        for (Equipment eq : equipmentList) {
            if (eq.eqNeedsMaintenance()) {
                needsMaintenance++;
                System.out.println("• " + eq.getName() + " - REQUIRES MAINTENANCE");
            } else {
                System.out.println("• " + eq.getName() + " - Operational");
            }
        }

        System.out.println("\nEQUIPMENT NEEDING MAINTENANCE: " + needsMaintenance);
    }

    // 5. SESSION SUMMARY
    private void showSessionSummary() {
        System.out.println(MAGENTA + BRIGHT + "\n========== SESSION REPORT ==========" + RESET);
        System.out.println("Total Equipment: " + equipmentList.size());

        int completedTasks = 0, pendingTasks = 0;
        for (Tasks task : taskList) {
            if (task.isCompleted()) completedTasks++; else pendingTasks++;
        }

        System.out.println("Total Tasks: " + taskList.size() +
                " (" + completedTasks + " completed, " + pendingTasks + " pending)");

        int maintenanceNeeded = 0;
        for (Equipment eq : equipmentList) {
            if (eq.eqNeedsMaintenance()) maintenanceNeeded++;
        }
        System.out.println("Equipment needing maintenance: " + maintenanceNeeded);

        System.out.println("\nRECENTLY COMPLETED TASKS:");
        boolean foundRecent = false;
        for (Tasks task : taskList) {
            if (task.isCompleted()) {
                System.out.println("• " + task.getName() + " - Completed");
                foundRecent = true;
            }
        }
        if (!foundRecent) System.out.println(RED +"No tasks completed in this session.");
    }

    // 6. EXIT SYSTEM
    private void exitSystem() {
        System.out.println(RED + BRIGHT + "\n========== SYSTEM SHUTDOWN ==========\n" + RESET);
        receiptGenerator.saveSessionSummary(equipmentList, taskList);
        System.out.println(BRIGHT + BRIGHT + "Session summary saved to 'farmwise_summary.txt'");
        System.out.println(YELLOW + BRIGHT + "THANK YOU FARMERS! UNTIL NEXT TIME, SEE YA!");
    }

    // UTILITY METHOD
    private int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("Please enter a number: ");
            scanner.next();
        }
        int input = scanner.nextInt();
        scanner.nextLine(); 
        return input;
    }

    // MAIN METHOD
    public static void main(String[] args) {
        FarmWise system = new FarmWise();
        system.run();
    }
}
