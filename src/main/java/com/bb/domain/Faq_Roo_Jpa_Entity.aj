// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.bb.domain;

import com.bb.domain.Faq;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;

privileged aspect Faq_Roo_Jpa_Entity {
    
    declare @type: Faq: @Entity;
    
    @Id
    @SequenceGenerator(name = "faqGen", sequenceName = "FAQ_SEQ")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "faqGen")
    @Column(name = "id")
    private Long Faq.id;
    
    @Version
    @Column(name = "version")
    private Integer Faq.version;
    
    public Long Faq.getId() {
        return this.id;
    }
    
    public void Faq.setId(Long id) {
        this.id = id;
    }
    
    public Integer Faq.getVersion() {
        return this.version;
    }
    
    public void Faq.setVersion(Integer version) {
        this.version = version;
    }
    
}