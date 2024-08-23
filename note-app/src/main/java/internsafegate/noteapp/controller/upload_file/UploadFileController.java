package internsafegate.noteapp.controller.upload_file;

import internsafegate.noteapp.service.upload_file.UploadFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/files")
public class UploadFileController {
    private final UploadFileService uploadFile;

    @PostMapping("")
    public List<String> uploadFile(
            @RequestPart("files") MultipartFile[] files
    ) throws Exception{
        List<String> listFile = uploadFile.setUrlFile(files);
        return listFile;
    }
}
