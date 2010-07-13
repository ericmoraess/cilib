/**
 * Computational Intelligence Library (CIlib)
 * Copyright (C) 2003 - 2010
 * Computational Intelligence Research Group (CIRG@UP)
 * Department of Computer Science
 * University of Pretoria
 * South Africa
 *
 * This library is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, see <http://www.gnu.org/licenses/>.
 */
package net.sourceforge.cilib.functions.continuous.unconstrained;

import net.sourceforge.cilib.functions.ContinuousFunction;
import net.sourceforge.cilib.type.types.Real;
import net.sourceforge.cilib.type.types.container.Vector;
import net.sourceforge.cilib.math.Maths;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test the validity of the implmentation of the {@link Elliptic} function.
 * @author Bennie Leonard
 */
public class EllipticTest {

    private ContinuousFunction function;

    @Before
    public void instantiate() {
        this.function = new Elliptic();
    }

    /**
     * Test of evaluate method.
     */
    @Test
    public void testEvaluate() {
        function.setDomain("R(-2, 2)^2");

        Vector x = new Vector();

        x.append(new Real(0.0));
        x.append(new Real(0.0));
        Assert.assertEquals(0.0, function.apply(x), Maths.EPSILON);

        x.setReal(0, 1.0);
        x.setReal(1, 2.0);
        Assert.assertEquals(4000001, function.apply(x), Maths.EPSILON);
    }
}
