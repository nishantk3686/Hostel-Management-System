//service layer
package com.hostelMS.serviceImpl;
import java.util.Scanner;
import org.apache.log4j.Logger;
import com.hostelMS.dao.userDao;
import com.hostelMS.daoImpl.userDaoImpl;
import com.hostelMS.exception.GlobalException;
import com.hostelMS.Model.user;
import com.hostelMS.service.userdashboard;

public class userdashboardImpl implements userdashboard {
	//declaring static objects to use in entire class
	static Logger log=Logger.getLogger(userdashboardImpl.class);
	static Scanner bs=new Scanner(System.in);
	static userdashboardImpl udl=new userdashboardImpl();
	static userDao dao=new userDaoImpl();
	static int userId;
	@Override
	//dashboard method
	public void dashboard(int uId) throws GlobalException {
		userId=uId;
		user u1=dao.viewRoom(userId);
		log.info("\t\t\t********************Welcome "+ u1.getUserName() + "********************");
		int op=0;
		while(op<6) {
			//user can select operation
			log.info("\nPRESS- 1 FOR VIEW ROOM \nPRESS- 2 FOR VIEW FEES DUE \nPRESS- 3 FOR VIEW YOUR PROFILE\nPRESS- 4 FOR UPDATE YOUR PHONE NUMBER \nPress 5 FOR CHANGE YOUR CURRENT PASSWORD");
			op=bs.nextInt();
			
			switch(op) {
		
		case 1->udl.viewRoom();
		
		case 2->udl.viewDueAmount();
		
		case 3->udl.viewProfile();
		
		case 4->udl.changePhonenumber();
		
		case 5->udl.changePassword();
		}
		}
	}

	//view room details of the user
	@Override
	public void viewRoom() {
		//calling dao layer
	user u1=dao.viewRoom(userId);
	log.info("Hello "+ u1.getUserName() +" your room number is"+u1.getUserRoom().getRoomId()+" room name is "+u1.getUserRoom().getRoomName()+" and it is "+u1.getUserRoom().getRoomType()+" room");
	}

	//view dueamount of the user
	@Override
	public void viewDueAmount() {
		//calling dao layer
		int amount=dao.viewDueAmount(userId);
		log.info("YOUR FEE DUE UPTO THIS MONTH IS :"+amount);
	}

	//viewProfile with toString 
	@Override
	public void viewProfile() {
		
		user u1=dao.viewProfile(userId);
		log.info(u1);
		
	}

	//to change phone number
	@Override
	public void changePhonenumber() {
		log.info("ENTER NEW PHONE NUMBER");
		String phone=bs.next();
		int st=dao.changePhone(userId, phone);
		if(st==1) {
			log.info("PHONE UPDATE SUCCESSFULLY ");
		}
	}

	//to update password, user have to enter current password
	@Override
	public void changePassword() throws GlobalException {
		
		log.info("ENTER CURRENT PASSWORD");
		String oldpwd=bs.next();
		log.info("ENTER NEW PASSWORD");
		String newpwd=bs.next();
		int st=dao.changePassword(userId, oldpwd, newpwd);
		if(st==1) {
			log.info("PASSWORD CHANGE SUCCESSFULLY ");
		}
	}
}

