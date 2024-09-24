import java.time.LocalDate;
import java.util.List;

public class HarmonogramPrinter {
    public void print(List<Task> taskList, LocalDate globalStart, LocalDate globalEnd, char detail){
        //printWeekly(taskList, globalStart, globalEnd);
        int nameBlockLength = getLongestNameLength(taskList);

        String title = "";
        printHeader(globalStart, globalEnd, nameBlockLength, detail);

        for(Task t: taskList){
            printTask(t, globalStart, globalEnd, nameBlockLength, detail);
        }

        System.out.print("\n\n\n");
    }

    private void printHeader(LocalDate globalStart, LocalDate globalEnd, int nameBlockLength, char detail){//}, String title) {
        LocalDate date = globalStart;
        String title = "";

        title += "Task Name";
        while(title.length() < nameBlockLength){
            title += " ";
        }

        do {
            title += "|" + date.toString();
            if (detail == 'W') { //weekly period
                date = date.plusWeeks(1);
            }else if (detail == 'M') { //weekly period
                    date = date.plusMonths(1);
            } else {//dayly period
                    date = date.plusDays(1);
            }
        } while (date.isBefore(globalEnd) || date.isEqual(globalEnd));

            System.out.println(title);
    }

    private void printTask(Task task, LocalDate globalStart, LocalDate globalEnd, int nameBlockLength, char detail){
        String line = "";
        line = task.getTaskName();
        while(line.length() < nameBlockLength){
            line += " ";
        }

        LocalDate harmonogramPeriodStartDate = globalStart, harmonogramPeriodEndDate = globalStart;
        do {
            if (detail == 'W') { //weekly period
                harmonogramPeriodEndDate = harmonogramPeriodStartDate.plusWeeks(1);
            } else if (detail == 'M') { //weekly period
                    harmonogramPeriodEndDate = harmonogramPeriodStartDate.plusMonths(1);
            } else {//dayly period
                    harmonogramPeriodEndDate = harmonogramPeriodStartDate.plusDays(1);
            }

            if(task.doesItStart(harmonogramPeriodStartDate, harmonogramPeriodEndDate)){
                line += "|    X     ";
            } else {
                line += "|          ";
            }
            harmonogramPeriodStartDate = harmonogramPeriodEndDate;
        } while(harmonogramPeriodStartDate.isBefore(globalEnd) || harmonogramPeriodStartDate.isEqual(globalEnd));

        System.out.println(line);
    }

    private int getLongestNameLength(List<Task> taskList){
        int length, longestLength = 0;
        for(Task t : taskList){
            length = t.getTaskName().length();
            if(length > longestLength){
                longestLength = length;
            }
        }
        return longestLength;
    }
}
