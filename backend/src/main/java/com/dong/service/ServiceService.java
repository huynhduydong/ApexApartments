package com.dong.service;

import com.dong.pojo.Customer;
import com.dong.pojo.Service;
import com.dong.pojo.UseService;

import java.util.List;
import java.util.Map;

public interface ServiceService {
    List<Service> getServicesByIdCustomer(int customerId);
    public boolean addOrUpdateService(Service s) ;
    public List<Service> getServices(Map<String, String> params);
    public Service getServiceById(int id);
    boolean deleteSer(int id);
    boolean deleteUseSer(int id);
    public void save(UseService useService);
    public UseService getUseServiceByCustomerIdAndServiceId(int customerId, int serviceId);

}
