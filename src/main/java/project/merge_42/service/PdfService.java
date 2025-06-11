package project.merge_42.service;


import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class PdfService {

    public byte[] mergePdfs(List<MultipartFile> files) throws IOException {
        // PDF 병합을 위한 유틸리티 객체 생성
        PDFMergerUtility pdfMerger = new PDFMergerUtility();
        // 병합된 PDF파일 내용을 담을 스트림 생성
        // 파일로 저장 X 메모리에서 처리하기 위해 ByteArrayOutputStream 사용
        ByteArrayOutputStream mergedPdfStream = new ByteArrayOutputStream();

        for (MultipartFile file : files) {
            pdfMerger.addSource(file.getInputStream());
        }

        pdfMerger.setDestinationStream(mergedPdfStream);

        pdfMerger.mergeDocuments(null);
        byte[] mergedPdf = mergedPdfStream.toByteArray();
        mergedPdfStream.close();
        return mergedPdf;
    }
}
