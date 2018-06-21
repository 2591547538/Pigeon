package com.pigeon.communication.utils;

import java.io.IOException;
import java.io.InputStream;

public class StubInputStream extends InputStream {

    private boolean closed = false;

    @Override
    public int read() throws IOException {

        return 0;
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
