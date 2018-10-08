

import java.util.function.DoubleUnaryOperator;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class PolyShape extends Polygon{

	private int sides;
	private double angle;
	private double dx, dy;
	private double x1, y1;

	private final ObservableList<Double> pPoints;
	private ControlPoint[] cPoints;

	public PolyShape(int sides){
		super();
		this.sides = sides;
		pPoints = this.getPoints();
		
		shapeColor(Color.LIGHTGREEN);
		shapeStroke(Color.GREY);
		
	}
	
	public void shapeColor(Color color) {
		this.setFill(color);
	}
	
	public void shapeStroke(Color color) {
		this.setStroke(color);
	}
	

	private void calculatePoints(){
		for( int side = 0; side < sides; side++){
			pPoints.addAll( point( Math::cos, dx / 2, angle, side, sides) + x1,
					point( Math::sin, dy / 2, angle, side, sides) + y1);
		}
	}

	private double radianShift(double x1, double y1, double x2, double y2){
		return Math.atan2( y2 - y1, x2 - x1);
	}

	private double point( DoubleUnaryOperator operation, double radius, double shift, double side, final int SIDES){
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
	
	private double distance(double x1, double y1, double x2, double y2){
		return Math.sqrt( (x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
	}

	public void reDraw(double x1, double y1, double x2, double y2, boolean symmetrical){
		angle = radianShift(x1,y1,x2,y2);
		if (symmetrical == true) {
			this.dx = distance(x1,y1,x2,y2);
			this.dy = distance(x1,y1,x2,y2);
		} else {
			this.dx = x2 - x1;
			this.dy = y2 - y1;
		}
		
		this.x1 = x1 + ((x2 - x1)/2);
		this.y1 = y1 + ((y2 - y1)/2);
		
		pPoints.clear();
		calculatePoints();
	}

	public Node[] getControlPoints(){
		return cPoints;
	}

}
