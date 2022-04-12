package org.reggsoft.srfcore.api;


import org.reggsoft.srfcore.ScadaRuntime;
import org.reggsoft.srfcore.persistance.dao.DataSourceVirtualRepository;
import org.reggsoft.srfcore.persistance.dao.NoticeMessageRepository;
import org.reggsoft.srfcore.persistance.entity.DataSourceVirtual;
import org.reggsoft.srfcore.persistance.entity.NoticeMessage;
import org.reggsoft.srfcore.services.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
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

    @Autowired
    private ApplicationContext context;

    @Autowired
    private DataSourceVirtualRepository dsRepo;

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

    @GetMapping("enable")
    public ResponseEntity<String> enableDS() {
        ScadaRuntime rt = (ScadaRuntime) context.getBean("getScadaRuntime");
        rt.initDataSources();
        rt.startDataSources();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("disable")
    public ResponseEntity<String> disableDS() {
        ScadaRuntime rt = (ScadaRuntime) context.getBean("getScadaRuntime");
        rt.stopDataSources();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("start")
    public ResponseEntity<String> start() {
        ScadaRuntime rt = (ScadaRuntime) context.getBean("getScadaRuntime");
        rt.runLogger();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("stop")
    public ResponseEntity<String> stop() {
        ScadaRuntime rt = (ScadaRuntime) context.getBean("getScadaRuntime");
        rt.stopLogger();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
