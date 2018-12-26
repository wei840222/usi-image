package com.wei840222.usi.image.service;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;
import java.util.List;

import com.wei840222.usi.image.model.ImageRecord;

@Slf4j
@Service
public class HBaseService {
    @Autowired
    private HbaseTemplate hbaseTemplate;

    public ImageRecord getImageRecord(String imageName) {
        return this.hbaseTemplate.get("image_record", imageName, "data", new RowMapper<ImageRecord>() {
            public ImageRecord mapRow(Result result, int rowNum) throws Exception {
                List<Cell> ceList = result.listCells();
                final ImageRecord imageRecord = new ImageRecord();
                if (ceList != null && ceList.size() > 0) {
                    for (Cell cell : ceList) {
                        switch (Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(),
                                cell.getQualifierLength())) {
                        case "imageName":
                            imageRecord.setImageName(getCellValueToString(cell));
                            break;
                        case "mergeFile":
                            imageRecord.setMergeFile(getCellValueToString(cell));
                            break;
                        case "startByte":
                            imageRecord.setStartByte(getCellValueToInteger(cell));
                            break;
                        case "fileSize":
                            imageRecord.setFileSize(getCellValueToInteger(cell));
                            break;
                        }
                    }
                    log.info("getImageRecord {}", imageRecord);
                    return imageRecord;
                }
                return null;
            }
        });
    }

    public void saveImageRecord(ImageRecord imageRecord) {
        this.hbaseTemplate.put("image_record", imageRecord.getImageName(), "data", "imageName",
                imageRecord.getImageName().getBytes());
        this.hbaseTemplate.put("image_record", imageRecord.getImageName(), "data", "mergeFile",
                imageRecord.getMergeFile().getBytes());
        this.hbaseTemplate.put("image_record", imageRecord.getImageName(), "data", "startByte",
                ByteBuffer.allocate(4).putInt(imageRecord.getStartByte()).array());
        this.hbaseTemplate.put("image_record", imageRecord.getImageName(), "data", "fileSize",
                ByteBuffer.allocate(4).putInt(imageRecord.getFileSize()).array());
    }

    private String getCellValueToString(Cell cell) {
        return Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
    }

    private Integer getCellValueToInteger(Cell cell) {
        return Bytes.toInt(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
    }
}