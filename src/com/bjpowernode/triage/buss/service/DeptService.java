package com.bjpowernode.triage.buss.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bjpowernode.triage.buss.dao.DeptDao;
import com.bjpowernode.triage.buss.entity.Dept;
import com.bjpowernode.triage.common.persistence.HibernateDao;
import com.bjpowernode.triage.common.service.BaseService;
import com.bjpowernode.triage.common.utils.DateUtils;
import com.bjpowernode.triage.common.utils.security.Digests;
import com.bjpowernode.triage.common.utils.security.Encodes;
import com.bjpowernode.triage.system.entity.User;

/**
 * 科室service
 * @author bjpowernode
 * @date 2016年1月15日
 */
@Service
@Transactional(readOnly = true)
public class DeptService extends BaseService<Dept, Integer> {
	
	/**加密方法*/
	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	private static final int SALT_SIZE = 8;	//盐长度

	@Autowired
	private DeptDao deptDao;

	@Override
	public HibernateDao<Dept, Integer> getEntityDao() {
		return deptDao;
	}

	/**
	 * 保存科室
	 * @param dept
	 */
	@Transactional(readOnly=false)
	public void save(Dept dept) {
		deptDao.save(dept);
	}

	
	/**
	 * 删除科室
	 * @param id
	 */
	@Transactional(readOnly=false)
	public void delete(Integer id){
		if(!isSupervisor(id))
			deptDao.delete(id);
	}
	
	
	/**
	 * 判断是否超级管理员
	 * @param id
	 * @return boolean
	 */
	private boolean isSupervisor(Integer id) {
		return id == 1;
	}
	
	/**
	 * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
	 */
	private void entryptPassword(User user) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		user.setSalt(Encodes.encodeHex(salt));

		byte[] hashPassword = Digests.sha1(user.getPlainPassword().getBytes(),salt, HASH_INTERATIONS);
		user.setPassword(Encodes.encodeHex(hashPassword));
	}
	
	
}
