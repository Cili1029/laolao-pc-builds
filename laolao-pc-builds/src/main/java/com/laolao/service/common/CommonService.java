package com.laolao.service.common;

import com.laolao.common.result.Result;
import com.laolao.pojo.forum.vo.ImageVO;
import org.springframework.web.multipart.MultipartFile;

public interface CommonService {
    Result<ImageVO> uploadImages(MultipartFile[] images, String type) throws Exception;
}
