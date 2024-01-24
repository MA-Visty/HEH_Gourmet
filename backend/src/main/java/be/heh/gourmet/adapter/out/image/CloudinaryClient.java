package be.heh.gourmet.adapter.out.image;

import be.heh.gourmet.application.port.in.IImageClient;
import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.cloudinary.utils.ObjectUtils;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class CloudinaryClient implements IImageClient {
    @Value("${CLOUDINARY_URL}")
    private String CLOUDINARY_URL;

    private Cloudinary cloudinary;

    @PostConstruct
    public void init() {
        cloudinary = new Cloudinary(CLOUDINARY_URL);
    }

    @Override
    public URL addImage(Optional<String> name, MultipartFile image) throws IOException {
        Uploader uploader = cloudinary.uploader();
        if (name.isEmpty()) {
            Map res = uploader.upload(image.getBytes(), ObjectUtils.emptyMap());
            return new URL((String) res.get("url"));
        } else {
            uploader.upload(image.getBytes(), ObjectUtils.asMap("public_id", name.get()));
            return new URL(cloudinary.url().generate(name.get()));
        }
    }

    @Override
    public void deleteImage(String name) throws Exception {
        cloudinary.api().deleteResources(List.of(name), ObjectUtils.emptyMap());
    }

    @Override
    public void deleteImage(URL url) throws Exception {
        String urlPath = url.getPath();
        String publicId = urlPath.substring(urlPath.lastIndexOf("upload/v") + 19, urlPath.lastIndexOf("."));
        Logger logger = org.slf4j.LoggerFactory.getLogger(CloudinaryClient.class);
        logger.info("publicId: " + publicId);
        cloudinary.api().deleteResources(List.of(publicId), ObjectUtils.emptyMap());
    }

    @Override
    public URL get(String name) throws MalformedURLException {
        // TODO: test with name that is not hosted on cloudinary
        return new URL(cloudinary.url().generate(name));
    }
}
