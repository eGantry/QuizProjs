package org.me.student;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Student.class)
public abstract class Student_ {

	public static volatile SingularAttribute<Student, StudentPK> studentPK;
	public static volatile SingularAttribute<Student, Date> admissionDate;
	public static volatile SingularAttribute<Student, String> major;
	public static volatile SingularAttribute<Student, String> department;

}

