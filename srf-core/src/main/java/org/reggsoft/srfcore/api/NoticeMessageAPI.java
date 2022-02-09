package org.reggsoft.srfcore.api;


import org.reggsoft.srfcore.persistance.dao.NoticeMessageRepository;
import org.reggsoft.srfcore.persistance.entity.NoticeMessage;
import org.reggsoft.srfcore.services.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/nm")
public class NoticeMessageAPI {

    @Autowired
    private NoticeMessageRepository repository;

    @Autowired
    private PermissionService permissions;

    @GetMapping()
    public ResponseEntity<List<NoticeMessage>> getAll() {
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<NoticeMessage> getOne(@PathVariable("id") int id) {
        return new ResponseEntity<>(repository.findById(id), HttpStatus.OK);
    }

    @PostMapping("{username}")
    public ResponseEntity<NoticeMessage> addMessage(@PathVariable("username") String username, @RequestBody NoticeMessage noticeMessage) {
        NoticeMessage n = repository.save(noticeMessage);
        permissions.addPermission(username, NoticeMessage.class, Long.valueOf(n.getId()), BasePermission.READ);
        return new ResponseEntity<>(n, HttpStatus.CREATED);
    }

}
