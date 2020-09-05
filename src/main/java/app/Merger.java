package app;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.TransformerException;

public class Merger {

    private final static Logger LOGGER = Logger.getLogger(Merger.class.getName());

    /**
     * Initializing.
     *
     * @param fileDocx
     * @param fileXslt
     * @param outputPath
     * @param fields
     * @throws IOException
     * @throws TransformerException
     * @throws URISyntaxException
     */
    public void init(File fileDocx, File fileXslt, File outputPath, Map<String, String> fields) throws IOException, TransformerException, URISyntaxException {
        LOGGER.log(Level.INFO, "Starting initialization ...");
        MergeFieldFinder mff = new MergeFieldFinder();

        /* If no XSLT file is provided, a new XSLT file will be generated */
        if (fileXslt == null) {
            XsltCreator xsltc = new XsltCreator();
            xsltc.init(fields, outputPath);
            fileXslt = new File(outputPath + File.separator + "doc.xslt");
        }

        mff.merge(fileDocx, fileXslt, outputPath, fields);
        LOGGER.log(Level.INFO, "Done!");
    }
}
