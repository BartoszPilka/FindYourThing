package FindYourThingApplication.controllers;

import FindYourThingApplication.services.ImageService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/images")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<String>> uploadImages(
            @RequestParam("files") List<MultipartFile> files) throws IOException {

        List<String> urls = new ArrayList<>();

        for (MultipartFile file : files) {
            String url = imageService.saveFile(file);
            urls.add(url);
        }

        return ResponseEntity.ok(urls);
    }
}
