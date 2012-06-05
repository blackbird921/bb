package com.bb.domain;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Configurable
@Entity
public class Faq {

    @NotNull
    @Size(min = 2, max = 100)
    private String question;

    @NotNull
    @Size(min = 2, max = 500)
    private String answer;

    @NotNull
    private Long questionOrder;

    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public String getQuestion() {
        return this.question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return this.answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Long getQuestionOrder() {
        return this.questionOrder;
    }

    public void setQuestionOrder(Long questionOrder) {
        this.questionOrder = questionOrder;
    }

    @PersistenceContext
    transient EntityManager entityManager;

    public static final EntityManager entityManager() {
        EntityManager em = new Faq().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

    public static long countFaqs() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Faq o", Long.class).getSingleResult();
    }

    public static List<Faq> findAllFaqs() {
        return entityManager().createQuery("SELECT o FROM Faq o", Faq.class).getResultList();
    }

    public static Faq findFaq(Long id) {
        if (id == null) return null;
        return entityManager().find(Faq.class, id);
    }

    public static List<Faq> findFaqEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Faq o", Faq.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

    @Transactional
    public void persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }

    @Transactional
    public void remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Faq attached = findFaq(this.id);
            this.entityManager.remove(attached);
        }
    }

    @Transactional
    public void flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }

    @Transactional
    public void clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }

    @Transactional
    public Faq merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Faq merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

    @Id
    @SequenceGenerator(name = "faqGen", sequenceName = "FAQ_SEQ")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "faqGen")
    @Column(name = "id")
    private Long id;

    @Version
    @Column(name = "version")
    private Integer version;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
