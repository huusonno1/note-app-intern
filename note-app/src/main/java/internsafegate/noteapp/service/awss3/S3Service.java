package internsafegate.noteapp.service.awss3;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.web.multipart.MultipartFile;

public interface S3Service {
    String uploadFile(MultipartFile file) throws FileUploadException;
}
