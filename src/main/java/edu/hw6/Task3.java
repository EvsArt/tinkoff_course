package edu.hw6;

import java.io.FileInputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;

public class Task3 {

    private Task3() {
    }

    public static AbstractFilter regularFile = Files::isRegularFile;
    public static AbstractFilter readable = Files::isReadable;

    public static AbstractFilter largerThan(long num) {
        return (Path path) -> Files.size(path) > num;
    }

    public static AbstractFilter magicNumber(int... bytes) {
        return (Path path) -> {
            byte[] fileBytes = new byte[bytes.length];
            try (FileInputStream fis = new FileInputStream(path.toFile())) {
                fis.read(fileBytes);
            }

            for (int i = 0; i < bytes.length; i++) {
                if (!Integer.toHexString(bytes[i])
                    .equals(String.format("%02x", fileBytes[i]))) {
                    return false;
                }
            }
            return true;
        };
    }

    @SuppressWarnings("MultipleStringLiterals")
    public static AbstractFilter globMatches(String glob) {
        String reg = glob
            .replaceAll("\\.", "\\\\.")
            .replaceAll("\\*", ".*");
        return (Path path) -> Pattern.compile("^" + reg + "$").matcher(
            path.getFileName().toString()
        ).matches();
    }

    public static AbstractFilter regexContains(String regex) {
        return (Path path) -> Pattern.compile(regex).matcher(
            path.getFileName().toString()
        ).find();
    }

    interface AbstractFilter extends DirectoryStream.Filter<Path> {
        default AbstractFilter and(AbstractFilter filter) {

            return (Path path) -> this.accept(path) && filter.accept(path);
        }
    }

}
