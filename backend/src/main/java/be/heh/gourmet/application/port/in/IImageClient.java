package be.heh.gourmet.application.port.in;

import org.springframework.web.multipart.MultipartFile;

public interface IImageClient {
    void addImage(String name, MultipartFile image);

    void deleteImage(String name);
}
