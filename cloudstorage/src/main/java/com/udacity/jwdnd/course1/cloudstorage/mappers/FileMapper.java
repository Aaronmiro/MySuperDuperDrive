package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import org.apache.ibatis.annotations.*;
import java.util.List;

/**
 * @author Aaron
 * @date 1/13/22 12:41 PM
 */
@Mapper
public interface FileMapper {

    @Select("SELECT * FROM FILES WHERE userid = #{userId}")
    List<File> getFilesByUserId(Integer userId);

    @Select("SELECT * FROM FILES WHERE filename = #{filename}")
    List<File> getFilesByName(String filename);

    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    List<File> getFilesById(Integer fileId);

    @Insert("INSERT INTO FILES(filename, contenttype, filesize, userid, filedata) VALUES(#{filename}, #{contentType}, #{fileSize}" +
            ", #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insertFile(File file);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    boolean deleteFile(Integer fileId);

}
