package com.hardik.farmapp.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hardik.farmapp.Enum.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.hardik.farmapp.Entity.DocumentMetaData;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
@AllArgsConstructor
@Table(name = "users")
@Builder
public class Users {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @JsonIgnore
    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @Builder.Default
    private List<FarmAnalysis> analyses = new ArrayList<>();

    @OneToMany(mappedBy = "uploadedBy",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonIgnore
    @Builder.Default
    private List<DocumentMetaData> uploadedDocuments = new ArrayList<>();

}
