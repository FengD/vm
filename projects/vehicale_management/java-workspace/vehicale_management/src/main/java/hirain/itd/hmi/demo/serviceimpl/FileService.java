package hirain.itd.hmi.demo.serviceimpl;

import com.google.common.collect.Lists;
import hirain.itd.hmi.demo.service.IFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileService implements IFileService {

    private Logger logger= LoggerFactory.getLogger(FileService.class);

    @Value("${web.upload-path}")
    private String uploadFolder;

    @Override
    public String upload(MultipartFile file){
        String fileName=file.getOriginalFilename();
        //扩展名
        String fileExtensionName=fileName.substring(fileName.lastIndexOf(".")+1);
        String uploadFileName= fileName+UUID.randomUUID().toString()+"."+fileExtensionName;
        logger.info("开始上传文件，上传文件的文件名是:{},上传的路径:{},新文件名:{}",fileName,uploadFolder,uploadFileName);
        File fileDir=new File(uploadFolder);
        if (!fileDir.exists()){
            //给文件夹赋予写的权限
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }
        File targetFile=new File(uploadFolder,uploadFileName);

        try {
            file.transferTo(targetFile);
        } catch (IOException e) {
            logger.error("上传文件异常");
            return null;
        }
        return uploadFolder+targetFile.getName();
    }
}
