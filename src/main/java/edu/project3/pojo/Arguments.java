package edu.project3.pojo;

import java.util.List;

public record Arguments(
    List<String> paths,
    String from,
    String to,
    String format
) {
}
