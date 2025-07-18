package com.example.URLShortner.Controller;

import com.example.URLShortner.Entity.RequestDTO;
import com.example.URLShortner.Entity.RequestEntity;
import com.example.URLShortner.Repo.Repo;
import com.example.URLShortner.ServiceLayer.URLShortenerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
public class RedirectController {

    @Autowired
    private Repo repo;

    @GetMapping("/{id}")
    public ResponseEntity<?> handleRedirect(@PathVariable String id) {
        String hashStr = URLShortenerUtils.generateShortUrlKey(id);
        if (repo.existsByRequestKey(hashStr)) {
            RequestEntity requestEntity = repo.findByRequestKey(hashStr);
            if (requestEntity != null) {
                String url = requestEntity.getURL();
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.setLocation(URI.create(url));
                return new ResponseEntity<>(httpHeaders, HttpStatus.MOVED_PERMANENTLY);
            }
        }
        return new ResponseEntity<>("Shortened URL not found", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/")
    public ResponseEntity<String> makeRedirects(@RequestBody RequestDTO requestDTO) {
        String hashStr = URLShortenerUtils.generateShortUrlKey(requestDTO.getRequestkey());
        if (repo.existsByRequestKey(hashStr)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Change key, already exists");
        } else {
            RequestEntity requestEntity = new RequestEntity(hashStr, requestDTO.getURL());
            repo.save(requestEntity);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }
}
