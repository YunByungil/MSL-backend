package Maswillaeng.MSLback.test;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class WebController {

    private final S3Uploader s3Uploader;

    @GetMapping("/tests3")
    public String index() {
        return "test";
    }

    @PostMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam("data") MultipartFile multipartFile) throws IOException {
        System.out.println("multipartFile.getOriginalFilename() = " + multipartFile.getOriginalFilename());
        return s3Uploader.upload(multipartFile, "static");
    }
}
