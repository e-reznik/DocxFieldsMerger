package app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipFile;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class MergeFieldFinder {

    private final static Logger LOGGER = Logger.getLogger(MergeFieldFinder.class.getName());

    public MergeFieldFinder() {
    }

    /**
     * Validating information and calling necessary methods for the merging
     * process.
     *
     * @param fileDocx the Docx file to be processed
     * @param fileXslt the XSLT with the rules
     * @param outputPath path of the new Docx
     * @param fields map with the field names and desired values
     * @throws IOException
     * @throws TransformerException
     */
    public void merge(File fileDocx, File fileXslt, File outputPath, Map fields) throws IOException, TransformerException {
        LOGGER.log(Level.INFO, "Starting merging...");
        if (!fileDocx.exists()) {
            throw new FileNotFoundException("File " + fileDocx + " not found. Process aborted.");
        }
        if (!fileXslt.exists()) {
            throw new FileNotFoundException("File " + fileXslt + " not found. Process aborted.");
        }
        // TODO: Check outputPath

        Path letter = copyDocx(fileDocx, outputPath);
        File fileDocxNew = new File(letter.toUri());

        InputStream is = getDocument(fileDocxNew);
        Path tempFile = mergeFields(fields, is, fileXslt);

        replaceFileDocx(fileDocxNew, tempFile);
    }

    /**
     * Creates a copy of the document, so that it can be processed.
     *
     * @param in the original Docx file to be copied
     * @return path to the new copy
     * @throws IOException
     */
    private Path copyDocx(File in, File outputPath) throws IOException {
        LOGGER.log(Level.INFO, "Copying Docx...");
        String fileNameNew = removeExtension(in) + ".docx";
        Path out = Path.of(outputPath + File.separator + fileNameNew);

        if (Files.exists(out)) {
            LOGGER.log(Level.INFO, "That file already existed: " + out + ". It has been overridden.");
            Files.delete(out);
        }

        Files.copy(in.toPath(), out);
        return out;
    }

    /**
     * Returns the file name without the extension.
     *
     * @param file desired filename
     * @return the name of the file, without the extension
     */
    private String removeExtension(File file) {
        String name = file.getName();
        int pos = name.lastIndexOf(".");
        if (pos > 0) {
            name = name.substring(0, pos);
        }
        return name;
    }

    /**
     * Returns the document.xml from the DOCX-archive.
     *
     * @param docx path to the Docx-archive
     * @return the InputStream of the document.xml
     * @throws URISyntaxException
     * @throws IOException
     */
    private InputStream getDocument(File docx) throws IOException {
        ZipFile zipFile = new ZipFile(docx);
        InputStream is = zipFile.getInputStream(zipFile.getEntry("word/document.xml"));

        return is;
    }

    /**
     * Passes the map with the mergefields to the XSLT and begins the
     * transformation.
     *
     * @param map
     * @param isXml
     * @param urlXslt
     * @throws URISyntaxException
     * @throws TransformerConfigurationException
     * @throws TransformerException
     */
    private Path mergeFields(Map<String, String> map, InputStream isXml, File urlXslt) throws TransformerConfigurationException, IOException, TransformerException {
        LOGGER.log(Level.INFO, "Starting actual mergin of the fields...");
        TransformerFactory factory = TransformerFactory.newInstance();
        Source xslt = new StreamSource(new File(urlXslt.toURI()));
        Transformer transformer = factory.newTransformer(xslt);

        /* Passing fields as parameter */
        try {
            map.keySet().forEach(s -> {
                transformer.setParameter(s, map.get(s));
            });
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "An error occured while passing the parameters to the XSLT: " + ex);
        }

        Source text = new StreamSource(isXml);

        File tempFile = File.createTempFile("document", "xml", new File(this.getClass().getResource("/docs/").getFile()));
        tempFile.deleteOnExit();
        transformer.transform(text, new StreamResult(tempFile));

        return tempFile.toPath();
    }

    /**
     * Overwrites the old document.xml in the Docx-archive with the new one.
     *
     * @param urlDocx the new document.xml
     * @throws IOException
     */
    private void replaceFileDocx(File urlDocx, Path documentTemp) throws IOException {
        LOGGER.log(Level.INFO, "Replacing old document.xml...");
        URI uri = URI.create("jar:file:" + urlDocx);

        Map<String, String> env = new HashMap<>();
        env.put("create", "true");

        try (FileSystem zipFS = FileSystems.newFileSystem(uri, env)) {
            Files.move(documentTemp, zipFS.getPath("/word/document.xml"), REPLACE_EXISTING);
        }
    }
}
