package Entity;

public enum LearningStrength {
    POOR("KEM"),
    WEAK("YEU"),
    AVERAGE("TRUNG BINH"),
    GOOD("KHA"),
    EXCELLENT("GIOI"),
    EXCEPTIONAL("XUAT SAC");

    private final String performance;

    LearningStrength(String performance) {
        this.performance = performance;
    }

    public String getPerformance() {
        return performance;
    }

}
