package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {
    @Select("SELECT * FROM NOTES WHERE userid = #{userId}")
    public List<Note> getAllByUserId(Integer userId);

    @Select("SELECT * FROM NOTES WHERE noteTitle = #{noteTitle}")
    public Note getByTitle(String noteTitle);

    @Select("SELECT * FROM NOTES WHERE noteid = #{noteId}")
    public Note getById(Integer noteId);

    @Insert("INSERT INTO NOTES(noteTitle, noteDescription, userId)" +
            "VALUES(#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    Integer insertOne(Note note);

    @Delete("DELETE FROM NOTES WHERE noteId = #{noteId}")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    Integer deleteById(Integer noteId);

    @Delete("DELETE FROM NOTES WHERE noteTitle = #{noteTitle}")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    Integer deleteByTitle(String noteTitle);

    @Update({"UPDATE NOTES SET notetitle=#{noteTitle}," +
            "notedescription=#{noteDescription},userid=#{userId} WHERE noteid=#{noteId}"})
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    Integer updateOne(Note note);
}
