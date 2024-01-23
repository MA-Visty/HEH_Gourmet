package be.heh.gourmet.application.port.in;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

public interface IImageClient {
    URL addImage(Optional<String> name, MultipartFile image) throws IOException;

    void deleteImage(String name) throws IOException;

    void deleteImage(URL url) throws Exception;

    URL get(String name) throws MalformedURLException;
}
