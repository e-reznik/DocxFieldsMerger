/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public class XsltCreator {

    public void init(Map<String, String> fields, File outputXslt) throws FileNotFoundException, IOException {
        // TODO: Logger
        StringBuilder inputBuffer;
        try (BufferedReader file = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/xslt/template.txt")))) {
            inputBuffer = new StringBuilder();
            String line;

            String param1 = ""; // TODO: String Builder
            String param2 = ""; // TODO: String Builder
            String param3 = ""; // TODO: String Builder
            for (String field : fields.keySet()) {
                param1 += "<xsl:param name='" + field + "' />\n";
                param2 += "<xsl:template match=\"*[following-sibling::*[1]/*[text()=' MERGEFIELD " + field + " '] | preceding-sibling::*[position() &lt; 4]/*[text()=' MERGEFIELD " + field + " ']]\"/>\n";
                param3 += "<xsl:template match=\"w:instrText[text()=' MERGEFIELD " + field + " ']\">\n<xsl:element name=\"w:t\">\n<xsl:value-of select=\"$" + field + "\" />\n</xsl:element>\n</xsl:template>\n\n";
            }

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

            try (FileOutputStream fileOut = new FileOutputStream(outputXslt + File.separator + "notes.xslt")) {
                fileOut.write(inputStr.getBytes());
            }

        }
    }
}
