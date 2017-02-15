package icezhg.trains;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by zhongjibing on 2017/2/15.
 */
public class Route implements Serializable {
    private static final long serialVersionUID = 6406437598828659762L;

    private String head;

    private String end;

    private final List<String> trace = new LinkedList<>();

    private int distance = 0;

    public Route(String head) {
        if (head == null) {
            throw new IllegalArgumentException("head node should not be null");
        }
        this.head = head;
        trace.add(head);
    }

    public String getHead() {
        return head;
    }

    public String getEnd() {
        return end;
    }

    public List<String> getTrace() {
        return new ArrayList<>(trace);
    }

    public int getDistance() {
        return distance;
    }

    public int getStop() {
        return trace.size() - 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Route route = (Route) o;

        return distance == route.distance && head.equals(route.head)
                && (end != null ? end.equals(route.end) : route.end == null) && trace.equals(route.trace);
    }

    @Override
    public int hashCode() {
        int result = head.hashCode();
        result = 31 * result + (end != null ? end.hashCode() : 0);
        result = 31 * result + trace.hashCode();
        result = 31 * result + distance;
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Route:[");
        for (int i = 0; i < this.trace.size(); i++) {
            builder.append(this.trace.get(i));
            if (i < this.trace.size() - 1 || this.trace.size() == 1) {
                builder.append("-");
            }
        }
        builder.append(":").append(distance).append("]");
        return builder.toString();
    }

    public void appendNode(String node, int distance) {
        if (distance == 0) {
            return;
        }

        this.trace.add(node);
        this.end = node;
        this.distance += distance;
    }

    public void appendRoute(Route route) {
        if (route == null || route.isEmpty()) {
            return;
        }
        if (this.isEmpty()) {
            throw new IllegalStateException("current route is empty");
        }
        if (!this.end.equals(route.getHead())) {
            throw new IllegalArgumentException("append route head should be current route end");
        }
        List<String> appendTrace = route.getTrace();
        for (int i = 1; i < appendTrace.size(); i++) {
            String at = appendTrace.get(i);
            trace.add(at);
        }
        end = route.getEnd();
        distance += route.getDistance();
    }

    public Route replace(String node, Route route) {
        if (!containsNode(node)) {
            throw new IllegalArgumentException("not exists node: " + node);
        }
        if (!route.getHead().equals(route.getEnd())) {
            throw new IllegalArgumentException("replacing route not closed");
        }
        if (!route.getHead().equals(node)) {
            throw new IllegalArgumentException("replacing route should start with " + node);
        }

        Route retRoute = new Route(head);
        retRoute.end = end;
        for (int i = 1; i < trace.size(); i++) {
            retRoute.trace.add(trace.get(i));
        }
        retRoute.distance = distance;

        int index = retRoute.trace.indexOf(node);
        List<String> replacing = route.getTrace();
        for (int i = 1; i < replacing.size(); i++) {
            retRoute.trace.add(index + i, replacing.get(i));
        }
        retRoute.distance += route.getDistance();
        return retRoute;
    }

    public boolean containsNode(String node) {
        return trace.contains(node);
    }

    public void clear() {
        trace.clear();
        trace.add(head);

        end = null;
        distance = 0;
    }

    public boolean isEmpty() {
        return trace.size() == 1;
    }

}
