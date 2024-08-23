package internsafegate.noteapp.service.upload_file;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UploadFileService {
    List<String> setUrlFile(MultipartFile[] files) throws Exception;
}
