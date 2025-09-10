package org.easywork.console.infra.repository.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.easywork.console.infra.repository.po.base.BasePO;

/**
 * 文件信息实体
 * 
 * @author fiveupup
 * @version 1.0.0
 * @date 2025/09/09
 */
@TableName(value = "sys_file_info")
@Data
@EqualsAndHashCode(callSuper = true)
public class FileInfoPO extends BasePO {
    
    /**
     * 文件名称
     */
    private String fileName;
    
    /**
     * 原始文件名
     */
    private String originalName;
    
    /**
     * 文件路径
     */
    private String filePath;
    
    /**
     * 文件URL
     */
    private String fileUrl;
    
    /**
     * 文件大小（字节）
     */
    private Long fileSize;
    
    /**
     * 文件类型
     */
    private String contentType;
    
    /**
     * 文件扩展名
     */
    private String extension;
    
    /**
     * 文件MD5
     */
    private String md5;
    
    /**
     * 存储类型 1-本地存储 2-OSS存储 3-云存储
     */
    private Integer storageType;
    
    /**
     * 存储桶名称
     */
    private String bucketName;
    
    /**
     * 上传用户ID
     */
    private Long uploadBy;
    
    /**
     * 上传用户名
     */
    private String uploadByName;
    
    /**
     * 业务类型
     */
    private String businessType;
    
    /**
     * 业务ID
     */
    private String businessId;
    
    /**
     * 状态 1-正常 0-删除
     */
    private Integer status;
    
    /**
     * 获取格式化的文件大小
     */
    public String getFormattedSize() {
        if (fileSize == null) {
            return "0 B";
        }
        
        long size = fileSize;
        String[] units = {"B", "KB", "MB", "GB", "TB"};
        int unitIndex = 0;
        
        while (size >= 1024 && unitIndex < units.length - 1) {
            size /= 1024;
            unitIndex++;
        }
        
        return String.format("%.2f %s", (double) size, units[unitIndex]);
    }
    
    /**
     * 是否为图片文件
     */
    public boolean isImage() {
        if (contentType == null) {
            return false;
        }
        return contentType.startsWith("image/");
    }
    
    /**
     * 是否为视频文件
     */
    public boolean isVideo() {
        if (contentType == null) {
            return false;
        }
        return contentType.startsWith("video/");
    }
    
    /**
     * 是否为音频文件
     */
    public boolean isAudio() {
        if (contentType == null) {
            return false;
        }
        return contentType.startsWith("audio/");
    }
}