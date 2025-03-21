package top.dc.userservice.Service;

import org.springframework.web.multipart.MultipartFile;
import top.dc.userservice.DTO.FileUrlVO;
import top.dc.userservice.VO.FoodRecognitionVO;

public interface CommonService {
    /**
     * 图片上传
     *
     * @param uploadFile 上传文件
     * @return 上传文件返回视图
     */
    FileUrlVO upload(MultipartFile uploadFile);
    FoodRecognitionVO uploadAndRecognizeFood(MultipartFile uploadFile);

}