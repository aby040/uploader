package net.aj.net.aj.github.uploader.controllers;

import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
public class IndexController {

    @GetMapping("/")
    public String index() {
        return "Demo upload of files array with dynamic keys using spring boot.</br>" +
                "Use the <code>/upload</code> path to try upload.";
    }

    @CrossOrigin(origins = "http://localhost:3000/")
    @PostMapping("/upload")
    public Map<String, ArrayList<String>> upload(MultipartHttpServletRequest request) {
        Map<String, ArrayList<String>> response = new HashMap<String, ArrayList<String>>();
        Map<String, String[]> fields = request.getParameterMap();
        fields.forEach((key, values) -> {
            response.put(key, new ArrayList<String>());
            for(String value: values) {
                response.get(key).add(value);
            }
        });
        MultiValueMap<String, MultipartFile> fileMap = request.getMultiFileMap();
        fileMap.forEach((key, files) -> {
            response.put(key, new ArrayList<String>());
            files.forEach((file) -> {
                response.get(key).add(file.getOriginalFilename());
            });
        });
        return response;
    }

}
