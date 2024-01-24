package be.heh.gourmet.adapter.in.web.exeption;

import lombok.Getter;

import java.util.Date;
import java.util.HashMap;

@Getter
public class InternalServerError {
    HashMap<String, Object> response = new HashMap<>();

    public static HashMap<String, Object> response() {
        return new InternalServerError().response;
    }

    public InternalServerError() {
        response.put("timestamp", new Date().getTime());
        response.put("status", 500);
        response.put("error", "Internal Server Error");
    }
}
