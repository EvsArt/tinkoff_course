package edu.project3.service;

import edu.project3.pojo.Arguments;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArgumentsService {

    private String path = "path";
    private String from = "from";
    private String to = "to";
    private String format = "format";

    public Arguments getArgumentsMap(String[] args) {

        Map<String, List<String>> argMap = new HashMap<>();

        String key = "";
        List<String> values = new ArrayList<>();
        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith("--")) {
                argMap.put(key, values);
                values = new ArrayList<>();
                key = args[i].substring(2);
            } else {
                values.add(args[i]);
            }
        }
        argMap.put(key, values);

        if (!argMap.containsKey(path) || argMap.get(path).isEmpty()) {
            throw new IllegalArgumentException();
        }

        return new Arguments(
            argMap.get(path),
            argMap.get(from) == null ? null : argMap.get(from).getFirst(),
            argMap.get(to) == null ? null : argMap.get(to).getFirst(),
            argMap.get(format) == null ? null : argMap.get(format).getFirst()
        );

    }

}
