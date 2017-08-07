package com.company.sample.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@NamePattern("%s|description")
@Table(name = "SAMPLE_DETAIL")
@Entity(name = "sample$Detail")
public class Detail extends StandardEntity {
    private static final long serialVersionUID = -4620982997175193206L;

    @Column(name = "DESCRIPTION", nullable = false)
    protected String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MASTER_ID")
    protected Master master;

    public void setMaster(Master master) {
        this.master = master;
    }

    public Master getMaster() {
        return master;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }


}