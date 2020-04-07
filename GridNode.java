import java.util.*;

public class GridNode {
    public int x,y;
    public String value;
    public HashSet<GridNode> adj;

    public GridNode(int xInt, int yInt, String val) {
        x = xInt;
        y = yInt;
        value = val;
        adj = new HashSet<GridNode>();
    }
}