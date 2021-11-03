package ru.borisof.pasteme.security.model;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

@Entity
@Table(name = "authority")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Authority {

  @Id
  @Column(name = "name", length = 50)
  @NotNull
  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    Authority authority = (Authority) o;
    return Objects.equals(name, authority.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this);
  }
}
