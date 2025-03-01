package top.dc.contentservice.VO;

import lombok.Data;
import top.dc.contentservice.entity.Share;

@Data
public class ShareVO {
    private Share share;
    private UserVO user;
}