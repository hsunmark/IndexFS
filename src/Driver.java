import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

public class Driver {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ArrayList<String> resultSet;
        FSIndex indexInterace = new FSIndexImpl();
        //String rootPath = JOptionPane.showInputDialog("Enter path root to index");

        //indexInterace.rebuildIndex(rootPath);
        while (true) {
            String query = JOptionPane.showInputDialog("Enter filename");
            resultSet = indexInterace.find(query);
            for (int i = 0; i < resultSet.size() ; i++) {
                System.out.println(resultSet.get(i));
            }
        }
    }

}
