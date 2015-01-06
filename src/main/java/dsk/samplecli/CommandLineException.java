package dsk.samplecli;

public class CommandLineException extends Exception {

    public CommandLineException(String message) {
        super(message, null, false, false);
    }
}
