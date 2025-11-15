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

    @Override
    public String getStatus() {
    String status = "PENDING";
        if (isCompleted) {
            status = "COMPLETED";
        } else if (new Date().after(dueDate)) {
            status = "OVERDUE";
        }
        return status;
}

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy HH:mm");
        String dueStr = sdf.format(dueDate);
        String equipment = assignedEq != null ? assignedEq : "Not assigned";
        return String.format("%s | Due: %s | Priority: %s | Equipment: %s", 
        super.toString(), dueStr, prio, equipment);
    }
}