package top.dc.userservice.Service.impl;


import com.aliyun.imagerecog20190930.models.RecognizeFoodResponse;
import com.aliyun.imagerecog20190930.models.RecognizeFoodResponseBody;
import com.aliyun.oss.OSS;
import com.aliyun.oss.model.ObjectMetadata;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.dc.userservice.DTO.FileUrlVO;
import top.dc.userservice.Service.CommonService;
import top.dc.userservice.VO.FoodRecognitionVO;
import top.dc.userservice.config.AliyunImageRecogConfig;
import top.dc.userservice.config.OssConfig;
import top.dc.userservice.exception.ServerException;
import com.aliyun.imagerecog20190930.models.RecognizeFoodRequest;
import com.aliyun.teaopenapi.models.Config;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CommonServiceImpl implements CommonService {
    private static final String[] IMAGE_TYPE = new String[]{".bmp", ".jpg", ".jpeg", ".gif", ".png"};

    @Resource
    private OSS ossClient;

    @Resource
    private OssConfig ossConfig;

    @Resource
    private AliyunImageRecogConfig imageRecogConfig;

    private com.aliyun.imagerecog20190930.Client imageRecogClient;

    @PostConstruct
    public void initImageRecogClient() throws Exception {
        Config config = new Config()
                .setAccessKeyId(ossConfig.getAccessKeyId())
                .setAccessKeySecret(ossConfig.getAccessKeySecret());
        config.endpoint = imageRecogConfig.getEndpoint();
        this.imageRecogClient = new com.aliyun.imagerecog20190930.Client(config);
    }

    @Override
    public FileUrlVO upload(MultipartFile uploadFile) {
        String returnImgUrl;

        boolean isLegal = false;
        for (String type : IMAGE_TYPE) {
            if (StringUtils.endsWithIgnoreCase(uploadFile.getOriginalFilename(), type)) {
                isLegal = true;
                break;
            }
        }
        if (!isLegal) {
            throw new ServerException("图片格式不正确");
        }

        String originalFilename = uploadFile.getOriginalFilename();
        String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFileName = UUID.randomUUID().toString() + fileType;
        String filePath = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
        String uploadImgeUrl = filePath + "/" + newFileName;

        try (InputStream inputStream = uploadFile.getInputStream()) {
            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentType("image/jpeg");

            ossClient.putObject(ossConfig.getBucketName(), uploadImgeUrl, inputStream, meta);
            returnImgUrl = "https://" + ossConfig.getBucketName() + "." + ossConfig.getEndpoint() + "/" + uploadImgeUrl;
            return new FileUrlVO(returnImgUrl);
        } catch (IOException e) {
            throw new ServerException("上传图片失败: " + e.getMessage());
        }
    }
    private FoodRecognitionVO convertToFoodVO(String imageUrl, RecognizeFoodResponse response) {
        FoodRecognitionVO vo = new FoodRecognitionVO();
        vo.setImageUrl(imageUrl);

        List<FoodRecognitionVO.FoodItem> foodItems = response.getBody().getData().getTopFives()
                .stream()
                .map(item -> {
                    FoodRecognitionVO.FoodItem food = new FoodRecognitionVO.FoodItem();
                    food.setName(item.getCategory()); // 菜品名称实际对应category字段
                    food.setConfidence(Double.valueOf(item.getScore()));
                    food.setCalorie(Double.valueOf(item.getCalorie()));
                    food.setCategory(List.of(item.getCategory()));
                    return food;
                })
                .collect(Collectors.toList());

        vo.setFoods(foodItems);
        return vo;
    }

    @Override
    public FoodRecognitionVO uploadAndRecognizeFood(MultipartFile uploadFile) {
        FileUrlVO fileUrlVO = upload(uploadFile);
        String imageUrl = fileUrlVO.getFileUrl();
        System.out.println("上传的图片链接: " + imageUrl); // 打印上传的图片链接

        try {
            RecognizeFoodRequest request = new RecognizeFoodRequest()
                    .setImageURL(fileUrlVO.getFileUrl());
            RecognizeFoodResponse response = imageRecogClient.recognizeFood(request);
            return convertToFoodVO(fileUrlVO.getFileUrl(), response);
        } catch (Exception e) {
            throw new ServerException("食品识别失败: " + e.getMessage());
        }
    }

}
