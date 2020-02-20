package hirain.itd.hmi.demo.service;

import org.springframework.web.multipart.MultipartFile;

public interface IFileService {
    String upload(MultipartFile file);
}
