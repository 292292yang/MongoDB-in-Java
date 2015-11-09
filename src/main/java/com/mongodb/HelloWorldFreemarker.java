package com.mongodb;


import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dhyey on 19-10-2015.
 */
public class HelloWorldFreemarker {
    public static void main(String args[]) throws IOException, TemplateException {
        Configuration config = new Configuration();
        config.setClassForTemplateLoading(HelloWorldFreemarker.class, "/");
        Template helloTemplate = config.getTemplate("Hello.ftl");
        StringWriter writer = new StringWriter();
        Map<String, Object> HelloMap = new HashMap<String, Object>();
        HelloMap.put("name","Freemarker");
        helloTemplate.process(HelloMap, writer);
        System.out.println(writer);
    }
}
