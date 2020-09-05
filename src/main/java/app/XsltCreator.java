package app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class XsltCreator {

    private final static Logger LOGGER = Logger.getLogger(XsltCreator.class.getName());

    public void init(Map<String, String> fields, File outputXslt) throws FileNotFoundException, IOException {
        LOGGER.log(Level.INFO, "Starting XSLT creation...");
        StringBuilder inputBuffer;
        try (BufferedReader file = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/xslt/template.txt")))) {
            inputBuffer = new StringBuilder();
            String line;

            StringBuilder param1 = new StringBuilder();
            StringBuilder param2 = new StringBuilder();
            StringBuilder param3 = new StringBuilder();

            fields.keySet().forEach((field) -> {
                param1.append("<xsl:param name='").append(field).append("' />\n");
                param2.append("<xsl:template match=\"*[following-sibling::*[1]/*[text()=' MERGEFIELD ").append(field).append(" '] | preceding-sibling::*[position() &lt; 4]/*[text()=' MERGEFIELD ").append(field).append(" ']]\"/>\n");
                param3.append("<xsl:template match=\"w:instrText[text()=' MERGEFIELD ").append(field).append(" ']\">\n<xsl:element name=\"w:t\">\n<xsl:value-of select=\"$").append(field).append("\" />\n</xsl:element>\n</xsl:template>\n\n");
            });

            while ((line = file.readLine()) != null) {
                if (line.contains("{param1}")) {
                    line = line.replace("{param1}", param1);
                } else if (line.contains("{param2}")) {
                    line = line.replace("{param2}", param2);
                } else if (line.contains("{param3}")) {
                    line = line.replace("{param3}", param3);
                }
                inputBuffer.append(line);
                inputBuffer.append('\n');
            }

            String inputStr = inputBuffer.toString();

            try (FileOutputStream fileOut = new FileOutputStream(outputXslt + File.separator + "doc.xslt")) {
                fileOut.write(inputStr.getBytes());
            }
        }
        LOGGER.log(Level.INFO, "Finished XSLT creation");
    }
}
