package edu.hw6;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task2 {

    public void cloneFile(Path path) throws FileNotFoundException {
        Objects.requireNonNull(path);

        if (!Files.exists(path)) {
            throw new FileNotFoundException("File not found in " + path);
        }

        String fileName = path.getFileName().toString();
        String newFileName = getNewCopyName(fileName);

        while (Files.exists(path.getParent().resolve(newFileName))) {
            newFileName = getNewCopyName(newFileName);
        }

        try {
            Files.createFile(path.resolveSibling(newFileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    protected String getNewCopyName(String oldFileName) {
        Objects.requireNonNull(oldFileName);
        if (oldFileName.isEmpty()) {
            throw new IllegalArgumentException();
        }

        String newName;

        int lastDotIndex = oldFileName.lastIndexOf('.');
        String fileExtension = lastDotIndex == -1 ? "" : oldFileName.substring(lastDotIndex);
        String fileName;
        if (!fileExtension.isEmpty()) {
            fileName = oldFileName.substring(0, lastDotIndex);
        } else {
            fileName = oldFileName;
        }

        Pattern copyPattern = Pattern.compile("\\s-\\sкопия(\\s\\((\\d+)\\))?$");
        Matcher matcher = copyPattern.matcher(fileName);

        String copyMarker = " - копия";
        if (!matcher.find()) {
            newName = fileName + copyMarker + fileExtension;
        } else {
            int copyNum = matcher.group(2) == null ? 1 : Integer.parseInt(matcher.group(2));
            int endFileNameIndex = fileName.lastIndexOf(copyMarker);
            fileName = fileName.substring(0, endFileNameIndex);
            newName = fileName + copyMarker + " (" + (copyNum + 1) + ")" + fileExtension;
        }

        return newName;

    }

}
