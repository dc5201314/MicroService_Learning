package top.dc.contentservice.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import top.dc.contentservice.VO.ShareVO;
import top.dc.contentservice.Service.ShareService;
import top.dc.contentservice.entity.Share;

@RestController
@RequestMapping("/share")
public class ShareController {

    @Autowired
    private ShareService shareService;

    @GetMapping("/{id}")
    @SentinelResource(value = "/share/{id}")
    public ShareVO getShareById(@PathVariable Integer id) {
//        Share share = shareService.getShareById(id).getShare();
//        try {
//            Thread.sleep(500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        // 返回分享信息和用户信息
        return shareService.getShareById(id);
    }
}
