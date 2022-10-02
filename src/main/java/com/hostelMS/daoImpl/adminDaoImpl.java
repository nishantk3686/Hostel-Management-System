package com.hostelMS.daoImpl;
import java.util.List;
import javax.persistence.Query;
import org.hibernate.Session;
import com.hostelMS.config.HibernateUtil;
import com.hostelMS.dao.adminDao;
import com.hostelMS.exception.GlobalException;
import com.hostelMS.Model.room;
import com.hostelMS.Model.user;

public class adminDaoImpl implements adminDao {

	//view all available rooms
	@Override
	public List<room> viewRooms() {
		//autoclosable session object
		try(Session ses=HibernateUtil.getSession()){
			//getting rows of a room table
			Query qu=ses.createQuery("from room");
			List<room> roomList=qu.getResultList();
			return roomList;
		}
	}

	
	//view the all registered users
	@Override
	public List<user> viewUsers() {
		
		try(Session ses=HibernateUtil.getSession()){
			//getting rows of a room table
			String student="student";
			Query qu=ses.createQuery("from user where userRole=:student").setParameter("student", student);
			List<user> userList=qu.getResultList();
			return userList;
		}
		
	}
	//creating new rooms
	@Override
	public int createRoom(room r1) throws GlobalException {
	
	try(Session ses=HibernateUtil.getSession()){
		
		ses.beginTransaction();
		String roomName=r1.getRoomName();
		room r2=null;
		//checking for existing roomname
		r2=(room)ses.createQuery("from room where roomName=:roomName").setParameter("roomName", roomName).uniqueResult();
		//if room name is unique then we can save the room
		if(r2==null)
		{
			ses.save(r1);
			//commit
			ses.getTransaction().commit();
			return 1;
		}
		else {
			throw new GlobalException("ROOM ALREADY EXISTS. TRY NEW ROOM NAME");
		}
		}
	}

	//allot the room to the user
	@Override
	public int allotRoom(int uId, int rId) {
		
		try(Session ses=HibernateUtil.getSession())
		{
			ses.beginTransaction();
			int status=ses.createQuery("update user set userRoom_roomId=:rId where userId=:uId").setParameter("rId", rId).setParameter("uId", uId).executeUpdate();
			ses.getTransaction().commit();
			return status;
			}
		}
	//delete the user
	@Override
	public int deleteUser(int uId) {
		
		try(Session ses=HibernateUtil.getSession())
		{
			ses.beginTransaction();
			int status= ses.createQuery("delete from user where userId=:uId").setParameter("uId", uId).executeUpdate();
			ses.getTransaction().commit();
			return status;
		}		
		}

	//retrive the students who are in one room
	@Override
	public List<user> userInARoom(int rId) {
		
		try(Session ses=HibernateUtil.getSession()){
			
			Query qu=ses.createQuery("from user where userRoom_roomId=:rId").setParameter("rId", rId);
			List<user> userList=qu.getResultList();
			return userList;
		}
	}

	//add due amount to the user
	@Override
	public int addDueAmount(int uId, int amount) {
		try(Session ses=HibernateUtil.getSession()){
			ses.beginTransaction();
			int dueamount=(int)ses.createQuery("select userFee from user where userId=:uId").setParameter("uId",uId).uniqueResult();
		
			dueamount+=amount;
			int status=ses.createQuery("update user set userFee=:dueamount where userId=:uId").setParameter("dueamount", dueamount).setParameter("uId", uId).executeUpdate();
			ses.getTransaction().commit();
			return status;
		}
	}
	
	//reduce the fee amount if user paidamount
	@Override
	public int paidDueAmount(int uId, int amount) {
		try(Session ses=HibernateUtil.getSession()){
			ses.beginTransaction();
			int dueamount=(int)ses.createQuery("select userFee from user where userId=:uId").setParameter("uId",uId).uniqueResult();
		
			dueamount-=amount;
			int status=ses.createQuery("update user set userFee=:dueamount where userId=:uId").setParameter("dueamount", dueamount).setParameter("uId", uId).executeUpdate();
			ses.getTransaction().commit();
			return status;
		}
	}


	@Override
	public user viewuserprofile(int uId) throws GlobalException {
		try(Session ses=HibernateUtil.getSession()){
			
			user u1=ses.get(user.class, uId);
			return u1;
		}
		
		
	}

}