package com.hostelMS.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class user {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int userId;
	@NotNull
	@Size(min=3,max=15,message="Username must be more than 3 and upto 15 characters ")
	private String userName;
	@NotNull
	@Pattern(regexp="[0-9]{10}",message="Phone number must be 10 digits")
	private String userPhone;
	@NotNull
	@Size(min=5,max=20,message="Password should be more than 5 characters")
	@NotNull
	private String userPassword;
	@NotNull
	@Size(min=4,max=20,message="Address should be more than 4 characters")
	private String userAddress;
	private String userRole;
	private int userFee;
	@ManyToOne
	private room userRoom;
	
}