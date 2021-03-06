package life.wyj.community.controller;

import life.wyj.community.dto.FileDTO;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

@Controller
public class FileController {
    @RequestMapping("/file/upload")
    @ResponseBody
    public FileDTO upload(HttpServletRequest request){
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartHttpServletRequest.getFile("editormd-image-file");
        if(file.isEmpty()){
            return null;
        }
        String fileName = file.getOriginalFilename();
        String[] filePaths = fileName.split("\\ .");
        String generatedFileName = UUID.randomUUID().toString()+"."+filePaths[filePaths.length-1];
        long size = file.getSize();
        try {
            File path = new File(ResourceUtils.getURL("classpath:").getPath());
            File dest1 = new File(path.getAbsolutePath(),"static/uploadImage/"+generatedFileName);
            file.transferTo(dest1);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileDTO fileDTO = new FileDTO();
        fileDTO.setSuccess(1);
        fileDTO.setUrl("/uploadImage/"+generatedFileName);
        return  fileDTO;
    }
}
