package dev.fredpena.dcm.data;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author me@fredpena.dev
 * @created 13/07/2025  - 11:55
 */

@Getter
@Setter
@Entity
@Table(name = "tenant")
public class Tenant implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tenantId;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(unique = true, updatable = false)
    private String tenantName;

}
