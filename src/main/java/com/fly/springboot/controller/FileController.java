package com.fly.springboot.controller;

import com.fly.springboot.entity.File;
import com.fly.springboot.service.FileService;
import com.fly.springboot.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * @author fly
 * @date 2018/5/19 20:38
 * @description
 **/
@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
public class FileController {

    @Autowired
    private FileService fileService;

    @RequestMapping("/")
    public String index(Model model){
        model.addAttribute("files", fileService.listFiles());
        return "index";
    }

    /**
     * 获取文件片信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity saveFile(@PathVariable("id") String id){
        File file = fileService.getFileById(id);
        if(file != null){
            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=\"" + file.getName() + "\"")
                    .header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
                    .header(HttpHeaders.CONTENT_LENGTH, file.getSize()+"")
                    .header("Connection",  "close")
                    .body(file.getContent());
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File was not fount");
        }
    }

    @GetMapping("/view/{id}")
    @ResponseBody
    public ResponseEntity serverFileOnline(@PathVariable("id") String id){
        File file = fileService.getFileById(id);
        if(file != null){
            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "fileName=\""+ file.getName() + "\"")
                    .header(HttpHeaders.CONTENT_TYPE, file.getContentType())
                    .header(HttpHeaders.CONTENT_LENGTH, file.getSize()+ "")
                    .header("Connection", "close")
                    .body(file.getContent());
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File was not fount");
        }
    }

    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file")MultipartFile file, RedirectAttributes redirectAttributes) {
        if(file.getSize() == 0){
            redirectAttributes.addFlashAttribute("message", "Your upload file is empty!!!!");
            return "redirect:/";
        }
        try {
            File f = new File(file.getOriginalFilename(), file.getContentType(), file.getSize(), file.getBytes());
            f.setMd5(MD5Util.getMD5(file.getInputStream()));
            f.setUploadDate(new Date());
            fileService.saveFile(f);
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "Your " + file.getOriginalFilename() + "is wrong!");
            return "redirect:/";
        }

        redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + file.getOriginalFilename() + "!");
        return "redirect:/";
    }

    @PostMapping("/upload")
    @ResponseBody
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file){
        File returnFile = null;
        try {
            File f = new File(file.getOriginalFilename(), file.getContentType(), file.getSize(), file.getBytes());
            f.setMd5(MD5Util.getMD5(file.getInputStream()));
            f.setUploadDate(new Date());
            returnFile = fileService.saveFile(f);
            returnFile.setPath("http://localhost:8081/view/" +f.getId());
            returnFile.setContent(null);
            return ResponseEntity.status(HttpStatus.OK).body("http://localhost:8081/view/" +f.getId());
        } catch (IOException |NoSuchAlgorithmException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @GetMapping("/delete/{id}")
    public String deleteFile(@PathVariable String id, RedirectAttributes redirectAttributes){
        fileService.removeFile(id);
        redirectAttributes.addAttribute("message", "删除文件成功！");
        return "redirect:/";
    }
}
