package com.exzeo.index.template.reader;

/**
 * Created by ajay on 3/9/14.
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

/**
 * Created by ajay on 8/7/14.
 */
class YAMLSecurityConfigurationBuilder {
    static Logger logger = LoggerFactory.getLogger(YAMLSecurityConfigurationBuilder.class.getName());

    /**
     * Returns the config
     *
     * @return Map<String, Object>
     */
    public Map<String, Object> getConfig() {
        return config;
    }

    private static Map<String, Object> config;

    /**
     * Parses a YAML configuration file. Refer to `/etc/test/worker/lucene-config-sample.yaml`
     * in source directory
     *
     * @param yamlFile YAML formatted configuration file
     */
    public YAMLSecurityConfigurationBuilder(File yamlFile) throws IOException {

        InputStream yamlData = null;

        try {
            if (logger.isInfoEnabled()) {
                logger.info("YAML File path:" + yamlFile.getAbsolutePath());
            }
            yamlData = new FileInputStream(yamlFile);
            Yaml yaml=new Yaml();
            config = (Map<String, Object>) (yaml.load(yamlData));

        } finally {
            if (yamlData != null) {
                yamlData.close();
            }
        }
    }

    /**
     * Sets the config
     *
     * @param config
     */
    public YAMLSecurityConfigurationBuilder(Map<String, Object> config) {
        this.config = config;
    }

    /**
     * Parses YAML file and returns Configuration object
     *
     * @return Configuration
     * @throws IOException throws
     */
    public static Map<String,Object> parse() throws IOException {


        Map<String, Object> valuesFromDb = new HashMap<String, Object>();

        Iterator it = config.entrySet().iterator();
        while (it.hasNext()) {

            Map.Entry pairs = (Map.Entry) it.next();
            valuesFromDb.put((String) pairs.getKey(), pairs.getValue());
        }
        return valuesFromDb;
    }
}

