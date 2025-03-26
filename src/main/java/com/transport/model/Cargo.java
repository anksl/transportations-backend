package com.transport.model;

import com.transport.model.enums.LoadApproachEntity;
import com.transport.model.enums.LoadMethodEntity;
import com.transport.model.enums.PackagingEntity;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cargos")
public class Cargo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "type")
    private LoadApproachEntity loadApproach;

    @Column(name = "loading_method")
    private LoadMethodEntity loadMethod;

    private PackagingEntity packaging;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
    @PrimaryKeyJoinColumn(referencedColumnName = "id")
    private Size size;

    @OneToMany(mappedBy = "cargo")
    private List<Transportation> transportations;
}
