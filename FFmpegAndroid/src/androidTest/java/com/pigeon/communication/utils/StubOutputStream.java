package com.pigeon.communication.utils;

import java.io.IOException;
import java.io.OutputStream;

public class StubOutputStream extends OutputStream {

    private boolean closed = false;

    @Override
    public void write(int oneByte) throws IOException {
    }

    @Override
    public void close() throws IOException {
        super.close();
        closed = true;
    }

    public boolean isClosed() {
        return closed;
    }
}
