package com.ejemplo.msa.entity;
import jakarta.persistence.*;
@Entity @Table(name="divisiones") public class EntityA { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id; @Column(name="nombre",nullable=false,unique=true,length=120) private String nombreA; public EntityA(){} public EntityA(Long id,String nombre){this.id=id;this.nombreA=nombre;} public Long getId(){return id;} public void setId(Long id){this.id=id;} public String getNombreA(){return nombreA;} public void setNombreA(String nombreA){this.nombreA=nombreA;} }
