--CREATE TABLE scada_user (
--    id INTEGER NOT NULL AUTO_INCREMENT,
--    username varchar(255) NOT NULL,
--    password varchar(255) NOT NULL,
--    first_name varchar(255)
--    PRIMARY KEY (id)
--);
--
CREATE TABLE IF NOT EXISTS acl_sid (
  id INTEGER NOT NULL AUTO_INCREMENT,
  principal INTEGER NOT NULL,
  sid varchar(100) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS acl_class (
  id INTEGER NOT NULL AUTO_INCREMENT,
  class varchar(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS acl_entry (
  id INTEGER NOT NULL AUTO_INCREMENT,
  acl_object_identity INTEGER NOT NULL,
  ace_order INTEGER NOT NULL,
  sid INTEGER NOT NULL,
  mask INTEGER NOT NULL,
  granting INTEGER NOT NULL,
  audit_success INTEGER NOT NULL,
  audit_failure INTEGER NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS acl_object_identity (
  id INTEGER NOT NULL AUTO_INCREMENT,
  object_id_class INTEGER NOT NULL,
  object_id_identity INTEGER NOT NULL,
  parent_object INTEGER DEFAULT NULL,
  owner_sid INTEGER DEFAULT NULL,
  entries_inheriting INTEGER NOT NULL,
  PRIMARY KEY (id)
);

ALTER TABLE acl_entry
ADD FOREIGN KEY (acl_object_identity) REFERENCES acl_object_identity(id);

ALTER TABLE acl_entry
ADD FOREIGN KEY (sid) REFERENCES acl_sid(id);

--
-- Constraints for table acl_object_identity
--
ALTER TABLE acl_object_identity
ADD FOREIGN KEY (parent_object) REFERENCES acl_object_identity (id);

ALTER TABLE acl_object_identity
ADD FOREIGN KEY (object_id_class) REFERENCES acl_class (id);

ALTER TABLE acl_object_identity
ADD FOREIGN KEY (owner_sid) REFERENCES acl_sid (id);
