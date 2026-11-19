package com.ejemplo.msa.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "MS-B")
public interface DivisionUsageClient {
    @GetMapping("/api/entity-b/uses-division/{divisionId}")
    Boolean hasProgramsUsingDivision(@PathVariable Long divisionId);
}
