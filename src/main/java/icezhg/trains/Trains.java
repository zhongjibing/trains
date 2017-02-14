package icezhg.trains;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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

        System.out.println("Output #1: " + graph.weight("A", "B", "C"));
        System.out.println("Output #2: " + graph.weight("A", "D"));
        System.out.println("Output #3: " + graph.weight("A", "D", "C"));
        System.out.println("Output #4: " + graph.weight("A", "E", "B", "C", "D"));
        System.out.println("Output #5: " + graph.weight("A", "E", "D"));
        // todo 定义类, 包含路径和距离
        System.out.println("Output #6: " + graph.weight("A", "E"));
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
