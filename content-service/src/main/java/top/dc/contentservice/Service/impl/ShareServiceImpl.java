package top.dc.contentservice.Service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import top.dc.contentservice.VO.ShareVO;

import top.dc.contentservice.VO.UserVO;
import top.dc.contentservice.Mapper.ShareMapper;
import top.dc.contentservice.Service.ShareService;
import top.dc.contentservice.entity.Share;

import top.dc.contentservice.openfeign.UserFeignClient;


@Service
public class ShareServiceImpl extends ServiceImpl<ShareMapper, Share> implements ShareService {
    @Autowired
    private UserFeignClient userFeignClient;
    @Autowired
    private ShareMapper shareMapper;
    @Override
    public ShareVO getShareById(Integer id) {
        Share share = shareMapper.selectById(id);

        UserVO user = userFeignClient.getUserById(share.getUserId());

        ShareVO shareVO = new ShareVO();
        shareVO.setShare(share);
        shareVO.setUser(user);

        return shareVO;
    }
}
