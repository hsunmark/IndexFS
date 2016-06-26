import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class FileTraverse implements Serializable {
	private static final long serialVersionUID = -5325866362422121113L;

	private int numberOfFiles = 0;
	private int numberOfDirs = 0;
	private int numberOfEmptyDirs = 0;
	private ArrayList<Fileinfo> fileData;
	private ArrayList<String> dirList;
	
	public ArrayList<Fileinfo> indexFiles(String url) {
		//Used temporary for traversing file system
		dirList = new ArrayList<>();
		fileData = new ArrayList<>();
		String path;
		File directory, currentFile;

		dirList.add(url); //Initial URL
		while(!dirList.isEmpty()) {
			int listOfFilesLength = 0;
			directory = new File(dirList.get(0));
			File[] listOfFiles = directory.listFiles();
			
			try{
				listOfFilesLength = listOfFiles.length;
				if(listOfFilesLength == 0)
					numberOfEmptyDirs++;
			} catch (NullPointerException e) {
				System.out.println("Empty directory: " + dirList.get(0));
			}

			for(int j= 0; j < listOfFilesLength; j++) {
				path = listOfFiles[j].getPath();
				if(listOfFiles[j].isFile()) {
					currentFile = new File(path);
					fileData.add(new Fileinfo(path, parseFileName(path), currentFile.length()));
					numberOfFiles++;

				} else if(listOfFiles[j].isDirectory()) {
					numberOfDirs++;
					dirList.add(listOfFiles[j].getPath());
				}
			}
			//try removing last index instead. Time will be O(1) instead of O(n)
			dirList.remove(0);
		}
		return fileData;
	}
	
	private String parseDirName(String pathToParse) {
		int lastSlash = pathToParse.lastIndexOf("\\");
		String parsedFileName = pathToParse.substring(lastSlash+1, pathToParse.length());
		return parsedFileName;
	}
	private String parseFileName(String pathToParse) {
		int lastSlash = pathToParse.lastIndexOf("\\");
		int dot = pathToParse.indexOf(".");
		String parsedFileName;
		try{
		parsedFileName = pathToParse.substring(lastSlash+1, dot);
		} catch(StringIndexOutOfBoundsException e) {
			parsedFileName = pathToParse.substring(lastSlash, pathToParse.length());
		}
		return parsedFileName;
	}

	public int getNumberOfFiles() {
		return numberOfFiles;
	}
	public int getNumberOfDirectories() {
		return numberOfDirs;
	}
	public int getNumberOfEmptyDirectories() {
		return numberOfEmptyDirs;
	}
}
