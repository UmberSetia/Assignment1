

import java.util.function.DoubleUnaryOperator;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class PolyShape extends Polygon{

	private int sides;

	private final ObservableList<Double> pPoints;
	private ControlPoint[] cPoints;

	public PolyShape(int sides){
		super();
		this.sides = sides;
		pPoints = this.getPoints();
		
		setFill(Color.LIGHTGREEN);
		setStroke(Color.GREY);
		registerControlPoints();
	}

	private void calculatePoints( double x, double y, double radius){
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

	public void registerControlPoints(){
		//to register control points create an array of control points,
		//have in mind every two points the polygon class getPoints() counts as one control point.

		//loop through all points of polygon getPoints() index by index,
		//again dont forget every 2 indices are considered one control point.
				
		//for every two indices manually add a ChangeListener to centerXProperty and centerYProperty
		//of your control point which extends Circle.
		
		//each ChangeListener will updated the corresponding index inside of the Polygon getPoints().
		
		cPoints = new ControlPoint[pPoints.size()/2];
		for (int i = 0; i < pPoints.size(); i+=2) {
			final int index = i;
			double centerXProperty = pPoints.get(i);
			double centerYProperty = pPoints.get(i+1);
			
		    pPoints.set(index, newXValue.doubleValue());		    	
		    pPoints.set(index+1, newYValue.doubleValue());

			this.cPoints[i/2] = new ControlPoint(centerXProperty,centerYProperty);
		}
				
	}

	public void reDraw( double x, double y, double radius){
		//clear your points then calculate the points again
		pPoints.clear();
		calculatePoints(x,y,radius);
	}

	public Node[] getControlPoints(){
		return cPoints;
	}
	
	
	
}
