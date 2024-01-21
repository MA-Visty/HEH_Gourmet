package be.heh.gourmet.adapter.out.image;

import be.heh.gourmet.application.port.in.IImageClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

@Repository
public class CloudinaryClientI implements IImageClient {

    @Override
    public void addImage(String name, MultipartFile image) {

    }

    @Override
    public void deleteImage(String name) {

    }
}
