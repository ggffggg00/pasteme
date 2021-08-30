package ru.borisof.pasteme.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Data()
@Entity()
@Builder()
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pm_paste")
public class Paste {

    public static enum PasteSyntaxType{
        JAVA,
        NONE
    }

    @JsonIgnore
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false, unique = true)
    public Long id;

    @Lob
    @Type(type="org.hibernate.type.BinaryType")
    @Column(name = "content", nullable = false)
    byte[] content;

    @Column(name = "title", nullable = false, length = 100)
    String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "syntax", nullable = false, length = 10)
    PasteSyntaxType syntaxType;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", nullable = false)
    Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated", nullable = false)
    Date updatedAt;

}
