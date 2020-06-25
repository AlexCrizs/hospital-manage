package com.bjpowernode.triage.buss.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 处方entity
 * @author bjpowernode
 * @date 2016年1月14日
 */
@Entity
@Table(name = "Prescription")
@DynamicUpdate @DynamicInsert
public class Prescription implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String symptom;
	private String diagnose;
	private String Medicine;
	
	public Prescription() {
	}
	
	public Prescription(Integer id) {
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "SYMPTOM", unique = true, nullable = false)
	public String getSymptom() {
		return symptom;
	}

	public void setSymptom(String symptom) {
		this.symptom = symptom;
	}
	
	@Column(name = "DIAGNOSE", unique = true, nullable = false)
	public String getDiagnose() {
		return diagnose;
	}

	public void setDiagnose(String diagnose) {
		this.diagnose = diagnose;
	}
	
	@Column(name = "MEDICINE", unique = true, nullable = false)
	public String getMedicine() {
		return Medicine;
	}

	public void setMedicine(String medicine) {
		Medicine = medicine;
	}
	
	
}