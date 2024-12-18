package com.evolveum.midscribe.generator.export;

import org.asciidoctor.Asciidoctor;
import org.asciidoctor.Attributes;
import org.asciidoctor.Options;
import org.asciidoctor.SafeMode;

import java.io.File;
import java.io.IOException;

/**
 * Created by Viliam Repan (lazyman).
 */
public class HtmlExporter extends ExporterBase {

    // todo figure out templates

    @Override
    public String getDefaultExtension() {
        return "html";
    }

    @Override
    public void export(File adocFile, File output) throws IOException {
        File dir = output.getAbsoluteFile().getParentFile();
        File file = new File(output.getName());

        String cssFilePath = "../../midscribe-core/src/main/resources/css/style.css";

        File cssFile = new File(cssFilePath);

        Attributes attributes = Attributes.builder()
                .styleSheetName(cssFile.exists() && cssFile.length() > 0 ? cssFilePath : null)
                .build();

        if (cssFile.exists() && cssFile.length() > 0) {
            Options options = Options.builder()
                    .safe(SafeMode.UNSAFE)
                    .toDir(dir)
                    .toFile(file)
                    .standalone(true)
                    .attributes(attributes)
                    .build();

            Asciidoctor doctor = createAsciidoctor();

            doctor.convertFile(adocFile, options);
        } else {
            // If the CSS file is empty or does not exist, convert without adding a custom stylesheet
            Options options = Options.builder()
                    .safe(SafeMode.UNSAFE)
                    .toDir(dir)
                    .toFile(file)
                    .standalone(true)
                    .build();

            try (Asciidoctor doctor = createAsciidoctor()) {
                doctor.convertFile(adocFile, options);
            }
        }
    }
}
