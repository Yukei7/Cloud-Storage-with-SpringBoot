package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public List<Note> getAllByUserId(Integer userId) {
        return noteMapper.getAllByUserId(userId);
    }

    public Integer insertOne(Note note) {
        return noteMapper.insertOne(note);
    }

    public Integer updateOne(Note note) {
        return noteMapper.updateOne(note);
    }

    public Integer deleteById(Integer noteId) {
        return noteMapper.deleteById(noteId);
    }

    public Integer deleteByTitle(String noteTitle) {
        return noteMapper.deleteByTitle(noteTitle);
    }

    public Note getById(Integer noteId) {
        return noteMapper.getById(noteId);
    }

    public Note getByTitle(String noteTitle) {
        return noteMapper.getByTitle(noteTitle);
    }
}
