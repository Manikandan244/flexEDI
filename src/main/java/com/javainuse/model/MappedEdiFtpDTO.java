package com.javainuse.model;

import java.util.ArrayList;
import java.util.List;

public class MappedEdiFtpDTO {

    List<GenerateEdiDTO> generateEdiDTOList = new ArrayList<>();
    List<GenerateEdiDTO> generateFtpDTOList = new ArrayList<>();

    public List<GenerateEdiDTO> getGenerateEdiDTOList() {
        return generateEdiDTOList;
    }

    public void setGenerateEdiDTOList(List<GenerateEdiDTO> generateEdiDTOList) {
        this.generateEdiDTOList = generateEdiDTOList;
    }

    public List<GenerateEdiDTO> getGenerateFtpDTOList() {
        return generateFtpDTOList;
    }

    public void setGenerateFtpDTOList(List<GenerateEdiDTO> generateFtpDTOList) {
        this.generateFtpDTOList = generateFtpDTOList;
    }
}
