package software.ulpgc.kata4.io;

import software.ulpgc.kata4.model.Movie;

import java.io.*;

public class FileMovieReader implements MovieReader{
    private final MovieDeserializer deserializer;
    private final BufferedReader reader;

    public FileMovieReader(File file, MovieDeserializer deserializer) throws IOException {
        this.deserializer = deserializer;
        this.reader = new BufferedReader(
                new InputStreamReader(
                new FileInputStream(file)));
        this.reader.readLine();
    }

    @Override
    public Movie read() throws IOException { return deserialize(reader.readLine()); }

    private Movie deserialize(String line) {
        return line != null ?
                deserializer.deserialize(line) :
                null;
    }

    @Override
    public void close() throws Exception { this.reader.close(); }
}
