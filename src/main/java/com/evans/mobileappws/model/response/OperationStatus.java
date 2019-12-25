package com.evans.mobileappws.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationStatus {
    private String operationResult;
    private String operationName;
}
