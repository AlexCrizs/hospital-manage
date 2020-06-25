package com.bjpowernode.triage.buss.dao;

import org.springframework.stereotype.Repository;

import com.bjpowernode.triage.buss.entity.Prescription;
import com.bjpowernode.triage.common.persistence.HibernateDao;


/**
 * 分诊DAO
 * @author bjpowernode
 */
@Repository
public class PrescriptionDao extends HibernateDao<Prescription, Integer>{

}
