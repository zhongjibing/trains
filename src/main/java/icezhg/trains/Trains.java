package icezhg.trains;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by zhongjibing on 2017/2/14.
 */
public class Trains {
    private static final String INPUT_FILE = "input.txt";

    public static void main(String[] args) throws IOException {
        String path = args.length > 0 ? args[0] : INPUT_FILE;

        File file = new File(path);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
        br.close();
        System.out.println(Arrays.toString(args));
    }
}
