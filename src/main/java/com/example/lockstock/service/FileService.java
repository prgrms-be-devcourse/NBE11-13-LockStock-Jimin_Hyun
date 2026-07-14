package com.example.lockstock.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
public class FileService {
    @Value("${file.upload-dir}")
    private String uploadDir;

    // getBytes() + Files.write() 방식과 비교했을 때의 핵심 차이
    // (기존) byte[] bytes = file.getBytes(); Files.write(...);
    // 메모리 : getBytes()는 파일 "전체"를 byte[]로 힙 메모리에 올린다 -> 큰 파일/동시 업로드 시 OOM(Out Of Memory) 위험
    // 반면 transferTo는 통째로 올리지 않고 옮기며, 같은 디스크면 복사가 아니라 이동방식이라 가볍고 빠르다.
    public String storeFile(MultipartFile file) {

        if ( file == null || file.isEmpty() ) return null;

        try {
            // 절대 경로로 다뤄 실행 위치에 영향받지 않게 한다.
            File dir = new File(uploadDir).getAbsoluteFile(); //객체를 가지고와서 절대 경로 반환
            if ( !dir.exists() ) dir.mkdirs(); // 예외 처리해야 할 곳

            String storedFileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            File dest = new File(dir, storedFileName);

            file.transferTo(dest);

            return dest.getName();
        } catch (Exception e) {
            throw new IllegalStateException("파일 저장에 실패 했습니다", e);
        }
    }

    public void deleteFile(String thumbnailPath){
        if(thumbnailPath == null || thumbnailPath.isBlank()) return;

        File file = new File(uploadDir,thumbnailPath);
        if(!file.exists()) return;
        file.delete();
    }
}
