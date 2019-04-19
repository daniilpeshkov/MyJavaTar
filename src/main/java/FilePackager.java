import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class FilePackager {

    private static int charByteLength(int a) {
        int length = 0;
        while (a != 0) {
            a >>=8;
            length+= 1;
        }
        return length;
    }

    private static String makeHeaderString(List<File> files) {
        StringBuilder header = new StringBuilder();
        files.forEach(file -> header.append(file.getName()).append(" ").append(file.length()).append(" "));
        return header.toString();
    }

    public static void writeFile(FileOutputStream fout, File file) throws IOException {
        FileInputStream fin = new FileInputStream(file);
        while (fin.available() > 0) {
            fout.write(fin.read());
        }
    }


    public static void packFiles(List<File> files, File outputFile) throws IOException {
        if (outputFile.exists()) throw new IllegalArgumentException("file " + outputFile.getName() + " already exists");
        FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
        for ( char a: makeHeaderString(files).toCharArray()) {
            fileOutputStream.write(a >> 8);
            fileOutputStream.write(a & 0xFF);
        }
        fileOutputStream.write('\n' >> 8);
        fileOutputStream.write('\n' & 0xFF);
        for (File file: files) {
            writeFile(fileOutputStream, file);
        }
        fileOutputStream.close();
    }



    public static void unpackFiles(File inputFile) throws IOException {

        FileInputStream fin = new FileInputStream(inputFile);
        StringBuilder header = new StringBuilder();

        char inputChar;
        while ((inputChar = (char) ((fin.read() << 8) | fin.read())) != '\n') {
            header.append(inputChar);
        }

        Pattern headerPattern = Pattern.compile("([^\\s]+\\s\\d+\\s*)+");
        Matcher matcher = headerPattern.matcher(header);

        if (matcher.matches()) {
            class elem {
                File file;
                long length;
                public elem(File file, long length) {
                    this.file = file;
                    this.length = length;
                }
            }

            List<elem> outputFiles = new ArrayList<>();

            Pattern headerElemPattern = Pattern.compile("[^\\s]+\\s\\d+\\s*");
            Matcher headerElemMatcher = headerElemPattern.matcher(header);
            headerElemMatcher.results().forEach(matchResult -> {
                String[] entries = matchResult.group().split(" ");
                outputFiles.add(new elem(new File(inputFile.getParent() + File.separator + entries[0]),
                        Long.valueOf(entries[1])));
            });

            for (elem elem: outputFiles) {
                FileOutputStream fout = new FileOutputStream(elem.file);
                for (long i = 1; i <= elem.length  ; i++) {
                    int in = fin.read();
                    if ( in == -1) throw new IllegalArgumentException("incorrect file type");
                    fout.write(in);
                }
            }
        } else {
            throw new IllegalArgumentException("incorrect file type");
        }

    }

}
