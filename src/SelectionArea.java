
import java.util.function.Consumer;

import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SelectionArea extends Rectangle{

	private Point2D start;

	public SelectionArea(){
		super();
		setOpacity( .4);
		setStrokeWidth( 2);
		setStroke( Color.GRAY);
		setFill( Color.LIGHTGRAY);
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

	public void clear(){
		setX( 0);
		setY( 0);
		setWidth( 0);
		setHeight( 0);
	}

	public boolean contains( Node node){
		return getBoundsInLocal().contains( node.getBoundsInLocal());
	}

	public void containsAny( ObservableList< Node> nodes, Consumer< Node> filter){
		nodes.filtered( this::contains).forEach( filter);
	}
}
