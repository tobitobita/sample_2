package dsk.samplecli;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class SimpleConsole extends WindowAdapter implements ActionListener, Runnable {

    private JFrame frame;
    private JTextArea textArea;
    private JTextField textField;

    private final PipedInputStream stdIs = new PipedInputStream();
    private final PipedInputStream errIs = new PipedInputStream();
    private final Thread inThread = new Thread(this);
    private final Thread stdThread = new Thread(this);
    private final Thread errThread = new Thread(this);
    private boolean quit;

    public SimpleConsole() {
        this.initComponent();
        this.initConsoleThread();
    }

    private void initComponent() {
        frame = new JFrame("Java Console");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = new Dimension((int) (screenSize.width / 2), (int) (screenSize.height / 2));
        int x = (int) (frameSize.width / 2);
        int y = (int) (frameSize.height / 2);
        frame.setBounds(x, y, frameSize.width, frameSize.height);

        textArea = new JTextArea();
        textArea.setEditable(false);
        JButton button = new JButton("Clear");

        textField = new JTextField();
        TextFieldStreamer inputStream = new TextFieldStreamer(textField);
        textField.addActionListener(inputStream);
        System.setIn(inputStream);

        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(new JScrollPane(textArea), BorderLayout.CENTER);
        frame.getContentPane().add(textField, BorderLayout.NORTH);
        frame.getContentPane().add(button, BorderLayout.SOUTH);

        frame.addWindowListener(this);
        button.addActionListener(this);
    }

    private void initConsoleThread() {
        try {
            PipedOutputStream pout = new PipedOutputStream(this.stdIs);
            System.setOut(new PrintStream(pout, true));
        } catch (IOException | SecurityException io) {
            textArea.append("Couldn't redirect STDOUT to this console\n" + io.getMessage());
        }

        try {
            PipedOutputStream pout2 = new PipedOutputStream(this.errIs);
            System.setErr(new PrintStream(pout2, true));
        } catch (IOException | SecurityException io) {
            textArea.append("Couldn't redirect STDERR to this console\n" + io.getMessage());
        }

        quit = false;

        stdThread.setDaemon(true);
        stdThread.start();

        errThread.setDaemon(true);
        errThread.start();
        
        inThread.setDaemon(true);
        inThread.start();
    }

    public void show() {
        frame.setVisible(true);
    }

    @Override
    public synchronized void windowClosed(WindowEvent evt) {
        quit = true;
        this.notifyAll(); // stop all threads
        try {
            stdThread.join(1000);
            stdIs.close();
        } catch (InterruptedException | IOException e) {
        }
        try {
            errThread.join(1000);
            errIs.close();
        } catch (InterruptedException | IOException e) {
        }
        System.exit(0);
    }

    @Override
    public synchronized void windowClosing(WindowEvent evt) {
        frame.setVisible(false);
        frame.dispose();
    }

    @Override
    public synchronized void actionPerformed(ActionEvent evt) {
        textArea.setText("");
    }

    @Override
    public synchronized void run() {
        try {
            while (Thread.currentThread() == stdThread) {
                try {
                    this.wait(100);
                } catch (InterruptedException e) {
                }
                if (stdIs.available() != 0) {
                    String input = this.readLine(stdIs);
                    EventQueue.invokeLater(() -> {
                        textArea.append(input);
                    });
                }
                if (quit) {
                    return;
                }
            }

            while (Thread.currentThread() == errThread) {
                try {
                    this.wait(100);
                } catch (InterruptedException e) {
                }
                if (errIs.available() != 0) {
                    String input = this.readLine(errIs);
                    EventQueue.invokeLater(() -> {
                        textArea.append(input);
                    });
                }
                if (quit) {
                    return;
                }
            }
        } catch (Exception e) {
            EventQueue.invokeLater(() -> {
                textArea.append("\nConsole reports an Internal error.");
                textArea.append("The error is: " + e);
            });
        }
    }

    public synchronized String readLine(PipedInputStream in) throws IOException {
        String input = "";
        do {
            int available = in.available();
            if (available == 0) {
                break;
            }
            byte b[] = new byte[available];
            in.read(b);
            input = input + new String(b, 0, b.length);
        } while (!input.endsWith("\n") && !input.endsWith("\r\n") && !quit);
        return input;
    }

    public static void main(String[] arg) {
        EventQueue.invokeLater(() -> {
            new SimpleConsole().show();

            // testing part
            // you may omit this part for your application
            // 
            System.out.println("Hello World 2");
            System.out.println("All fonts available to Graphic2D:\n");
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            String[] fontNames = ge.getAvailableFontFamilyNames();
            for (String fontName : fontNames) {
                System.out.println(fontName);
            }
            // Testing part: simple an error thrown anywhere in this JVM will be printed on the Console
            // We do it with a seperate Thread becasue we don't wan't to break a Thread used by the Console.
            System.out.println("\nLets throw an error on this console");
        });
    }
}
