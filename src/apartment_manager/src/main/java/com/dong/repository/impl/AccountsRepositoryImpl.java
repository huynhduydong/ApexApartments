package com.dong.repository.impl;

import com.dong.pojo.Accounts;
import com.dong.pojo.Customer;
import com.dong.pojo.Service;
import com.dong.pojo.UseService;
import com.dong.repository.AccountsRepository;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Objects;

@Repository
@Transactional
public class AccountsRepositoryImpl implements AccountsRepository {
    @Autowired
    private LocalSessionFactoryBean factory;
    @Override
    public List<Accounts> getAccounts() {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM Accounts ");

        return q.getResultList();
    }

    @Override
    public Accounts getAccountsById(int id) {
        {
            Session s = this.factory.getObject().getCurrentSession();
            return s.get(Accounts.class, id);
        }
    }

    @Override
    public boolean addOrUpdateAccounts(Accounts r) {
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
    public boolean deleteAccounts(int id) {
        Session session = Objects.requireNonNull(this.factory.getObject()).getCurrentSession();
        Accounts c = this.getAccountsById(id);
        try {
            session.delete(c);
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return false;
        }    }

    @Override
    public List<Accounts> getAccountsUser() {

        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<Service> q = b.createQuery(Service.class);
        Root rAcc= q.from(Accounts.class);
        Root rCus= q.from(Customer.class);
        q.select(rAcc);
        q.where(b.equal(rCus.get("accountsId"),rAcc.get("id")));
        Query query = session.createQuery(q);
        return query.getResultList();
    }

    @Override
    public Customer getAccountsByIdCustomer(int customerId) {
//        Session session = this.factory.getObject().getCurrentSession();
//        CriteriaBuilder b = session.getCriteriaBuilder();
//        CriteriaQuery<Service> q = b.createQuery(Service.class);
//        Root rA= q.from(Accounts.class);
//        Root rC= q.from(Customer.class);
//        q.select(rA);
//        q.where(b.equal(rC.get("id"), customerId));
//        q.where(b.equal(rA.get("id"), rC.get("accountsId")));
//        Query query = session.createQuery(q);
//        return query.getResultList();

        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<Customer> q = b.createQuery(Customer.class);
        Root rC= q.from(Customer.class);
        q.select(rC);
        q.where(b.equal(rC.get("id"), customerId));
        Query query = session.createQuery(q);
        return (Customer) query.getSingleResult();
    }

}
