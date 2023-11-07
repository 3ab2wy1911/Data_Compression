import java.io.*;

public class Manager {
    private final File file;
    private String text;

//______________________________________________________________________________________________________________________

    public Manager(String fileName) {
        this.file = new File(fileName);
        this.text = "";
    }

//______________________________________________________________________________________________________________________

    public String getText() {
        return text;
    }

//______________________________________________________________________________________________________________________

    public void setText(String text) {
        this.text = text;
    }

//______________________________________________________________________________________________________________________

    public void readFile(){

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                text = text.concat(line);
                text =text.concat("\n");
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception as needed (print, log, or throw)
        }
    }

//______________________________________________________________________________________________________________________

    public void writeFile(){
        if (text == null) {
            System.out.println("Error Occurred during writing !!!");
            return;
        }

        // ----------------------------------------------------

        // Writing the content to a file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(text);
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception as needed (print, log, or throw)
        }
    }

//______________________________________________________________________________________________________________________

}
