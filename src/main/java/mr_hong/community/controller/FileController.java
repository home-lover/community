package mr_hong.community.controller;

import mr_hong.community.dto.FileDto;
import mr_hong.community.dto.ResultDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

@Controller
public class FileController {
    @Value("${file.staticAccessPath}")
    private String staticAccessPath;
    @Value("${file.uploadFolder}")
    private String imgPath;


    @RequestMapping("/file/upload")
    @ResponseBody
    public Object upload(@RequestParam("editormd-image-file") MultipartFile file) {
        FileDto fileDto = new FileDto();
        if (!file.isEmpty()) {
            String filename = file.getOriginalFilename();
            String filePath = imgPath + "/" + filename;
            try {
                BufferedOutputStream out = new BufferedOutputStream(
                        new FileOutputStream(new File(filePath)));
                out.write(file.getBytes());
                out.flush();
                out.close();
                fileDto.setSuccess(1);
                fileDto.setUrl(staticAccessPath  + filename);
                return fileDto;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return ResultDto.errorOf(2006, "图片上传出错");
            } catch (IOException e) {
                e.printStackTrace();
                return ResultDto.errorOf(2006, "图片上传出错");
            }
        }
        return ResultDto.errorOf(2006, "图片上传出错!");
    }
   /* public FileDto upload(){
        FileDto fileDto = new FileDto();
        fileDto.setSuccess(1);
        fileDto.setUrl(staticAccessPath+"xiyang.jpg");
        return fileDto;
    }*/
}
