package com.dong.repository;

import com.dong.pojo.Customer;
import com.dong.pojo.Service;
import com.dong.pojo.UseService;

import java.util.List;

public interface ServiceRepository {
    List<Service> getServicesByIdCustomer(int customerId);
    public boolean addOrUpdateService(Service s) ;

    boolean deleteSer(int id);
    List<Service> getServices();
    public Service getServiceById(int id);
    public void save(UseService useService);
    public UseService getUseServiceByCustomerIdAndServiceId(int customerId, int serviceId);


    boolean deleteUseSer(int id);

    UseService getUseServiceById(int id);
}
