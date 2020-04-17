package com.evolveum.midscribe.generator;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * Created by Viliam Repan (lazyman).
 */
public class VelocityGeneratorProcessor {

    private VelocityEngine engine;

    public VelocityGeneratorProcessor(File template, Properties props) {
        init(template, props);
    }

    private void init(File template, Properties properties) {
        Properties props = new Properties();
        props.put(RuntimeConstants.RESOURCE_LOADER, "composite");
        props.put("composite.resource.loader.instance", new CompositeResourceLoader(template));

        if (properties != null) {
            props.putAll(properties);
        }

        engine = new VelocityEngine();
        engine.init(props);
    }

    private Template getTemplate(String name) {
        return engine.getTemplate(name, StandardCharsets.UTF_8.name());
    }

    public void process(Writer output, GeneratorContext ctx) throws IOException {
        Template template = getTemplate("/template/documentation.vm");
        if (template == null) {
            return;
        }

        VelocityContext context = new VelocityContext();
        context.put("configuration", ctx.getConfiguration());
        context.put("client", ctx.getClient());
        context.put("utils", TemplateUtils.class);
        context.put("processor", new ProcessorUtils(ctx));

        template.merge(context, output);
    }
}
