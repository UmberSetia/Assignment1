

import java.util.function.DoubleUnaryOperator;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.shape.Polygon;

public class PolyShape extends Polygon{

	private int sides;

	private final ObservableList< Double> pPoints;
	private ControlPoint[] cPoints;

	public PolyShape( int sides){
		super();
		this.sides = sides;
		//Initialize variable with appropriate data
	}

	private void cacluatePoints( double x, double y, double radius){
		final double shift = radianShift( sides);
		for( int side = 0; side < sides; side++){
			pPoints.addAll( point( Math::cos, radius, shift, side, sides) + x
					, point( Math::sin, radius, shift, side, sides) + y);
		}
	}

	private double radianShift( final int SIDES){
		return Math.PI / 2 - Math.PI / SIDES;
	}

	private double point( DoubleUnaryOperator operation, double radius, double shift, double side, final int SIDES ){
		return radius * operation.applyAsDouble( shift + side * 2.0 * Math.PI / SIDES);
	}

	private void registerControlPoints(){
		//to register control points create an array of control points,
		//have in mind every two points the polygon class getPoints() counts as one control point.

		//loop through all points of polygon getPoints() index by index,
		//again dont forget every 2 indices are considered one control point.

		//for every two indices manually add a ChangeListener to centerXProperty and centerYProperty
		//of your control point which extends Circle.
		
		//each ChangeListener will updated the corresponding index inside of the Polygon getPoints().
	}

	public void reDraw( double x, double y, double radius){
		//clear your points then calculate the points again
	}

	public Node[] getControlPoints(){
		return cPoints;
	}
}
