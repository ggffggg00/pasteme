package ru.borisof.pasteme.snippet.model.entity;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "syntax_type")
@Entity
@Cacheable
public class SyntaxType {

  @Id
  @Column(name = "syntax_code", nullable = false)
  private String code;

  @Column(name = "display_name", nullable = false)
  private String displayName;

}