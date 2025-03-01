package top.dc.contentservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import top.dc.contentservice.VO.ShareVO;
import top.dc.contentservice.Service.ShareService;

@RestController
@RequestMapping("/share")
public class ShareController {

    @Autowired
    private ShareService shareService;

    @GetMapping("/{id}")
    public ShareVO getShareById(@PathVariable Integer id) {
        // 返回分享信息和用户信息
        return shareService.getShareById(id);
    }
}
