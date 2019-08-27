import java.io.*;

public class Ui {
    // iostreams
    private InputStream inStream;
    private BufferedReader in;
    private OutputStream outStream;
    private Writer out;

    private Ui(InputStream inStream, OutputStream outStream){
        this.inStream = inStream;
        this.in = new BufferedReader(new InputStreamReader(inStream));
        this.outStream = outStream;
        this.out = new PrintWriter(outStream);
    }

    public static Ui newUi(InputStream inStream, OutputStream outStream) {
        return new Ui(inStream, outStream);
    }

    public void close() {
        try {
            if (in != null) {
                in.close();
            }
        } catch (IOException e1) {

        }
        try {
            if (out != null) {
                out.close();
            }
        } catch (IOException e2) {

        }
    }

    public void write(String line) throws IOException {
        out.write(line);
    }

    public void flush() throws IOException {
        out.flush();
    }

    public String readLine() throws IOException {
        return in.readLine();
    }
}
