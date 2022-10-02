package com.hostelMS.dao;

import java.util.List;

import com.hostelMS.exception.GlobalException;
import com.hostelMS.Model.*;
public interface adminDao {

	public List<room> viewRooms();
	public List<user> viewUsers();
	public int createRoom(room r1) throws GlobalException;
	public int allotRoom(int uId,int rId);
	public int deleteUser(int uId);
	public List<user> userInARoom(int rId);
	public int addDueAmount(int uId,int amount);
	public int paidDueAmount(int uId,int amount);
	public user viewuserprofile(int uId)throws GlobalException;
	
}