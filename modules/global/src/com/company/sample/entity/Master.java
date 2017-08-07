package com.company.sample.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s|name")
@Table(name = "SAMPLE_MASTER")
@Entity(name = "sample$Master")
public class Master extends StandardEntity {
    private static final long serialVersionUID = -5622417975114646516L;

    @Column(name = "NAME", nullable = false)
    protected String name;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "master")
    protected List<Detail> details;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDetails(List<Detail> details) {
        this.details = details;
    }

    public List<Detail> getDetails() {
        return details;
    }


}