package com.ejemplo.msa.dto;
import jakarta.validation.constraints.NotBlank; import jakarta.validation.constraints.Size;
public class EntityADto { private Long id; @NotBlank @Size(max=120) private String nombreA; public EntityADto(){} public EntityADto(Long id,String nombreA){this.id=id;this.nombreA=nombreA;} public Long getId(){return id;} public void setId(Long id){this.id=id;} public String getNombreA(){return nombreA;} public void setNombreA(String nombreA){this.nombreA=nombreA;} }
