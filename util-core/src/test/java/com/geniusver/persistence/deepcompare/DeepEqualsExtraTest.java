/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2021 the original author or authors.
 */

package com.geniusver.persistence.deepcompare;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @author meixuesong
 */
public class DeepEqualsExtraTest {
    @Test
    public void should_support_custom_comparator() {
        SampleClass sampleClass1 = new SampleClass();
        SampleClass sampleClass2 = new SampleClass();

        sampleClass1.money = new BigDecimal("10.000001");
        sampleClass2.money = BigDecimal.TEN;

        DeepEquals deepEquals = new DeepEquals(new DeepEqualsDefaultOption());
        assertThat(deepEquals.isDeepEquals(sampleClass1, sampleClass2), is(true));
    }

    @Test
    public void should_ignore_specify_fields() {
        SampleClass sample1 = new SampleClass();
        SampleClass sample2 = new SampleClass();
        sample2.children.add(new SampleClass());

        DeepEqualsDefaultOption option = new DeepEqualsDefaultOption();
        Set<String> ignoredFieldNames = new HashSet<>();
        ignoredFieldNames.add("children");
        option.getIgnoreFieldNames().put(SampleClass.class, ignoredFieldNames);
        DeepEquals deepEquals = new DeepEquals(option);

        assertThat(deepEquals.isDeepEquals(sample1, sample2), is(true));
    }


    private static class SampleClass {
        private String id = "";
        private boolean checked = true;
        private int age = 0;
        private long milliseconds = 0;
        private float length = 0.00F;
        private double area = 0.00D;
        private BigDecimal money = BigDecimal.ZERO;
        private List<SampleClass> children = new ArrayList<>();
    }

}
