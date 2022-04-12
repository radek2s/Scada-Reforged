package org.reggsoft.srfcore.api;

import org.reggsoft.srfcore.persistance.dao.DataSourceVirtualRepository;
import org.reggsoft.srfcore.persistance.entity.DataSourceVirtual;
import org.reggsoft.srfcore.services.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/ds")
public class DataSourceAPI {

    @Autowired
    private DataSourceVirtualRepository repository;

    @Autowired
    private PermissionService permissions;

    @GetMapping()
    public ResponseEntity<List<DataSourceVirtual>> getAll() {
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<DataSourceVirtual> addVirtual(@RequestBody DataSourceVirtual ds) {
        DataSourceVirtual dsv = repository.save(ds);
        permissions.addPermission("admin", DataSourceVirtual.class, Long.valueOf(dsv.getId()), BasePermission.READ);
        return new ResponseEntity<>(dsv, HttpStatus.CREATED);
    }
}
