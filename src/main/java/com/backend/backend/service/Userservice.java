package com.backend.backend.service;

import java.util.List;
import java.util.UUID;

import org.jspecify.annotations.Nullable;

import com.backend.backend.dto.Adduserdto;
import com.backend.backend.dto.Googlelogindto;
import com.backend.backend.dto.Updateuser;
import com.backend.backend.dto.UserLogindto;
import com.backend.backend.dto.Userdto;
import com.backend.backend.dto.Userlogindata;
import com.backend.backend.dto.Userpasswordchange;

import jakarta.validation.Valid;

public interface Userservice {

  @Nullable
  Userdto createnewuser(Adduserdto adduserdto);

  @Nullable
  UserLogindto isvalid(Userlogindata userlogindata);

  @Nullable
  Userdto updateuserdetail(Updateuser userdto, UUID id);

  @Nullable
  List<Userdto> getstudentall();
  UserLogindto googleLogin(Googlelogindto idToken);
  void passwordchange(String email, String newpass);

  
 
  
}
