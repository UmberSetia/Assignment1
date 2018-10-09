import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.event.EventTarget;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;



public class MapArea extends Pane{
	
	private PolyShape activeShape;
	private double initialX, initialY;
	private ObservableList<Node> children;
	private List<ControlPoint> selectedShapes;
	private ToolState tool;
	private SelectionArea selectArea;
	private boolean selected;
	
	public MapArea() {
		super();
		tool = ToolState.getState();
		children = this.getChildren();
		registerMouseEvents();
		this.setStyle("-fx-background-color: white");
		selectedShapes = new ArrayList<ControlPoint>();
	}
	
	public void registerMouseEvents() {
		addEventHandler(MouseEvent.MOUSE_PRESSED, (e)->pressed(e));
		addEventHandler(MouseEvent.MOUSE_DRAGGED, (e)->dragged(e));
		addEventHandler(MouseEvent.MOUSE_RELEASED, (e)->released(e));
	}
	
	private Tool activeTool() {
		return tool.getTool();
	}

	public void pressed(MouseEvent e) {

		initialX = e.getX();
		initialY = e.getY();
		
		switch(activeTool())
		{
			case Door: break;
			case Move:
				break;
			case Path: break;
			case Select:
				selectArea = new SelectionArea();
				selectArea.start(initialX, initialY);
				children.add(selectArea);
				break;
			case Erase:
				removeShape(e);
				break;
			case Room: 			
				activeShape = new PolyShape(tool.getOption());
				children.add(activeShape);
				break;
		default:
			break;
		}
	}

	public void dragged(MouseEvent e) {
		switch(tool.getTool()) 
		{
		case Door: break;
		case Move:
			if (selected == true ) {
				for (int i = 0; i < selectedShapes.size(); i++) {
					selectedShapes.get(i).translate(e.getX()-initialX,e.getY()-initialY);
				}
			} else {
				if (e.getTarget() instanceof ControlPoint) {
					moveControlPoint(e.getTarget(),e);
				} 
				if (e.getTarget() instanceof PolyShape) {
					moveShape(e.getTarget(),e);
				}
			}
						
			initialX = e.getX();
			initialY = e.getY();		
			break;
		case Path: break;
		case Select:
			selectArea.end(e.getX(), e.getY());
			break;
		case Erase: break;
		case Room: 
			activeShape.reDraw(initialX, initialY, e.getX(), e.getY(), true);	
			break;
		default:
			break;			
		}
	}
	

	public void released(MouseEvent e) {
		if (selected == true) {
			deselectChildren();
		}
		
		switch(tool.getTool()) {
		case Door: break;
		case Move: break;
		case Path: break;
		case Select:
			confirmSelected();
			children.remove(selectArea);		
			break;
		case Erase: break;
		case Room: 
			activeShape.registerControlPoints();
            children.addAll(activeShape.getControlPoints());
			break;
		default:
			break;
		}
		
	}
	
	private void removeShape(MouseEvent e) {
		children.removeAll(((PolyShape) e.getTarget()).getControlPoints());
		children.remove(e.getTarget());
		
	}
	
	private void moveControlPoint(EventTarget eventTarget, MouseEvent e) {
		((ControlPoint) eventTarget).translate(e.getX()-initialX,e.getY()-initialY);
	}
	
	private void moveShape(EventTarget eventTarget, MouseEvent e) {
		Node[] cPoints = ((PolyShape) eventTarget).getControlPoints();
		for(int i = 0; i < cPoints.length; i++) {
			((ControlPoint) cPoints[i]).translate(e.getX()-initialX,e.getY()-initialY);
		}
	}
		
	private void confirmSelected() {
		for (int i = 0; i < children.size(); i++) {
			if (selectArea.contains(children.get(i))) {
				((ControlPoint) children.get(i)).setFill(Color.BLACK);
				selectedShapes.add((ControlPoint) children.get(i));
				selected = true;

			}
		}
	}
	
	private void deselectChildren() {
		for (int i = 0; i < selectedShapes.size(); i++) {
			selectedShapes.get(i).setFill(Color.LIGHTGRAY);
		}		
		selectedShapes.clear();
		selectArea.clear();
		selected = false;
	}
	
}