package com.bjpowernode.triage.buss.dao;

import org.springframework.stereotype.Repository;

import com.bjpowernode.triage.buss.entity.Patient;
import com.bjpowernode.triage.common.persistence.HibernateDao;


/**
 * 患者DAO
 * @author bjpowernode
 * @date 2016年1月15日
 */
@Repository
public class PatientDao extends HibernateDao<Patient, Integer>{

}
