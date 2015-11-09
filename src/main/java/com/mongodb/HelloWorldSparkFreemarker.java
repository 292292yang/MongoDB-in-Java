package com.mongodb;

import freemarker.template.Configuration;
import freemarker.template.Template;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dhyey on 19-10-2015.
 */

public class HelloWorldSparkFreemarker {
    public static void main(String args[]){
        final Configuration config = new Configuration();
        config.setClassForTemplateLoading(HelloWorldSparkFreemarker.class, "/");
        Spark.get("/hello", new Route() {
            public Object handle(Request request, Response response) throws Exception {
                Template helloTemplate = config.getTemplate("Hello.ftl");
                StringWriter writer = new StringWriter();
                Map<String, Object> HelloMap = new HashMap<String, Object>();
                HelloMap.put("name","Freemarker");
                helloTemplate.process(HelloMap, writer);
                System.out.println(writer);
                return writer;
            }
        });
    }
}
