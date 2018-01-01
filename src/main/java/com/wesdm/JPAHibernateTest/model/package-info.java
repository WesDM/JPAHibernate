//@org.hibernate.annotations.GenericGenerator(name = "ID_GENERATOR", strategy = "enhanced-sequence", parameters = {
//		@org.hibernate.annotations.Parameter(name = "sequence_name", value = "JPA_SEQUENCE"),
//		@org.hibernate.annotations.Parameter(name = "initial_value", value = "1000") }
//)
//
@org.hibernate.annotations.GenericGenerator(name = "ID_GENERATOR_POOLED", strategy = "enhanced-sequence", parameters = {
		@org.hibernate.annotations.Parameter(name = "sequence_name", value = "JPA_SEQUENCE"),
		@org.hibernate.annotations.Parameter(name = "increment_size", value = "100"),
		@org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled-lo") })
package com.wesdm.JPAHibernateTest.model;