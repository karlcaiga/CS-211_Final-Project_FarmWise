import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

class Receipt {
    private SimpleDateFormat dateFormat;

    public Receipt() {
        dateFormat = new SimpleDateFormat("MMM dd, yyyy HH:mm");
    }

    public void generateReceipt(Tasks task) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter("taskReceipt_" + task.getId() + ".txt"));

            writer.println("TASK COMPLETION RECEIPT");
            writer.println("Task ID: " + task.getId());
            writer.println("Task Name: " + task.getName());
            writer.println("Completed: " + dateFormat.format(task.getCompletionDate()));
            writer.println("Priority: " + task.getPriority());

            String equipment;
            if (task.getAssignedEq() != null) {
                equipment = task.getAssignedEq();
            } else {
                equipment = "None";
            }

            writer.println("Equipment: " + equipment);
            writer.println("Status: " + task.getStatus());
            writer.println("Generated: " + dateFormat.format(new Date()));
            writer.close();

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }  
    }

    public void saveSessionSummary(List<Equipment> equipmentList, List<Tasks> taskList) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter("FarmWise_Summary.txt"));

            writer.println("FarmWise SUMMARY");
            writer.println("Generated: " + dateFormat.format(new Date()));

            writer.println("EQUIPMENT: ");
            for (int i = 0; i < equipmentList.size(); i++) {
                Equipment eq = equipmentList.get(i);
                writer.println("- " + eq.getName() + " (" + eq.getStatus() + ")");
            }

            writer.println("TASK: ");
            for (int i = 0; i < taskList.size(); i++) {
                Tasks task = taskList.get(i);
                writer.println("- " + task.getName() + " (" + task.getStatus() + ")");
            }

            writer.close();

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}