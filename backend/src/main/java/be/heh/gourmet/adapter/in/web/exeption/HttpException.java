package be.heh.gourmet.adapter.in.web.exeption;

import java.util.Date;
import java.util.HashMap;

public interface HttpException {
    default HashMap<String, Object> toResponse() {
        HashMap<String, Object> response = new HashMap<>();
        response.put("timestamp", new Date(System.currentTimeMillis()));
        response.put("error", getMessage());
        response.put("status", httpStatus());
        return response;
    }

    String getMessage();

    int httpStatus();
}
