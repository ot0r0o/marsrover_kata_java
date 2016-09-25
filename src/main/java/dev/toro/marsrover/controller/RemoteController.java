/**
 * Created by Alberto Toro on 25/09/16.
 */
package dev.toro.marsrover.controller;

import dev.toro.marsrover.entity.dto.RemoteDTO;
import dev.toro.marsrover.service.RemoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/remote", produces = MediaType.APPLICATION_JSON_VALUE)
public class RemoteController {

    @Autowired
    private RemoteService remoteService;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> loadData(@RequestBody final RemoteDTO remote) {
        return new ResponseEntity<>(remoteService.makeMovementsByRemote(remote),
                HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> check() {
        return new ResponseEntity<>(remoteService.check(),
                HttpStatus.OK);
    }
}
