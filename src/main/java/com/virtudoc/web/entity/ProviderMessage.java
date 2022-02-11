package com.virtudoc.web.entity;

import javax.persistence.*;

@Entity
@Table(name = "provider_message")
public class ProviderMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "provider_message_seq")
    @SequenceGenerator(name = "provider_message_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "sender_account_id")
    private UserAccount senderAccount;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "user_account_id")
    private UserAccount userAccount;

    @Column(name = "message", nullable = false, length = 4000)
    private String message;

    @Column(name = "subject", nullable = false)
    private String subject;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public UserAccount getSenderAccount() {
        return senderAccount;
    }

    public void setSenderAccount(UserAccount senderAccount) {
        this.senderAccount = senderAccount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}