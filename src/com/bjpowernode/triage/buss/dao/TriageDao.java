package com.bjpowernode.triage.buss.dao;

import org.springframework.stereotype.Repository;

import com.bjpowernode.triage.buss.entity.Triage;
import com.bjpowernode.triage.common.persistence.HibernateDao;


/**
 * 分诊DAO
 * @author bjpowernode
 * @date 2016年1月15日
 */
@Repository
public class TriageDao extends HibernateDao<Triage, Integer>{

}
