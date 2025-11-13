abstract class FarmClass {
    private String farmId;
    private String farmName;

    public FarmClass(String farmId, String farmName) {
        this.farmId = farmId;
        this.farmName = farmName;
    }

    public String getId() {
        return farmId;
    }

    public String getName() {
        return farmName;
    }

    public void setName(String farmName) {
        this.farmName = farmName;
    }

    public abstract String getStatus();

    @Override
    public String toString() {
        return String.format("ID: %s | Name: %s | Status: %s", farmId, farmName, getStatus());
    }
}