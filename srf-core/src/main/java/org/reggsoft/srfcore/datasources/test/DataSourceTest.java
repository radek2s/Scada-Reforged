package org.reggsoft.srfcore.datasources.test;

import org.reggsoft.srfcore.datasources.AbstractDataSource;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "datasource")
public class DataSourceTest extends AbstractDataSource {

    private String comment;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
