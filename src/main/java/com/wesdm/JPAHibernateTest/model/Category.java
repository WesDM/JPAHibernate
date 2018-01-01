package com.wesdm.JPAHibernateTest.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_GENERATOR_POOLED")
	@org.hibernate.annotations.GenericGenerator(name = "ID_GENERATOR_POOLED", strategy = "enhanced-sequence",
			parameters = { @org.hibernate.annotations.Parameter(name = "sequence_name", value = "cat_seq"),
					@org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
					@org.hibernate.annotations.Parameter(name = "increment_size", value = "5"),
					@org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled-lo") })
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_generator")
//	@SequenceGenerator(name = "sequence_generator", sequenceName = "cat_seq", allocationSize = 5)
    protected Long id;

    protected String name;

    @OneToMany(mappedBy = "category")
    protected Set<CategorizedItem> categorizedItems = new HashSet<>();

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<CategorizedItem> getCategorizedItems() {
        return categorizedItems;
    }

    // ...
}
