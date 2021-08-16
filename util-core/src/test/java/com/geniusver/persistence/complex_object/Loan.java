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

package com.geniusver.persistence.complex_object;

import com.geniusver.persistence.Entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Loan implements Entity, Serializable {
    private String id;
    private Integer totalMonth;
    private List<RepaymentPlan> repaymentPlans;
    private int version;

    public Loan(String id, Integer totalMonth, int version) {
        this.id = id;
        this.totalMonth = totalMonth;
        this.version = version;
    }

    public void createPlans() {
        List<RepaymentPlan> plans = new ArrayList<>();
        for (int i = 0; i < totalMonth; i++) {
            plans.add(new RepaymentPlan(i, BigDecimal.ONE, "PLAN"));
        }

        repaymentPlans = plans;
    }

    public void payPlan(int planNo) {
        for (RepaymentPlan repaymentPlan : repaymentPlans) {
            if (repaymentPlan.getNo().equals(planNo)) {
                repaymentPlan.setStatus("PAID");
                return;
            }
        }

        throw new IllegalArgumentException(String.format("loan no (%s, %d) not exists.", id, planNo));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getTotalMonth() {
        return totalMonth;
    }

    public void setTotalMonth(Integer totalMonth) {
        this.totalMonth = totalMonth;
    }

    public List<RepaymentPlan> getRepaymentPlans() {
        return repaymentPlans;
    }

    public void setRepaymentPlans(List<RepaymentPlan> repaymentPlans) {
        this.repaymentPlans = repaymentPlans;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
