package edu.kit.tm.cm.serviceenvironment.api.api;

import edu.kit.tm.cm.serviceenvironment.api.api.DTO.RequestObjects.SubmitAPI;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping(path = "v1/api-specification")
public interface APISpecificationApi {
    @PostMapping
    void submitAPI(@Valid @RequestBody SubmitAPI submitAPI);

    @DeleteMapping("/{slug}")
    void deleteAPI(@PathVariable String slug);
}
