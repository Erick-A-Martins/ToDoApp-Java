package com.erick.factory;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.erick.dao.TaskDao;
import com.erick.dao.TaskDaoHibernate;

public class BeanFactory {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        return new Configuration().configure().buildSessionFactory();
    }

    public static TaskDao createTaskDao() {
        return new TaskDaoHibernate(sessionFactory);
    }
}
