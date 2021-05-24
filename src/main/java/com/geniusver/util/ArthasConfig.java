package com.geniusver.util;

import cn.hutool.core.io.resource.NoResourceException;
import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.dialect.Props;

import java.util.Properties;

/**
 * @author GeniusV
 */
public class ArthasConfig {
    private String javaPath = "java";
    private String arthasBootJarPath = "../arthas-boot.jar";
    public static final String CUSTOM_PROPERTIES_NAME = "arthas-util-config.properties";

    public ArthasConfig() {
        init();
    }

    private  synchronized void init() {
        Properties prop = null;
        try {
            prop = new Props(CUSTOM_PROPERTIES_NAME);
        } catch (NoResourceException e) {
            System.out.println(CUSTOM_PROPERTIES_NAME + " not exist");
            return;
        }
        javaPath = StrUtil.isEmpty(prop.getProperty("javaPath")) ? javaPath : prop.getProperty("javaPath");
        arthasBootJarPath = StrUtil.isEmpty(prop.getProperty("arthasBootJarPath")) ? arthasBootJarPath : prop.getProperty("arthasBootJarPath");
    }

    public String getJavaPath() {
        return javaPath;
    }

    public String getArthasBootJarPath() {
        return arthasBootJarPath;
    }

    public void setJavaPath(String javaPath) {
        this.javaPath = javaPath;
    }

    public void setArthasBootJarPath(String arthasBootJarPath) {
        this.arthasBootJarPath = arthasBootJarPath;
    }

    @Override
    public String toString() {
        return "ArthasConfig{" +
                "javaPath='" + javaPath + '\'' +
                ", arthasBootJarPath='" + arthasBootJarPath + '\'' +
                '}';
    }
}
