package com.bjpowernode.triage.buss.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bjpowernode.triage.buss.dao.PatientDao;
import com.bjpowernode.triage.buss.entity.Patient;
import com.bjpowernode.triage.common.persistence.HibernateDao;
import com.bjpowernode.triage.common.service.BaseService;
import com.bjpowernode.triage.common.utils.security.Digests;
import com.bjpowernode.triage.common.utils.security.Encodes;
import com.bjpowernode.triage.system.entity.User;

/**
 * 患者service
 * @author bjpowernode
 * @date 2016年1月15日
 */
@Service
@Transactional(readOnly = true)
public class PatientService extends BaseService<Patient, Integer> {
	
	/**加密方法*/
	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	private static final int SALT_SIZE = 8;	//盐长度

	@Autowired
	private PatientDao patientDao;

	@Override
	public HibernateDao<Patient, Integer> getEntityDao() {
		return patientDao;
	}

	/**
	 * 保存患者
	 * @param patient
	 */
	@Transactional(readOnly=false)
	public void save(Patient patient) {
		patientDao.save(patient);
	}

	
	/**
	 * 删除患者
	 * @param id
	 */
	@Transactional(readOnly=false)
	public void delete(Integer id){
		if(!isSupervisor(id))
			patientDao.delete(id);
	}
	
	
	/**
	 * 判断是否超级管理员
	 * @param id
	 * @return boolean
	 */
	private boolean isSupervisor(Integer id) {
		return id == 1;
	}
	
	
	
}
