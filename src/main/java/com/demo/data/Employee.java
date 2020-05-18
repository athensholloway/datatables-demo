package com.demo.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Employee {
	  @Id
	  @GeneratedValue
	  private Long id;
	  private String name;
	  private String dept;
	  private int salary;
}
