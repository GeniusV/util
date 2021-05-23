package com.geniusver.util;

import cn.hutool.core.io.resource.NoResourceException;
import cn.hutool.setting.dialect.Props;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author GeniusV
 */
class ArthasUtilTest {

    @Test
    void loadNonExistProperties() {
        assertThrows(NoResourceException.class, () -> {
            Props props = new Props("not exist");
        });
    }
}