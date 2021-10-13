package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {
    @Select("SELECT * FROM FILES WHERE FILEID=#{fileId}")
    public File getById(Integer fileId);

    @Select("SELECT * FROM FILES WHERE USERID=#{userId}")
    public List<File> getAllByUserId(Integer userId);

    @Select("SELECT * FROM FILES WHERE FILENAME=#{filename}")
    public File getByFilename(String filename);

    @Select("SELECT * FROM FILES WHERE FILENAME=#{filename} AND USERID=#{userId}")
    public File getByFilenameAndUserId(String filename, Integer userId);

    @Insert("INSERT INTO FILES (FILENAME, CONTENTTYPE, FILESIZE, USERID, FILEDATA) " +
            "VALUES(#{filename}, #{contenttype}, #{filesize}, #{userid}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    Integer insertFile(File file);

    @Update({"UPDATE FILES SET " +
            "FILENAME=#{filename},CONTENTTYPE=#{contenttype}," +
            "FILESIZE=#{filesize},USERID=#{userid}," +
            "FILEDATA=#{filedata}"})
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    public Integer updateFile(File file);

    @Delete("DELETE FROM FILES WHERE FILEID=#{fileId}")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    public Integer deleteById(Integer fileId);

    @Delete("DELETE FROM FILES WHERE FILENAME=#{filename}")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    public Integer deleteByFilename(String filename);
}
