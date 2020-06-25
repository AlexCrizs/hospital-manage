package com.bjpowernode.triage.system.dao;

import org.springframework.stereotype.Repository;

import com.bjpowernode.triage.common.persistence.HibernateDao;
import com.bjpowernode.triage.system.entity.User;


/**
 * 用户DAO
 * @author ty
 * @date 2016年1月13日
 */
@Repository
public class UserDao extends HibernateDao<User, Integer>{

}
