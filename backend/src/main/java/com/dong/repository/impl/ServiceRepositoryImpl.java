package com.dong.repository.impl;

import com.dong.pojo.Customer;
import com.dong.pojo.Service;
import com.dong.pojo.UseService;
import com.dong.repository.ServiceRepository;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Objects;
@org.springframework.stereotype.Service
@Transactional
public class ServiceRepositoryImpl implements ServiceRepository {
    @Autowired
    private LocalSessionFactoryBean factory;
    @Override
    public List<Service> getServicesByIdCustomer(int customerId) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Service> query = builder.createQuery(Service.class);
        Root<Service> serviceRoot = query.from(Service.class);
        Root<UseService> useServiceRoot = query.from(UseService.class);
        query.select(serviceRoot)
                .where(
                        builder.and(
                                builder.equal(serviceRoot.get("id"), useServiceRoot.get("serviceId")),
                                builder.equal(useServiceRoot.get("customerId"), customerId)
                        )
                );

        Query q = session.createQuery(query);
        return q.getResultList();
    }
    @Override
    public boolean addOrUpdateService(Service r) {
        Session s = this.factory.getObject().getCurrentSession();
        try {
            if (r.getId() == null) {
                s.save(r);
            } else {
                s.update(r);
            }
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Service> getServices() {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM Service ");
        return q.getResultList();
    }
    @Override
    public Service getServiceById(int id) {
        Session session = Objects.requireNonNull(this.factory.getObject()).getCurrentSession();
        return session.get(Service.class, id);
    }

    @Override
    public void save(UseService useService) {
        Session s = this.factory.getObject().getCurrentSession();
        s.save(useService);
    }

    @Override
    public boolean deleteSer(int id) {
        Session session = Objects.requireNonNull(this.factory.getObject()).getCurrentSession();
        Service c = this.getServiceById(id);
        try {
            session.delete(c);
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    @Override
    public UseService getUseServiceByCustomerIdAndServiceId(int customerId, int serviceId) {
        Session session = Objects.requireNonNull(this.factory.getObject()).getCurrentSession();
        String hql = "FROM UseService u WHERE u.customerId.id = :customerId AND u.serviceId.id = :serviceId";
        Query query = session.createQuery(hql, UseService.class);
        query.setParameter("customerId", customerId);
        query.setParameter("serviceId", serviceId);
        List<UseService> results = query.getResultList();
        if (results != null && !results.isEmpty()) {
            return results.get(0);
        } else {
            return null;
        }
    }
    @Override
    public UseService getUseServiceById(int id) {
        Session session = Objects.requireNonNull(this.factory.getObject()).getCurrentSession();
        return session.get(UseService.class, id);
    }
    @Override
    public boolean deleteUseSer(int id) {
        Session session = Objects.requireNonNull(this.factory.getObject()).getCurrentSession();
        UseService c = this.getUseServiceById(id);
        try {
            session.delete(c);
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return false;
        }
        }



}
