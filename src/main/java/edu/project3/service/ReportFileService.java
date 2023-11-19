package edu.project3.service;

import edu.project3.Constants;
import edu.project3.pojo.ReportTable;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("MultipleStringLiterals")
public class ReportFileService {

    Logger logger = LogManager.getLogger();

    private final Path parentPath = Constants.reportsPath;

    public Path createDocument(String format, List<ReportTable> tables) {

        try {
            if (format.equals("markdown")) {
                return createMDDocument(tables);
            } else {
                return createADOCDocument(tables);
            }
        } catch (IOException e) {
            logger.error("Error with writing report to file!");
            throw new RuntimeException(e);
        }

    }

    protected Path createMDDocument(List<ReportTable> tables) throws IOException {
        Path fileName = parentPath.resolve("report.md");
        Files.deleteIfExists(fileName);
        Files.createFile(fileName);
        try (OutputStream os = Files.newOutputStream(fileName)) {
            for (ReportTable table : tables) {
                os.write(("### " + table.name() + "\n").getBytes());
                List<String> columnsName = table.args().getFirst();
                os.write(("|" + String.join("|", columnsName) + "|\n").getBytes());
                os.write("|----------|----------|\n".getBytes());
                for (int i = 1; i < table.args().size(); i++) {
                    os.write(("|" + String.join("|", table.args().get(i)) + "|\n").getBytes());
                }
            }
        }
        logger.info("Report " + fileName + " has been formed");
        return fileName;
    }

    protected Path createADOCDocument(List<ReportTable> tables) throws IOException {
        Path fileName = parentPath.resolve("report.adoc");
        Files.deleteIfExists(fileName);
        Files.createFile(fileName);
        try (OutputStream os = Files.newOutputStream(fileName)) {
            for (ReportTable table : tables) {
                os.write(("=== " + table.name() + "\n").getBytes());
                List<String> columnsName = table.args().getFirst();
                String[] sizes = new String[columnsName.size()];
                Arrays.fill(sizes, "1");
                os.write(("[cols=\"" + String.join(",", sizes) + "\"]\n").getBytes());
                os.write("|===\n".getBytes());
                for (String s : columnsName) {
                    os.write(("|" + s + "\n").getBytes());
                }
                for (int i = 1; i < table.args().size(); i++) {
                    for (String s : table.args().get(i)) {
                        os.write(("|" + s + "\n").getBytes());
                    }
                }
                os.write("|===\n".getBytes());
            }
        }
        logger.info("Report " + fileName + " has been formed");
        return fileName;
    }

}
