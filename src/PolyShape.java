

import java.util.function.DoubleUnaryOperator;
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
		
		shapeColor(Color.LIGHTGREEN);
		shapeStroke(Color.GREY);
		
		registerControlPoints();
	}
	
	public void shapeColor(Color color) {
		this.setFill(color);
	}
	
	public void shapeStroke(Color color) {
		this.setStroke(color);
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
		
		this.cPoints = new ControlPoint[pPoints.size()/2];
		for (int i = 0; i < pPoints.size(); i+=2) {
			final int index = i;
			cPoints[i/2] = new ControlPoint(pPoints.get(i), pPoints.get(i+1));
			
		    this.cPoints[i/2].addChangeListener(
		    		(value, oldV, newV) -> pPoints.set(index, newV.doubleValue()),
		    		(value, oldV, newV) -> pPoints.set(index+1, newV.doubleValue())
		    		);	    
		}
				
	}

	public void reDraw( double x, double y, double radius){
		pPoints.clear();
		calculatePoints(x,y,radius);
	}

	public Node[] getControlPoints(){
		return cPoints;
	}
	
	
	
}
