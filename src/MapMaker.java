import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MapMaker extends Application{
	
	public static final String INFO_PATH = "resources/icons/info.txt";
	public static final String HELP_PATH = "resources/icons/help.txt";
	public static final String CREDITS_PATH = "resources/icons/credits.txt";
	public static final String CSS_PATH = "resources/css/style.css";
	Label selectedTool = new Label("Tool: ");
	FileChooser fc = new FileChooser();
	@Override
	public void init() throws Exception {
		super.init();
		
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane rootPane = new BorderPane();
		
		MapArea mapArea = new MapArea();			
		rootPane.setCenter(mapArea);
		
		MenuBar menuBar = new MenuBar(
				new Menu("File", null, 
						createMenuItem("New", (e)->{}), 
						//createMenuItem("Open", (e)->{}),
						createMenuItem("Save", (e)->{}),
						new SeparatorMenuItem(),
						createMenuItem("Exit", (e)->{Platform.exit();})),
				new Menu("Help", null, 
						createMenuItem("Credit", (e)->displayCredit()), 
						createMenuItem("Info", (e)->displayInfo()),
						new SeparatorMenuItem(),
						createMenuItem("Help", (e)->displayHelp()))
				);
		
		rootPane.setTop(menuBar);
		
		ToolBar toolBar = new ToolBar(
				selectedTool, 
				new Separator(),
				new Label("Options: {}")
				);
		toolBar.setPrefWidth(rootPane.getWidth());
		rootPane.setBottom(toolBar);
		
		ToolBar tools =  new ToolBar(
				createButton("Select", 
						(e)->{ToolState.getState().setTool(Tool.Select);
						selectedTool.setText("Tool: Select");}),
				createButton("Move", 
						(e)->{ToolState.getState().setTool(Tool.Move);
						selectedTool.setText("Tool: Move");}),
				createRoom(
						(e)->{ToolState.getState().setTool(Tool.Room);}),
				createButton("Path", 
						(e)->{ToolState.getState().setTool(Tool.Path);
						selectedTool.setText("Tool: Path");}),
				createButton("Erase", 
						(e)->{ToolState.getState().setTool(Tool.Erase);
						selectedTool.setText("Tool: Erase");}),
				createButton("Door", 
						(e)->{ToolState.getState().setTool(Tool.Door);
						selectedTool.setText("Tool: Door");})
				);
		tools.setOrientation(Orientation.VERTICAL);
		tools.setPrefWidth(52);
		rootPane.setLeft(tools);
				
		VBox detail = new VBox(
				new ListView<String>(),
				new Separator(),
				new GridPane());
		rootPane.setRight(detail);
		
		Scene scene = new Scene(rootPane,800,600);
		scene.getStylesheets().add( new File(CSS_PATH).toURI().toString());
		primaryStage.addEventHandler(KeyEvent.KEY_RELEASED, 
				e->primaryStage.hide());
		primaryStage.setScene(scene);
		primaryStage.setTitle("Map Maker");
		primaryStage.show();
	}
	

	@Override
	public void stop() throws Exception {
		super.stop();
	}
	
	private MenuButton createRoom(EventHandler<ActionEvent> handler) {
		Label roomIcon = new Label();
		roomIcon.setId("Room-icon");
		MenuButton item = new MenuButton("", roomIcon,
				createMenuItem("Line",(e)->{setRoomTool("Line", 2);}),
				createMenuItem("Triangle",(e)->{setRoomTool("Triangle", 3);}),
				createMenuItem("Rectangle",(e)->{setRoomTool("Rectangle", 4);}),
				createMenuItem("Pentagon",(e)->{setRoomTool("Pentagon", 5);}),
				createMenuItem("Hexagon",(e)->{setRoomTool("Hexagon", 6);}));
		item.setId("Room");
		item.setPopupSide(Side.RIGHT);
		item.setOnAction(handler);
		return item;
	}
	
	private MenuItem createMenuItem( String name, EventHandler<ActionEvent> handler) {
		Label icon = new Label();
		icon.setId( name + "-icon");
		MenuItem item = new MenuItem(name, icon);
		item.setId(name);
		item.setOnAction(handler);
		return item;
	}
	
	private Button createButton(String name, EventHandler<ActionEvent> handler) {
		Label icon = new Label();
		icon.setId(name + "-icon");
		Button item = new Button("", icon);
		item.setId(name);
		item.setOnAction(handler);
		return item;
	}
	
	private void setRoomTool(String name, int option) {
		ToolState.getState().setTool(Tool.Room);
		selectedTool.setText("Tool: Room: " + name);
		ToolState.getState().setOption(option);
	}

	private void displayAlert( String title, String context) {
		Alert alert = new Alert( AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setContentText(context);
		alert.show();
	}
	
	private String loadFile(String path) {
		String str = "";
		try {
			//str = Files.lines(Paths.get(path)).reduce(str,(String a, String b)-> {return a+b;});
			str = Files.lines(Paths.get(path)).reduce(str,(a, b)->a+System.lineSeparator()+b+System.lineSeparator());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	private void displayCredit() {
		displayAlert("Credit", loadFile(CREDITS_PATH));
	}
	
	private void displayHelp() {
		displayAlert("Help", loadFile(HELP_PATH));
		
	}
	
	private void displayInfo() {
		displayAlert("Info", loadFile(INFO_PATH));
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
