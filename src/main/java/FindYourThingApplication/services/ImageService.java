package FindYourThingApplication.services;

import FindYourThingApplication.entities.Image;
import FindYourThingApplication.entities.Item;
import FindYourThingApplication.repositories.ImageRepository;
import FindYourThingApplication.services.providers.ItemProvider;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ImageService
{
    private final ImageRepository imageRepository;

    private final ItemProvider itemProvider;

    public ImageService(ImageRepository imageRepository, ItemProvider itemProvider) {
        this.imageRepository = imageRepository;
        this.itemProvider = itemProvider;
    }

    private final String uploadDir = "${UPLOADS_DIRECTORY}/";

    @Transactional
    public String saveFile(MultipartFile file) throws IOException {

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path path = Paths.get(uploadDir, fileName);

        Files.createDirectories(path.getParent());
        Files.write(path, file.getBytes());

        // URL który zapiszesz w bazie
        return "/files/" + fileName;
    }

    @Transactional
    public void deleteFile(String imageUrl)
    {
        if(imageUrl == null)
            throw new IllegalArgumentException("ID of an image must not be null");
        try
        {
            String fileName = Paths.get(imageUrl).getFileName().toString();
            Path filePath = Paths.get(uploadDir).resolve(fileName).normalize();

            if (!filePath.startsWith(uploadDir)) {
                throw new SecurityException("Invalid file path");
            }

            Files.deleteIfExists(filePath);
        }
        catch (IOException e) {
            throw new RuntimeException("Failed to delete file: " + imageUrl, e);
        }
    }
}
