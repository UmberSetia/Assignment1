import javafx.beans.value.ChangeListener;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

class ControlPoint extends Circle {
		
		public ControlPoint(double x, double y){
			super(x,y,4,Color.LIGHTGREY);
		}

		public void addChangeListener(ChangeListener<Number> handlerX, ChangeListener<Number> handlerY) {
			centerXProperty().addListener(handlerX);
			centerYProperty().addListener(handlerY);	
		}

		public void translate(double dx, double dy) {
			centerXProperty().set(centerXProperty().get() + dx);
			centerYProperty().set(centerYProperty().get() + dy);
		}
}

