
public class ToolState {
	private final static ToolState toolState = new ToolState();
	private Tool tool = Tool.Select;
	private int option;
	
	public Tool getTool() {
		return this.tool;
	}
	
	public static ToolState getState() {
		return toolState;
	}
	
	public int getOption() {
		return option;
		
	}
	
	public void setTool(Tool tool) {
		this.tool = tool;
		
	}
	
	public void setOption(int option) {
		this.option = option;
	}
	

}
