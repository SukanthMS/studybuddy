package com.example.studybuddy.entity;

import jakarta.persistence.*;

@Entity
@Table(name="study_groups")
public class StudyGroup {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    private String subject;
    private Integer maxMembers;
    private Integer creatorId;

    public StudyGroup() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id=id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject=subject;
    }

    public Integer getMaxMembers() {
        return maxMembers;
    }

    public void setMaxMembers(Integer maxMembers) {
        this.maxMembers=maxMembers;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId=creatorId;
    }
}