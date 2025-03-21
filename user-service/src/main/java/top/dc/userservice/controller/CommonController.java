package top.dc.userservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.dc.userservice.DTO.FileUrlVO;
import top.dc.userservice.Service.CommonService;
import top.dc.userservice.VO.FoodRecognitionVO;


@RestController
@RequestMapping("common")
@AllArgsConstructor
public class CommonController {

    private final CommonService commonService;


    @PostMapping(value = "/upload/img")
    public FileUrlVO upload(@RequestParam("file") MultipartFile file) {
        return commonService.upload(file);
    }
    @PostMapping("/upload/recognize-food")
    public FoodRecognitionVO uploadAndRecognizeFood(@RequestParam("file") MultipartFile file) {
        return commonService.uploadAndRecognizeFood(file);
    }
}