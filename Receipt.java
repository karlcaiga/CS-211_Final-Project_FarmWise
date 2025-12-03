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
            String filename = "taskReceipt_" + task.getId() + ".txt";
            PrintWriter writer = new PrintWriter(new FileWriter(filename));

            writer.println("=".repeat(40));
            writer.println("       TASK COMPLETION RECEIPT");
            writer.println("=".repeat(40));
            writer.println("Task ID:        " + task.getId());
            writer.println("Task Name:      " + task.getName());
            writer.println("Completed:      " + dateFormat.format(task.getCompletionDate()));
            writer.println("Priority:       " + task.getPriority());
            writer.println("Equipment:      " + (task.getAssignedEq() != null ? task.getAssignedEq() : "None"));
            writer.println("Status:         " + task.getStatus());
            writer.println("-".repeat(40));
            writer.println("Generated:      " + dateFormat.format(new Date()));
            writer.println("=".repeat(40));
            writer.close();

            System.out.println("Receipt saved as: " + filename);

        } catch (IOException e) {
            System.out.println("Error generating receipt: " + e.getMessage());
        }
    }

    public void saveSessionSummary(List<Equipment> equipmentList, List<Tasks> taskList) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter("FarmWise_Summary.txt"));

            writer.println("=".repeat(50));
            writer.println("           FARMWISE SESSION SUMMARY");
            writer.println("=".repeat(50));
            writer.println("Generated: " + dateFormat.format(new Date()));
            writer.println();

            writer.println("EQUIPMENT (" + equipmentList.size() + " items):");
            writer.println("-".repeat(50));
            for (Equipment eq : equipmentList) {
                writer.println("• " + eq.getName() + " - " + eq.getStatus() + " (" + eq.getId() + ")");
            }
            writer.println();

            writer.println("TASKS (" + taskList.size() + " items):");
            writer.println("-".repeat(50));
            for (Tasks task : taskList) {
                writer.println("• " + task.getName() + " - " + task.getStatus() + " (" + task.getId() + ")");
            }

            writer.println("=".repeat(50));
            writer.close();

        } catch (IOException e) {
            System.out.println("Error saving summary: " + e.getMessage());
        }
    }
}