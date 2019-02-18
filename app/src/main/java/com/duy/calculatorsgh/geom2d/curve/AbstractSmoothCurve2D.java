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

/**
 * File: 	AbstractSmoothCurve2D.java
 * Project: javaGeom
 * <p>
 * Distributed under the LGPL License.
 * <p>
 * Created: 21 mai 09
 */
package com.duy.calculatorsgh.geom2d.curve;

import com.duy.calculatorsgh.geom2d.Point2D;
import com.duy.calculatorsgh.geom2d.line.LinearShape2D;

import java.util.ArrayList;
import java.util.Collection;


/**
 * Provides a base implementation for smooth curves.
 *
 * @author dlegland
 */
public abstract class AbstractSmoothCurve2D implements LinearShape2D, ContinuousCurve2D, Cloneable {


    /**
     * Returns an empty set of Point2D, as a smooth curve does not have
     * singular points by definition.
     *
     * @see Curve2D#singularPoints()
     */
    public Collection<Point2D> singularPoints() {
        return new ArrayList<>(0);
    }

    /**
     * Returns a set of Point2D, containing the extremities of the curve
     * if they are not infinite.
     *
     * @see Curve2D#vertices()
     */
    public Collection<Point2D> vertices() {
        ArrayList<Point2D> array = new ArrayList<Point2D>(2);
        if (!Double.isInfinite(this.t0()))
            array.add(this.firstPoint());
        if (!Double.isInfinite(this.t1()))
            array.add(this.lastPoint());
        return array;
    }

    /**
     * Returns always false, as a smooth curve does not have singular points
     * by definition.
     *
     * @see Curve2D#isSingular(double)
     */
    public boolean isSingular(double pos) {
        return false;
    }


}
