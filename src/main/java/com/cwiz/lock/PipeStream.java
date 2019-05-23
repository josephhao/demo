package com.cwiz.lock;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

public class PipeStream {

    static class WriteData {
        void write(PipedWriter writer) {
            try {
                System.out.println("Write: ");
                int i = 30;
                while (i-- > 0) {
                    String msg = "" + i;
                    writer.write(msg);
                    System.out.print(msg);
                }
                System.out.println();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    static class ReadData {
        void read(PipedReader reader) {
            try {
                System.out.println("Read: ");
                int len = 0;
                char[] bytes = new char[1024];
                while ((len = reader.read(bytes)) != -1) {
                    String msg = new String(bytes, 0 , len);
                    System.out.print(msg);
                }
                System.out.println();
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static class ThreadReader extends Thread {
        ReadData readData;
        PipedReader reader;

        ThreadReader(ReadData readData, PipedReader reader) {
            this.readData = readData;
            this.reader = reader;
        }

        @Override
        public void run() {
            readData.read(reader);
        }
    }

    static class ThreadWriter extends Thread {
        WriteData writeData;
        PipedWriter writer;

        ThreadWriter(WriteData writeData, PipedWriter writer) {
            this.writeData = writeData;
            this.writer = writer;
        }

        @Override
        public void run() {
            writeData.write(writer);
        }
    }


    public static void main(String[] args) throws IOException, InterruptedException {
        WriteData writeData = new WriteData();
        ReadData readData = new ReadData();

        PipedWriter writer = new PipedWriter();
        PipedReader reader = new PipedReader();

        writer.connect(reader);
//        reader.connect(writer);

        ThreadWriter threadWriter = new ThreadWriter(writeData, writer);
        ThreadReader threadReader = new ThreadReader(readData, reader);

        threadReader.start();
        Thread.sleep(2000);
        threadWriter.start();

    }

}
