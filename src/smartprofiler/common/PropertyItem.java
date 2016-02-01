package smartprofiler.common;

public class PropertyItem {
	
	private String nameAttr;
	private int statusAttr;
	
	public PropertyItem(String name, int status){
		nameAttr = name;
		statusAttr = status;
		
	}
	
	public String getName(){
		return nameAttr;
	}
	
	public void setName(String name){
		nameAttr = name;
	}
	
	public int getStatus(){
		return statusAttr;
	}
	
	public void setStatus(int status){
		statusAttr = status;
	}

}
