package janisl.musicdb.beatport;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Invalid Beatport path")  // 404
public class BeatportInvalidPathException extends RuntimeException {
    
}
