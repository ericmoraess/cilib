package net.sourceforge.cilib.pso.positionupdatestrategies;

import net.sourceforge.cilib.entity.Particle;
import net.sourceforge.cilib.pso.positionupdatestrategies.boundaryconstraintstrategies.BoundaryConstraintStrategy;
import net.sourceforge.cilib.pso.positionupdatestrategies.boundaryconstraintstrategies.ClampingBoundaryConstraintStrategy;
import net.sourceforge.cilib.type.types.Numeric;
import net.sourceforge.cilib.type.types.container.Vector;

/**
 * Standard Position Update Strategy with boundary constraint handling. The logic that determines if
 * and when a {@linkplain Particle} steps over it's boundaries is implemented in the
 * {@link #updatePosition(net.sourceforge.cilib.entity.Particle)} method. The desired effect is
 * achieved by specifying a {@linkplain BoundaryConstraintStrategy} using the
 * {@link #setBoundaryConstraintStrategy(BoundaryConstraintStrategy)} method. Because the
 * "overstepping logic" is handled in the above mentioned method, the specific
 * {@linkplain BoundaryConstraintStrategy} should implement two methods:
 * {@linkplain BoundaryConstraintStrategy#constrainLower(Numeric, Numeric)} and
 * {@linkplain BoundaryConstraintStrategy#constrainUpper(Numeric, Numeric)}.
 * @author Wiehann Matthysen
 */
public class BoundedPositionUpdateStrategy implements PositionUpdateStrategy {
	private static final long serialVersionUID = -1323696017002776467L;

	private BoundaryConstraintStrategy boundaryConstraintStrategy;

	public BoundedPositionUpdateStrategy() {
		boundaryConstraintStrategy = new ClampingBoundaryConstraintStrategy();
	}

	public BoundedPositionUpdateStrategy(BoundedPositionUpdateStrategy copy) {
		boundaryConstraintStrategy = copy.boundaryConstraintStrategy.clone();
	}

	public BoundedPositionUpdateStrategy clone() {
		return new BoundedPositionUpdateStrategy(this);
	}

	public void setBoundaryConstraintStrategy(BoundaryConstraintStrategy boundaryConstraintStrategy) {
		this.boundaryConstraintStrategy = boundaryConstraintStrategy;
	}

	public BoundaryConstraintStrategy getBoundaryConstraintStrategy() {
		return boundaryConstraintStrategy;
	}

	/**
	 * Update the position of the <tt>Particle</tt> so that it always stays within the domain
	 * boundaries. The logic
	 * @param particle The <tt>Particle</tt> to perform the position update on.
	 */
	public void updatePosition(Particle particle) {
		Vector position = (Vector) particle.getPosition();
		Vector velocity = (Vector) particle.getVelocity();

		for (int i = 0; i < position.getDimension(); ++i) {
			Numeric currentPosition = position.getNumeric(i);
			Numeric currentVelocity = velocity.getNumeric(i);
			double newPosition = position.getReal(i);
			newPosition += velocity.getReal(i);

			if (newPosition < currentPosition.getLowerBound()) { // crossed the lower boundary
				boundaryConstraintStrategy.constrainLower(currentPosition, currentVelocity);
			}
			else if (newPosition >= currentPosition.getUpperBound()) { // crossed the upper boundary
				boundaryConstraintStrategy.constrainUpper(currentPosition, currentVelocity);
			}
			else { // didn't cross any boundary, just update
				position.setReal(i, newPosition);
			}
		}
	}
}
