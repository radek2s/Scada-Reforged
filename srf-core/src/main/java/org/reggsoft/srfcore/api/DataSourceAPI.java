//package org.reggsoft.srfcore.api;
//
//import org.reggsoft.srfcore.user.datasources.AbstractDataSource;
//import org.reggsoft.srfcore.user.datasources.virtual.DataSourceVirtual;
//import org.reggsoft.srfcore.persistance.dao.DataSourceRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping(path = "api/ds")
//public class DataSourceAPI {
//
//    @Autowired
//    private DataSourceRepository repository;
//
//    @GetMapping()
//    public ResponseEntity<List<AbstractDataSource>> getAll() {
//        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
//    }
//
//    @PostMapping()
//    public ResponseEntity<AbstractDataSource> addVirtual(@RequestBody DataSourceVirtual ds) {
//        return new ResponseEntity<>(repository.save(ds), HttpStatus.CREATED);
//    }
//}
