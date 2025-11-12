package com.example.masterdetailtable.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "master_table")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Master {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @OneToMany(mappedBy = "master", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Detail> details = new ArrayList<>();

    public void addDetail(Detail detail) {
        details.add(detail);
        detail.setMaster(this);
    }

    public void removeDetail(Detail detail) {
        details.remove(detail);
        detail.setMaster((Master) null);
    }
}