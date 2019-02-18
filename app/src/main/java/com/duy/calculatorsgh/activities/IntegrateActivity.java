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

package com.duy.calculatorsgh.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.duy.calculatorsgh.R;
import com.duy.calculatorsgh.activities.base.AbstractEvaluatorActivity;
import com.duy.calculatorsgh.calc.BasicCalculatorActivity;
import com.duy.calculatorsgh.evaluator.EvaluateConfig;
import com.duy.calculatorsgh.evaluator.MathEvaluator;
import com.duy.calculatorsgh.evaluator.exceptions.ExpressionChecker;
import com.duy.calculatorsgh.evaluator.thread.Command;
import com.duy.calculatorsgh.model.IntegrateItem;
import com.duy.calculatorsgh.utils.ConfigApp;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;

import java.util.ArrayList;

/**
 * Integrate(f(x), {x, a, b})
 * Integrate of function f(x) with variable x, with lower limit a, upper limit b
 * Created by Duy on 07-Dec-16.
 */
public class IntegrateActivity extends AbstractEvaluatorActivity {
    private static final String STARTED = IntegrateActivity.class.getName() + "started";
    private boolean isDataNull = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(getString(R.string.integrate));
        mHint1.setHint(getString(R.string.enter_function));
        btnSolve.setText(R.string.integrate);

        //hide and show view
        mLayoutLimit.setVisibility(View.VISIBLE);
        getIntentData();

        boolean isStarted = mPreferences.getBoolean(STARTED, false);
        if ((!isStarted) || ConfigApp.DEBUG) {
            if (isDataNull) {
                mInputFormula.setText("sqrt(1-x^2)/x^2");
                editFrom.setText("sqrt(2)/2");
                editTo.setText("1");
            }
            clickHelp();
        }

    }

    /**
     * get data from activity start it
     */
    private void getIntentData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(BasicCalculatorActivity.DATA);
        if (bundle != null) {
            String data = bundle.getString(BasicCalculatorActivity.DATA);
            if (data != null) {
                mInputFormula.setText(data);
                isDataNull = false;
                clickEvaluate();
            }
        }
    }


    @Override
    public void clickHelp() {
        final SharedPreferences.Editor editor = mPreferences.edit();
        TapTarget target0 = TapTarget.forView(mInputFormula,
                getString(R.string.enter_function),
                getString(R.string.input_integrate_here));
        target0.drawShadow(true)
                .cancelable(true)
                .targetCircleColor(R.color.colorAccent)
                .transparentTarget(true)
                .outerCircleColor(R.color.colorPrimary)
                .dimColor(R.color.colorPrimaryDark).targetRadius(70);

        TapTarget target1 = TapTarget.forView(editFrom,
                getString(R.string.lower_limit),
                getString(R.string.limit_from_desc))
                .drawShadow(true)
                .cancelable(true)
                .targetCircleColor(R.color.colorAccent)
                .transparentTarget(true)
                .outerCircleColor(R.color.colorPrimary)
                .dimColor(R.color.colorPrimaryDark).targetRadius(70);

        TapTarget target2 = TapTarget.forView(editTo,
                getString(R.string.upper_limit),
                getString(R.string.limit_to_desc));
        target2.drawShadow(true)
                .cancelable(true)
                .targetCircleColor(R.color.colorAccent)
                .transparentTarget(true)
                .outerCircleColor(R.color.colorPrimary)
                .dimColor(R.color.colorPrimaryDark);

        TapTarget target3 = TapTarget.forView(btnSolve,
                getString(R.string.integrate),
                getString(R.string.push_integrate_button));
        target3.drawShadow(true)
                .cancelable(true)
                .targetCircleColor(R.color.colorAccent)
                .transparentTarget(true)
                .outerCircleColor(R.color.colorPrimary)
                .dimColor(R.color.colorPrimaryDark);

        TapTargetSequence sequence = new TapTargetSequence(IntegrateActivity.this);
        sequence.targets(target0, target1, target2, target3);
        sequence.listener(new TapTargetSequence.Listener() {
            @Override
            public void onSequenceFinish() {
                editor.putBoolean(STARTED, true);
                editor.apply();
                clickEvaluate();
            }

            @Override
            public void onSequenceCanceled(TapTarget lastTarget) {

            }
        });
        sequence.start();
    }

    @Override
    protected String getExpression() {
        String inp = mInputFormula.getCleanText();

        //check empty input
        String from = editFrom.getText().toString();
        if (from.isEmpty()) {
            editFrom.requestFocus();
            editFrom.setError(getString(R.string.enter_limit));
            return null;
        }

        try {
            ExpressionChecker.checkExpression(from);
        } catch (Exception e) {
            hideKeyboard();
            handleExceptions(editFrom, e);
            return null;
        }

        //check empty input
        String to = editTo.getText().toString();
        if (to.isEmpty()) {
            editTo.requestFocus();
            editTo.setError(getString(R.string.enter_limit));
            return null;
        }

        try {
            ExpressionChecker.checkExpression(to);
        } catch (Exception e) {
            hideKeyboard();
            handleExceptions(editTo, e);
            return null;
        }

        IntegrateItem integrateItem = new IntegrateItem(inp,
                editFrom.getText().toString(),
                editTo.getText().toString());
        return integrateItem.getInput();
    }

    @Override
    public Command<ArrayList<String>, String> getCommand() {
        return new Command<ArrayList<String>, String>() {
            @Override
            public ArrayList<String> execute(String input) {
                EvaluateConfig config = EvaluateConfig.loadFromSetting(getApplicationContext());
                String fraction = MathEvaluator.getInstance().evaluateWithResultAsTex(input,
                        config.setEvalMode(EvaluateConfig.FRACTION));

                String decimal = MathEvaluator.getInstance().evaluateWithResultAsTex(input,
                        config.setEvalMode(EvaluateConfig.DECIMAL));

                ArrayList<String> result = new ArrayList<>();
                result.add(fraction);
                result.add(decimal);
                return result;
            }
        };
    }


}
