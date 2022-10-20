package ir.mvbdx.mywallet.model.dto;

import ir.mvbdx.mywallet.model.entity.Role;
import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link Role} entity
 */
@Data
public class RoleDTO implements Serializable {
    private Long id;
    private String name;
}