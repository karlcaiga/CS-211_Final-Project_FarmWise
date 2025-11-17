import java.text.SimpleDateFormat;
import java.util.Date;

class Equipment extends FarmClass {
    private String eqType;
    private Date eqLastMaintenance;
    private boolean eqNeedsMaintenance;

    public Equipment (String farmId, String farmName, String eqType) {
        super(farmId, farmName);
        this.eqType = eqType;
        this.eqLastMaintenance = new Date();
        this.eqNeedsMaintenance = false;
    }

    public void markMaintenanceNeeded() {
        eqNeedsMaintenance = true;
    }

    public void performEqMaintenance() {
        eqNeedsMaintenance = false;
        eqLastMaintenance = new Date();
    }

    public boolean eqNeedsMaintenance() {
        return eqNeedsMaintenance;
    }

    public Date getEqLastMaintenance() {
        return eqLastMaintenance;
    }

    public String getEqType() {
        return eqType;
    }

    @Override
    public String getStatus() {
    String status = "OPERATIONAL";
        if (eqNeedsMaintenance) {
            status = "NEEDS MAINTENANCE";
        }
    return status;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
        return String.format("%s | Type: %s | Last Maintenance: %s", 
        super.toString(), eqType, sdf.format(eqLastMaintenance));
    }
}