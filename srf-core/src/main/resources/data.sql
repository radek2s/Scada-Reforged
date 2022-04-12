--INSERT INTO scada_user (username, email, enabled, first_name, last_name, password) VALUES ('admin', 'admin@mail.com', 1, 'Scada', 'Administrator', '$2a$12$PDBKGH7tqpUDWKBG59msIuMcfGT0jV3tJGS9TJIJsh.gevwEuGnCC');

--INSERT INTO datasource (sid, name, update_period_type, update_periods)
--  VALUES ('DS_01', 'DataSource 1', 1, 5);
--INSERT INTO datasource (sid, name, update_period_type, update_periods)
--  VALUES ('DS_02', 'DataSource 2', 1, 5);
--INSERT INTO datasource (sid, name, update_period_type, update_periods)
--  VALUES ('DS_03', 'DataSource 3', 1, 5);

--INSERT INTO role(id, name, roles) VALUES
--    (1, 'ROLE_ADMIN')

INSERT INTO system_message(id,content) VALUES
    (1,'First Level Message'),
    (2,'Second Level Message'),
    (3,'Third Level Message');

INSERT INTO ds_virtual(id,sid,name,update_period, enabled) VALUES
    (1,'SID_012', 'Advanced', 5, true);

INSERT INTO acl_sid (id, principal, sid) VALUES
(1, 0, 'ROLE_ADMIN'),
(2, 1, 'admin');

INSERT INTO acl_class (id, class) VALUES
(1, 'org.reggsoft.srfcore.persistance.entity.NoticeMessage'),
(2, 'org.reggsoft.srfcore.persistance.entity.DataSourceVirtual');


INSERT INTO acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES
(1, 1, 1, NULL, 2, 0),
(2, 1, 2, NULL, 2, 0),
(3, 1, 3, NULL, 2, 0),
(4, 2, 1, NULL, 2, 0);

INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES
(1, 1, 1, 2, 1, 1, 1, 0),
(2, 2, 1, 2, 1, 1, 1, 0),
(3, 3, 1, 2, 1, 1, 1, 0),
(4, 4, 1, 2, 1, 1, 1, 0);

