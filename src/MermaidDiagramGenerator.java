
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class MermaidDiagramGenerator {
    public static void main(String[] args) throws IOException {
        // Use JFileChooser to let the user select the datapackage.json file
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select datapackage.json");

        int result = fileChooser.showOpenDialog(null);
        if (result != JFileChooser.APPROVE_OPTION) {
            System.out.println("No file selected. Exiting.");
            return;
        }

        File jsonFile = fileChooser.getSelectedFile();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(jsonFile);

        StringBuilder mermaid = new StringBuilder("flowchart LR\n");

        // First, declare each table node
        for (JsonNode resource : root.get("resources")) {
            String name = resource.get("name").asText();
            mermaid.append("    ").append(name).append("\n");
        }

        // Now add foreign key relationships as arrows
        for (JsonNode resource : root.get("resources")) {
            String source = resource.get("name").asText();
            JsonNode schema = resource.get("schema");
            if (schema.has("foreignKeys")) {
                for (JsonNode fk : schema.get("foreignKeys")) {
                    String target = fk.get("reference").get("resource").asText();
                    JsonNode fields = fk.get("fields");

                    String keyLabel;
                    if (fields.isArray()) {
                        StringBuilder labelBuilder = new StringBuilder();
                        for (int i = 0; i < fields.size(); i++) {
                            if (i > 0) labelBuilder.append(", ");
                            labelBuilder.append(fields.get(i).asText());
                        }
                        keyLabel = labelBuilder.toString();
                    } else {
                        keyLabel = fields.asText();
                    }

                    mermaid.append("    ").append(source)
                           .append("-->|")
                           .append(keyLabel)
                           .append("|")
                           .append(target).append("\n");
                }
            }
        }

        // Print Mermaid diagram
        System.out.println("```mermaid");
        System.out.println(mermaid.toString());
        System.out.println("```");
    }
}
