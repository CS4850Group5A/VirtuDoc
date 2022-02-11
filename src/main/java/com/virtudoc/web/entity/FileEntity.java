package com.virtudoc.web.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "file_entity")
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "file_entity_seq")
    @SequenceGenerator(name = "file_entity_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "file_path", nullable = false, unique = true)
    private String filePath;

    @Column(name = "storage_type", nullable = false)
    private String storageType;

    @ManyToMany
    @JoinTable(name = "file_entity_user_accounts",
            joinColumns = @JoinColumn(name = "file_entity_id"),
            inverseJoinColumns = @JoinColumn(name = "user_accounts_id"))
    private List<UserAccount> userAccounts = new ArrayList<>();

    @Column(name = "uploaded_date", nullable = false)
    private Date uploadedDate;

    public Date getUploadedDate() {
        return uploadedDate;
    }

    public List<UserAccount> getUserAccounts() {
        return userAccounts;
    }

    public void setUserAccounts(List<UserAccount> userAccounts) {
        this.userAccounts = userAccounts;
    }

    public String getStorageType() {
        return storageType;
    }

    public void setStorageType(String storageType) {
        this.storageType = storageType;
    }

    public String getFilePath() {
        return filePath;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}