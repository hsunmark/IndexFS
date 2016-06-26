import java.io.IOException;
import java.util.ArrayList;

public interface FSIndex {

    void rebuildIndex(String rootToIndexpath) throws IOException;

    ArrayList<String> find(String string) throws IOException, ClassNotFoundException;

}
