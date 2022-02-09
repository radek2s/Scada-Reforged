package org.reggsoft.srfcore.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.*;
import org.springframework.stereotype.Service;

@Service
public class PermissionService {

    @Autowired
    private MutableAclService aclService;

    public void addPermission(String username, Class<?> type, Long id, Permission permission) {
        ObjectIdentity objectIdentity = new ObjectIdentityImpl(type, id);
        Sid sid = new PrincipalSid(username);
        MutableAcl acl;
        try {
            acl = (MutableAcl) aclService.readAclById(objectIdentity);
        } catch (NotFoundException e) {
            acl = aclService.createAcl(objectIdentity);
        }
        acl.insertAce(acl.getEntries().size(), permission, sid, true);
        aclService.updateAcl(acl);
    }
}
