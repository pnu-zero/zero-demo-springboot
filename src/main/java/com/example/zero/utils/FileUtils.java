package com.example.zero.utils;

import com.example.zero.project.domain.model.ProjectDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class FileUtils {

    private static final String USER_HOME = System.getProperty("user.home");
    private static final String DEFAULT_DIR = USER_HOME + "/project_files";
    public static final String STATIC_FILE_DIR = "/static";
    public static final String DYNAMIC_FILE_DIR = "/dynamic";

    public static String generateFilePath(ProjectDto projectDto, String fileType){
        String PROJECT_DIR = projectDto.getGroup_id() + File.separator + projectDto.getUser_id();
        return DEFAULT_DIR + File.separator + PROJECT_DIR + File.separator + fileType;
    }

    public static String uploadStaticFile(String dir, MultipartFile staticFile) throws IOException {
        File folder = new File(dir);
        if (!folder.exists()) {
            try {
                folder.mkdirs();
                log.info(dir + "Project File folder generated!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String fullPath = dir + File.separator + staticFile.getOriginalFilename();
        Path path = Paths.get(fullPath).toAbsolutePath();
        System.out.println(path);
        staticFile.transferTo(path.toFile());
        return path.toString();
    }

}
