package icezhg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import icezhg.trains.Graph;
import icezhg.trains.Route;
import junit.framework.TestCase;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {

    public void testNode() {
        Graph graph = new Graph();
        graph.draw("A", "B", 5);
        graph.draw("B", "C", 4);
        graph.draw("C", "D", 8);
        graph.draw("D", "C", 8);
        graph.draw("D", "E", 6);
        graph.draw("A", "D", 5);
        graph.draw("C", "E", 2);
        graph.draw("E", "B", 3);
        graph.draw("A", "E", 7);

        System.out.println(graph);
        System.out.println(Arrays.toString(graph.getFromNodes("B")));
        System.out.println(Arrays.toString(graph.getFromNodes("C")));
        List<Route> b = graph.getFromRoute("B");
        System.out.println(b);
        System.err.println(Arrays.toString(graph.getToNodes("C")));
        System.err.println(graph.getToRoute("C"));
        List<Route> route = graph.findRoute("C", "C");
        System.out.println(route);

        System.out.println(graph.findRoute("A", "A"));
        System.out.println(graph.findRoute("B", "B"));
        System.out.println(graph.findRoute("C", "C"));
        System.out.println(graph.findRoute("D", "D"));
        System.out.println(graph.findRoute("E", "E"));

    }

    public void testRoute() {
        Graph graph = new Graph();
        graph.draw("A", "D", 5);
        graph.draw("B", "C", 4);
        graph.draw("B", "E", 8);
        graph.draw("E", "B", 8);
        graph.draw("D", "B", 8);

        List<Route> route = graph.findRoute("A", "C");
        System.out.println(route);
    }

    public void testReplace() {
        List<String> list = new ArrayList<>(Arrays.asList("A", "B", "C", "D"));
        List<String> l = Arrays.asList("b", "E", "F", "b");
        int index = list.indexOf("B");
        System.out.println(index);
        for (int i = 1; i < l.size(); i++) {
            list.add(index + i, l.get(i));
        }
        System.out.println(list);
    }

    public void testReplaceRoute() {
        Route route = new Route("A");
        route.appendNode("B", 3);
        route.appendNode("C", 5);
        route.appendNode("D", 7);
        System.out.println("route:" + route + route.getStop());

        Route replacing = new Route("B");
        replacing.appendNode("E", 4);
        replacing.appendNode("F", 1);
        replacing.appendNode("B", 3);
        System.out.println("replacing:" + replacing + replacing.getStop());

        Route b = route.replace("B", replacing);
        System.out.println("result:" + route + route.getStop());
        System.out.println("result2:" + b + b.getStop());
    }
}
