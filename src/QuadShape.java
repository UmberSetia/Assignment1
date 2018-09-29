import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class QuadShape extends Rectangle{
	private Point2D start;

	public QuadShape(){
		super();
		setOpacity(1);
		setStrokeWidth(2);
		setStroke( Color.LIGHTGREEN);
		setFill( Color.LIGHTGREEN);
	}

	public void start( double x, double y){
		start = new Point2D( x, y);
		setX( x);
		setY( y);
	}

	public void end( double x, double y){
		double width = x - start.getX();
		double height = y - start.getY();
		setX( width < 0 ? x : start.getX());
		setY( height < 0 ? y : start.getY());
		setWidth( Math.abs( width));
		setHeight( Math.abs( height));
	}
}
