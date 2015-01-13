package dsk.samplecli;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

public class XmlSeiarizer {

    private static final String FILE_PATH = "/Users/PAPRIKA/Desktop/data.xml";

    public void save(Object obj) {
        try (XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(FILE_PATH)));) {
            encoder.writeObject(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
