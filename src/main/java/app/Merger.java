package app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.TransformerException;

public class Merger {

    private final static Logger LOGGER = Logger.getLogger(Merger.class.getName());

    MergeFieldFinder mff = new MergeFieldFinder();

    public void init() throws IOException, TransformerException, URISyntaxException {
        File fileDocx = new File(this.getClass().getResource("/docs/doc.docx").toURI());
        File fileXslt = new File(this.getClass().getResource("/docs/doc.xslt").toURI());
        File outputPath = new File("/home/evgenij/docs/merger/");

        Map fields = null;
        try {
            fields = setFields();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "An error occured during the merging process: " + ex);
        }
        mff.merge(fileDocx, fileXslt, outputPath, fields);
    }

    /**
     * Sets the fields in the map needed for the merging process.
     *
     * @return map with the requiered fields
     * @throws Exception
     */
    private Map setFields() throws Exception {

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
