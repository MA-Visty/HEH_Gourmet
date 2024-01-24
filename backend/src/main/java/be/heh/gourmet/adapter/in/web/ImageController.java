package be.heh.gourmet.adapter.in.web;

import be.heh.gourmet.adapter.in.web.exeption.CustomException;
import be.heh.gourmet.adapter.in.web.exeption.InternalServerError;
import be.heh.gourmet.application.port.in.IImageClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("/api")
public class ImageController {
    @Autowired
    @Qualifier("getImageClient")
    IImageClient imageManager;

    @GetMapping("/image/{name}")
    public ResponseEntity<Object> getImage(@PathVariable String name) {
        try {
            URL url = imageManager.get(name);
            return new ResponseEntity<>(Map.of("url", url), null, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error while getting image", e);
            return ResponseEntity.internalServerError().body(InternalServerError.response());
        }
    }

    @DeleteMapping("/image")
    public ResponseEntity<Object> deleteImage(@RequestParam Optional<String> name, @RequestParam Optional<URL> url) {
        try {
            if (name.isEmpty() && url.isEmpty()) {
                CustomException e = new CustomException("Missing parameter", HttpStatus.BAD_REQUEST);
                return new ResponseEntity<>(e.toResponse(), null, e.httpStatus());
            } else if (name.isPresent() && url.isPresent()) {
                CustomException e = new CustomException("Too many parameters", HttpStatus.BAD_REQUEST);
                return new ResponseEntity<>(e.toResponse(), null, e.httpStatus());
            } else if (name.isPresent()) {
                imageManager.deleteImage(name.get());
                return ResponseEntity.noContent().build();
            } else {
                imageManager.deleteImage(url.get());
                return ResponseEntity.noContent().build();
            }
        } catch (Exception e) {
            log.error("Error while deleting image", e);
            return ResponseEntity.internalServerError().body(InternalServerError.response());
        }
    }

    @PostMapping("/image")
    public ResponseEntity<Object> addImage(@RequestPart MultipartFile image) {
        try {
            URL url = imageManager.addImage(Optional.empty(), image);
            return ResponseEntity.created(new URI(url.toString())).body(Map.of("url", url));
        } catch (Exception e) {
            log.error("Error while adding image", e);
            return ResponseEntity.internalServerError().body(InternalServerError.response());
        }
    }
}
