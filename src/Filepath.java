import java.io.Serializable;
import java.util.ArrayList;


public class Filepath implements Serializable {
	private static final long serialVersionUID = 4028486387794280194L;
	private String name;
	private ArrayList<String> path;
	
	Filepath(String name, String path) {
		this.path = new ArrayList<String>();
		this.name = name; //IF this is set .toLowerCase, also change hashcodes .toLowerCase
		this.path.add(path);
	}


	public void setName(String nameToSet) {
		name = nameToSet;
	}
	public void addPath(String pathToFile) {
		path.add(pathToFile);
	}
	public ArrayList<String> getPath() {
		return path;
	}
	public String getName() {
		return name;
	}
}
