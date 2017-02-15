package icezhg.trains;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhongjibing on 2017/2/14.
 */
public class Graph {

    private final List<String> NODE = new ArrayList<>();

    private final Matrix MATRIX = new Matrix();

    private final Map<String, List<Route>> CYCLE_ROUTES = new HashMap<>();

    private int nodeIndex(String node) {
        return NODE.indexOf(node);
    }

    private String nodeName(int index) {
        if (index < 0 || index > NODE.size() - 1) {
            throw new IllegalArgumentException("node index is out of range");
        }
        return NODE.get(index);
    }

    private int addNode(String node) {
        int index = nodeIndex(node);
        if (index > -1) {
            return index;
        }

        NODE.add(node);
        return NODE.size() - 1;
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

    public List<Route> getFromRoute(String toNode) {
        if (!NODE.contains(toNode)) {
            throw new IllegalArgumentException("node is not exists: " + toNode);
        }

        List<Route> retList = new ArrayList<>();
        int[] col = MATRIX.getCol(nodeIndex(toNode));
        for (int i = 0; i < col.length; i++) {
            if (col[i] > 0) {
                Route route = new Route(nodeName(i));
                route.appendNode(toNode, col[i]);
                retList.add(route);
            }
        }
        return retList;
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

    public List<Route> getToRoute(String fromNode) {
        if (!NODE.contains(fromNode)) {
            throw new IllegalArgumentException("node is not exists: " + fromNode);
        }

        List<Route> retList = new ArrayList<>();
        int[] row = MATRIX.getRow(nodeIndex(fromNode));
        for (int i = 0; i < row.length; i++) {
            if (row[i] > 0) {
                Route route = new Route(fromNode);
                route.appendNode(nodeName(i), row[i]);
                retList.add(route);
            }
        }
        return retList;
    }

    public Graph draw(String fromNode, String toNode, int weight) {
        MATRIX.setVal(addNode(fromNode), addNode(toNode), weight);
        return this;
    }

    public boolean containsNode(String node) {
        return NODE.contains(node);
    }

    public boolean containsRoute(String fromNode, String toNode) {
        return !route(fromNode, toNode).isEmpty();
    }

    public boolean containsRoute(String... nodes) {
        return !route(nodes).isEmpty();
    }

    public Route route(String fromNode, String toNode) {
        assertNodeExists(fromNode);
        assertNodeExists(toNode);

        Route route = new Route(fromNode);
        route.appendNode(toNode, MATRIX.getVal(nodeIndex(fromNode), nodeIndex(toNode)));
        return route;
    }

    public Route route(String... nodes) {
        if (nodes == null || nodes.length < 2) {
            throw new IllegalArgumentException("at least 2 nodes required");
        }

        Route retRoute = new Route(nodes[0]);
        for (int i = 0; i < nodes.length - 1; i++) {
            Route r = route(nodes[i], nodes[i + 1]);
            if (r.isEmpty()) {
                retRoute.clear();
                break;
            }
            retRoute.appendNode(nodes[i + 1], r.getDistance());
        }
        return retRoute;
    }

    public List<Route> findRoute(String fromNode, String toNode) {
        List<Route> retList = new ArrayList<>();
        List<Route> fromRoutes = getFromRoute(toNode);
        for (Route froute : fromRoutes) {
            if (froute.getHead().equals(fromNode)) {
                retList.add(froute);
                continue;
            }
            retList.addAll(findRoute(fromNode, froute));
        }
        return retList;
    }

    private List<Route> findRoute(String fromNode, Route toRoute) {
        List<Route> retList = new ArrayList<>();
        List<Route> fromRoutes = getFromRoute(toRoute.getHead());
        for (Route froute : fromRoutes) {
            if (froute.getHead().equals(fromNode)) {
                froute.appendRoute(toRoute);
                retList.add(froute);
                continue;
            }
            if (toRoute.containsNode(froute.getHead())) {
                continue;
            }

            froute.appendRoute(toRoute);
            retList.addAll(findRoute(fromNode, froute));
        }
        return retList;
    }

    private void updateCycleRoute() {
        for (String node : NODE) {
            CYCLE_ROUTES.put(node, findRoute(node, node));
        }
    }

    public List<Route> findRouteByStopLess(String fromNode, String toNode, int maxStop) {
        List<Route> retList = new ArrayList<>();
        for (Route route : findRouteByStopCyclically(fromNode, toNode, maxStop)) {
            if (route.getStop() <= maxStop) {
                retList.add(route);
            }
        }
        return retList;
    }

    public List<Route> findRouteByStop(String fromNode, String toNode, int stop) {
        List<Route> retList = new ArrayList<>();
        for (Route route : findRouteByStopCyclically(fromNode, toNode, stop)) {
            if (route.getStop() == stop) {
                retList.add(route);
            }
        }
        return retList;
    }

    public Route findShortestRoute(String fromNode, String toNode) {
        List<Route> routeList = findRoute(fromNode, toNode);
        if (routeList.isEmpty()) {
            return new Route(fromNode);
        }

        Route retRoute = routeList.get(0);
        for (int i = 1; i < routeList.size(); i++) {
            if (routeList.get(i).getDistance() < retRoute.getDistance()) {
                retRoute = routeList.get(i);
            }
        }
        return retRoute;
    }

    public List<Route> findRouteByDistanceLess(String fromNode, String toNode, int maxDistance) {
        List<Route> retList = new ArrayList<>();
        for (Route route : findRouteByDistanceCyclically(fromNode, toNode, maxDistance)) {
            if (route.getDistance() < maxDistance) {
                retList.add(route);
            }
        }
        return retList;
    }

    public List<Route> findRouteByDistanceCyclically(String fromNode, String toNode, int maxDistance) {
        updateCycleRoute();

        List<Route> routeList = findRoute(fromNode, toNode);
        for (int i = 0; i < routeList.size(); i++) {
            Route route = routeList.get(i);
            for (String node : NODE) {
                if (route.containsNode(node) && !CYCLE_ROUTES.get(node).isEmpty()) {
                    addReplacedRouteByDistance(routeList, route, node, maxDistance);
                }
            }
        }
        return routeList;
    }

    public List<Route> findRouteByStopCyclically(String fromNode, String toNode, int maxStop) {

        updateCycleRoute();

        List<Route> routeList = findRoute(fromNode, toNode);
        for (int i = 0; i < routeList.size(); i++) {
            Route route = routeList.get(i);
            for (String node : NODE) {
                if (route.containsNode(node) && !CYCLE_ROUTES.get(node).isEmpty()) {
                    addReplacedRouteByStop(routeList, route, node, maxStop);
                }
            }
        }
        return routeList;
    }

    private void addReplacedRouteByStop(List<Route> routeList, Route route, String node, int maxStop) {
        for (Route replacing : CYCLE_ROUTES.get(node)) {
            if (route.getStop() + replacing.getStop() <= maxStop) {
                Route replacedRoute = route.replace(node, replacing);
                if (!routeList.contains(replacedRoute)) {
                    routeList.add(replacedRoute);
                }
            }
        }
    }

    private void addReplacedRouteByDistance(List<Route> routeList, Route route, String node, int maxDistance) {
        for (Route replacing : CYCLE_ROUTES.get(node)) {
            if (route.getDistance() + replacing.getDistance() <= maxDistance) {
                Route replacedRoute = route.replace(node, replacing);
                if (!routeList.contains(replacedRoute)) {
                    routeList.add(replacedRoute);
                }
            }
        }
    }

    private void assertNodeIndexRange(int index) {
        if (index < 0 || index > NODE.size() - 1) {
            throw new IllegalArgumentException("node index is out of range");
        }
    }

    private void assertNodeExists(String node) {
        if (!containsNode(node)) {
            throw new IllegalArgumentException("not exists node: " + node);
        }
    }

    @Override
    public String toString() {
        return "Graph:[NODE:" + NODE.toString() + ", MATRIX:" + MATRIX.toString() + "]";
    }
}
