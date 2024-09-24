import javax.swing.*;
import java.io.*;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskManager {
    private ArrayList<Task> taskList = new ArrayList<>();

    public List<Task> getTaskList(){
        List<Task> tasks = new ArrayList<>(taskList);
        return tasks;
    }

    public LocalDate getGlobalStartDate(){
        LocalDate startDate, globalStartDate;

        globalStartDate = taskList.get(0).getStartDate();
        for(int i = 1; i < taskList.size(); i++){
            startDate = taskList.get(i).getStartDate();
            if(startDate.isBefore(globalStartDate)){
                globalStartDate = startDate;
            }
        }
        return globalStartDate;
    }

    public LocalDate getGlobalEndDate(){
        LocalDate endDate, globalEndDate;

        globalEndDate = taskList.get(0).getEndDate();
        for(int i = 1; i < taskList.size(); i++){
            endDate = taskList.get(i).getEndDate();
            if(endDate.isAfter(globalEndDate)){
                globalEndDate = endDate;
            }
        }
        return globalEndDate;
    }

    public void addTask(Task task){
        taskList.add(task);
    }

    public void readTasksFromFile(){
        File selectedFile = selectFile();

        if(selectedFile != null){
            readFile(selectedFile);
        } else {
            System.err.println("No file selected. Finishing. Bye ...");
            System.exit(1);
        }
    }

    private File selectFile(){
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        } else {
            return null;
        }
    }

    private void readFile(File file){
        try (BufferedReader br = new BufferedReader(new FileReader(file))){
            String line;
            while (!(line = br.readLine()).isEmpty()){
                if(line.charAt(0) == '#'){
                    continue;
                }
                String[] parts = line.split("\\|");
                String name = parts[0].trim();
                LocalDate start = LocalDate.parse(parts[1].trim());
                int frequency = Integer.parseInt(parts[2].trim());
                char period = Character.valueOf(parts[3].trim().charAt(0));
                LocalDate end = LocalDate.parse(parts[4].trim());
                addTask(new Task(name, start, end, frequency, period));
            }
        } catch (IOException e ){
            System.err.println(e.getMessage());
        }
    }
}
