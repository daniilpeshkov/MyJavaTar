
import java.io.*;
import java.nio.file.FileSystem;

import static junit.framework.TestCase.assertTrue;

public class Test {

    @org.junit.Test
    public void test() {
        Main.main("src\\test\\resources\\input\\kiminonawa.jpg -out src\\test\\resources\\output\\out1.out".split(" "));
        Main.main("-u src\\test\\resources\\output\\out1.out".split(" "));
        try {
            assertTrue(fileContentEquals(new File("src\\test\\resources\\input\\kiminonawa.jpg"),
                    new File("src\\test\\resources\\output\\kiminonawa.jpg")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @org.junit.Test
    public void test2() {
        Main.main("src\\test\\resources\\input\\日本.txt src\\test\\resources\\input\\2.txt -out src\\test\\resources\\output\\out2.out".split(" "));
        Main.main("-u src\\test\\resources\\output\\out2.out".split(" "));
        try {
            assertTrue(fileContentEquals(new File("src\\test\\resources\\input\\日本.txt"),
                    new File("src\\test\\resources\\output\\日本.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean fileContentEquals(File file1, File file2) throws IOException {
        FileInputStream fin1 = new FileInputStream(file1);
        FileInputStream fin2 = new FileInputStream(file2);
        while (fin1.available() * fin2.available() != 0) {
            int in1 = fin1.read();
            int in2 = fin2.read();
            if (in1 != in2) return false;
        }
        return true;
    }
}