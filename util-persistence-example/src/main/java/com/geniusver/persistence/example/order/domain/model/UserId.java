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

package com.geniusver.persistence.example.order.domain.model;


import cn.hutool.core.lang.Assert;
import com.geniusver.persistence.example.common.Default;
import com.geniusver.persistence.example.order.domain.external.UserService;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * UserId
 *
 * @author GeniusV
 */
@Getter
@EqualsAndHashCode
@ToString
public class UserId {
    public final long value;

    @Default
    public UserId(long value) {
        this.value = value;
    }

    public UserId() {
        this.value = -1;
    }

    public boolean validate(UserService userService) {
        Assert.notNull(userService);
        Assert.isTrue(value >= 0);
        Assert.isTrue(userService.isUserExists(this));
        return true;
    }
}
