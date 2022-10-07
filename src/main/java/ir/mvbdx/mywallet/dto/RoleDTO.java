package ir.mvbdx.mywallet.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link ir.mvbdx.mywallet.entity.Role} entity
 */
@Data
public class RoleDTO implements Serializable {
    private Long id;
    private String name;
}