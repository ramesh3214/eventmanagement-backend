package com.backend.backend.service;

import org.jspecify.annotations.Nullable;

import com.backend.backend.dto.Adduserdto;
import com.backend.backend.dto.Userdto;

import jakarta.validation.Valid;

public interface Userservice {

  @Nullable
  Userdto createnewuser(Adduserdto adduserdto);
  
}
