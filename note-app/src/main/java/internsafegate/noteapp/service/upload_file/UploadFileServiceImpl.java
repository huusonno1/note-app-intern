package internsafegate.noteapp.service.upload_file;

import internsafegate.noteapp.service.awss3.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UploadFileServiceImpl implements UploadFileService{

    private final S3Service s3Service;

    @Value("${aws.cloudfront.domainName}")
    private String domainName;
    @Override
    public List<String> setUrlFile(MultipartFile[] files) throws Exception {
        List<String> listImageUrl = Arrays.stream(files)
                .map(file -> {
                    try {
                        return "https://" + domainName +"/"+ s3Service.uploadFile(file);
                    } catch (Exception e) {
                        return null;
                    }
                })
                .filter(url -> url != null)
                .collect(Collectors.toList());
        return listImageUrl;
    }
}
