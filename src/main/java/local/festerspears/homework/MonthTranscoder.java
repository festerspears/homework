package local.festerspears.homework;

public class MonthTranscoder {

    public static int JanuaryAsInt = 0;
    public static int DecemberAsInt = 11;

    public static String toString(int month) {
        if (month < JanuaryAsInt || month > DecemberAsInt) {
            throw new IllegalArgumentException(
                    String.format("The month argument must be between %s and %s.", JanuaryAsInt,
                            DecemberAsInt));
        }
        return switch (month) {
            case 0 -> "January";
            case 1 -> "February";
            case 2 -> "March";
            case 3 -> "April";
            case 4 -> "May";
            case 5 -> "June";
            case 6 -> "July";
            case 7 -> "August";
            case 8 -> "September";
            case 9 -> "October";
            case 10 -> "November";
            default -> "December";
        };
    }

    public static int toInt(String month) {
        return switch (month) {
            case "January" -> 1;
            case "February" -> 2;
            case "March" -> 3;
            case "April" -> 4;
            case "May" -> 5;
            case "June" -> 6;
            case "July" -> 7;
            case "August" -> 8;
            case "September" -> 9;
            case "October" -> 10;
            case "November" -> 11;
            default -> 12;
        };
    }
}