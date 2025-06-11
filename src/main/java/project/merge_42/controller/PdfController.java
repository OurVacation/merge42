package project.merge_42.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.merge_42.service.PdfService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/pdf")
public class PdfController {
    private final PdfService pdfService;

    public PdfController(PdfService pdfService) {
        this.pdfService = pdfService;
    }

    @PostMapping("/merge")
    public ResponseEntity<byte[]> mergePdfhandler(@RequestParam("files") List<MultipartFile> files){
        if (files == null || files.size() < 2) {
            return ResponseEntity.badRequest().body("2개 이상의 파일을 업로드 해주세요".getBytes());
        }
        try {
            // 1. Service 객체에서 병합된 pdf 파일 byte를 받아옴
            byte[] mergePdfBytes = pdfService.mergePdfs(files);
            // 2. 브라우저가 이렇게 받아온 바이트를 어떻게 처리할지 알려주는 헤더 정보 생성
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "merged.pdf");
            headers.setContentLength(mergePdfBytes.length);

            return new ResponseEntity<>(mergePdfBytes, headers, HttpStatus.OK);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Failed to merge PDF files".getBytes());
        }
    }
}
