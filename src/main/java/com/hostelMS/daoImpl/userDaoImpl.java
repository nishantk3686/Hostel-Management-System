package com.hostelMS.daoImpl;

import org.hibernate.Session;

import com.hostelMS.config.HibernateUtil;
import com.hostelMS.dao.userDao;
import com.hostelMS.exception.GlobalException;
import com.hostelMS.Model.user;


public class userDaoImpl implements userDao{

	//getting user profile to print room details
	@Override
	public user viewRoom(int uId) {
		
		try(Session ses=HibernateUtil.getSession()){
			
			user u2=ses.get(user.class,uId);
			return u2;
		}
		
	}

	//getting dueamount
	@Override
	public int viewDueAmount(int uId) {
	//autoclosable session
		try(Session ses=HibernateUtil.getSession()){
		
			int amount=(int)ses.createQuery("select userFee from user where userId=:uId").setParameter("uId", uId).uniqueResult();
				return amount;
		}
	}

	//getting profile of the user
	@Override
	public user viewProfile(int uId) {
		try(Session ses=HibernateUtil.getSession()){
			
			user u2=ses.get(user.class,uId);
			return u2;
	}
	}
	//Here i updating phone number
	@Override
	public int changePhone(int uId, String phone) {
		
		try(Session ses=HibernateUtil.getSession()){
			ses.beginTransaction();
		int status=ses.createQuery("update user set userPhone=:phone where userId=:uId").setParameter("phone", phone).setParameter("uId",uId).executeUpdate();
			ses.getTransaction().commit();
			return status;	
		}
	}

	//Checking current password to update to new password
	@Override
	public int changePassword(int uId, String oldPwd, String newPwd) throws GlobalException {
		
		try(Session ses=HibernateUtil.getSession()){
			ses.beginTransaction();
			user u1=ses.get(user.class, uId);
			if(u1.getUserPassword().equals(oldPwd)) {
				int status =ses.createQuery("update user set userPassword=:newPwd where userId=:uId").setParameter("newPwd", newPwd).setParameter("uId", uId).executeUpdate();
				ses.getTransaction().commit();
				return status;
			}
			else {
				throw new GlobalException("Please provide your current password To upadate to new password");
			}			
		}
				
	}

}