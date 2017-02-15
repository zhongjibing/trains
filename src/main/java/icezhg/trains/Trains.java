package icezhg.trains;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhongjibing on 2017/2/14.
 */
public class Trains {

    public static void main(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("input is required");
        }

        Graph graph = creatGraph(args[0]);

        // #1 distance of route A-B-C
        Route r1 = graph.route("A", "B", "C");
        System.out.println("Output #1: " + (r1.isEmpty() ? "NO SUCH ROUTE" : "" + r1.getDistance()));

        // #2 distance of route A-D
        Route r2 = graph.route("A", "D");
        System.out.println("Output #2: " + (r2.isEmpty() ? "NO SUCH ROUTE" : "" + r2.getDistance()));

        // #3 distance of route A-D-C
        Route r3 = graph.route("A", "D", "C");
        System.out.println("Output #3: " + (r3.isEmpty() ? "NO SUCH ROUTE" : "" + r3.getDistance()));

        // #4 distance of route A-E-B-C-D
        Route r4 = graph.route("A", "E", "B", "C", "D");
        System.out.println("Output #3: " + (r4.isEmpty() ? "NO SUCH ROUTE" : "" + r4.getDistance()));

        // #5 distance of route A-E-D
        Route r5 = graph.route("A", "E", "D");
        System.out.println("Output #3: " + (r5.isEmpty() ? "NO SUCH ROUTE" : "" + r5.getDistance()));

        // #6 num of route C-C with max 3 stop
        List<Route> l6 = graph.findRouteByStopLess("C", "C", 3);
        System.out.println("Output #6: " + l6.size());

        // #7 num of route A-C with 4 stop
        List<Route> l7 = graph.findRouteByStop("A", "C", 4);
        System.out.println("Output #7: " + l7.size());

        // #8 length of shortest route A-C
        Route r8 = graph.findShortestRoute("A", "C");
        System.out.println("Output #8: " + r8.getDistance());

        // #9 length of shortest route B-B
        Route r9 = graph.findShortestRoute("B", "B");
        System.out.println("Output #9: " + r9.getDistance());

        // #10 num of route C-C
        List<Route> l10 = graph.findRouteByDistanceLess("C", "C", 30);
        System.out.println("Output #10: " + l10.size());

    }

    private static Graph creatGraph(String file) {
        StringBuilder context = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                context.append(line);
            }
        } catch (IOException e) {
            System.err.println("create graph error:" + e.toString());
        }

        Graph graph = new Graph();

        Pattern pattern = Pattern.compile("[a-zA-Z][a-zA-Z][0-9]");
        Matcher matcher = pattern.matcher(context.toString());
        while (matcher.find()) {
            String group = matcher.group();
            graph.draw("" + group.charAt(0), "" + group.charAt(1), Integer.parseInt("" + group.charAt(2)));
        }
        return graph;
    }
}
