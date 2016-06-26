import java.io.Serializable;
import java.util.Comparator;


public class Fileinfo implements Serializable {
	private static final long serialVersionUID = -4745925989706931662L;
	private String path;
	private String name;
	private long size;
	
	Fileinfo(String path, String name, long size) {
		this.path = path;
		this.name = name;
		this.size = size / 1000000; //MB
	}
	
	public long getSize() {
		return size;
	}

	public String getName() {
		return name;
	}

	public String getPath() {
		return path;
	}

}

class FileinfoSort implements Comparator<Fileinfo>
{
	@Override
	public int compare(Fileinfo file1, Fileinfo file2) 
	{
		return (int) (file2.getSize() - file1.getSize()); 
	}
	
}