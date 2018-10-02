import javafx.beans.property.DoubleProperty;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

class ControlPoint extends Circle {
		private DoubleProperty x,y;
		final Delta delta = new Delta();
		
		ControlPoint(double x, double y){
			super();
			super.setRadius(4);
			super.setCenterX(x);
			super.setCenterY(y);
					
			setFill(Color.GREY);
			setStroke(Color.GREY);
			super.setRadius(10);
			
			this.x.bind(centerXProperty());
			this.y.bind(centerYProperty());
			registerMouseEvents();
		}
		
		public void registerMouseEvents() {
			addEventHandler(MouseEvent.MOUSE_PRESSED, (e)->pressed(e));
			addEventHandler(MouseEvent.MOUSE_DRAGGED, (e)->dragged(e));
			addEventHandler(MouseEvent.MOUSE_RELEASED, (e)->released(e));
		}

		private void pressed(MouseEvent e) {
			e.consume();
			delta.x = getCenterX() - e.getX();
			delta.y = getCenterY() - e.getY();
		}

		private void dragged(MouseEvent e) {
			e.consume();
			double newXValue = e.getX() + delta.x;
			double newYValue = e.getY() + delta.y;
			if (newXValue > 0 && newXValue < getScene().getWidth()) {
				setCenterX(newXValue);
			}
			if (newYValue > 0 && newYValue < getScene().getHeight()) {
				setCenterY(newYValue);
			}
			
		}

		private void released(MouseEvent e) {
			//e.consume();
		}
			
		private class Delta {double x, y;}
}
