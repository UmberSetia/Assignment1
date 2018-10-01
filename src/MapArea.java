import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

public class MapArea extends Pane{
	
	private Shape activeShape;
	private double startX, startY, oldX, oldY;
	private ObservableList<Node> children;
	private ToolState tool;
	private SelectionArea selectArea;
	
	public MapArea() {
		super();
		tool = ToolState.getState();
		children = this.getChildren();
		registerMouseEvents();
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
		e.consume();
		startX = e.getX();
		startY = e.getY();
		oldX = e.getX();
		oldY = e.getY();
		
		switch(activeTool())
		{
			case Door:
			case Move:
			case Path:
			case Select:
				selectArea = new SelectionArea();
				selectArea.start(startX, startY);
				children.add(selectArea);
				break;
			case Erase:
				break;
			case Room: 
				int o = tool.getOption();
				if (o == 2) {
					activeShape = new Line(startX,startY,startX,startY);
				} else if (o == 4) {
					activeShape = new QuadShape();
					((QuadShape) activeShape).start(startX, startY);
				} else {
					activeShape = new PolyShape(o);
					((PolyShape) activeShape).getControlPoints();
				}
				children.add(activeShape);
				break;
		default:
			break;
		}
	}

	public void dragged(MouseEvent e) {
		e.consume();
		switch(tool.getTool()) 
		{
		case Door:
		case Move:
		case Path:
		case Select:
			selectArea.end(startX, startY);
			break;
		case Erase:
		case Room: 
			int o = tool.getOption();
			if (o == 2) {
				if (activeShape == null) break;
				((Line) activeShape).setEndX(e.getX());
				((Line) activeShape).setEndY(e.getY());
			} else if (o == 4) {
				((QuadShape) activeShape).end(startX, startY);
			} else {
				((PolyShape) activeShape).reDraw(startX, startY, distance(oldX, oldY, startX, startY));
			}
			break;
		default:
			break;
			
		}
		startX = e.getX();
		startY = e.getY();
		
		
	}
	
	public void released(MouseEvent e) {
		switch(tool.getTool()) {
		case Door:
		case Move:
		case Path:
		case Select:
			children.remove(selectArea);
			break;
		case Erase:
		case Room: 
			break;
		default:
			break;
		}
	}
	
	private double distance( double x1, double y1, double x2, double y2){
	    return Math.sqrt((x2-x1) * (x2-x1) + (y2-y1) * (y2-y1));
	}
}
