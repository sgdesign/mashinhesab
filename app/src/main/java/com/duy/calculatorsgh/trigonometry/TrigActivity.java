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

package com.duy.calculatorsgh.trigonometry;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.duy.calculatorsgh.R;
import com.duy.calculatorsgh.activities.base.AbstractEvaluatorActivity;
import com.duy.calculatorsgh.data.SampleData;
import com.duy.calculatorsgh.evaluator.EvaluateConfig;
import com.duy.calculatorsgh.evaluator.MathEvaluator;
import com.duy.calculatorsgh.evaluator.thread.Command;
import com.duy.calculatorsgh.model.TrigItem;
import com.duy.calculatorsgh.utils.ConfigApp;
import com.google.common.collect.Lists;

import java.util.ArrayList;

import static com.duy.calculatorsgh.model.TrigItem.TRIG_TYPE.EXPAND;
import static com.duy.calculatorsgh.model.TrigItem.TRIG_TYPE.EXPONENT;
import static com.duy.calculatorsgh.model.TrigItem.TRIG_TYPE.REDUCE;

/**
 * Trigonometric Activity
 * Created by Duy on 31-Jan-17.
 */

public class TrigActivity extends AbstractEvaluatorActivity {
    public static final String TYPE = "TrigActivity";
    public static final String TAG = "TrigActivity";
    private int mType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        mType = intent.getIntExtra(TYPE, -1);
        if (mType == -1) {
            Toast.makeText(this, "Bundle nullable!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        init();
    }

    /**
     * - set title for activity
     * - Set text for button solve
     * - if this is first start activity or debug enable, give example data
     */
    private void init() {
        btnSolve.setText(R.string.eval);
        boolean started;
        switch (mType) {
            case EXPAND:
                setTitle(R.string.tit_trig_expand);
                started = mPreferences.getBoolean(TAG + "expand", false);
                if (!started || ConfigApp.DEBUG) {
                    mInputFormula.setText(SampleData.TRIG_EXPAND_DATA[0]);
                    mPreferences.edit().putBoolean(TAG + "expand", true).apply();
                }
                break;
            case REDUCE:
                setTitle(R.string.tit_trig_reduce);
                started = mPreferences.getBoolean(TAG + "reduce", false);
                if (!started || ConfigApp.DEBUG) {
                    mInputFormula.setText(SampleData.TRIG_REDUCE_DATA[0]);
                    mPreferences.edit().putBoolean(TAG + "reduce", true).apply();
                }
                break;
            case EXPONENT:
                setTitle(R.string.tit_trig_to_exp);
                started = mPreferences.getBoolean(TAG + "exponent", false);
                if (!started || ConfigApp.DEBUG) {
                    mInputFormula.setText(SampleData.TRIG_EXPONENT_DATA[0]);
                    mPreferences.edit().putBoolean(TAG + "exponent", true).apply();
                }
                break;

        }
    }

    @Override
    protected String getExpression() {
        TrigItem item = new TrigItem(mInputFormula.getCleanText());
        switch (mType) {
            case EXPAND:
                item.setType(EXPAND);
                break;
            case REDUCE:
                item.setType(REDUCE);
                break;
            case EXPONENT:
                item.setType(EXPONENT);
                break;
        }
        return item.getInput();
    }

    @Override
    public void clickHelp() {
    }


    @Override
    public Command<ArrayList<String>, String> getCommand() {
        return new Command<ArrayList<String>, String>() {
            @Override
            public ArrayList<String> execute(String input) {

                String fraction = MathEvaluator.getInstance().evaluateWithResultAsTex(input,
                        EvaluateConfig.loadFromSetting(getApplicationContext())
                                .setEvalMode(EvaluateConfig.FRACTION));
                return Lists.newArrayList(fraction);
            }
        };
    }
}
