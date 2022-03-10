package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author Aaron
 * @date 1/13/22 2:09 PM
 */
@Service
public class NoteService {

    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public List<Note> getNotesByUserId(Integer userId){
        return noteMapper.getNotesByUserId(userId);
    }

    public int insertNote(Note note){
        if (note.getNoteId()!=null){
            return noteMapper.updateNote(note);
        }else {
            return noteMapper.insertNote(note);
        }
    }

    public boolean deleteNoteByNoteId(Integer noteId){
        return noteMapper.deleteNote(noteId);
    }
}
