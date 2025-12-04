import java.text.SimpleDateFormat;
import java.util.Date;

class Tasks extends FarmClass {
    private Date dueDate;
    private Date completionDate;
    private String prio;
    private String assignedEq;
    private boolean isCompleted;

    public Tasks(String farmId, String farmName, Date dueDate, String prio) {
        super(farmId, farmName);
        this.dueDate = dueDate;
        this.prio = prio;
        this.isCompleted = false;
        this.assignedEq = null;
    }

    public void assignEq(String equipmentId) {
        assignedEq = equipmentId;
    }

    public void markComplete() {
        isCompleted = true;
        completionDate = new Date();
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public String getPriority() {
        return prio;
    }

    public String getAssignedEq() {
        return assignedEq;
    }

    public Date getCompletionDate() {
        return completionDate;
    }

    public void updateTask(String newName, String newPriority, Date newDueDate) {
        if (newName != null && !newName.trim().isEmpty()) {
            setName(newName);
        }
        if (newPriority != null && !newPriority.trim().isEmpty()) {
            this.prio = newPriority.toUpperCase();
        }
        if (newDueDate != null) {
            this.dueDate = newDueDate;
        }
    }

    @Override
    public String getStatus() {
        if (isCompleted) {
            return "COMPLETED";
        } else if (new Date().after(dueDate)) {
            return "OVERDUE";
        }
        return "PENDING";
    }

    public boolean isOverdue() {
        return !isCompleted && new Date().after(dueDate);
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy HH:mm");
        String dueStr = sdf.format(dueDate);
        String equipment = assignedEq != null ? assignedEq : "Not assigned";
        String status = getStatus();
        return String.format("%s | Due: %s | Priority: %s | Equipment: %s | Status: %s",
                super.toString(), dueStr, prio, equipment, status);
    }
}