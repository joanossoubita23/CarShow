package com.binary.carShow.service;

import com.binary.carShow.entity.Owner;

import java.util.List;

public interface OwnerService {


     List<Owner> getOwners();

    Owner getOwnerById(Long id);

    Owner addOwner(Owner owner);
}
