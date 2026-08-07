package com.hardik.farmapp.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStatus {

    private long chatCount;

    private long analysisCount;

    private long pdfCount;

    private long imageCount;
}
