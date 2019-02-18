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
import android.util.Log;

import com.duy.calculatorsgh.evaluator.MathEvaluator;
import com.duy.calculatorsgh.evaluator.Constants;
import com.duy.calculatorsgh.evaluator.FormatExpression;

/**
 * Created by Duy on 01-Jan-17.
 */

public class DerivativeItem extends ExprInput {
    private static final String TAG = "DerivativeItem";
    private String input;
    private String var = "x";
    private String level = "1";

    public DerivativeItem(String input, String var) {
        this.input = FormatExpression.cleanExpression(input);
        //if var = "", do not set var
        if (!var.isEmpty()) this.var = var;
    }

    public DerivativeItem(String input, String var, String level) {
        this.input = FormatExpression.cleanExpression(input);
        if (!var.isEmpty()) this.var = var;     //if var = "", do not set var
        this.level = level;
    }

    public DerivativeItem(String input) {
        this.input = FormatExpression.cleanExpression(input);
    }

    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var;
    }

    @Override
    public boolean isError(MathEvaluator evaluator) {
        return evaluator.isSyntaxError(input);
    }

    public String getInput() {
        //build mResult
        String res = Constants.DERIVATIVE +
                Constants.LEFT_PAREN +
                input + "," + "{" + var + "," + level + "}" +
                Constants.RIGHT_PAREN;
        //pri(2x + sin(x), x)
        Log.d(TAG, "getInput: " + res);
        return res;
    }

    @Override
    public String toString() {
        return this.getInput();
    }

    @Override
    public String getError(MathEvaluator evaluator, Context applicationContext) {
        return null;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
