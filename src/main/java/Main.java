import com.beust.jcommander.JCommander;

import java.io.File;

public class Main {

    public static void main(String[] args) {

        TarOptions options = new TarOptions();
        JCommander jCommander = JCommander.newBuilder().addObject(options).build();
        jCommander.setProgramName("tar");

        try {
            jCommander.parse(args);

            if (options.outputFile != null & options.files != null & options.inputFile == null) {
                FilePackager.packFiles(options.files, options.outputFile);
            } else if (options.outputFile == null & options.files == null & options.inputFile != null) {
                FilePackager.unpackFiles(options.inputFile);
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

    }
}
