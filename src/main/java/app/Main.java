package app;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.TransformerException;

public class Main {

    public static void main(String[] args) throws IOException {
        Main m = new Main();
        Map fields = m.getFields();

        File fileDocx = new File("/home/evgenij/docs/doc.docx");
        File fileXslt = new File("/home/evgenij/docs/doc.xslt"); // can be null
        File outputPath = new File("/home/evgenij/docs/merger/");

        Merger merger = new Merger();
        try {
            merger.init(fileDocx, fileXslt, outputPath, fields);
        } catch (TransformerException | URISyntaxException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Example map with example fields.
     *
     * @return map with fields
     */
    private Map getFields() {
        Map<String, String> fields = new HashMap<>();
        String today = LocalDate.now().toString();

        /* Header */
        fields.put("firstName", "Jenny");
        fields.put("lastName", "Curran");
        fields.put("street", "Rural Route");
        fields.put("houseNo", "2");
        fields.put("city", "Greenbow");
        fields.put("state", "Ala.");
        fields.put("zip", "39902");

        fields.put("subject", "Dear Jenny,");
        fields.put("date", today);

        /* Body */
        fields.put("person1", "Momma");
        fields.put("noun1", "destiny");
        fields.put("person2", "Lieutenant Dan");
        fields.put("verb", "floating");
        fields.put("noun2", "breeze");

        return fields;
    }
}
