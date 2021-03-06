package com.wei840222.usi.image.controller;

import java.util.Optional;

import javax.validation.Valid;

import com.wei840222.usi.image.model.ImageRecord;
import com.wei840222.usi.image.service.HBaseService;
import com.wei840222.usi.image.service.RedisService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/image-record")
public class ImageRecordController {
    @Autowired
    private RedisService redisService;
    @Autowired
    private HBaseService hbaseService;

    @GetMapping
    @RequestMapping("/{imageName}")
    public ResponseEntity<ImageRecord> getImageRecord(@PathVariable("imageName") String imageName) {
        Optional<ImageRecord> optionalImageRecord = Optional.ofNullable(this.redisService.get(imageName));
        if (optionalImageRecord.isPresent()) {
            log.info("getImageRecord - cache hit - 200");
            return new ResponseEntity<ImageRecord>(optionalImageRecord.get(), HttpStatus.OK);
        }
        else {
            optionalImageRecord = Optional.ofNullable(this.hbaseService.getImageRecord(imageName));
            if (optionalImageRecord.isPresent()) {
                log.info("getImageRecord - 200");
                return new ResponseEntity<ImageRecord>(optionalImageRecord.get(), HttpStatus.OK);
            } else {
                log.info("getImageRecord - 404");
                return new ResponseEntity<ImageRecord>(HttpStatus.NOT_FOUND);
            }
        }
    }

    @PostMapping
    public ResponseEntity<Void> addImageRecord(@Valid @RequestBody ImageRecord imageRecord) {
        this.redisService.set(imageRecord.getImageName(), imageRecord);
        this.hbaseService.saveImageRecord(imageRecord);
        log.info("addImageRecord - 200", imageRecord);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}