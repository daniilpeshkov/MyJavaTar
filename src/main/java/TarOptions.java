import com.beust.jcommander.Parameter;
import com.beust.jcommander.converters.FileConverter;
import java.io.File;
import java.util.List;


public final class TarOptions {

    @Parameter(
            description = "files for packing",
            converter = FileConverter.class,
            validateWith = FileValidator.class,
            order = 1
    )
    public List<File> files;

    @Parameter(names = {"-u", "--unpack"},
            description = "name of input file",
            converter = FileConverter.class,
            validateWith = FileValidator.class,
            arity = 1,
            order = 2
    )
    public File inputFile;

    @Parameter(names = {"-out", "--output"},
            description = "name of output file",
            converter = FileConverter.class,
            arity = 1,
            order = 3
    )
    public File outputFile;
}
