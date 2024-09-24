import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Harmonogram {
    public static void main(String[] args) {
        TaskManager tm = new TaskManager();
        HarmonogramPrinter hp = new HarmonogramPrinter();
        Scanner s = new Scanner(System.in);

        tm.readTasksFromFile();

        char detail = 'a';
        do {
            LocalDate globalStart = getStartDate(s, tm);
            LocalDate globalEnd = getEndDate(s, tm);
            detail = getDetail(s);
            if(detail != 'Q') {
                clearConsole();
                hp.print(tm.getTaskList(), globalStart, globalEnd, detail);
            }
        }while(detail!='Q');
    }

    private static char getDetail(Scanner s){
        System.out.println("Select detail required (default is Week)");
        System.out.println("M - month");
        System.out.println("W - week");
        System.out.println("D - day");
        System.out.println("Q to quit");

        char c = 'W';

        do{
            String line = s.nextLine().toUpperCase().trim();

            if(!(line.isEmpty())) {
                c = line.charAt(0);
                if((c != 'W') && (c != 'M') && (c != 'D') && (c != 'Q')){
                    System.err.println("Invalid character, try again ...");
                }
            }
        } while( (c != 'W') && (c != 'M') && (c != 'D') && (c != 'Q') );
        return c;
    }

    private static LocalDate getStartDate(Scanner s, TaskManager tm ){
        LocalDate result = null;
        boolean end = false;

        System.out.println("Start date (if empty line is entered, the date will be calculated from the file)");
        System.out.println("Enter the date in YYYY-MM-DD format:");

        do {
            String textDate = s.nextLine();
            if (textDate.isEmpty()) {
                result = tm.getGlobalStartDate();
            }else{
                try {
                    result = LocalDate.parse(textDate.trim());
                } catch (DateTimeParseException e) {
                    System.err.println("Invalid format, try again");
                }
            }
        }while(result == null);
        return result;
    }

    private static LocalDate getEndDate(Scanner s, TaskManager tm ){
        LocalDate result = null;
        boolean end = false;

        System.out.println("End date (if empty line is entered, the date will be calculated from the file)");
        System.out.println("Enter the date in YYYY-MM-DD format:");

        do {
            String textDate = s.nextLine();
            if (textDate.isEmpty()) {
                result = tm.getGlobalEndDate();
            }else{
                try {
                    result = LocalDate.parse(textDate.trim());
                } catch (DateTimeParseException e) {
                    System.err.println("Invalid format, try again");
                }
            }
        }while(result == null);
        return result;
    }

    public static void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            else {
                System.out.print("\033\143");
            }
        } catch (IOException | InterruptedException ex) {}
    }
}
