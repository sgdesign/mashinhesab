/*
 * Copyright (C) 2018 Duy Tran Le
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.duy.calculatorsgh.model;

import android.content.Context;

import com.duy.calculatorsgh.evaluator.MathEvaluator;
import com.duy.calculatorsgh.evaluator.Constants;

/**
 * Created by Duy on 06-Jan-17.
 */

public class NumberIntegerItem extends ExprInput {
    private String number = "";
    private String function = "";

    public NumberIntegerItem(String number) {
        this.number = number;
    }

    public NumberIntegerItem() {
        this.number = Constants.ZERO;
    }

    public int length() {
        return number.length();
    }

    @Override
    public boolean isError(MathEvaluator evaluator) {
        return false;
    }

    @Override
    public String getInput() {
        return this.function + "(" + number + ")";
    }

    @Override
    public String toString() {
        return number;
    }

    @Override
    public String getError(MathEvaluator evaluator, Context applicationContext) {
        return null;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String cmd) {
        this.function = cmd;
    }
}
