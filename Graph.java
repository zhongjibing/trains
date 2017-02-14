package icezhg.trains;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhongjibing on 2017/2/14.
 */
public class Graph {

    private final List<String> NODE = new ArrayList<>();

    private final Matrix MATRIX = new Matrix();

    private int nodeIndex(String node) {
        int index = NODE.indexOf(node);
        if (index > -1) {
            return index;
        }
        NODE.add(node);
        return NODE.size() - 1;
    }

    private String nodeName(int index) {
        if (index < 0 || index > NODE.size() - 1) {
            throw new IllegalArgumentException("node index is out of range");
        }
        return NODE.get(index);
    }

    public String[] getFromNodes(String toNode) {
        if (!NODE.contains(toNode)) {
            throw new IllegalArgumentException("node is not exists: " + toNode);
        }

        List<String> list = new ArrayList<>();
        int[] col = MATRIX.getCol(nodeIndex(toNode));
        for (int i = 0; i < col.length; i++) {
            if (col[i] > 0) {
                list.add(nodeName(i));
            }
        }
        return list.toArray(new String[]{});
    }

    public String[] getToNodes(String fromNode) {
        if (!NODE.contains(fromNode)) {
            throw new IllegalArgumentException("node is not exists: " + fromNode);
        }

        List<String> list = new ArrayList<>();
        int[] row = MATRIX.getRow(nodeIndex(fromNode));
        for (int i = 0; i < row.length; i++) {
            if (row[i] > 0) {
                list.add(nodeName(i));
            }
        }
        return list.toArray(new String[]{});
    }

    public Graph draw(String fromNode, String toNode, int weight) {
        MATRIX.setVal(nodeIndex(fromNode), nodeIndex(toNode), weight);
        return this;
    }

    public List<String[]> findPathByWeight(String toNode, int maxTotalWeight) {
        return null; // todo
    }

    public List<String[]> findPathByDeep(String toNode, int maxDeep) {
        return null; // todo
    }

    @Override
    public String toString() {
        return MATRIX.toString();
    }
}
