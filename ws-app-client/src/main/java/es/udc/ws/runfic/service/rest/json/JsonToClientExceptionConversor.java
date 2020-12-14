package es.udc.ws.runfic.service.rest.json;

import java.io.InputStream;

public class JsonToClientExceptionConversor {
    public static Exception fromNotFoundErrorCode(InputStream content) {
        throw new UnsupportedOperationException();
    }

    public static Exception fromBadRequestErrorCode(InputStream content) {
        throw new UnsupportedOperationException();
    }

    public static Exception fromGoneErrorCode(InputStream content) {
        throw new UnsupportedOperationException();
    }
}
