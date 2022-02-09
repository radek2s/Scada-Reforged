package org.reggsoft.srfcore.persistance.dao;

import java.util.List;

import org.reggsoft.srfcore.persistance.entity.NoticeMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

public interface NoticeMessageRepository extends JpaRepository<NoticeMessage, Long> {

    @PostFilter("hasPermission(filterObject, 'READ')")
    List<NoticeMessage> findAll();

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    List<NoticeMessage> findAll();

    @PostAuthorize("hasPermission(returnObject, 'READ')")
    NoticeMessage findById(Integer id);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    NoticeMessage save(@Param("noticeMessage")NoticeMessage noticeMessage);

}