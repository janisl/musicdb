package janisl.musicdb.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No such release import status")  // 404
public class ReleaseImportStatusNotFoundException extends RuntimeException {
    
}
