package com.exzeo.index.template.reader;

import org.apache.log4j.Logger;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

/**
 * Hello world!
 */
public class App {
    private static final Logger LOGGER = Logger.getLogger(App.class.getName());

    public static void main(String[] args) throws IOException {

        File file = new File("/disk2/workspace/exzeo-proplet/etc/common/worker-conf/template.yaml");
        File file1 = new File("/disk2/workspace/exzeo-proplet/etc/common/worker-conf/myyamlfile.yaml");

        String firstName = "ajay";
        String lastName = "soni";
        Map<String, Object> yamlInfo = null;
        yamlInfo = readTemplate(file);
        System.out.println("======first ======="+yamlInfo);
        Iterator it = yamlInfo.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();
            if(pairs.getKey().equals("firstName")) {
                yamlInfo.put((String) pairs.getKey(), firstName);
            }
            if(pairs.getKey().equals("lastName")) {
                yamlInfo.put((String) pairs.getKey(), lastName);
            }
        }
        writeYamlByMap(yamlInfo,file1);
        System.out.println("=======second======="+readTemplate(file1));
    }

    public static Map<String, Object> readTemplate(File file) throws IOException {

        Map<String, Object> valuesFromTemplate = null;

        new YAMLSecurityConfigurationBuilder(file);
        try {
            valuesFromTemplate = YAMLSecurityConfigurationBuilder.parse();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return valuesFromTemplate;
    }

    public static void writeYamlByMap(Map<String,Object> yamlInfo, File file) {

        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Yaml yaml = new Yaml(options);
        FileWriter writer = null;
        try {
            writer = new FileWriter(file);
            yaml.dump(yamlInfo, writer);

        } catch (Exception ex) {
            LOGGER.error("Error occured in writing the yaml file", ex);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (Exception ex) {
                    LOGGER.error("Error occured in closing the yaml file", ex);
                }
            }
        }
    }

}

