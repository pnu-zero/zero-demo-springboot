package com.example.zero.utils;


import com.example.zero.project.exception.InvalidFileExtensionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
@Slf4j
public class FileUtils {

    private static final String USER_CURRENT_DIR = System.getProperty("user.dir");
    private static final String DEFAULT_DIR = USER_CURRENT_DIR + "/project_files";
    public static final String STATIC_FILE_DIR = "/static";
    public static final String DYNAMIC_FILE_DIR = "/dynamic";

    public static String generateFilePath(String fileType){
        return DEFAULT_DIR + fileType;
    }

    public static String uploadStaticFile(String dir, MultipartFile staticFile, String subdomain) throws IOException {
        File folder = new File(dir);
        if (!folder.exists()) {
            try {
                folder.mkdirs();
                log.info(dir + "Project File folder generated!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 파일 확장자 추출
        String originalFilename = staticFile.getOriginalFilename();
        String fileExtension = "";
        if (originalFilename != null && originalFilename.lastIndexOf(".") != -1) {
            fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        if (!fileExtension.equals(".zip") && !fileExtension.equals(".tar")) {
            throw new InvalidFileExtensionException("올바른 파일 형식이 아닙니다.");
        }

        // 파일 이름을 subdomain으로 변경
        String filename = subdomain + fileExtension;
        String fullPath = dir + File.separator + filename;

        Path path = Paths.get(fullPath).toAbsolutePath();
        System.out.println(path);
        staticFile.transferTo(path.toFile());
        log.info(fullPath + "generated!");
        return path.toString();
    }

}
