package com.evolveum.midscribe.cmd.action;

import com.evolveum.midscribe.generator.ExportFormat;

public enum OutputFormat {

    ASCIIDOC(null),

    PDF(ExportFormat.PDF),

    HTML(ExportFormat.HTML);

    private final ExportFormat format;

    OutputFormat(ExportFormat format) {
        this.format = format;
    }

    public ExportFormat getFormat() {
        return format;
    }
}
