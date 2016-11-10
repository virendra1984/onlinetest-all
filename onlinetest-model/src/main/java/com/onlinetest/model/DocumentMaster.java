package com.onlinetest.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;

/**
 * Created by virendra k Singh on 27/09/16.
 */

@Entity
@Table(name = "document_master")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DocumentMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "document_type_id")
    private Long documentTypeId;

    @Column(name = "document_type")
    private String documentType;

    @Column(name = "document_name")
    private String documentName;

    @Column(name = "document_pdf")
    private String documentPdf;

    @Transient
    private Boolean isNewAccountPreDoc;

    @Transient
    private Boolean isExistingAccountPreDoc;

    public Boolean getIsExistingAccountPreDoc() {
        return isExistingAccountPreDoc;
    }

    public void setIsExistingAccountPreDoc(Boolean isExistingAccountPreDoc) {
        this.isExistingAccountPreDoc = isExistingAccountPreDoc;
    }

    public Boolean getIsNewAccountPreDoc() {
        return isNewAccountPreDoc;
    }

    public void setIsNewAccountPreDoc(Boolean isNewAccountPreDoc) {
        this.isNewAccountPreDoc = isNewAccountPreDoc;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDocumentTypeId() {
        return documentTypeId;
    }

    public void setDocumentTypeId(Long documentTypeId) {
        this.documentTypeId = documentTypeId;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getDocumentPdf() {
        return documentPdf;
    }

    public void setDocumentPdf(String documentPdf) {
        this.documentPdf = documentPdf;
    }

}