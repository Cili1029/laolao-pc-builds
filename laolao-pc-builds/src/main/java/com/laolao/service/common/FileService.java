package com.laolao.service.common;

import com.laolao.common.result.Result;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface FileService {
    Result<List<String>> uploadImages(MultipartFile[] images, String type) throws Exception;

    Result<String> deleteImages(Map<String, String> url);
}
