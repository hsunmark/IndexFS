import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;


public class FSIndexImpl implements FSIndex {

	private HashMap<Integer, Filepath> pathMap;
	private HashMap<Integer, Fileinfo> fileMap;
	private ArrayList<Fileinfo> fileIndex;
	private long timeStart, timeStop, totalTime;
	private String pathToIndex;

	public void indexFiles() {
		FileTraverse ft = new FileTraverse();
		fileIndex = ft.indexFiles(pathToIndex);
	}

	public void printSize(int filesToPrint) {
		Collections.sort(fileIndex, new FileinfoSort());
		for(int i = 0; i < filesToPrint; i++) {
			System.out.println(fileIndex.get(i).getSize() + " MB" + "   " + fileIndex.get(i).getPath());
		}
	}

	public void printStats(FileTraverse ft) {
		System.out.println(ft.getNumberOfFiles() + " files found");
		System.out.println(ft.getNumberOfDirectories() + " directories found");
		System.out.println(ft.getNumberOfEmptyDirectories() + " empty directories found");
		System.out.println("Indexing built in: " + totalTime + " s");
	}

	public ArrayList<String> find(String string) throws IOException, ClassNotFoundException {
		if(pathMap == null) loadIndex();
		ArrayList<String> resultSet = new ArrayList<>();
		int pathHash = string.hashCode();
		try {
			for (int i = 0; i < pathMap.get(pathHash).getPath().size(); i++) {
				resultSet.add(pathMap.get(pathHash).getPath().get(i));
			}} catch(NullPointerException e) {
			System.out.println("No such file");
		}
		return resultSet;
	}

	private void loadIndex() throws IOException, ClassNotFoundException {

		InputStream inputStream = FSIndexImpl.class.getResourceAsStream("index/fs_hashmap.ser");
		InputStream inbuffer = new BufferedInputStream(inputStream);
		ObjectInput objIn = new ObjectInputStream(inbuffer);

		pathMap = (HashMap<Integer, Filepath>) objIn.readObject();
		objIn.close();
		inbuffer.close();
		inputStream.close();
	}

	public void rebuildIndex(String rootToIndexPath) throws IOException {
		pathToIndex = rootToIndexPath;
		pathMap = new HashMap<>();
		int hashcode = 0;
		indexFiles();

		for(int i = 0; i < fileIndex.size(); i++) {
			hashcode = fileIndex.get(i).getName().hashCode();
			if(pathMap.containsKey(hashcode)) {
				pathMap.get(hashcode).addPath(fileIndex.get(i).getPath());
			}
			else pathMap.put(hashcode, new Filepath(fileIndex.get(i).getName(), fileIndex.get(i).getPath()));
		}
		System.out.println("Done building hashmap");

		OutputStream outfile = new FileOutputStream(new File("src/index/fs_hashmap.ser"));
		OutputStream outbuffer = new BufferedOutputStream(outfile);
		ObjectOutput output = new ObjectOutputStream(outbuffer);
		output.writeObject(pathMap);
		output.close();
	}

	public long getTime() {
		return Calendar.getInstance().getTimeInMillis();
	}

}
