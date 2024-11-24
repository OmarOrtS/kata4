package software.ulpgc.kata4.control;

import software.ulpgc.kata4.io.*;
import software.ulpgc.kata4.model.Movie;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class ImportCommand implements Command{
    public ImportCommand() {}

    @Override
    public void execute() {
        try {
            File input = getInputFile();
            File output = getOutputFile();
            doExecute(input, output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doExecute(File input, File output) throws Exception {
        try(MovieReader reader = createMovieReader(input);
            MovieWriter writer = createMovieWriter(output))  {
            while (true) {
                Movie movie = reader.read();
                if(movie == null) break;
                writer.write(movie);
            }
        }
    }

    private DatabaseMovieWriter createMovieWriter(File file) throws SQLException {
        return new DatabaseMovieWriter(deleteIfExists(file));
    }

    private File deleteIfExists(File file) {
        if (file.exists()) file.delete();
        return file;
    }

    private MovieReader createMovieReader(File file) throws IOException { return new FileMovieReader(file, new TsvMovieDeserializer()); }

    private File getOutputFile() { return new File("movies.db"); }

    private File getInputFile() { return new File(new File("title.basics.tsv").getAbsolutePath()); }
}
