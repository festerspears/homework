package local.festerspears.homework;

public final class MonthlyUsageDetail {

    private final String month;
    private final int year;
    private final double usage;

    public MonthlyUsageDetail(String month, int year, double usage) {
        this.month = month;
        this.year = year;
        this.usage = usage;
    }

    public String getMonth() {
        return this.month;
    }

    public int getYear() {
        return this.year;
    }

    public double getUsage() {
        return this.usage;
    }

    public int compareByAge(MonthlyUsageDetail thatDetail) {
        int result = 0;
        int thisMonth = MonthTranscoder.toInt(this.getMonth());
        int thatMonth = MonthTranscoder.toInt(thatDetail.getMonth());
        if (this.getYear() < thatDetail.getYear()) {
            result = 1;
        } else if (this.getYear() > thatDetail.getYear()) {
            result = -1;
        } else if (thisMonth < thatMonth) {
            result = 1;
        } else if (thisMonth > thatMonth) {
            result = -1;
        }

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        MonthlyUsageDetail usageDetail = (MonthlyUsageDetail) obj;
        boolean isEqual = false;
        if (this.getMonth().equalsIgnoreCase(usageDetail.getMonth())
                && this.year == usageDetail.getYear()
                && this.getUsage() == usageDetail.getUsage()) {
            isEqual = true;
        }
        return isEqual;
    }

    @Override
    public int hashCode() {
        return String.format("%s%s", this.getMonth(), this.getYear()).hashCode();
    }

    @Override
    public String toString() {
        return String.format("{%s, %s, %s}", this.month, this.getYear(), this.getUsage());
    }
}
