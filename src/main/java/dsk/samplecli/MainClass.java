package dsk.samplecli;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MainClass {

    public static void main(String[] args) {
        CommandLineRunner runner = new SampleCommand();
        try (BufferedReader r = new BufferedReader(new InputStreamReader(System.in));) {
            String line;
            while ((line = r.readLine()) != null) {
                runner.execute(line.split(" "));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
