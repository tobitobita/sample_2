package dsk.samplecli;

public interface CommandLineRunner {

    void execute(String... arg) throws CommandLineException, ParseException;
}
