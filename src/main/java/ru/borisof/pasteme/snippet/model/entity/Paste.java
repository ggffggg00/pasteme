package ru.borisof.pasteme.snippet.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.ToString.Exclude;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import ru.borisof.pasteme.account.model.entity.User;

@Table(name = "paste")
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Paste {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonIgnore
  @Column(name = "id", unique = true, updatable = false)
  private long id;

  @Column(name = "title", updatable = false, length = 100)
  private String title;

  @Lob
  @Column(name = "content", updatable = false)
  private String content;

  @ManyToOne(targetEntity = SyntaxType.class, optional = false)
  private SyntaxType syntaxType;

  @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
  @Exclude
  private User sender;

  @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
  @Exclude
  private User recipient;

  @CreationTimestamp
  @Column(name = "created_at", updatable = false, nullable = false)
  private LocalDateTime createdAt;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    Paste paste = (Paste) o;
    return Objects.equals(id, paste.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}