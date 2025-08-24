package com.seven.friend.controller.file;

import com.seven.common.core.domain.R;
import com.seven.common.file.domain.OSSResult;
import com.seven.friend.service.file.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private IFileService fileService;

    @PostMapping("/upload")
    public R<OSSResult> upload(@RequestBody MultipartFile file) {
        return R.ok(fileService.upload(file));
    }
}
