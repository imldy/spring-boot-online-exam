package lsgwr.exam.utils;

import cn.hutool.core.io.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/***********************************************************
 * @note      : 文件传输工具类
 * @version   : V1.0 at 2019/5/19 16:15
 ***********************************************************/
@Slf4j
public class FileTransUtil {
    // 基础路径，所有上传的文件都必须在这个路径下
    private static final String BASE_UPLOAD_DIR = "upload_files";

    /**
     * 获取完整的文件保存路径
     * @param dir 用户指定的目录
     * @return 完整的文件保存路径
     */
    private static String getFullPath(String dir) {
        return BASE_UPLOAD_DIR + "/" + dir;
    }

    /**
     * 上传单个文件
     *
     * @param uploadfile 上传的文件
     * @param dir        文件要保存的文件夹
     * @return 成功或者失败消息
     */
    public static String uploadFile(MultipartFile uploadfile, String dir) {
        log.info("Single file upload!");
        if (uploadfile.isEmpty()) {
            return "文件名不能为空";
        }
        try {
            saveUploadedFiles(Arrays.asList(uploadfile), dir);
        } catch (IOException e) {
            e.printStackTrace();
            return "后台服务异常";
        }
        log.info("file upload successfully! " + dir);
        return "文件上传成功";
    }

    /**
     * 多文件上传
     *
     * @param uploadfiles 要上传的多个文件
     * @param dir         要保存的目录
     * @return 成功或者失败消息
     */
    public static String uploadFiles(MultipartFile[] uploadfiles, String dir) {
        log.debug("Multiple file upload!");
        String uploadedFileName = Arrays.stream(uploadfiles).map(x -> x.getOriginalFilename()).filter(x -> !StringUtils.isEmpty(x)).collect(Collectors.joining(" , "));
        if (StringUtils.isEmpty(uploadedFileName)) {
            return "文件名不能为空";
        }
        try {
            FileTransUtil.saveUploadedFiles(Arrays.asList(uploadfiles), dir);
        } catch (IOException e) {
            return "后台服务异常";
        }
        log.info("file upload successfully! " + uploadedFileName);
        return "文件上传成功";
    }

    /**
     * 保存文件到指定路径
     *
     * @param files 上传的文件
     * @param dir 存储到的路径
     * @return 所有文件存储到的真实路径列表
     * @throws IOException 文件保存异常
     */
    public static List<Path> saveUploadedFiles(List<MultipartFile> files, String dir) throws IOException {
        String fullPath = getFullPath(dir);
        List<Path> savedPaths = new ArrayList<>();
        
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                continue;
            }
            if (!FileUtil.exist(fullPath)) {
                // 文件夹不存在就创建
                FileUtil.mkdir(fullPath);
            }
            byte[] bytes = file.getBytes();
            String fileName = file.getOriginalFilename().replace("\\", "/");
            if (fileName.lastIndexOf('/')>0){
                // 上传文件夹的时候会有这种情况
                String fileDir = fullPath + "/" + fileName.substring(0, fileName.lastIndexOf('/'));
                if (!FileUtil.exist(fileDir)) {
                    // 文件夹不存在就创建,创建文件夹的时候会用到
                    FileUtil.mkdir(fileDir);
                }
            }
            // 使用 Path 对象来处理路径
            Path path = Paths.get(fullPath).resolve(fileName.replace("\\", "/"));
            // 确保父目录存在
            Files.createDirectories(path.getParent());
            Files.write(path, bytes);
            savedPaths.add(path);
        }
        return savedPaths;
    }

    /**
     * 根据文件路径下载文件
     *
     * @param filePath 要现在的文件的路径
     * @return 文件流
     * @throws IOException 文件流读取异常
     */
    public static ResponseEntity<InputStreamResource> downloadFile(String filePath) throws IOException {
        log.info("downloading file : " + filePath);
        FileSystemResource file = new FileSystemResource(filePath);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", new String(file.getFilename().getBytes("gbk"), "iso-8859-1")));
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        System.out.println(file.getFilename());
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(file.contentLength())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(file.getInputStream()));
    }

}
