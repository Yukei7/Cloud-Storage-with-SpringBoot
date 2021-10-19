package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {
    @Select("SELECT * FROM CREDENTIALS WHERE CREDENTIALID=#{credentialId}")
    public Credential getById(Integer credentialId);

    @Select("SELECT * FROM CREDENTIALS WHERE USERID=#{userId}")
    public List<Credential> getAllByUserId(Integer userId);

    @Select("SELECT * FROM CREDENTIALS WHERE URL=#{url}")
    public Credential getByURL(String url);

    @Insert("INSERT INTO CREDENTIALS(url, username, key, password, userId) " +
            "VALUES(#{url}, #{username}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    public Integer insert(Credential credential);

    @Update("UPDATE CREDENTIALS SET URL=#{url}," +
            "USERNAME=#{username},KEY=#{key},PASSWORD=#{password}," +
            "USERID=#{userId} " +
            "WHERE CREDENTIALID=#{credentialId}")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    public Integer update(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE CREDENTIALID=#{credentialId}")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    public Integer deleteById(Integer credentialId);
}
