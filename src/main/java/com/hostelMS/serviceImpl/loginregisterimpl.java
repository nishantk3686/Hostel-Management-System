package com.hostelMS.serviceImpl;
import java.util.Scanner;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.apache.log4j.Logger;
import com.hostelMS.App;
import com.hostelMS.dao.hostelMSDao;
import com.hostelMS.daoImpl.hostelMSdaoImpl;
import com.hostelMS.exception.GlobalException;
import com.hostelMS.Model.user;
import com.hostelMS.service.admindashboard;
import com.hostelMS.service.loginregister;
import com.hostelMS.service.userdashboard;

public class loginregisterimpl implements loginregister {
	static Logger log=Logger.getLogger(App.class);
	static Scanner bs=new Scanner(System.in);
	static hostelMSDao dao=new hostelMSdaoImpl();
	
	//registration method
	public void register() throws GlobalException{
		log.info("WELCOME TO SANSKRITI HOSTEL");
		log.info("ENTER USERNAME");
		String uname=bs.next();
		log.info("CREATE PASSWORD");
		String upwd=bs.next();
		log.info("ENTER PHONE NUMBER");
		String uphone=bs.next();
		log.info("ENTER ADDRESS");
		String uaddress=bs.next();
		
		user u1=new user();
		u1.setUserName(uname);
		u1.setUserPassword(upwd);
		u1.setUserPhone(uphone);
		u1.setUserAddress(uaddress);
		u1.setUserRole("student");
		u1.setUserRoom(null);
		u1.setUserFee(0);
		
		ValidatorFactory vf= Validation.buildDefaultValidatorFactory();
		Validator valid=vf.getValidator();
		
		Set<ConstraintViolation<user>> violations=	valid.validate(u1);
		
		if(violations.size()>0)
		{
			for(ConstraintViolation<user> violate:violations)
				log.info(violate.getMessage());
		}
		else {
		int status=dao.registration(u1);
			
			if(status==1) {
				log.info("YOU ARE SUCCESFULLY REGISTERED WITH OUR HOSTEL");
			}
			else {
				throw new GlobalException("SOMETHING WENT WRONG. TRY AGAIN !!!!");
			}
		}
	}
	

	public void login()throws GlobalException {
		log.info("WELCOME TO SANSKRITI HOSTEL \n PROVIDE SOME DETAILS TO LOGIN....");
		
		log.info("ENTER USERNAME");
		String username=bs.next();
		log.info("ENTER PASSWORD");
		String password=bs.next();
		//checking login
		user u1=dao.login(username, password);
		//success message
		log.info("Hello"+u1.getUserName()+"Login Success");
		userdashboard udl=new userdashboardImpl();
		admindashboard adl=new admindashboardImpl();
		//if userrole is student userdashboard will open
		//if userrole is admin admindashboard will open
		if(u1.getUserRole().equals("student")) {
			udl.dashboard(u1.getUserId());
		}
		else if(u1.getUserRole().equals("admin")) {
			adl.dashboard();
		}
	}

}