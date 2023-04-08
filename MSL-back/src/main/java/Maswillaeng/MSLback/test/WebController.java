package Maswillaeng.MSLback.test;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class WebController {

    private final S3Uploader s3Uploader;

    @GetMapping("/tests3")
    public String index() {
        return "test";
    }

    @PostMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam("data") MultipartFile multipartFile,
                         Authentication authentication) throws IOException {
        System.out.println("multipartFile.getOriginalFilename() = " + multipartFile.getOriginalFilename());
        Long userId = Long.parseLong(authentication.getName());
        return s3Uploader.upload(multipartFile, "static", userId);
    }
}
