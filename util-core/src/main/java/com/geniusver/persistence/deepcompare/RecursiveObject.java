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

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * The recursive object used by DeepEquals
 * Based on the deep equals implementation of https://github.com/jdereg/java-util
 *
 * @author John DeRegnaucourt (john@cedarsoftware.com)
 * @author meixuesong
 * @author GeniusV
 */
class RecursiveObject {
    private Set<DualObject> visited = new HashSet<>();
    private Stack<DualObject> stack = new Stack<>();

    public void push(DualObject dk) {
        if (!visited.contains(dk)) {
            stack.push(dk);
        }
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public DualObject pop() {
        DualObject object = stack.pop();
        addVisited(object);

        return object;
    }

    private void addVisited(DualObject object) {
        visited.add(object);
    }
}
