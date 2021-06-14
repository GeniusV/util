/*
 * Copyright 2021 GeniusV
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

    @Override
    public String toString() {
        return "ArthasConfig{" +
                "javaPath='" + javaPath + '\'' +
                ", arthasBootJarPath='" + arthasBootJarPath + '\'' +
                '}';
    }
}
